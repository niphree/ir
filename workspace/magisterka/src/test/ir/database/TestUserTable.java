package test.ir.database;

import ir.database.TagTable;
import ir.database.UserTable;
import ir.hibernate.HibernateUtil;

import java.util.List;

import junit.framework.TestCase;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import test.ir.utils.HibernateTestUtils;

public class TestUserTable extends TestCase{

	@Override
	protected void setUp() throws Exception {
		HibernateTestUtils.cleanUp();
		super.setUp();
	}
	
	@Override
	protected void tearDown() throws Exception {
		HibernateTestUtils.cleanUp();
		super.tearDown();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testEmptyUsers(){
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();

		List<UserTable> user = session.createQuery("from UserTable m").list();
		assertEquals("List should be empty", 0, user.size());
		tx.commit();
		session.close();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testUserAdding(){
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		UserTable usr = new UserTable("Test_user");
		session.save(usr);
		
		tx.commit();
		session.close();
		session = HibernateUtil.getSession();
		tx = session.beginTransaction();
		
		List<UserTable> users = session.createQuery("from UserTable m").list();
		
		assertEquals(1, users.size());
		assertEquals(users.get(0).getName(), "Test_user");
		
		session.close();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testUserTagAdding(){
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		UserTable usr1 = new UserTable("Test_user");
		session.save(usr1);
		UserTable usr2 = new UserTable("Test_user2");
		session.save(usr2);
		UserTable usr3 = new UserTable("Test_user3");
		session.save(usr3);
		
		TagTable tag1 = new TagTable("tag1");
		TagTable tag2 = new TagTable("tag2");
		TagTable tag3 = new TagTable("tag3");
		TagTable tag4 = new TagTable("tag4");
		TagTable tag5 = new TagTable("tag5");
		
		session.save(tag1);
		session.save(tag2);
		session.save(tag3);
		session.save(tag4);
		session.save(tag5);
		
		Long tg_id = tag5.getId();

		session.save(usr1);
		session.save(usr2);

		
		tx.commit();
		session.close();
		session = HibernateUtil.getSession();
		tx = session.beginTransaction();
		
		List<UserTable> users = session.createQuery("from UserTable m").list();
		assertEquals(3, users.size());
		
		List<TagTable> tags = session.createQuery("from TagTable m").list();
		assertEquals(5, tags.size());
		
				
		tx.commit();
		session.close();
		session = HibernateUtil.getSession();
		tx = session.beginTransaction();
		// ADDINGS tags again
		
		tag5 = (TagTable)session.get(TagTable.class, tg_id);
		

		
		tx.commit();
		session.close();
		session = HibernateUtil.getSession();
		tx = session.beginTransaction();
	

		tags = session.createQuery("from TagTable m").list();
		assertEquals(5, tags.size());
		
		tx.commit();
		session.close();
		
	}
}
