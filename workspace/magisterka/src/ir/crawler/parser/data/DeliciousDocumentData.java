package ir.crawler.parser.data;

import ir.crawler.parser.html.PageParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.sun.syndication.feed.synd.SyndCategory;
import com.sun.syndication.feed.synd.SyndEntry;

public class DeliciousDocumentData implements EntryData{
	
	
	private String url;
	
	private String page;
	private String clean_page;
	//private String title;

	private HashMap<ParserUserData, List<ParserTags>> usr_tags = new HashMap<ParserUserData, List<ParserTags>>();
	
	
	
	public DeliciousDocumentData(String url){
		this.url = url;
	}
	
	public void fetch_target_page() throws IOException{
		PageParser pp = new PageParser(url);
		clean_page = pp.get_cleaned_page();
		page = pp.get_page();
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public void parse_entry(SyndEntry entry){
		
		ParserUserData usr = new ParserUserData(entry.getAuthor());

		List<SyndCategory> cat = entry.getCategories();
		List<ParserTags> tags = new ArrayList<ParserTags>();
		
		for (SyndCategory c : cat){
			ParserTags tag = new ParserTags(c.getName());
			tags.add(tag);
		}
		usr_tags.put(usr, tags);
	}
	
	public HashMap<ParserUserData, List<ParserTags>> getUsr_tags() {
		return usr_tags;
	}
	
	public void set_page(String page){
		this.page = page;
	}
	
	public String get_page(){
		return this.page;
	}

	
	public void set_url(String url){
		this.url = url;
	}
	
	public String get_url(){
		return url;
	}
	
	public void set_clean_page(String clean_page) {
		this.clean_page = clean_page;
	}
	
	public String get_clean_page() {
		return clean_page;
	}
	
	
	

}
