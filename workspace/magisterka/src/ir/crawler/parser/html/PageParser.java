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


	private int get_val(String s){
		int value = 0;
		for (int a = 0; a < s.length(); a++){
			String sub = s.substring(a, a+1);
			if (sub.compareTo(",") == 0){
				continue;
			}
			try {
				int add = Integer.valueOf(sub);
				value = value * 10 + add;
			} catch (NumberFormatException e) {
				break;
			}
		}
		return value;
	}

	public int get_facebook_values(){
		try {
			Element elem = doc.select("span.connect_widget_not_connected_text").first();
			String val = elem.html();
			//System.out.println("!" + val);
			int int_val = get_val(val);
			return int_val;
		} catch (Exception e) {
			e.printStackTrace();
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
