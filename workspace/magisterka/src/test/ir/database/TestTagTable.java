package test.ir.database;

import ir.database.TagTable;
import ir.hibernate.HibernateUtil;

import java.util.List;

import junit.framework.TestCase;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import test.ir.utils.HibernateTestUtils;

public class TestTagTable extends TestCase{

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
	public void testEmptyTags(){
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();

		List<TagTable> tags = session.createQuery("from TagTable m").list();
		assertEquals("List should be empty", 0, tags.size());
		tx.commit();
		session.close();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testTagAdding(){
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		TagTable tag = new TagTable("Test_tag");
		session.save(tag);
		
		tx.commit();
		session.close();
		session = HibernateUtil.getSession();
		tx = session.beginTransaction();
		
		List<TagTable> tags = session.createQuery("from TagTable m").list();
		
		assertEquals("List should be empty", 1, tags.size());
		assertEquals(tags.get(0).getTag(), "Test_tag");
		
		session.close();
	}
}
