package ir.crawler.parser.html;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class LinkFilter {

	URI uri;
	
	public LinkFilter(URL url) {
		try {
			uri = url.toURI();
		} catch (URISyntaxException e) {
			// shouldn't come to this
			e.printStackTrace();
		}
	}
	
	public List<String> filterList(List<String> rawList){
		List<String> newList = new ArrayList<String>();
		
		Iterator<String> it = rawList.iterator();
		while(it.hasNext()){
			String newlink = filter(it.next());
			if (newlink != null){
				newList.add(newlink);
			}
		}
		
		return newList;
	}
	 
	private String filter(String link){
		if (link == null) return null;
		if (link.startsWith("#")) return null;
		URI newuri = uri.resolve(link);
		
		return newuri.toString();
	}
	
}
