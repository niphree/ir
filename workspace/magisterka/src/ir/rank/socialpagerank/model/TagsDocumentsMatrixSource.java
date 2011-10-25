package ir.rank.socialpagerank.model;



public class TagsDocumentsMatrixSource extends AbstractMatrixSource{

	
	public TagsDocumentsMatrixSource(int row, int col) {
		super(row, col);
		// TODO Auto-generated constructor stub
	}
	String get_main_sql_id(){
		String sql_ids  = "select t.id from TagTable as t order by t.id";
		return sql_ids;
	}
	String get_secondary_sql_id(){
		String sql_ids  = "select tag_id, doc_id " +
						  "from tag_doc " +
						  "order by tag_id";
		return sql_ids;
	}
	String get_main_sql_id_t(){
		String sql_ids  = "select d.id from DocumentTable as d order by d.id";
		return sql_ids;
	}
	String get_secondary_sql_id_t(){
		String sql_ids  = "select doc_id, tag_id " +
						  "from tag_doc " +
						  "order by doc_id ";
		return sql_ids;
	}


	@Override
	String get_row_sql() {
		String row_sql = "select max(id) from TagTable";
		return row_sql;
	}

	@Override
	String get_col_sql() {
		String col_sql = "select max(id) from DocumentTable";
		return col_sql;
	}

	
	@Override
	String get_name() {
		return "tag_doc";
	}
	@Override
	public boolean nativ_sql() {
		return true;
	}

}
