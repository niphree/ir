package ir.database;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="USER")
public class UserTable {  //I FIGHT FOR THE USER!!!

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(unique = true)
	private String name;	
	
	@OneToMany(mappedBy = "user")
	List<UserTagDocTable> tags_document_list;
	
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
