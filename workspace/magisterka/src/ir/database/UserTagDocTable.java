package ir.database;

import java.util.ArrayList;
import java.util.List;



public class UserTagDocTable {

	

	private Long id;
	

	private UserTable user;
	

	private DocumentTable doc;
	

	private List<TagTable> tags;
	

	private Integer how_much;
	
	public UserTagDocTable() {
	}
	

	public void setUser(UserTable user) {
		this.user = user;
		
	}
	public UserTable getUser() {
		return user;
	}
	
	public void setDoc(DocumentTable doc) {
		this.doc = doc;
	}
	public DocumentTable getDoc() {
		return doc;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setTags(List<TagTable> tags) {
		this.tags = tags;
	}
	public void update_Tags(List<TagTable> tags) {
		if (this.tags == null) 
			this.tags = new ArrayList<TagTable>();
		this.tags.addAll(tags);
		
	}
	
	public List<TagTable> getTags() {
		if (tags == null)
			tags = new ArrayList<TagTable>();
		return tags;
	}


	public void add_tag(TagTable tag) {
		if (this.tags == null) 
			this.tags = new ArrayList<TagTable>();
		this.tags.add(tag);
		
	}
	
	public Integer get_how_much() {
		if (how_much == null)
			how_much = 0;
		return how_much;
	}


	public void add_how_much(Integer how_much) {
		this.how_much = how_much;
		
	}
	
}
