package ir.rank.socialpagerank.model;



public class TagsDocumentsMatrixSource extends AbstractMatrixSource{

	String get_main_sql_id(){
		String sql_ids  = "select t.id from TagTable as t order by t.id";
		return sql_ids;
	}
	String get_secondary_sql_id(){
		String sql_ids  = "select d.id from UserTagDocTable utd join utd.doc d join utd.tags t where t.id=?";
		return sql_ids;
	}
	String get_main_sql_id_t(){
		String sql_ids  = "select d.id from DocumentTable as d order by d.id";
		return sql_ids;
	}
	String get_secondary_sql_id_t(){
		String sql_ids  = "select t.id from UserTagDocTable utd join utd.tags t where utd.doc=?";
		return sql_ids;
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
	String get_name() {
		return "tag_doc";
	}

}
