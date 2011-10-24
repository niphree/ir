package test.ir.rank.socialpagerank;

import ir.rank.socialpagerank.MatrixVectorMultiplier;
import ir.rank.socialpagerank.SocialPageRank;
import ir.rank.socialpagerank.model.DocumentUserMatrixSource;

import org.junit.Test;

public class TestSocialRank {

	@Test
	public void test(){
		SocialPageRank spr = new SocialPageRank();
		//spr.calcRank();
		spr.init_calc_rank();
	}
	
	//@Test
	public void test2(){
		DocumentUserMatrixSource d_u = new DocumentUserMatrixSource(5, 3);
		d_u.max_interval = 2;
		int[] a11 = {1,2};
		int[] a21 = {3,4};
		//Object[] a = {1; {0}};
		
		
		MatrixVectorMultiplier.multiple(null, d_u, false);
	}
}
