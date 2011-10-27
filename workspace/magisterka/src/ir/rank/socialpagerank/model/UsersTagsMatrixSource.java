package ir.rank.socialpagerank.model;



public class UsersTagsMatrixSource extends AbstractMatrixSource{

	public UsersTagsMatrixSource(int row, int col) {
		super(row, col);
		// TODO Auto-generated constructor stub
	}
	String get_main_sql_id(){
		String sql_ids  = "select u.id from UserTable as u order by u.id";
		return sql_ids;
	}
	String get_secondary_sql_id(){
		String sql_ids  = "select user_id, tag_id, how_much " +
		  "from tag_usr " +
		  "order by user_id ";
		return sql_ids;
	}
	String get_main_sql_id_t(){
		String sql_ids  = "select t.id from TagTable as t order by t.id";
		return sql_ids;
	}
	String get_secondary_sql_id_t(){
		String sql_ids  = "select tag_id, user_id, how_much " +
		  				  "from tag_usr " +
		  				  "order by tag_id";
		return sql_ids;
	}
	
	@Override
	String get_row_sql() {
		String row_sql = "select max(id) from UserTable";
		return row_sql;
	}

	@Override
	String get_col_sql() {
		String col_sql = "select max(id) from TagTable";
		return col_sql;
	}

	
	
	@Override
	String get_name() {
		return "user_tag";
	}
	@Override
	public boolean nativ_sql() {
		return true;
	}

}
