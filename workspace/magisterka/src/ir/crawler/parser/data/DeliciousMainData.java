package ir.crawler.parser.data;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

import com.sun.syndication.feed.synd.SyndEntry;

public class DeliciousMainData implements EntryData {

	List<String> entries_url = new ArrayList<String>();
	
	@Override
	@SuppressWarnings("unchecked")
	public void parse_entry(SyndEntry entry){
		
		List<Element> elems = (List<Element>)entry.getForeignMarkup();
		
		for (Element e : elems)
			entries_url.add(e.getValue());
			
	}
	
	public List<String> get_entries_url() {
		return entries_url;
	}
}
