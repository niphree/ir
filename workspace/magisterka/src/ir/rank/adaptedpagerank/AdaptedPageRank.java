package ir.rank.adaptedpagerank;

import ir.connector.ConnectorFactory;
import ir.rank.adaptedpagerank.model.DocUserTagMatrixSource;
import ir.rank.common.MatrixVectorMultiplier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

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
	public void init_maxes() throws SQLException, ClassNotFoundException{
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

	public DenseDoubleMatrix1D norm_vector_copy(DenseDoubleMatrix1D vector,
			DenseDoubleMatrix1D vector_prev, long iter){
		DenseDoubleMatrix1D norm_vector = new DenseDoubleMatrix1D(vector.size());
		Algebra alg = new Algebra();
		double norm = vector.zSum(); // Math.sqrt(alg.norm2(vector));
		for (int i=0; i< vector.size(); i++){
			//double d = (vector.get(i)*1000)/norm;
			double d = vector.get(i);
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
			System.out.println("adapted page rank iter: " + i);
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
		//	System.out.println("equals?");
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
		/*System.out.println("save to db:");
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		for (int id=0; id<vector.size(); id++){
			if (id<doc_max){
			//	System.out.println("update document set adapted_page_rank="+vector.get(id)+" where new_id="+(id+1)+"; ");
				SQLQuery sqlQuery = session.createSQLQuery("update document set adapted_page_rank="+vector.get(id)+" where new_id="+(id+1)+"; ");
				sqlQuery.executeUpdate();
			}
			if (id>=doc_max  && id < doc_max + usr_max){
				SQLQuery sqlQuery = session.createSQLQuery("update user set adapted_page_rank="+vector.get(id)+" where new_id="+(id+1 - doc_max)+"; ");
				sqlQuery.executeUpdate();
			}
			if (id>=doc_max + usr_max  && id < doc_max+ usr_max+tag_max){
				SQLQuery sqlQuery = session.createSQLQuery("update tag set adapted_page_rank="+vector.get(id)+" where new_id="+(id+1 - (doc_max + usr_max))+"; ");
				sqlQuery.executeUpdate();

			}
			if ((id%100) == 0){
				tx.commit();
				tx = session.beginTransaction();
			}
		}
		try{
			tx.commit();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		session.close();
		*/
	}



	public double[] calc_diff(DenseDoubleMatrix1D v1, DenseDoubleMatrix1D v2){
		double min = Integer.MAX_VALUE;
		double max = 0;
		int len = v1.size();
	//	System.out.println("v1 " + v1.size());
	//	System.out.println("v2 " + v2.size());
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
