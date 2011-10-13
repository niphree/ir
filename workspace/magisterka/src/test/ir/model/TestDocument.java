package test.ir.model;

import ir.crawler.CrawlerType;
import ir.crawler.parser.data.DeliciousDocumentData;
import ir.crawler.parser.feed.DeliciousURLFeedReader;
import ir.database.DocumentTable;
import ir.database.TagTable;
import ir.database.UserTable;
import ir.database.UserTagDocTable;
import ir.hibernate.HibernateUtil;
import ir.model.DocumentSaver;

import java.io.File;
import java.io.IOException;
import java.util.List;

import junit.framework.TestCase;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Ignore;
import org.junit.Test;

import test.ir.utils.HibernateTestUtils;
import test.ir.utils.LuceneTestUtils;

import com.sun.syndication.io.FeedException;

public class TestDocument extends TestCase{

	@Override
	protected void setUp() throws Exception {
		HibernateTestUtils.cleanUp();
		LuceneTestUtils.cleanUp();
		super.setUp();
	}
	
	@Override
	protected void tearDown() throws Exception {
		HibernateTestUtils.cleanUp();
		LuceneTestUtils.cleanUp();
		super.tearDown();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void test_unique() throws Exception{
		DeliciousURLFeedReader reader = new DeliciousURLFeedReader(new File("src/test/ir/crawler/parser/feed/del_repeat_test.txt"));
		DeliciousDocumentData data = reader.parse();
		
		DocumentSaver doc = new DocumentSaver();
		doc.save_data_from_parser(data, CrawlerType.NEW);
		
		Session session = HibernateUtil.getSession();
		//Transaction tx = session.beginTransaction();
		
		List<UserTagDocTable> utds = session.createQuery("from UserTagDocTable m").list();
		assertEquals(1, utds.size());
		
		List<UserTable> users = session.createQuery("from UserTable m").list();
		assertEquals(1, users.size());
		
		List<DocumentTable> docs = session.createQuery("from DocumentTable m").list();
		assertEquals(1, docs.size());
		
		session.close();
		
		reader = new DeliciousURLFeedReader(new File("src/test/ir/crawler/parser/feed/del_repeat_test2.txt"));
		data = reader.parse();
		
		doc = new DocumentSaver();
		doc.save_data_from_parser(data, CrawlerType.NEW);
		
		session = HibernateUtil.getSession();
		//tx = session.beginTransaction();
		
		utds = session.createQuery("from UserTagDocTable m").list();
		assertEquals(2, utds.size());
		
		users = session.createQuery("from UserTable m").list();
		assertEquals(1, users.size());
		
		docs = session.createQuery("from DocumentTable m").list();
		assertEquals(1, docs.size());
		for (UserTagDocTable utd: utds){
			System.out.println("id: + " + utd.getId() + " doc_id: " + utd.getDoc().getId() + 
					" usr: " + utd.getUser().getName() + " tags: ");
			for (TagTable tag : utd.getTags()){
				System.out.println("  id: " + tag.getId() + " name: " + tag.getTag());
			}
		}
		session.close();
	}
	/*
	 * 		for (UserTagDocTable utd: utds){
			System.out.println("id: + " + utd.getId() + " doc_id: " + utd.getDoc().getId() + 
					" usr: " + utd.getUser().getName() + " tags: ");
			for (TagTable tag : utd.getTags()){
				System.out.println("  id: " + tag.getId() + " name: " + tag.getTag());
			}
		}
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void test_document_create() throws IllegalArgumentException, FeedException, IOException{
		DeliciousURLFeedReader reader = new DeliciousURLFeedReader(new File("src/test/ir/crawler/parser/feed/page_feed_del.txt"));
		DeliciousDocumentData data = reader.parse();
		
		DocumentSaver doc = new DocumentSaver();
		doc.save_data_from_parser(data, CrawlerType.NEW);
		
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		List<DocumentTable> docs = session.createQuery("from DocumentTable m").list();
		assertEquals(1, docs.size());
		List<UserTable> users = session.createQuery("from UserTable m").list();
		assertEquals(100, users.size());
		
		tx.commit();
		session.close();
		
		
		
	}
	
	@Ignore
	@Test
	public void testAddingDocument(){
/*		String doc = "test document";
		Document d1 = new Document(doc);
		
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		TagTable tag = new TagTable("Test_tag");
		session.save(tag);
		
		tx.commit();
		session.close();
		List<TagTable> tags = new ArrayList<TagTable>();
		tags.add(tag);
		
		d1.addTags(tags);
		Long id = d1.getId();
		
		Document d2 = new Document(id);
		//List<TagTable> tts = d2.getTags(); 
		//session = HibernateUtil.getSession();
		//assertEquals(1, tts.size());
		//session.close();*/
	}
	
	
	
}
