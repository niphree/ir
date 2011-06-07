package test.ir.utils;

import ir.database.DocumentTable;
import ir.database.TagTable;
import ir.database.UserTable;
import ir.database.UserTagDocTable;
import ir.hibernate.HibernateUtil;

import java.util.List;

import junit.framework.TestCase;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class HibernateTestUtils {

	/*
	 * STATICs ARE EVIL,
	 * FOR TEST USE ONLY!!
	 * 
	 * ALSO UTILS CLASS ARE EVIL... OH WELL... NVRMND...
	 */
	public static void cleanUp() {
		Session session = HibernateUtil.getSession();
		
		session.close();
		session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		deleteObjects(UserTable.class, session);
		deleteObjects(DocumentTable.class, session);
		deleteObjects(TagTable.class, session);
		
		deleteObjects(UserTagDocTable.class, session);

		tx.commit();

		session.close();
		session = HibernateUtil.getSession();

		tx = session.beginTransaction();
		
		checkObjects(DocumentTable.class, session);
		checkObjects(TagTable.class, session);
		checkObjects(UserTable.class, session);
		checkObjects(UserTagDocTable.class, session);
		tx.commit();
		session.close();
	}
	
	private static void checkObjects(Class<?> clazz, Session session){
		@SuppressWarnings("unchecked")
		List<Object> objects = session.createQuery("from " + clazz.getName()).list();
		TestCase.assertEquals("LIST NOT EMPTY AFTER CLEANUP!", objects.size(), 0);
	}
	
	private static void deleteObjects(Class<?> clazz, Session session){
		@SuppressWarnings("unchecked")
		List<Object> objects = session.createQuery("from " + clazz.getName()).list();
		
		for (Object ob : objects) {
			session.delete(ob);
		}
	}
}
