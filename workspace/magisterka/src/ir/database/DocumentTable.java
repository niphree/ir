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
	
	@Column(columnDefinition = "int(11) default 0")
	int facebook_value;
	
	@Column(columnDefinition = "int(11) default 0")
	int digg_value;
	
	@Column(columnDefinition = "int(11) default 0")
	int twitter_value;
	
	
	@Column(nullable = true, columnDefinition="text")
	String tags_cashe1;
	
	@Column(columnDefinition = "bool DEFAULT FALSE")
	Boolean top_delicous;
	
	@Column(columnDefinition = "bool DEFAULT false")
	Boolean page_fetch;
	
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
	
	
	public void setTwitter_value(int twitter_value) {
		this.twitter_value = twitter_value;
	}
	
	public int getTwitter_value() {
		return twitter_value;
	}
	
	public void setDigg_value(int digg_value) {
		this.digg_value = digg_value;
	}
	
	public int getDigg_value() {
		return digg_value;
	}
	
	public void setFacebook_value(int facebook_value) {
		this.facebook_value = facebook_value;
	}
	
	public int getFacebook_value() {
		return facebook_value;
	}
	
	public void setTags_cashe1(String tags_cashe) {
		this.tags_cashe1 = tags_cashe;
	}
	
	public String getTags_cashe1() {
		return tags_cashe1;
	}
	
	public void setTop_delicous(boolean top_delicous) {
		this.top_delicous = top_delicous;
	}
	
	public boolean getTop_delicous() {
		if (this.top_delicous == null)
			return false;
		return this.top_delicous;
	}

	public Boolean getPage_fetch() {
		if (page_fetch == null)
			page_fetch = false;
		return page_fetch;
	}
	
	public void setPage_fetch(Boolean page_fetch) {
		this.page_fetch = page_fetch;
	}
}
