package test.ir.database;

import ir.database.DocumentTable;
import ir.database.TagTable;
import ir.database.UserTable;
import ir.database.UserTagDocTable;
import ir.hibernate.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import test.ir.utils.HibernateTestUtils;

public class TestDocTagUsr extends TestCase{

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
	@Test
	public void testMe2(){
		// test if adding user - list(tag...) - doc is unique
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		
		
		tx.commit();
		session.close();
	}
	
	
	@Test
	public void testMe(){
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		
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
		
		UserTable user1 = new UserTable("u1");
		UserTable user2 = new UserTable("u2");
		UserTable user3 = new UserTable("u3");
		
		session.save(user1);
		session.save(user2);
		session.save(user3);
		
		DocumentTable doc1 = new DocumentTable();
		DocumentTable doc2 = new DocumentTable();
		
		session.save(doc1);
		session.save(doc2);
		
		List<TagTable> tags1 = new ArrayList<TagTable>();
		tags1.add(tag1);
		tags1.add(tag2);
		tags1.add(tag3);
		
		List<TagTable> tags2 = new ArrayList<TagTable>();
		tags2.add(tag2);
		tags2.add(tag3);
	
		List<TagTable> tags3 = new ArrayList<TagTable>();
		tags3.add(tag4);
		
		List<TagTable> tags4 = new ArrayList<TagTable>();
		tags4.add(tag1);
		tags4.add(tag5);
		
		UserTagDocTable utd1 = new UserTagDocTable();
		UserTagDocTable utd2 = new UserTagDocTable();
		UserTagDocTable utd3 = new UserTagDocTable();
		UserTagDocTable utd4 = new UserTagDocTable();
		
		utd1.setDoc(doc1);
		utd1.setUser(user1);
		utd1.setTags(tags1);
		
		utd2.setDoc(doc1);
		utd2.setUser(user2);
		utd2.setTags(tags2);
		
		utd3.setDoc(doc1);
		utd3.setUser(user3);
		utd3.setTags(tags3);
		
		utd4.setDoc(doc2);
		utd4.setUser(user1);
		utd4.setTags(tags4);
		
		session.save(utd1);
		session.save(utd2);
		session.save(utd3);
		session.save(utd4);
		
		Long u_id1 = user1.getId();
		Long u_id2 = user2.getId();
		Long u_id3 = user3.getId();
		
		Long d_id1 = doc1.getId();
		Long d_id2 = doc2.getId();
		
		
		tx.commit();
		session.close();
		
		session = HibernateUtil.getSession();
		tx = session.beginTransaction();
		
		List<DocumentTable> docs = session.createQuery("from DocumentTable m").list();
		assertEquals(2, docs.size());
		List<UserTable> users = session.createQuery("from UserTable m").list();
		assertEquals(3, users.size());
		
		DocumentTable d1 = (DocumentTable)session.get(DocumentTable.class, d_id1);
		List<UserTagDocTable> utd_list_1 = d1.getTags_document_list();
		
		DocumentTable d2 = (DocumentTable)session.get(DocumentTable.class, d_id2);
		List<UserTagDocTable> utd_list_2 = d2.getTags_document_list();
		//System.out.println(utd_1);
		
		assertEquals(3, utd_list_1.size());
		
		for (UserTagDocTable utd : utd_list_1){
		//	System.out.println(utd.getUser().getName());
			if (utd.getUser().getName().equalsIgnoreCase("u1")){
				assertEquals(3, utd.getTags().size());
			}
		}
		assertEquals(1, utd_list_2.size());
		for (UserTagDocTable utd : utd_list_2){
		//	System.out.println(utd.getUser().getName());
			if (utd.getUser().getName().equalsIgnoreCase("u1")){
				assertEquals(2, utd.getTags().size());
			}
		}
		
		/*
		List<UserTagDocTable> utd_list = session.createQuery("from UserTagDocTable m").list();
		for (UserTagDocTable utd : utd_list){
			System.out.println("UTD: " + utd.getId());
			System.out.println("USER: " + utd.getUser().getName() + " id: " + utd.getUser().getId());
			System.out.println("DOC: " + utd.getDoc().getId());
			for (TagTable t: utd.getTags())
				System.out.println(t.getTag());
			
		}*/
		

		
		session.close();
		
		
	}

}
