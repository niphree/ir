package ir.analyzer;

import java.util.List;

public class SearchDocument {
	Long id;
	String doc;
	String url;
	List<Object[]> tags;
	int users;
	String source = "DEL"; //for now, change if more
	
	public SearchDocument(Long id, String doc, String url, List<Object[]> tags, long users ) {
		this.id = id;
		this.doc = doc;
		this.url = url;
		this.tags = tags;
		//this.users = users;
	}
	
	@Override
	public String toString() {
		String res = "id: " + id + " ,doc: " + doc;
		res = res + " <b>TAGS: </b> ";
		//System.out.println("tags:");
		for (Object[] tag : tags){ 
			res += "[ " + tag[1] +" (x " + tag[2]+ ") ]";
		}
		
		return res;
	}
}
