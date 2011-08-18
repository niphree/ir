package ir.analyzer;

import java.util.List;

public class SearchDocument {
	Long id;
	String doc;
	String url;
	List<Object[]> tags;
	int users;
	String source = "DEL"; //for now, change if more
	String cache;
	
	public SearchDocument(Long id, String doc,  List<Object[]> tags, long users ) {
		this.id = id;
		this.doc = doc;
		
		this.tags = tags;
		//this.users = users;
	}
	
	
	public SearchDocument(Long id, String doc, String cache, String url) {
		this.id = id;
		this.doc = doc;
		this.cache = cache;
		this.url = url;


	}
	@Override
	public String toString() {
		String res = "id: " + id + " ,doc: " + doc;
		res += "<BR>";
		res += cache;		
		return res;
	}
	
	
	public String toString2() {
		String res = "id: " + id + " ,doc: " + doc;
		res = res + " <b>TAGS: </b> ";
		//System.out.println("tags:");
		if (tags != null)
			for (Object[] tag : tags){ 
				res += "[ " + tag[1] +" (x " + tag[2]+ ") ]";
			}
		
		return res;
	}
}
