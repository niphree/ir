package ir.database;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="USERTAGDOC")
public class UserTagDocTable {

	
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne (targetEntity = UserTable.class, cascade = {CascadeType.REMOVE})
	private UserTable user;
	
	@ManyToOne(targetEntity = DocumentTable.class, cascade = {CascadeType.REMOVE})
	private DocumentTable doc;
	
	@ManyToMany(targetEntity = TagTable.class, cascade = {CascadeType.REMOVE})
	private List<TagTable> tags;
	
	
	public UserTagDocTable() {
	}
	
	
	/*
	 STD SETTER AND GETTER - 
	 
	 HIBERNATE SUCKS, 
	 HIBERNATE, WHY DONT YOU LEARN FROM DJANGO
	 PYTHON AND DJANGO RULEZ !
	 
	 */
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
	
	public List<TagTable> getTags() {
		return tags;
	}
	
	
	
}
