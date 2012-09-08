package ir.rank.socialpagerank.model;

import ir.rank.common.model.AbstractMatrixSource;



public class UsersTagsMatrixSource extends AbstractMatrixSource{

	public UsersTagsMatrixSource(int row, int col) {
		super(row, col);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public String get_secondary_sql_id(){
		String sql_ids  = "select user_id, tag_id, how_much " +
		  "from tag_usr " +
		  "order by user_id, tag_id ";
		return sql_ids;
	}

	
	@Override
	public String get_secondary_sql_id_t(){
		String sql_ids  = "select tag_id, user_id, how_much " +
		  				  "from tag_usr " +
		  				  "order by tag_id, user_id";
		return sql_ids;
	}
	
	@Override
	public String  get_row_sql() {
		String row_sql = "select max(new_id) from UserTable";
		return row_sql;
	}

	@Override
	public String get_col_sql() {
		String col_sql = "select max(new_id) from TagTable";
		return col_sql;
	}

	
	
	@Override
	public String get_name() {
		return UsersTagsMatrixSource.get_static_name();
	}
	
	public static String get_static_name(){
		return "user_tag";
	}
	
	@Override
	public boolean nativ_sql() {
		return true;
	}

}
