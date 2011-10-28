package ir.rank.socialpagerank.model;

import ir.rank.common.model.AbstractMatrixSource;



public class DocumentUserMatrixSource extends AbstractMatrixSource{

	
	/* doc1 - u1, u2, u3,... un
	 * doc2 - u1, u2, u3,... un
	 * ...     ................
	 * docn - u1, u2, u3,... un
	 * p
	 * T:
	 * u1   - doc1, doc2, ... docn
	 * u2   - doc1, doc2, ... docn
	 * ...     ................
	 * un   - doc1, doc2, ... docn
	 */

	public DocumentUserMatrixSource(int row, int col) {
		super(row, col);
		// TODO Auto-generated constructor stub
	}
	@Override
	public String get_main_sql_id(){
		String sql_ids  = "select d.id from DocumentTable as d order by d.id";
		return sql_ids;
	}
	@Override
	public String get_secondary_sql_id(){
		String sql_ids  = "select doc_id, user_id, how_much"+
			" from usertagdoc  "+
			" order by doc_id";
		
		return sql_ids;
	}
	@Override
	public String get_main_sql_id_t(){
		String sql_ids  = "select u.id from UserTable as u order by u.id";
		return sql_ids;
	}
	@Override
	public String get_secondary_sql_id_t(){
		String sql_ids  = "select user_id, doc_id,  how_much "+
		" from usertagdoc " +
		" order by user_id";
		return sql_ids;
	}

	@Override
	public String get_row_sql() {
		String row_sql = "select max(id) from DocumentTable";
		return row_sql;
	}

	@Override
	public String get_col_sql() {
		String col_sql = "select max(id) from UserTable";
		return col_sql;
	}
	@Override
	public String get_name() {
		return "doc_user";
	}
	@Override
	public boolean nativ_sql() {
		return true;
	}
}
