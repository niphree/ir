package ir.model;

import ir.crawler.CrawlerType;
import ir.crawler.parser.data.DeliciousDocumentData;
import ir.crawler.parser.data.ParserUserData;
import ir.database.DocumentTable;
import ir.database.TagTable;
import ir.database.UserTable;
import ir.database.UserTagDocTable;
import ir.hibernate.HibernateUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class DocumentSaver {
	//Writer writer;
	public DocumentSaver() {
		//this.writer = writer;
	}




	public boolean save_data_from_parser(DeliciousDocumentData data, CrawlerType type) throws Exception{
		System.out.println("save_data_from_parser");
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		try {
			//System.out.println(data.get_url());

			DocumentTable doc = create_doc(session, data.get_url(), type);
			List<UserTagDocTable> utds = doc.getTags_document_list();
			Map<String, UserTagDocTable> utd_users = get_users(utds);

			tx.commit();
			session.close();


			//System.out.println(doc.getId());
			HashMap<ParserUserData, List<String>>  feed_tags = data.getUsr_tags();



			for (ParserUserData ft : feed_tags.keySet() ){
				UserTagDocTable utd = null;
				UserTable usr = null;
				session = HibernateUtil.getSession();
				tx = session.beginTransaction();

				Map<String, TagTable> tags = new HashMap<String, TagTable>();
				if (utd_users.containsKey(ft.nick )){
					//pobierz z doc user i utd
					utd = utd_users.get(ft.nick);

					usr = utd.getUser();
					if (type == CrawlerType.USER){
						usr.set_new_data(false);
						session.saveOrUpdate(usr);
					}
					if (usr == null){
						usr = get_user(ft.nick, session, type);
						utd.setUser(usr);
						session.saveOrUpdate(usr);
					}
					tags = get_tags(utd);
				}
				else{
					// mozliwe ze nowy uzytkownik
					usr = get_user(ft.nick, session, type);
					session.saveOrUpdate(usr);
					//nowa dana
					utd = new UserTagDocTable();
					utd.setUser(usr);
					utd.setDoc(doc);


				}
				session.saveOrUpdate(utd);


				//dodac tagi, sprawdzac czy juz nie ma dodanych
				//feed_tags.get(ft)

				for (String tag: feed_tags.get(ft)){

					if (tag == null)
						continue;
					if (tags.containsKey(tag)) //w utd znajduje sie polaczenie tag - user
						continue;
					else { //brak polaczenie user - tag
						// pobieramy obiekt tag:
						//session = HibernateUtil.getSession();
						//tx = session.beginTransaction();
						TagTable tag_o = get_tag(session, tag);
						if (tag_o != null){
							//dodajemy polaczenie
							utd.add_tag(tag_o);
							tags.put(tag, tag_o);

						}
						session.saveOrUpdate(utd);
						//tx.commit();
						//session.close();
					}

				}
				tx.commit();
				session.close();
				
				//zapis utd


				//should work...
			}
		}
		catch (Exception e) {
			tx.rollback();
			if (session.isConnected())
				session.close();
			throw e;
		}
		//tx.commit();
		//session.close();

		return true;


	}
	@SuppressWarnings( "unchecked" )
	DocumentTable create_doc(Session session, String url, CrawlerType type){

		List<DocumentTable> doc_list = (List<DocumentTable>)session.
		createQuery("from DocumentTable m where url like ?").
		setString(0, url).
		list();

		boolean update = true;
		DocumentTable doc;
		if (doc_list.size() == 1)
			doc = doc_list.get(0);
		//create new
		else {
			update = false;
			doc = new DocumentTable(url);
			//new_document = true;
		}
		if (type == CrawlerType.TOP){
			System.out.println("document update");
			doc.setTop_delicous(true);
		}
		if (type == CrawlerType.DOC){  //jesli dane sa z przegladania dokuemtnow, to dane uznajemu juz za NIE nowe
			
			doc.set_new_data(false);
		}
		session.saveOrUpdate(doc);
		if (update)
			System.out.println(doc.getId() + " document update");
		else
			System.out.println(doc.getId() + "new document");
		return doc;
	}

	Map<String, TagTable> get_tags(UserTagDocTable utd){
		Map<String, TagTable> tags = new HashMap<String, TagTable>();

		for (TagTable tag: utd.getTags()){
			tags.put(tag.getTag(), tag);
		}

		return tags;

	}


	Map<String, UserTagDocTable> get_users(List<UserTagDocTable> utds){
		Map<String, UserTagDocTable> users = new HashMap<String, UserTagDocTable>();

		for (UserTagDocTable utd : utds){
			UserTable ut = utd.getUser();
			if (ut == null)
				continue;
			Hibernate.initialize(utd.getTags());
			users.put(ut.getName(), utd);
		}

		return users;

	}



	@SuppressWarnings("unchecked")
	TagTable get_tag(Session session, String tag){
		TagTable tag_o = null;
		try {
			List<TagTable> tags_table = (List<TagTable>)session.
			createQuery("from TagTable m where tag = ?").
			setString(0, tag).
			list();


			if (tags_table.size() == 1)
				tag_o = tags_table.get(0);
			else {
				tag_o = new TagTable(tag);
				session.saveOrUpdate(tag_o);
			}

		}
		catch (Exception e) {
			System.out.println("EXCEPTION in adding tag: "  + tag);
			e.printStackTrace();
		}
		return tag_o;
	}

	@SuppressWarnings("unchecked")

	private UserTable get_user(String name, Session session,  CrawlerType type){
		UserTable user = null;
		try {
			List<UserTable> users = (List<UserTable>)session.
			createQuery("from UserTable m where name = ?").
			setString(0, name).
			list();

			if (users.size() == 1){
				user = users.get(0);
				if (type == CrawlerType.USER )
					user.set_new_data(false);
			}
			else{
				user = new UserTable(name);
			}
		}catch (Exception e) {
			System.out.println("EXCEOTION in adding user: " + name);
			e.printStackTrace();
		}
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