package ir.rank.socialpagerank.model;

import cern.colt.matrix.impl.SparseDoubleMatrix2D;


public class UsersTagsMatrixSource extends AbstractMatrixSource{

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
		String row_sql = "select count(*) from UserTable";
		return row_sql;
	}

	@Override
	String get_col_sql() {
		String col_sql = "select count(*) from TagTable";
		return col_sql;
	}


}
