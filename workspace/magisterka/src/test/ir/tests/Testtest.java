package test.ir.tests;

import ir.hibernate.HibernateUtil;

import org.hibernate.Session;
import org.junit.Test;

import com.google.common.collect.LinkedHashMultiset;

public class Testtest {

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
	
}
