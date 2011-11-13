package ir.crawler;

import ir.database.DocumentTable;
import ir.hibernate.HibernateUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class URLDeliciousCrawler extends AbstractDeliciousFeedCrawler{
	protected boolean new_data;
	protected int interval = 1000;  
	protected int max = 0;
	protected int current = 0;
	long id;
	
	public URLDeliciousCrawler(boolean new_data, long id) {
		type = CrawlerType.DOC;
		this.new_data = new_data;
		this.id = id;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void crawl() throws InterruptedException {

		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		String query = "";
		if (new_data) //moga czasem byc nulle, w szczegolnosci po odzyskaniu danych albo nowych danych
			query = "select count(*) as c from DocumentTable m where id>60000 " ;
		else 
			query = "select count(*) as c from DocumentTable m where id>60000";
		List<Long>  count = (List<Long>)session.
			createQuery(query).
			list();
		long c_to = count.get(0);
		
		current = 0;
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
		
		List<String> urls = new ArrayList<String>();
		String query = "";
		if (new_data) //moga czasem byc nulle, w szczegolnosci po odzyskaniu danych albo nowych danych
			query = "from DocumentTable m order by id DESC";
		else 
			query = "from DocumentTable m where id <? order by id ";
		List<DocumentTable> docs = null; 
			
		if (new_data)	
			docs = (List<DocumentTable>)session.createQuery(query).list();
		else
			docs = (List<DocumentTable>)session.
			createQuery(query).setLong(0, id).
			setFirstResult(current).setMaxResults(interval).
			list();
		
		tx.commit();
		session.close();
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// to nie powinno sie wydarzyc (czyli bedzie sie zdazalo w 9 na 10 przypadków...)
			e.printStackTrace();
			return urls;
		}
		for (DocumentTable doc: docs){
			String url = doc.getUrl();
			
			
	        String u = "http://feeds.delicious.com/v2/rss/url/" + getMD5(md, url);
	        String u2 = "http://feeds.delicious.com/v2/rss/url/" + getMD5(md, url+"/");
	        //System.out.println(u);
			urls.add(u);
			urls.add(u2);
		}
		return urls;
	}

	String getMD5(MessageDigest md, String url){
		byte[] array = md.digest(url.getBytes());
		StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; ++i) {
          sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
       } 
        return sb.toString();
	}
	
	@Override
	public String get_name() {
		return "URLDeliciousCrawler ";
	}

}
