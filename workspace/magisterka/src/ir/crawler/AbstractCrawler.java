package ir.crawler;

import ir.database.DocumentTable;
import ir.hibernate.HibernateUtil;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

abstract class AbstractCrawler extends Thread{

	public static Long WAIT_TIME = Long.valueOf(30 * 60 * 1000); //in secundes
	
	protected abstract void save(DocumentTable doc, int val);
	protected abstract int get_value(String url);
	protected abstract String get_name();
	
	
	public void crawl(){
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();

		@SuppressWarnings("unchecked")
		List<DocumentTable> docs = (List<DocumentTable>)session.createQuery("from DocumentTable m").list();
		
		tx.commit();
		session.close();
		
		for (DocumentTable doc: docs){
			try {
		
			sleep(5000);
			
			String url = doc.getUrl();
			int val = get_value(url);
			System.out.println(get_name() + ": " + val);
			save(doc, val);
			} catch (Exception e) {
				System.out.println(get_name() + " DocumentTable FOR EXCEPTION!!");
				e.printStackTrace();
			}
			
		}
		
	}
	
	
	@Override
	public void run() {
	/*	try {
			//sleep(100000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}*/
		while(true){
			try {
				start_crawler();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void start_crawler() throws InterruptedException {
		System.out.println(get_name() + " crawler, start of tick");
		Calendar start_time = Calendar.getInstance();
		
		//actions
		crawl();
		
		System.out.println(get_name() + " crawler, sleeping, good night");
		Calendar end_time = Calendar.getInstance();
		long wait = WAIT_TIME - (end_time.getTimeInMillis() - start_time.getTimeInMillis());
		if (wait < 0)
			wait = 0;

		sleep(wait);

	}
}
