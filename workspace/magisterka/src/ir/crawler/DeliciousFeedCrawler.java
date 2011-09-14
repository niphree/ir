package ir.crawler;

import ir.analyzer.Writer;
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
import java.util.Random;

import com.sun.syndication.io.FeedException;

// Thread ! zmienic na !
public class DeliciousFeedCrawler extends Thread{  
	
	private String DELICIOUS_URL = "http://feeds.delicious.com/v2/rss/?count=100";
	private Map<String, Integer> url_list = new HashMap<String, Integer>();
	private boolean recreate;
	private static Integer ERROR_NUMBER = 3;
	private static Long WAIT_TIME = Long.valueOf(30 * 60 * 1000); //in secundes
	private CrawlerType type;
	
	
	public DeliciousFeedCrawler(boolean recreate, CrawlerType type) {
		this.recreate = recreate;
		this.type = type;
		
	}
	
	public DeliciousFeedCrawler(boolean recreate, CrawlerType type, String url ) {
		this.DELICIOUS_URL = url;
		this.recreate = recreate;
		this.type = type;
	}
	
	@Override
	public void run() {
		try {
			Writer writer = new Writer();
			if (recreate == true){
				writer.reset();
			}
			while (true){
				try {
					start_crawler(writer);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	private void start_crawler(Writer writer) throws InterruptedException, IOException{
			System.out.println("delicous crawler, start of tick");
			Calendar start_time = Calendar.getInstance();
			crawl(writer);
			System.out.println("delicous crawler, sleeping, good night");
			Calendar end_time = Calendar.getInstance();
			long wait = WAIT_TIME - (end_time.getTimeInMillis() - start_time.getTimeInMillis());
			if (wait < 0)
				wait = 0;

			sleep(wait);


	}
	
	public void crawl(Writer writer) throws InterruptedException{
		try {
			Random r = new Random();
			List<String> hotlist = crawl_main_page();
			hotlist.addAll(url_list.keySet());
			for (String document_url : hotlist){
				try {
					sleep(5000 + r.nextInt(5) * 1000);
					System.out.println("detail for page: " + document_url);
					DeliciousDetailFeedReader detail_reader = new DeliciousDetailFeedReader(
							new URL(document_url+"?count=100"));
					DeliciousDocumentData doc_data = detail_reader.parse();
					DocumentSaver doc = new DocumentSaver(writer);
					System.out.println("saving data to DB");
					boolean success = doc.save_data_from_parser(doc_data, type);
					
					if (success == false)
						handle_exception(document_url);
					else
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
					System.out.println(e.getClass().getName());
				
					handle_exception(document_url);
					e.printStackTrace();
				}
			}
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (FeedException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
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
