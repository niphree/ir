package ir.rank.socialpagerank;

import ir.database.DocumentTable;
import ir.hibernate.HibernateUtil;
import ir.rank.common.MatrixVectorMultiplier;
import ir.rank.socialpagerank.model.DocumentUserMatrixSource;
import ir.rank.socialpagerank.model.TagsDocumentsMatrixSource;
import ir.rank.socialpagerank.model.UsersTagsMatrixSource;

import java.util.Arrays;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import cern.colt.matrix.DoubleFactory1D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import cern.colt.matrix.linalg.Algebra;

public class SocialPageRank {
	
	public static double MIN_DIFF = 0.1;
	public int doc_max;
	public int tag_max;
	public int usr_max;
	
	@SuppressWarnings("unchecked")
	public void init_maxes(){
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		String sql1 = "select max(id) from TagTable";
		String sql2 = "select max(id) from UserTable";
		String sql3 = "select max(id) from DocumentTable";
		
		List<Long> tags = session.createQuery(sql1).list();
		tag_max = tags.get(0).intValue();
				
		List<Long> users = session.createQuery(sql2).list();
		usr_max = users.get(0).intValue();
		
		List<Long> docs = session.createQuery(sql3).list();
		doc_max = docs.get(0).intValue();
		
		tx.commit();
		session.close();
	}
	
	DenseDoubleMatrix1D getRandomVector(){
		DenseDoubleMatrix1D po = (DenseDoubleMatrix1D)DoubleFactory1D.dense.random(385546);
		return po;
	}
	public void init_calc_rank(){
		/*
		MatrixVectorMultiplier.read_matrix(new DocumentUserMatrixSource(doc_max, usr_max), true);
		MatrixVectorMultiplier.read_matrix(new DocumentUserMatrixSource(doc_max, usr_max), false);
		
		MatrixVectorMultiplier.read_matrix(new TagsDocumentsMatrixSource(tag_max, doc_max), true);
		MatrixVectorMultiplier.read_matrix(new TagsDocumentsMatrixSource(tag_max, doc_max), false);
		
		MatrixVectorMultiplier.read_matrix(new UsersTagsMatrixSource(usr_max, tag_max), true);
		MatrixVectorMultiplier.read_matrix(new UsersTagsMatrixSource(usr_max, tag_max), false);
		*/
	}
	public DenseDoubleMatrix1D norm_vector_copy(DenseDoubleMatrix1D vector){
		DenseDoubleMatrix1D norm_vector = new DenseDoubleMatrix1D(vector.size());
		Algebra alg = new Algebra();
		double norm = Math.sqrt(alg.norm2(vector));
		for (int i=0; i< vector.size(); i++){
			norm_vector.set(i, vector.get(i)/norm);
		}
		
		return norm_vector;
		
	}
	
	public void calcRank(){
		System.out.println("calculating social page rank");
		
		DenseDoubleMatrix1D po = (DenseDoubleMatrix1D)DoubleFactory1D.dense.random(doc_max);
		DenseDoubleMatrix1D prev_norm = norm_vector_copy(po);
		System.out.println("po len: " + po.size());
		
		boolean end = false;
		long i = 0;
		while(!end){
			System.out.println("social page rank iter: " + i);
			
			//DenseDoubleMatrix1D temp = (DenseDoubleMatrix1D)po.copy();
			//System.out.println("temp len: " + temp.size());
			
			
			DenseDoubleMatrix1D users1 = MatrixVectorMultiplier.multiple(po, 
					new DocumentUserMatrixSource(doc_max, usr_max), true);
			System.out.println("users1t len: " + users1.size());		
			
			DenseDoubleMatrix1D tags1 = MatrixVectorMultiplier.multiple(users1, 
					new UsersTagsMatrixSource(usr_max, tag_max), true);
			System.out.println("tags1t len: " + tags1.size());	
			
			DenseDoubleMatrix1D docs1 = MatrixVectorMultiplier.multiple(tags1, 
					new TagsDocumentsMatrixSource(tag_max, doc_max), true);
			System.out.println("docs1t len: " + docs1.size());	
			
			tags1 = MatrixVectorMultiplier.multiple(docs1, 
					new TagsDocumentsMatrixSource(usr_max, doc_max), false);
			System.out.println("tags1 len: " + tags1.size());	
			
			users1= MatrixVectorMultiplier.multiple(tags1, 
					new UsersTagsMatrixSource(usr_max, tag_max), false);
			System.out.println("users1 len: " + users1.size());	
			
			po = MatrixVectorMultiplier.multiple(users1, 
					new DocumentUserMatrixSource(doc_max, usr_max), false);
			System.out.println("docs1 len: " + po.size());
			
			po = norm_vector_copy(po);

			if (po.equals(prev_norm)){
				prev_norm = po;
				end = true;
				System.out.println("END!");
			}
			double[] diff = calc_diff(po, prev_norm);
			prev_norm = po;
			
			//end = true;
			System.out.println(Arrays.toString(diff));
			i++;
			save_to_db(prev_norm);
		}
		
	}

	public void save_to_db(DenseDoubleMatrix1D vector){
		System.out.println("save to db:");
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		for (int id=0; id<vector.size(); id++){
			DocumentTable doc = (DocumentTable)session.get(DocumentTable.class, Long.valueOf(id+1));
			if (doc != null){
				doc.set_social_page_rank(vector.get(id));
				session.update(doc);
			}
		}
		tx.commit();
		session.close();
	}
	
	
	
	public double[] calc_diff(DenseDoubleMatrix1D v1, DenseDoubleMatrix1D v2){
		double min = Integer.MAX_VALUE;
		double max = 0;
		int len = v1.size();
		System.out.println("v1 " + v1.size());
		System.out.println("v2 " + v2.size());
		double temp = 0;
		for (int i=0; i<len; i++){
			temp = Math.abs(v1.get(i) - v2.get(i));
			if (temp<min)
				min = temp;
			if (temp>max)
				max = temp;
		}
		double[] ret= {min, max};
		return ret;
	}
	
}
