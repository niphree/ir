package ir.crawler.parser.html;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class PageParser {
	private Document doc;
	
	public PageParser(String url) throws IOException{
		doc  = Jsoup.connect(url).get();
	}
	
	
	public int get_facebook_values(){
		try {
			Element elem = doc.select("span.connect_widget_not_connected_text").first();
			String val = elem.html();
			int int_val = Integer.valueOf(val);
			return int_val;
		} catch (Exception e) {
			System.out.println("FACEBOOK COUNT RETRIVE COUNT FAIL !");
			return 0;
		}
		
	}
	
	public String get_cleaned_page(){
		String page = doc.body().text();
		return page; 
	}
	
	public String get_page(){
		String page = doc.body().html();
		return page;
	}
	
	
}
