package ir.crawler.parser.html;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.htmlparser.Tag;
import org.htmlparser.visitors.NodeVisitor;


public class  LinkVisitor extends NodeVisitor{
	private static final String A = "A";
	private static final String HREF = "HREF";
	
	private List<String> rawlinks = new ArrayList<String>();
	private LinkFilter filter;
	
	
	public LinkVisitor(URL url) {
		filter = new LinkFilter(url);
	}
	
	@Override
	public void visitTag(Tag tag) {
		
		String name = tag.getTagName();
		
		if (A.equalsIgnoreCase(name)){
			String href = tag.getAttribute(HREF);
			rawlinks.add(href);
		}
		
	}
	
	public List<String> getRawLinks(){
		return rawlinks;
	}
	
	public List<String> getFilteredLinks(){
		return filter.filterList(rawlinks);
	}
	
	
}
