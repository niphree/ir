package ir.database;

import java.util.ArrayList;
import java.util.List;


public class UserTable {  //I FIGHT FOR THE USER!!!


	private Long id;
	

	private String name;	
	

	List<UserTagDocTable> tags_document_list;
	

	private Long doc_count;
	

	private Long tag_count; //ile razy uzytkownik tagowal cos - wszystkie tagi uzyte w dokumentach 
	

	private Long tag_dist_count; // ile tagow /roznych/ uzytkownik uzyl
	

	Boolean new_data;   //dopiero dodane
	

	private Double adapted_page_rank;
	
	public UserTable() { }
	public UserTable(String name) { this.name = name; }
	
	
	
	/** STANDARDS GETTERS/SETTERS FOR HIBERNATE **/
	
	
	
	public void setId(Long id) { this.id = id; }
	public Long getId() { return id; }
	
	public void setTag_document(UserTagDocTable tags_document){
		if (this.tags_document_list == null)
			this.tags_document_list = new ArrayList<UserTagDocTable>();
		tags_document_list.add(tags_document);
		tags_document.setUser(this);
	
	}
	
	public void setTags_document(List<UserTagDocTable> tags_document) {
		this.tags_document_list = tags_document;
	}
	
	public List<UserTagDocTable> getTags_document() {
		return tags_document_list;
	}
	
	
	public void setName(String name){ this.name = name; }
	public String getName(){ return name; }
	
	public Boolean get_new_data() {
		if (new_data == null)
			new_data = true;
		return new_data;
	}
	
	public void set_new_data(Boolean new_data) {
		this.new_data = new_data;
	}
	
	
	public void set_doc_count(Long doc_count) {
		this.doc_count = doc_count;
	}
	public Long get_doc_count() {
		if (doc_count == null)
			return Long.valueOf(0);
		return doc_count;
	}

	
	public void set_tag_count(Long tag_count) {
		this.tag_count = tag_count;
	}
	public Long get_tag_count() {
		if (tag_count == null)
			return Long.valueOf(0);
		return tag_count;
	}
	
	public void set_tag_dist_count(Long tag_dist_count) {
		this.tag_dist_count = tag_dist_count;
	}
	public Long get_tag_dist_count() {
		if (tag_dist_count == null)
			return Long.valueOf(0);
		return tag_dist_count;
	}
	
	public void set_adapted_page_rank(Double adapted_page_rank) {
		this.adapted_page_rank = adapted_page_rank;
	}
	public double get_adapted_page_rank() {
		if (adapted_page_rank == null)
			return Double.valueOf(0);
		return adapted_page_rank;
	}
	
	/*@ManyToMany(targetEntity=TagTable.class)
	private Set<TagTable> tags;
	*/
	
	/*
	public void addTag(TagTable tag){
		if (tags == null)
			tags = new HashSet<TagTable>();
		this.tags.add(tag); 
	}*/
	
	/*
	public void setTags(Set<TagTable> tags){ this.tags = tags; }
	public Set<TagTable> getTags(){ return tags; }
	*/
	
	
}
