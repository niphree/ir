package ir.crawler;

import ir.database.UserTable;
import ir.hibernate.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserDeliciousCrawler extends AbstractDeliciousFeedCrawler{

	protected boolean new_data;
	protected int interval = 1000;
	protected int max = 0;
	protected int current = 0;
	
	public UserDeliciousCrawler(boolean new_data) {
		type = CrawlerType.USER;
		this.new_data = new_data;
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public void crawl() throws InterruptedException {

		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		String query = "";
		if (new_data) //moga czasem byc nulle, w szczegolnosci po odzyskaniu danych albo nowych danych
			query = "select count(*) as c from UserTable m where new_data = true or new_data is null";
		else 
			query = "select count(*) as c from UserTable m";
		List<Long>  count = (List<Long>)session.
			createQuery(query).
			list();
		long c_to = count.get(0);
		
		current = 1000;
		max = (int)c_to;
		
		System.out.println(count.get(0));
		tx.commit();
		session.close();
		
		while (current < max){
			super.crawl();
			current+=interval;
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<String> get_urls() {
		
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		
		
		
		session = HibernateUtil.getSession();
		tx = session.beginTransaction();
		List<String> urls = new ArrayList<String>();
		String query = "";
		query = "";
		if (new_data) //moga czasem byc nulle, w szczegolnosci po odzyskaniu danych albo nowych danych
			query = "from UserTable m where new_data = true or new_data is null order by id DESC";
		else 
			query = "from UserTable m order by id DESC";
		List<UserTable> users = (List<UserTable>)session.
			createQuery(query).setFirstResult(current).setMaxResults(interval).
			list();
		
		
		tx.commit();
		session.close();
		for (UserTable usr: users){
			urls.add("http://feeds.delicious.com/v2/rss/" + usr.getName());
		}
		return urls;
		
	}

	public String get_name() {
		return "UserDeliciousCrawler ";
	}
	
}