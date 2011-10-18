package ir.rank.socialpagerank.model;

import cern.colt.matrix.impl.SparseDoubleMatrix2D;

// leniwe pobieranie kolumn albo wierszy w zaleznosci od 
// tego czy chcemy transpose matrix czy niet. 
public abstract class AbstractMatrixSource {

	int max_row;
	int max_col;
	boolean transpose;
	int interval; 
	
	int current = 0;
	/*
	 * this function calculate number of row/col
	 * intervals for trans=true/false
	 * 
	 */
	abstract public void init();
	
	abstract void init_max_row();
	abstract void init_max_col();
	
	public abstract SparseDoubleMatrix2D get_part_matrix();
	public abstract SparseDoubleMatrix2D get_part_t_matrix();
	
	
	/**
	 * @return null if all matrixes fetched
	 */
	public SparseDoubleMatrix2D get_matrix(){
		if (current >= get_actual_rows())
			return null;
		SparseDoubleMatrix2D matrix = null;
		
		if (transpose) 
			matrix = get_part_t_matrix();
		else matrix = get_part_matrix();
		current = current + interval;
		return matrix;
	}
	
	//use in init
	void calculate_interval(){
		int max = Integer.MAX_VALUE;
		double max_columns = get_max_row() * get_max_col();  
	}
	
	public int get_max_row(){
		return max_row;
	}
	public int get_max_col(){
		return max_col;
	}
	public void setTranspose(boolean transpose){
		this.transpose = transpose;
	}
	public int get_actual_rows(){
		if (transpose) return get_max_col();
		return get_max_row();
	}
	
	public int get_actual_col(){
		if (transpose) return get_max_row();
		return get_max_col();
	}
	
	
}
