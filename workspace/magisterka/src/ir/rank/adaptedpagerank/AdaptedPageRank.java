package ir.rank.adaptedpagerank;

import ir.database.DocumentTable;
import ir.database.TagTable;
import ir.database.UserTable;
import ir.hibernate.HibernateUtil;
import ir.rank.adaptedpagerank.model.DocUserTagMatrixSource;
import ir.rank.common.MatrixVectorMultiplier;

import java.util.Arrays;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import cern.colt.matrix.DoubleFactory1D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import cern.colt.matrix.linalg.Algebra;
import cern.colt.matrix.linalg.Blas;
import cern.colt.matrix.linalg.SmpBlas;

public class AdaptedPageRank {

	public int doc_max;
	public int tag_max;
	public int usr_max;

	public double alpha;
	public double beta;
	public double gamma;
	
	public AdaptedPageRank(double alpha, double beta, double gamma){
		this.alpha = alpha;
		this.beta = beta;
		this.gamma = gamma;
	}
	
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

	public DenseDoubleMatrix1D norm_vector_copy(DenseDoubleMatrix1D vector,
			DenseDoubleMatrix1D vector_prev, long iter){
		DenseDoubleMatrix1D norm_vector = new DenseDoubleMatrix1D(vector.size());
		Algebra alg = new Algebra();
		double norm = Math.sqrt(alg.norm2(vector));
		for (int i=0; i< vector.size(); i++){
			double d = (vector.get(i)*100)/norm;
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

	public void calc_rank(){
		System.out.println("Calculating adapted page rank");

		DenseDoubleMatrix1D po = (DenseDoubleMatrix1D)DoubleFactory1D.dense.random(doc_max + usr_max + tag_max);
		DenseDoubleMatrix1D prev_norm = norm_vector_copy(po, po, 0);
		
		
		System.out.println("po len: " + po.size());
		Blas blas = SmpBlas.smpBlas;
		boolean end = false;
		long i = 0;
		while(!end){
			System.gc();
			DenseDoubleMatrix1D alpha_po1 = (DenseDoubleMatrix1D) po.copy();
			DenseDoubleMatrix1D gamma_po3 = (DenseDoubleMatrix1D) po.copy();
			blas.dscal(alpha, alpha_po1);
			blas.daxpy(gamma, gamma_po3, alpha_po1); //wynik w alpha_po1
			System.gc();
			DenseDoubleMatrix1D beta_po2 = MatrixVectorMultiplier.multiple(po, 
					new DocUserTagMatrixSource(doc_max, usr_max, tag_max), true);
			System.gc();
			blas.daxpy(beta, beta_po2, alpha_po1);  //wynik w alpha_po1
			
			po = norm_vector_copy(alpha_po1, po, i);
			System.out.println("equals?");
			if (po.equals(prev_norm)){
				prev_norm = po;
				end = true;
				System.out.println("END!");
			}
			double[] diff = calc_diff(po, prev_norm);
			prev_norm = po;
			System.gc();

			//end = true;
			System.out.println(Arrays.toString(diff));
			i++;
			save_to_db(prev_norm);
			System.gc();

		}

	}
	public void save_to_db(DenseDoubleMatrix1D vector){
		System.out.println("save to db:");
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		for (int id=0; id<vector.size(); id++){
			if (id<doc_max){
				DocumentTable obj = (DocumentTable)session.get(DocumentTable.class, Long.valueOf(id+1));
				if (obj != null){
					obj.set_adapted_page_rank(vector.get(id));
					session.update(obj);
				}
			}
			if (id>=doc_max  && id < doc_max + usr_max){
				UserTable obj = (UserTable)session.get(UserTable.class, Long.valueOf(id+1));
				if (obj != null){
					obj.set_adapted_page_rank(vector.get(id));
					session.update(obj);
				}
			}
			if (id>=doc_max + usr_max  && id < doc_max+ usr_max+tag_max){
				TagTable obj = (TagTable)session.get(TagTable.class, Long.valueOf(id+1));
				if (obj != null){
					obj.set_adapted_page_rank(vector.get(id));
					session.update(obj);
				}
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
