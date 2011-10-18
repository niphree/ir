package ir.rank.socialpagerank.model;

import cern.colt.matrix.impl.SparseDoubleMatrix2D;


public class DocumentUserMatrixSource extends AbstractMatrixSource{

	
	/* doc1 - u1, u2, u3,... un
	 * doc2 - u1, u2, u3,... un
	 * ...     ................
	 * docn - u1, u2, u3,... un
	 * 
	 * T:
	 * u1   - doc1, doc2, ... docn
	 * u2   - doc1, doc2, ... docn
	 * ...     ................
	 * un   - doc1, doc2, ... docn
	 */

	String sql_ids  = "select d.id from documenttable as d";
	
	String sql_ids2 = "select u.id from usertable as ut join usertagdoctable as utd where utd.doc_id=?";
	
	@Override
	public
	void init() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public SparseDoubleMatrix2D get_part_matrix() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public SparseDoubleMatrix2D get_part_t_matrix() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	String get_row_sql() {
		String row_sql = "select count(*) from DocumentTable";
		return row_sql;
	}

	@Override
	String get_col_sql() {
		String col_sql = "select count(*) from UserTable";
		return col_sql;
	}

}
