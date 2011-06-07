package ir.database;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "DOCUMENT")
public class DocumentTable {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(unique = true, nullable = false)
	private String url;
	
	@OneToMany(mappedBy = "doc", cascade = {CascadeType.REMOVE})
	List<UserTagDocTable> tags_document_list;
	
	public DocumentTable(){ }
	public DocumentTable(String url){ this.url = url; }
	
	/** STANDARDS GETTERS/SETTERS FOR HIBERNATE **/

	
	public void setId(Long id) { this.id = id; }
	public Long getId() { return id; }
	
	public void setTags_document_list(List<UserTagDocTable> tags_document_list) {
		this.tags_document_list = tags_document_list;
	}
	
	public List<UserTagDocTable> getTags_document_list() {
		return tags_document_list;
	}
	
	public void setTag_document(UserTagDocTable tags_document){
		//System.out.println("!!!!!!!!!!!!");
		if (this.tags_document_list == null)
			this.tags_document_list = new ArrayList<UserTagDocTable>();
		tags_document_list.add(tags_document);
		tags_document.setDoc(this);
	
	}
	
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getUrl() {
		return url;
	}
	

}
