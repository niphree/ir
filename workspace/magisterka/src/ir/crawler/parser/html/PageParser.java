package ir.crawler.parser.html;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class PageParser {
	private Document doc;
	
	public PageParser(String url) throws IOException{
		doc  = Jsoup.connect(url).get();
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
