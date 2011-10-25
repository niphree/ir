package ir.rank.socialpagerank.model;



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
	String get_main_sql_id(){
		String sql_ids  = "select d.id from DocumentTable as d order by d.id";
		return sql_ids;
	}
	String get_secondary_sql_id(){
		String sql_ids  = "select u.id from UserTagDocTable utd join utd.user u where utd.doc=?";
		return sql_ids;
	}
	String get_main_sql_id_t(){
		String sql_ids  = "select u.id from UserTable as u order by u.id";
		return sql_ids;
	}
	String get_secondary_sql_id_t(){
		String sql_ids  = "select doc.id from UserTagDocTable utd join utd.doc doc where utd.user=?";
		return sql_ids;
	}

	@Override
	String get_row_sql() {
		String row_sql = "select max(id) from DocumentTable";
		return row_sql;
	}

	@Override
	String get_col_sql() {
		String col_sql = "select max(id) from UserTable";
		return col_sql;
	}
	@Override
	String get_name() {
		return "doc_user";
	}
	@Override
	public boolean nativ_sql() {
		return false;
	}
}
