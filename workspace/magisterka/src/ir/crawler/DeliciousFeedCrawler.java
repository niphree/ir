package ir.crawler;

import ir.crawler.parser.data.DeliciousMainData;
import ir.crawler.parser.feed.DeliciousMainFeedReader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.sun.syndication.io.FeedException;


public class DeliciousFeedCrawler extends AbstractDeliciousFeedCrawler{  
	

	protected boolean recreate;
	
	protected String DELICIOUS_URL;
	
	public DeliciousFeedCrawler(boolean recreate, CrawlerType type) {
		this.recreate = recreate;
		this.type = type;
		
	}
	
	public DeliciousFeedCrawler(boolean recreate, CrawlerType type, String url ) {
		this.DELICIOUS_URL = url;
		this.recreate = recreate;
		this.type = type;
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

	@Override
	public List<String> get_urls() {
		List<String> urls = new ArrayList<String>();
		try{
			urls = crawl_main_page();
		}catch (Exception e) {
			System.out.println("EXCEPTION CRAWLING MAIN PAGE, TYPE: " + type);
			e.printStackTrace();
		}
		return urls;
		
	}
}
