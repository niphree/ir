package ir.crawler;

import ir.crawler.parser.data.DeliciousDocumentData;
import ir.crawler.parser.feed.DeliciousURLFeedReader;
import ir.model.DocumentSaver;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.sun.syndication.io.FeedException;


public abstract class AbstractDeliciousFeedCrawler extends Thread{  
	
	
	protected Map<String, Integer> fail_url_list = new HashMap<String, Integer>();
	protected static Integer ERROR_NUMBER = 3;
	protected static Long WAIT_TIME = Long.valueOf(10 * 60 * 1000); //in secundes
	protected CrawlerType type;
	
	
	public abstract List<String> get_urls();
	

	@Override
	public void run() {
	//	try {
			//Writer writer = new Writer();
			//if (recreate == true){
			//	writer.reset();
			//}
			while (true){
				try {
					Random r = new Random();
					sleep(1000 + r.nextInt(10000));
					start_crawler();
					System.out.println(type.toString() + " TICK!");
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		//} catch (IOException e) {
		//	// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}
		
	}
	
	
	
	private void start_crawler() throws InterruptedException, IOException{
			System.out.println(type.toString() + " delicous crawler, start of tick");
		//	Calendar start_time = Calendar.getInstance();
			crawl();
			System.out.println(type.toString() + " delicous crawler, sleeping, good night");
			
	//		if (type == CrawlerType.NEW) return; //continue working
			
		//	Calendar end_time = Calendar.getInstance();
		//	long wait = WAIT_TIME - (end_time.getTimeInMillis() - start_time.getTimeInMillis());
		//	if (wait < 0)
		//		wait = 0;

		//	sleep(wait);


	}
	public abstract String get_name();
	
	public void crawl() throws InterruptedException{
		List<String> hotlist = null;
		try {
			Random r = new Random();
			hotlist = get_urls();
			hotlist.addAll(fail_url_list.keySet());   // ursy do przejrzenia
			
			for (String document_url : hotlist){
				try {
					sleep(100);
					System.out.println("detail for page: " + type + " - " + document_url);
					DeliciousURLFeedReader detail_reader = new DeliciousURLFeedReader(
							new URL(document_url+"?count=100"));
					DeliciousDocumentData doc_data = detail_reader.parse();
					if (doc_data == null){
						System.out.println(this.getClass().getCanonicalName() + " error in parsing page:  " + document_url);
						continue;
					}
					DocumentSaver doc = new DocumentSaver();
					System.out.println("saving data to DB");
					boolean success = doc.save_data_from_parser(doc_data, type);
					
					if (success == false)
						handle_exception(document_url);
					else
						fail_url_list.remove(document_url);
				} catch (FeedException e) {
					handle_exception(document_url);
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					handle_exception(document_url);
					e.printStackTrace();
				} catch (MalformedURLException e) {
					// just print stack trace - url removed
					if (fail_url_list.containsKey(document_url))
						fail_url_list.remove(document_url);
					System.out.println("MALFORMED URL:" + document_url);
					e.printStackTrace();
				} catch (IOException e) {
					System.out.println(e.getClass().getName());
				
					handle_exception(document_url);
					e.printStackTrace();
				} catch (Exception e) {
					handle_exception(document_url);
					e.printStackTrace();
				}
			}
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
			hotlist.clear();
		}
	}
	
	private void handle_exception(String url){
		System.out.println("Exception handled");
		if (fail_url_list.containsKey(url)){
			if (fail_url_list.get(url) >= ERROR_NUMBER){
				fail_url_list.remove(url);
			}
			else {
				fail_url_list.put(url, fail_url_list.get(url) + 1);
			}
		}
		else fail_url_list.put(url, 0);
	}
	

}
