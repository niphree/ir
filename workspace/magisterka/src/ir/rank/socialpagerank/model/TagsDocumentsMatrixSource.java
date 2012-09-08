package ir.rank.socialpagerank.model;

import ir.rank.common.model.AbstractMatrixSource;



public class TagsDocumentsMatrixSource extends AbstractMatrixSource{

	
	public TagsDocumentsMatrixSource(int row, int col) {
		super(row, col);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String get_secondary_sql_id(){
		String sql_ids  = "select tag_id, doc_id, how_much " +
						  "from tag_doc " +
						  "order by tag_id, tag_id";
		return sql_ids;
	}

	
	
	@Override
	public String get_secondary_sql_id_t(){
		String sql_ids  = "select doc_id, tag_id, how_much " +
						  "from tag_doc " +
						  "order by doc_id, tag_id ";
		return sql_ids;
	}


	@Override
	public String get_row_sql() {
		String row_sql = "select max(new_id) from TagTable";
		return row_sql;
	}

	@Override
	public String get_col_sql() {
		String col_sql = "select max(new_id) from DocumentTable";
		return col_sql;
	}

	
	@Override
	public String get_name() {
		return TagsDocumentsMatrixSource.get_static_name();
	}
	
	
	public static String get_static_name(){
		return "tag_doc";
	}
	
	@Override
	public boolean nativ_sql() {
		return true;
	}

}
