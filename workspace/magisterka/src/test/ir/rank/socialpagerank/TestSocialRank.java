package test.ir.rank.socialpagerank;

import ir.rank.socialpagerank.SocialPageRank;
import ir.rank.socialpagerank.model.DocumentUserMatrixSource;
import ir.rank.socialpagerank.model.TagsDocumentsMatrixSource;
import ir.rank.socialpagerank.model.UsersTagsMatrixSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cern.colt.matrix.impl.DenseDoubleMatrix2D;

public class TestSocialRank {

	//@Test
	public void test() throws SQLException, ClassNotFoundException{
		SocialPageRank spr = new SocialPageRank();
		spr.init_maxes();
		//spr.tag_max = 1444191;
		//spr.usr_max = 673675;
		//spr.doc_max = 1087528;
		spr.init_calc_rank();
	}
	
	//@Test
	public void test_fix_files(){
		String name1 = (TagsDocumentsMatrixSource.get_static_name())+"_t_" + 1 + ".out";
		String name2 = (TagsDocumentsMatrixSource.get_static_name()) + 1 + ".out";
		String name3 = (UsersTagsMatrixSource.get_static_name())+"_t_" + 2 + ".out";
		String name4 = (UsersTagsMatrixSource.get_static_name()) + 1 + ".out";
		//List<Object[]> list_hash_matrix = (List<Object[]>) = FileUtils.open_file(name1);
	}
	
	//@Test
	public void test2(){
		
		
		Object[][] a1 = {{1,1}, {2,2}};
		Object[][] a2 = {{3,4}};
		Object[][] a3 = {{3,1}, {2,2}, {1,3}};
		Object[][] a4 = {};
		Object[][] a5 = {{1,4}};
		
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
		DenseDoubleMatrix2D vector = new DenseDoubleMatrix2D(3,3);
		vector.set(1, 1, 3);
		vector.set(3, 3, 3);
		//Object[] a = {1; {0}};
		
		
		//MatrixVectorMultiplier.multiple(null, d_u, false);
	}
	
	
	//@Test
	public void test3() throws SQLException, ClassNotFoundException{
		/*System.out.println(Double.MIN_VALUE);
		System.out.println(Double.MAX_EXPONENT);
		System.out.println(Double.MIN_EXPONENT);
		System.out.println(Double.SIZE);
		*/
		SocialPageRank rank = new SocialPageRank();
		rank.init_maxes();
		System.out.println(rank.tag_max);
		System.out.println(rank.doc_max);
		System.out.println(rank.usr_max);
		
		//rank.init_calc_rank();
		rank.calcRank();
		
	
	}
}
