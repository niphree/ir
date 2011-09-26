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
	
	public URLDeliciousCrawler(boolean new_data) {
		type = CrawlerType.DOC;
		this.new_data = new_data;
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public List<String> get_urls() {
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		List<String> urls = new ArrayList<String>();
		String query = "";
		if (new_data) //moga czasem byc nulle, w szczegolnosci po odzyskaniu danych albo nowych danych
			query = "from DocumentTable m where new_data = true or new_data is null ";
		else 
			query = "from DocumentTable m where new_data = false ";
		System.out.println("b");
		List<DocumentTable> docs = (List<DocumentTable>)session.
			createQuery(query).
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
			
			byte[] array = md.digest(url.getBytes());
			StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < array.length; ++i) {
	          sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
	       }
	        String u = "http://feeds.delicious.com/v2/rss/url/" + sb.toString() ;
	        //System.out.println(u);
			urls.add(u);
		}
		return urls;
	}

}
