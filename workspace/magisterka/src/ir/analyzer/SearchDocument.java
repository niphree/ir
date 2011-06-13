package ir.analyzer;

import ir.gwt.shared.DatabaseResults;

import com.google.common.collect.LinkedHashMultiset;

public class SearchDocument {
	Long id;
	String doc;
	String url;
	LinkedHashMultiset<DatabaseResults> tags;
	LinkedHashMultiset<DatabaseResults> users;
	String source = "DEL"; //for now, change if more
	
	public SearchDocument(Long id, String doc, String url, LinkedHashMultiset<DatabaseResults> tags, LinkedHashMultiset<DatabaseResults> users) {
		this.id = id;
		this.doc = doc;
		this.url = url;
		this.tags = tags;
		this.users = users;
	}
	
	@Override
	public String toString() {
		String res = "id: " + id + " ,doc: " + doc;
		res = res + " <b>TAGS: </b> ";
		for (DatabaseResults tag : tags){
			res += "[ " + tag.value +" (x " + tags.count(tag)+ ") ]";
		}
		res = res + " <b>USERS: </b> ";
		for (DatabaseResults user : users){
			res += "[ " + user.value +" (x " + users.count(user)+ ") ]";
		}
		return res;
	}
}
