package ir.model;

import ir.analyzer.Writer;
import ir.crawler.parser.data.DeliciousDocumentData;
import ir.crawler.parser.data.ParserTags;
import ir.crawler.parser.data.ParserUserData;
import ir.database.DocumentTable;
import ir.database.TagTable;
import ir.database.UserTable;
import ir.database.UserTagDocTable;
import ir.hibernate.HibernateUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;


public class DocumentSaver {
	Writer writer;
	public DocumentSaver(Writer writer) {
		this.writer = writer;
	}
	
	@SuppressWarnings( "unchecked" )
	public void save_data_from_parser(DeliciousDocumentData data){
		System.out.println("save_data_from_parser");
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		System.out.println(data.get_url());
		List<DocumentTable> doc_list = (List<DocumentTable>)session.
			createQuery("from DocumentTable m where url like ?").
			setString(0, data.get_url()).
			list();
		
		DocumentTable doc = null;
		boolean new_document = false;
		//update
		if (doc_list.size() == 1)
			doc = doc_list.get(0);
		//create new
		else {
			doc = new DocumentTable(data.get_url());
			new_document = true;
		}
		session.saveOrUpdate(doc);
		
		HashMap<ParserUserData, List<ParserTags>>  feed_tags = data.getUsr_tags();
		
		for (ParserUserData ft : feed_tags.keySet() ){
			UserTable usr = get_user(ft.nick, session);
			session.saveOrUpdate(usr);
			List<TagTable> tags = get_tags(feed_tags.get(ft), session);
			
			UserTagDocTable utd = new UserTagDocTable();  //get from doc, check if null
			
			utd.setDoc(doc);
			utd.setUser(usr);
			utd.setTags(tags);
			
			session.saveOrUpdate(utd);
			
			// save to LUCENE
		}
		
		tx.commit();
		session.close();
		//everything was ok
		if (new_document){
			System.out.println("Saving to lucene, doc id: " + doc.getId());
			writer.addDocument(data.get_clean_page(), doc.getId());
		}
	}
	
	@SuppressWarnings("unchecked")
	private List<TagTable> get_tags(List<ParserTags> parser_tags, Session session){
		List<TagTable> tags = new ArrayList<TagTable>();
		
		for (ParserTags p_tag : parser_tags){
			List<TagTable> tags_table = (List<TagTable>)session.
				createQuery("from TagTable m where tag = ?").
				setString(0, p_tag.tag).
				list();
			
			TagTable tag = null;
			if (tags_table.size() == 1)
				tag = tags_table.get(0);
			else
				tag = new TagTable(p_tag.tag);
			
			session.saveOrUpdate(tag);
			tags.add(tag);
		}
		return tags;
		
	}
	
	@SuppressWarnings("unchecked")
	private UserTable get_user(String name, Session session){
		List<UserTable> users = (List<UserTable>)session.
			createQuery("from UserTable m where name = ?").
			setString(0, name).
			list();
		UserTable user = null;
		if (users.size() == 1)
			user = users.get(0);
		else
			user = new UserTable(name);
		return user;
	}
	
	/**
	 * use to get document to database
	 * @param id
	 */
	/*
	public Document(Long id) {
		this.id = id;
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		doc = (DocumentTable) session.get(DocumentTable.class, id);
		tx.commit();
		session.close();
	}
	*/
	/**
	 * use to save doc to database
	 * and to add document to lucene
	 * @param doc
	 */
	/*
	public Document(String document){
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		doc = new DocumentTable();
		session.save(doc);
		id = doc.getId();
		tx.commit();
		session.close();
		Writer writer = new Writer();
		writer.addDocument(document, id);
	}
	
	public Long getId() {
		return doc.getId();
	}
	
	public void addUsers(List<UserTable> users){
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		doc = (DocumentTable) session.get(DocumentTable.class, id);
		for (UserTable user : users){
	//		doc.addUser(user);
		}
		session.update(doc);
		tx.commit();
		session.close();
	}
	
	public List<UserTable> getUsers(){
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		doc = (DocumentTable) session.get(DocumentTable.class, id);

		//List<UserTable> ut = doc.getUsers();
		
		tx.commit();
		session.close();
		return null;
	//	return ut;
	}
	
	public void addTags(List<TagTable> tags){
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		doc = (DocumentTable) session.get(DocumentTable.class, id);
		
		for (TagTable tag : tags){
	//		doc.addTag(tag);
		}
		
		session.update(doc);
		tx.commit();
		session.close();
	}
	
	public List<TagTable> getTags(){
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		doc = (DocumentTable) session.get(DocumentTable.class, id);

	//	List<TagTable> tt = doc.getTags();
	//	Hibernate.initialize(tt); // ohh lazy lazy hibernate (lazy init prevented)

		tx.commit();
		session.close();
		return null;
	//	return tt;
	}
	*/
	
}