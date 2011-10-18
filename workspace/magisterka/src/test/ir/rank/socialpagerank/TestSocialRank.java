package test.ir.rank.socialpagerank;

import ir.rank.socialpagerank.SocialPageRank;

import org.junit.Test;

public class TestSocialRank {

	@Test
	public void test(){
		SocialPageRank spr = new SocialPageRank();
		spr.calcRank();
	}
}
