package ir.rank.socialpagerank;

import ir.connector.ConnectorFactory;
import ir.rank.common.MatrixVectorMultiplier;
import ir.rank.socialpagerank.model.DocumentUserMatrixSource;
import ir.rank.socialpagerank.model.TagsDocumentsMatrixSource;
import ir.rank.socialpagerank.model.UsersTagsMatrixSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

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
		try {
		//Session session = HibernateUtil.getSession();
		//Transaction tx = session.beginTransaction();
		String sql1 = "select max(new_id) as id from Tag";
		String sql2 = "select max(new_id) as id from User";
		String sql3 = "select max(new_id) as id from Document";
		
		ConnectorFactory cf = ConnectorFactory.instance();
		
		ResultSet rs = cf.execute(sql1);
		rs.next();
		tag_max = rs.getInt(1);
		
		rs = cf.execute(sql2);
		rs.next();
		usr_max = rs.getInt(1);
		
		rs = cf.execute(sql3);
		rs.next();
		doc_max = rs.getInt(1);
		
		cf.close();
		

		
		System.out.println(tag_max);
		System.out.println(usr_max);
		System.out.println(doc_max);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	DenseDoubleMatrix1D getRandomVector(){
		DenseDoubleMatrix1D po = (DenseDoubleMatrix1D)DoubleFactory1D.dense.random(385546);
		return po;
	}
	public void init_calc_rank() throws SQLException, ClassNotFoundException{
		System.out.println("init calc rank");
		MatrixVectorMultiplier.read_matrix(new DocumentUserMatrixSource(doc_max, usr_max), true);
		MatrixVectorMultiplier.read_matrix(new DocumentUserMatrixSource(doc_max, usr_max), false);
	
		MatrixVectorMultiplier.read_matrix(new TagsDocumentsMatrixSource(tag_max, doc_max), true);
		MatrixVectorMultiplier.read_matrix(new TagsDocumentsMatrixSource(tag_max, doc_max), false);
		
		MatrixVectorMultiplier.read_matrix(new UsersTagsMatrixSource(usr_max, tag_max), true);
		MatrixVectorMultiplier.read_matrix(new UsersTagsMatrixSource(usr_max, tag_max), false);
		
	}
	public DenseDoubleMatrix1D norm_vector_copy(DenseDoubleMatrix1D vector, 
			DenseDoubleMatrix1D vector_prev, long iter){
		DenseDoubleMatrix1D norm_vector = new DenseDoubleMatrix1D(vector.size());
		Algebra alg = new Algebra();
		double norm = Math.sqrt(alg.norm2(vector));
		for (int i=0; i< vector.size(); i++){
			double d = (vector.get(i)*1000)/norm;
			if (iter !=0 ){
				if (d == 0 && vector_prev.get(i) != 0)
					norm_vector.set(i, vector_prev.getQuick(i));
				else
					norm_vector.set(i, d);
			}
			else
				norm_vector.set(i, d);
		}
		
		return norm_vector;
		
	}
	
	public void calcRank(){
		System.out.println("calculating social page rank");
		
		DenseDoubleMatrix1D po = (DenseDoubleMatrix1D)DoubleFactory1D.dense.random(doc_max);
		DenseDoubleMatrix1D prev_norm = norm_vector_copy(po, po, 0);
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
			System.gc();
			//System.out.println(users1);
			DenseDoubleMatrix1D tags1 = MatrixVectorMultiplier.multiple(users1, 
					new UsersTagsMatrixSource(usr_max, tag_max), true);
			System.out.println("tags1t len: " + tags1.size());
			
			//System.out.println(tags1);
			users1 = null;
			System.gc();
			
			DenseDoubleMatrix1D docs1 = MatrixVectorMultiplier.multiple(tags1, 
					new TagsDocumentsMatrixSource(tag_max, doc_max), true);
			System.out.println("docs1t len: " + docs1.size());	
			tags1 = null;
			System.gc();
		//	System.out.println(docs1);
			//DenseDoubleMatrix1D docs1 = (DenseDoubleMatrix1D)DoubleFactory1D.dense.random(doc_max);
			tags1 = MatrixVectorMultiplier.multiple(docs1, 
					new TagsDocumentsMatrixSource(tag_max, doc_max), false);
			System.out.println("tags1 len: " + tags1.size());	
			docs1 = null;
			System.gc();
			
			users1= MatrixVectorMultiplier.multiple(tags1, 
					new UsersTagsMatrixSource(usr_max, tag_max), false);
			System.out.println("users1 len: " + users1.size());	
			tags1 = null;
			System.gc();
			
			po = MatrixVectorMultiplier.multiple(users1, 
					new DocumentUserMatrixSource(doc_max, usr_max), false);
			System.out.println("docs1 len: " + po.size());
			users1 = null;
			System.gc();
			
			//System.out.println(po);
			
			po = norm_vector_copy(po, prev_norm, i);
			//System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!");
			//System.out.println(po);
			//System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!");
			if (po.equals(prev_norm)){
				prev_norm = po;
				end = true;
				System.out.println("END!");
			}
			double[] diff = calc_diff(po, prev_norm);
			prev_norm = po;
			
			//end = true;
			System.out.println(Arrays.toString(diff));
			if (diff[1] < Math.pow(10, -10)){
				end = true;
				System.out.println("END! - 10^10");
			}
			i++;
			save_to_db(prev_norm);
			System.gc();
		}
		
	}

	public void save_to_db(DenseDoubleMatrix1D vector){
	/*	System.out.println("save to db:");
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		for (int id=0; id<vector.size(); id++){
			
			int id2= id+1;
			SQLQuery sqlQuery = session.createSQLQuery("update document set social_page_rank="+vector.get(id)+" where new_id="+id2+"; ");
			//System.out.println(vector.get(id));
			//System.out.println(id+1);
			sqlQuery.executeUpdate();
		}
		tx.commit();
		session.close();
		*/
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
