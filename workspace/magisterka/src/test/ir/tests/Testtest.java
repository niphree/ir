package test.ir.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;


public class Testtest {
	
	@Test
	public void split_string(){
		String tag = "a.a_ad!d-db-b    a#b  a%v a@d  a&f";
		
		List<String> pre_tags = new ArrayList<String>();
			
			String[] tmp = tag.split("\\s+");
			for (String t: tmp){
				String[] tmp2 =   t.split("[_!@&%#\\.\\|/\\-]");
				pre_tags.addAll(Arrays.asList(tmp2));
			}
			Set<String> tmp_s = new HashSet<String>();
			tmp_s.addAll(pre_tags);
			pre_tags.clear();
			pre_tags.addAll(tmp_s);
		
		System.out.println(pre_tags);
	}
/*
	private class Testing {
		public String a;
		public long l;
		
		public Testing(String a, long l){
			this.a = a;
			this.l = l;
		}
		
		@Override
		public String toString() {
			return "a: " + a + " l:" + l;
		}

		
		

	}
	
	
	@Test
	public void test_collection(){
		LinkedHashMultiset<Testing> set = LinkedHashMultiset.create();
		set.add(new Testing("a," , 2));
		set.add(new Testing("a," , 2));
		set.add(new Testing("a," , 2));
		
		System.out.println(set.contains(new Testing("a," , 2)));
		System.out.println(set);
	}
	
	@Test
	public void test_db(){
		 Session s = HibernateUtil.getSession();
	}
	*/
}
