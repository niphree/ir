package test.ir.rank.adaptedpagerank;

import ir.rank.adaptedpagerank.AdaptedPageRank;
import ir.rank.adaptedpagerank.model.DocUserTagMatrixSource;
import ir.rank.common.MatrixVectorMultiplier;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import cern.colt.matrix.DoubleFactory1D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;

public class TestAdaptedPageRank {
	
	@Test
	public void test_run() throws SQLException, ClassNotFoundException{
		AdaptedPageRank page_rank = new AdaptedPageRank(0.35, 0.65, 0);
		page_rank.init_maxes();
		page_rank.calc_rank();
	}
	
	//@Test
	public void test2(){
		int[] b1 = {1,1};
		int[] b2 = {2,2};
		int[] b3 = {3,4};
		int[] b4 = {3,1};
		int[] b5 = {2,2};
		int[] b6 = {1,3};
		int[] b7 = {1,4};
		
		
		Object[] a1 = {b1, b2};
		Object[] a2 = {b3};
		Object[] a3 = {b4, b5, b6};
		Object[] a4 = {};
		Object[] a5 = {b7};
		
		Object a11[] = {1, a1};
		Object a12[] = {2, a2};
		Object a13[] = {3, a3};
		Object a14[] = {4, a4};
		Object a15[] = {5, a5};
			
		List<Object[]> tmp = new ArrayList<Object[]>(Arrays.asList(a11, a12, a13, a14, a15));
		
		DocUserTagMatrixSource d_u = new DocUserTagMatrixSource(5, 5, 5);
		
		d_u.list_hash_matrix=tmp;
		
		double[] v = {1,1,1};
		DenseDoubleMatrix1D vector = new DenseDoubleMatrix1D(v);
		vector = (DenseDoubleMatrix1D)DoubleFactory1D.dense.make(15, 1);
		System.out.println(Arrays.toString(vector.toArray()));
		DenseDoubleMatrix1D r = MatrixVectorMultiplier.multiple(vector, d_u, false);
		
		System.out.println(r.toString());
		System.out.println(Arrays.toString(r.toArray()));
		
		//Object[] a = {1; {0}};
		
		
		//MatrixVectorMultiplier.multiple(null, d_u, false);
	}
}
