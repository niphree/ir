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
	public String get_secondary_sql_id(){
		String sql_ids  = "select document.new_id, user.new_id, how_much "+
			" from usertagdoc , document , user " +
		    " where usertagdoc.doc_id = document.id " +
		    " and usertagdoc.user_id = user.id " +
			" order by document.new_id, user.new_id ";
		
		return sql_ids;
	}

	@Override
	public String get_secondary_sql_id_t(){
		String sql_ids  = "select u.new_id, d.new_id,  how_much "+
		" from usertagdoc utd, document d, user u " +
		" where utd.doc_id = d.id " +
		" and utd.user_id = u.id " +
		" order by u.new_id, d.new_id ";
		return sql_ids;
	}

	@Override
	public String get_row_sql() {
		String row_sql = "select max(new_id) from DocumentTable";
		return row_sql;
	}

	@Override
	public String get_col_sql() {
		String col_sql = "select max(new_id) from UserTable";
		return col_sql;
	}
	
	
	@Override
	public String get_name() {
		return DocumentUserMatrixSource.get_static_name();
	}
	@Override
	public boolean nativ_sql() {
		return true;
	}
	
	public static String get_static_name(){
		return "doc_user";
	}
}
