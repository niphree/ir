package ir.crawler;

import ir.crawler.parser.data.DeliciousDocumentData;
import ir.crawler.parser.data.DeliciousMainData;
import ir.crawler.parser.feed.DeliciousDetailFeedReader;
import ir.crawler.parser.feed.DeliciousMainFeedReader;
import ir.model.DocumentSaver;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.syndication.io.FeedException;


// Thread ! zmienic na !
public class DeliciousFeedCrawler extends Thread{  
	//TODO change to thread
	
	private static String DELICIOUS_URL = "http://feeds.delicious.com/v2/rss/?count=100";
	private Map<String, Integer> url_list = new HashMap<String, Integer>();
	private static Integer ERROR_NUMBER = 3;
	private static Long WAIT_TIME = Long.valueOf(30 * 60 * 1000); //in secundes
	
	@Override
	public void run() {
		try {
			start_crawler();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	public void start_crawler() throws InterruptedException{
		while (true){
			System.out.println("start of tick");
			Calendar start_time = Calendar.getInstance();
			crawl();
			System.out.println("waiting for time to elapse");
			Calendar end_time = Calendar.getInstance();
			long wait = WAIT_TIME - (end_time.getTimeInMillis() - start_time.getTimeInMillis());
			if (wait < 0){
				wait = 0;
			}
			sleep(wait);
			//sleep(wait);
			
			
		}
	}
	
	public void crawl() throws InterruptedException{
		try {
			
			List<String> hotlist = crawl_main_page();
			hotlist.addAll(url_list.keySet());
			for (String document_url : hotlist){
				try {
					sleep(2000);
					System.out.println("detail for page: " + document_url);
					DeliciousDetailFeedReader detail_reader = new DeliciousDetailFeedReader(
							new URL(document_url+"?count=100"));
					DeliciousDocumentData doc_data = detail_reader.parse();
					DocumentSaver doc = new DocumentSaver();
					System.out.println("saving data to DB");
					doc.save_data_from_parser(doc_data);
					url_list.remove(document_url);
				} catch (FeedException e) {
					handle_exception(document_url);
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					handle_exception(document_url);
					e.printStackTrace();
				} catch (MalformedURLException e) {
					// just print stack trace - url removed
					if (url_list.containsKey(document_url))
						url_list.remove(document_url);
					e.printStackTrace();
				} catch (IOException e) {
					handle_exception(document_url);
					e.printStackTrace();
				}
			}
		} catch (IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (FeedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	private void handle_exception(String url){
		System.out.println("Exception handled");
		if (url_list.containsKey(url)){
			if (url_list.get(url) >= ERROR_NUMBER){
				url_list.remove(url);
			}
			else {
				url_list.put(url, url_list.get(url) + 1);
			}
		}
		else url_list.put(url, 0);
	}
	
	private List<String> crawl_main_page() throws IllegalArgumentException, MalformedURLException, FeedException, IOException{
		System.out.println("trying to parse main delicous site");
		DeliciousMainFeedReader mainReader = new DeliciousMainFeedReader(
				new URL(DELICIOUS_URL));
		DeliciousMainData hotlist_data = mainReader.parse();
		System.out.println("delicous main page parsed");
		List<String> hotlist = hotlist_data.get_entries_url();
		
		return hotlist;
	}
}
