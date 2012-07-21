package ir.crawler.saver;

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

public class JSONDataBaseSaver {

	String doc_name;
	List<String> tag_list;
	Map<Integer, List<Integer>> user_tags;
	String PATTERN = "TEST_USER_NAME_";
	
	
	public JSONDataBaseSaver(String doc_name, List<String> tag_list, Map<Integer, List<Integer>> user_tags){
		this.doc_name = doc_name;
		this.tag_list = tag_list;
		this.user_tags = user_tags;
		
		
		
	}
	
	
	public void save_to_db(){
		
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		DocumentTable doc = create_doc(session, doc_name);
		List<UserTagDocTable> utds = doc.getTags_document_list();
		Map<String, UserTagDocTable> utd_users = get_users(utds);
		//System.out.println(tag_list);
		
		for (Integer usr_id: user_tags.keySet()){
			List<Integer> tg_list = user_tags.get(usr_id);
			UserTable usr = null;
			UserTagDocTable utd = null;
			Map<String, TagTable> tags = new HashMap<String, TagTable>();
			//set up user/utd
			if (utd_users.containsKey(PATTERN+usr_id)){
				utd = utd_users.get(PATTERN+usr_id);
				utd = (UserTagDocTable)session.get(UserTagDocTable.class, utd.getId());
				usr = utd.getUser();
				tags = get_tags(utd);
			}
			else {
				usr = get_user(usr_id, session);
				session.saveOrUpdate(usr);
				List<UserTagDocTable> utd_list = doc.getTags_document_list();
				for (UserTagDocTable ut : utd_list){
					if (ut.getUser().getName() == usr.getName())
						utd = ut;
				}
				if (utd == null)
					utd = new UserTagDocTable();
				utd.setUser(usr);
				utd.setDoc(doc);
				tags = get_tags(utd);
				session.saveOrUpdate(utd);
				
				
			}
			
		//	System.out.println(tg_list);
			//
			for (Integer tag_id: tg_list){
				String tag= tag_list.get(tag_id);
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
					session.update(utd);

				}
			}
			
		}
		
		tx.commit();
		session.close();
		
	}
	
	
	
	
	@SuppressWarnings( "unchecked" )
	DocumentTable create_doc(Session session, String id){
		String PATTERN = "TEST_DOC_NAME_";
		String name = PATTERN+id;
			
		List<DocumentTable> doc_list = (List<DocumentTable>)session.
		createQuery("from DocumentTable m where url like ?").
		setString(0, name).
		list();

		boolean update = true;
		DocumentTable doc;
		if (doc_list.size() == 1)
			doc = doc_list.get(0);
		//create new
		else {
			update = false;
			doc = new DocumentTable(name);
			//new_document = true;
		}

		doc.set_new_data(false);

		session.saveOrUpdate(doc);
		if (update)
			System.out.println(doc.getId() + " document update");
		else
			System.out.println(doc.getId() + " new document");
		return doc;
	}
	
	
	@SuppressWarnings("unchecked")
	private UserTable get_user(int id, Session session){
		
		String name = PATTERN+id;
		
		UserTable user = null;
		try {
			List<UserTable> users = (List<UserTable>)session.
			createQuery("from UserTable m where name = ?").
			setString(0, name).
			list();

			if (users.size() == 1){
				user = users.get(0);
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
	Map<String, TagTable> get_tags(UserTagDocTable utd){
		Map<String, TagTable> tags = new HashMap<String, TagTable>();

		for (TagTable tag: utd.getTags()){
			tags.put(tag.getTag(), tag);
		}

		return tags;

	}
	
}
