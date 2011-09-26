package ir.crawler;

import ir.hibernate.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserDeliciousCrawler extends AbstractDeliciousFeedCrawler{

	protected boolean new_data;
	
	public UserDeliciousCrawler(boolean new_data) {
		type = CrawlerType.USER;
		this.new_data = new_data;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<String> get_urls() {
		
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		String query = "";
		if (new_data) //moga czasem byc nulle, w szczegolnosci po odzyskaniu danych albo nowych danych
			query = "select count(*) as c from UserTable m where new_data = true or new_data is null";
		else 
			query = "select count(*) as c from UserTable m where new_data = false ";
		List<Long>  count = (List<Long>)session.
			createQuery(query).
			list();
		Long c_to = count.get(0);
		
		System.out.println(count.get(0));
		tx.commit();
		session.close();
		return new ArrayList<String>();
		
		
		/*
		session = HibernateUtil.getSession();
		tx = session.beginTransaction();
		List<String> urls = new ArrayList<String>();
		query = "";
		if (new_data) //moga czasem byc nulle, w szczegolnosci po odzyskaniu danych albo nowych danych
			query = "from UserTable m where new_data = true or new_data is null";
		else 
			query = "from UserTable m where new_data = false ";
		System.out.println("a");
		List<UserTable> users = (List<UserTable>)session.
			createQuery(query).
			list();
		
		
		tx.commit();
		session.close();
		for (UserTable usr: users){
			urls.add("http://feeds.delicious.com/v2/rss/" + usr.getName());
		}
		return urls;
		*/
	}

	
	
}
