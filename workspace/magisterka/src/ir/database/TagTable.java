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
