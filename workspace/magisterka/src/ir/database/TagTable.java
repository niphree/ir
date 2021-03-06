package ir.database;



public class TagTable {
	
	
	private Long id;
	
	
	private String tag;
	

	private Long user_count; //ilu uzytkownikow (distinct) uzylo tego tagu 
	

	private Long doc_dist_count; //ile dokumentow (distinct) jest otagowanych tym tagiem
	

	private Long doc_count; //ile dokumentow jest otagowanych tym tagiem - z powtorzeniami
	

	private Double adapted_page_rank;
	
	
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
	
	public void set_adapted_page_rank(Double adapted_page_rank) {
		this.adapted_page_rank = adapted_page_rank;
	}
	public double get_adapted_page_rank() {
		if (adapted_page_rank == null)
			return Double.valueOf(0);
		return adapted_page_rank;
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
