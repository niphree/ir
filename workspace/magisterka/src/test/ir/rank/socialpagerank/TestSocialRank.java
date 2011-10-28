package test.ir.rank.socialpagerank;

import ir.rank.common.MatrixVectorMultiplier;
import ir.rank.socialpagerank.SocialPageRank;
import ir.rank.socialpagerank.model.DocumentUserMatrixSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import cern.colt.matrix.DoubleFactory1D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;

public class TestSocialRank {

	//@Test
	public void test(){
		SocialPageRank spr = new SocialPageRank();
		spr.init_maxes();
		spr.init_calc_rank();
	}
	
	//@Test
	public void test2(){
		
		int[] a1 = {1, 2};
		int[] a2 = {3};
		int[] a3 = {3, 2, 1};
		int[] a4 = {};
		int[] a5 = {1};
		
		Object a11[] = {1, a1};
		Object a12[] = {2, a2};
		Object a13[] = {3, a3};
		Object a14[] = {4, a4};
		Object a15[] = {5, a5};
			
		List<Object[]> tmp = new ArrayList<Object[]>(Arrays.asList(a11, a12, a13, a14, a15));
		DocumentUserMatrixSource d_u = new DocumentUserMatrixSource(5, 3);
		d_u.max_interval = 2;
		d_u.list_hash_matrix=tmp;
		
		double[] v = {1,1,1};
		DenseDoubleMatrix1D vector = new DenseDoubleMatrix1D(v);
		vector = (DenseDoubleMatrix1D)DoubleFactory1D.dense.random(3);
		System.out.println(Arrays.toString(vector.toArray()));
		DenseDoubleMatrix1D r = MatrixVectorMultiplier.multiple(vector, d_u, false);
		
		System.out.println(r.toString());
		System.out.println(Arrays.toString(r.toArray()));
		
		//Object[] a = {1; {0}};
		
		
		//MatrixVectorMultiplier.multiple(null, d_u, false);
	}
	
	
	@Test
	public void test3(){
		SocialPageRank rank = new SocialPageRank();
		rank.init_maxes();
		System.out.println(rank.tag_max);
		System.out.println(rank.doc_max);
		System.out.println(rank.usr_max);
		
		rank.calcRank();
		
	
	}
}
