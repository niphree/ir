package ir.crawler.parser.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.sun.syndication.feed.synd.SyndCategory;
import com.sun.syndication.feed.synd.SyndEntry;

public class DeliciousDocumentData implements EntryData{
	
	
	private String url;
	
	private String page;
	private String clean_page;
	//private String title;
	List<Character> special = new ArrayList<Character>( Arrays.asList('%', '@', '!', ',', '.', '-', '"', '\'', '$', '#', '^', 
			'&', '*', '(', ')', '+', '=', '_', '{', '}', '[', ']', ';', 
			':', '<', '>', '?', '\\', '/', '|' ));
	
	
	private HashMap<ParserUserData, List<String>> usr_tags = new HashMap<ParserUserData, List<String>>();
	
	
	
	public DeliciousDocumentData(String url){
		this.url = url;
	}
	/*
	public void fetch_target_page() throws IOException{
		PageParser pp = new PageParser(url);
		clean_page = pp.get_cleaned_page();
		page = pp.get_page();
	}
	*/
	
	public String clean_tag(String tag){	
		//System.out.println(">"+tag+"<");
		String tag2 = "";
		for (int i = 0; i<tag.length(); i++){
			if (Character.UnicodeBlock.of(tag.charAt(i)) == Character.UnicodeBlock.BASIC_LATIN)
				tag2 = tag2 + tag.charAt(i);
		}
		tag = tag2;
		//System.out.println(">"+tag+"<");
		//System.out.println(tag.hashCode());
		int iter = 0;
		
		     
		while (iter<tag.length()){
			
			if ( special.contains(tag.charAt(iter)) )
				iter++;
			else break;
		}
		tag = tag.substring(iter, tag.length());
		
		iter = tag.length();
		while (iter>0){
			iter--;
			if ( special.contains(tag.charAt(iter)) )
				continue;
			else {
				iter++;
				break;
			}
		}

		if (iter >= 0)
			tag = tag.substring(0, iter);
		//System.out.println(">"+tag+"<");
		return tag;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void parse_entry(SyndEntry entry){
		
		ParserUserData usr = new ParserUserData(entry.getAuthor());

		List<SyndCategory> cat = entry.getCategories();
		List<String> tags = new ArrayList<String>();
		
		for (SyndCategory c : cat){
			String tag = c.getName();
			if (tag != null){
				System.out.println("cleaning tag:>"+tag+"<");
				tag = tag.trim();
				tag = clean_tag(tag);
				tag = tag.trim();
				System.out.println("cleaned tag:>"+tag+"<");
			}
			if (tag == "")
				tag = null;
			if (tag != null)
				tags.add(tag);
		}
		usr_tags.put(usr, tags);
	}
	
	public HashMap<ParserUserData, List<String>> getUsr_tags() {
		return usr_tags;
	}
	
	public void set_page(String page){
		this.page = page;
	}
	
	public String get_page(){
		return this.page;
	}

	
	public void set_url(String url){
		if (url.endsWith("/") || url.endsWith("\\")) {
			url = url.substring(0, url.length()-1);
		}
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
