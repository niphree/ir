package ir.rank.socialpagerank.model;



public class TagsDocumentsMatrixSource extends AbstractMatrixSource{

	@Override
	public
	void init() {
		// TODO Auto-generated method stub
		
	}



	@Override
	String get_row_sql() {
		String row_sql = "select count(*) from TagTable";
		return row_sql;
	}

	@Override
	String get_col_sql() {
		String col_sql = "select count(*) from DocumentTable";
		return col_sql;
	}



	@Override
	String get_main_sql_id_t() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	String get_main_sql_id() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	String get_secondary_sql_id_t() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	String get_secondary_sql_id() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	String get_name() {
		return "tag_doc";
	}

}
