package ir.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="TAG")
public class TagTable {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(unique = true, nullable = false)
	private String tag;
	
	@Column()
	private Long user_count; //ilu uzytkownikow (distinct) uzylo tego tagu 
	
	@Column()
	private Long doc_dist_count; //ile dokumentow (distinct) jest otagowanych tym tagiem
	
	@Column()
	private Long doc_count; //ile dokumentow jest otagowanych tym tagiem - z powtorzeniami
	
	
	/*
	@OneToMany(targetEntity = UserTable.class)
	private Set<UserTable>  users;
	*/
	
	public TagTable() {	}
	public TagTable(String tag) { this.tag = tag; }
	
	/** STANDARDS GETTERS/SETTERS FOR HIBERNATE **/
	
	public void setId(Long id) { this.id = id; }
	public Long getId() { return id; }
	
	public void setTag(String tag){ this.tag = tag;}
	public String getTag(){ return tag;}
	
	public Long get_user_count() {
		if (user_count == null)
			return Long.valueOf(0);
		return user_count;
	}
	
	public void set_user_count(Long user_count) {
		this.user_count = user_count;
	}
	
	public Long get_doc_dist_count() {
		if (doc_dist_count == null)
			return Long.valueOf(0);
		return doc_dist_count;
	}
	
	public void set_doc_dist_count(Long doc_dist_count) {
		this.doc_dist_count = doc_dist_count;
	}
	
	
	public Long get_doc_count() {
		if (doc_count == null)
			return Long.valueOf(0);
		return doc_count;
	}
	
	public void set_doc_count(Long doc_count) {
		this.doc_count = doc_count;
	}
	
	
	/*
	public void addUser(UserTable user){
		if (users == null) 
			users = new HashSet<UserTable>();
		users.add(user);
	}
	
	public void setUsers(Set<UserTable> users){ this.users = users; }
	public Set<UserTable> getUsers(){ return users; }
	*/
	
}
