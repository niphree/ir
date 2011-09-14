package ir.crawler;

import ir.database.DocumentTable;
import ir.hibernate.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

class Container{
	
	String url;
	Long id;
	
	public Container(String a, Long b) {
		this.url = a;
		this.id = b;
	}
}
public class LuceneCrawler extends Thread{
	
	public static void main(String[] args) {
		LuceneCrawler crawler = new LuceneCrawler();
		crawler.lucene_crawl();
	}
		
		//public static Long WAIT_TIME = Long.valueOf(30 * 60 * 1000); //in secundes
		@Override
		public void run() {
			while(true){
				try {
					lucene_crawl();
					//sleep(WAIT_TIME);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		private void start_thread(List<Container> container, int count ){
			try {
				System.out.println("starting thread: " + count);
				LuceneDetailCrawler crawler = new LuceneDetailCrawler(container, count);
				crawler.start();
				
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		public void lucene_crawl(){
			Session session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			
			
			List<DocumentTable> docs = session.createQuery("from DocumentTable m where page_fetch is null or page_fetch= 0 ").list();
			
			tx.commit();
			session.close();
			List<Container> container = new ArrayList<Container>();
			int counter = 0;
			int number = 0;
			for (DocumentTable doc: docs){
				counter++;
				String url = doc.getUrl();
				long id = doc.getId();
				container.add(new Container(url, id));
				if (counter == 10000){
					number++;
					start_thread(container, number);
					counter = 0;
					container = new ArrayList<Container>();
				}
				
			}
			start_thread(container, 0);
			System.out.println("Ending Document table cache update. going to sleep. Goodnight.");
		}
}


