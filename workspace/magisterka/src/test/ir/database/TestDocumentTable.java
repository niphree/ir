package test.ir.database;

import ir.database.DocumentTable;
import ir.database.TagTable;
import ir.database.UserTable;
import ir.hibernate.HibernateUtil;

import java.util.List;

import junit.framework.TestCase;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import test.ir.utils.HibernateTestUtils;

public class TestDocumentTable extends TestCase{

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
	public void testEmptyDocumentsSelecting(){
		
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();

		List<DocumentTable> docs = session.createQuery("from DocumentTable m").list();
		assertEquals("List should be empty", 0, docs.size());
		tx.commit();
		session.close();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testAddingDoc(){
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		DocumentTable doc = new DocumentTable();
		session.save(doc);
		
		tx.commit();
		session.close();
		session = HibernateUtil.getSession();
		tx = session.beginTransaction();
		
		List<DocumentTable> docs = session.createQuery("from DocumentTable m").list();
		
		assertEquals("List should be empty", 1, docs.size());
		
		session.close();
	}
	
	@Test
	public void testTagUserConnection(){
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		UserTable usr1 = new UserTable("Test_user");
		UserTable usr2 = new UserTable("Test_user2");
		UserTable usr3 = new UserTable("Test_user3");
		
		
		TagTable tag1 = new TagTable("tag1");
		TagTable tag2 = new TagTable("tag2");
		TagTable tag3 = new TagTable("tag3");
		TagTable tag4 = new TagTable("tag4");
		TagTable tag5 = new TagTable("tag5");
		
		DocumentTable doc1 = new DocumentTable();
		DocumentTable doc2 = new DocumentTable();
		DocumentTable doc3 = new DocumentTable();
		
		session.save(usr1);
		session.save(usr2);
		session.save(usr3);
		
		session.save(tag1);
		session.save(tag2);
		session.save(tag3);
		session.save(tag4);
		session.save(tag5);

		
		session.save(doc1);
		session.save(doc2);
		session.save(doc3);
		
		Long id1 = doc1.getId();
		Long id2 = doc2.getId();
		Long id3 = doc3.getId();
		
		tx.commit();
		session.close();
		session = HibernateUtil.getSession();
		tx = session.beginTransaction();
		
		doc1 = (DocumentTable)session.get(DocumentTable.class, id1);
		doc2 = (DocumentTable)session.get(DocumentTable.class, id2);
		doc3 = (DocumentTable)session.get(DocumentTable.class, id3);
		

		
		tx.commit();
		session.close();
		
	}
	
	
}
