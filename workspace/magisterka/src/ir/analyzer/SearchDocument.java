package ir.analyzer;

public class SearchDocument {
	Long id;
	String doc;
	public SearchDocument(Long id, String doc) {
		this.id = id;
		this.doc = doc;
	}
	
	@Override
	public String toString() {
		return "id: " + id + " ,doc: " + doc;
	}
}
