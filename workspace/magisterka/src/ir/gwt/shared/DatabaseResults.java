package ir.gwt.shared;


public class DatabaseResults {

	public String value;
	public long id;
	
	public DatabaseResults(String value, long id){
		this.value = value;
		this.id = id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DatabaseResults) {
			DatabaseResults d_res = (DatabaseResults) obj;
			return value.equals(d_res.value);
			
		}
		return super.equals(obj);
		
	}
	
	
	
	
	@Override
	public int hashCode() {
		return value.hashCode();
	}
}
