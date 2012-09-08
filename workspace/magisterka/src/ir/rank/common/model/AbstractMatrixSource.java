package ir.rank.common.model;

import ir.connector.ConnectorFactory;
import ir.util.FileUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cern.colt.matrix.impl.SparseDoubleMatrix2D;

// leniwe pobieranie kolumn albo wierszy w zaleznosci od 
// tego czy chcemy transpose matrix czy niet. 
public abstract class AbstractMatrixSource {

	public AbstractMatrixSource(int row, int col){
		max_row = row;
		max_col = col;
	}
	
	long max_row;
	long max_col;
	public int max_interval; 
	
	boolean transpose;
	
	// { [ id1 [id11, id12 , ...]  ] , [ id2 [id21, id22 , ...]  ] ,   .... [id_n ,[]] } 
	public List<Object[]> list_hash_matrix = null;
	public int current_list_matrix_elem;
	
	int current = 0;
	int current_global_row_element = 0;
	
	String current_filename = null;
	public int current_file_count = 0;
	/*
	 * this function calculate number of row/col
	 * intervals for trans=true/false
	 * 
	 */

	abstract public String get_secondary_sql_id_t();
	abstract public String get_secondary_sql_id();
	abstract public String get_row_sql();
	abstract public String get_col_sql();
	abstract public String get_name();
	abstract public boolean nativ_sql();
	
	public final void init(){
		//System.out.println("calc interval");
		calculate_interval();
	}
	
	public final int get_max_interval(){
		return max_interval;
	}

	String get_real_file_name(int file_current){
		if (transpose) return (get_name()+"_t_" + file_current + ".out");
		else return  (get_name()+"_" + file_current + ".out");
	}

	private void add_elements(List<Integer[]> tmp_list, int id){
		Object[] tmp_val_array = new Object[(tmp_list.size())];
		int i=0;
		for (Integer[] elem: tmp_list){
			int[] tmp = {elem[0], elem[1]};
			tmp_val_array[i] = tmp;
			i++;
		}
		Object[] tmp_array = {id , tmp_val_array}; 
		list_hash_matrix.add(tmp_array);
	}
	
	//THIS!!!!!
	@SuppressWarnings("unchecked")
	public final void create_file_native() throws SQLException, ClassNotFoundException{
		
		list_hash_matrix = new ArrayList<Object[]>();

		
		String sql2 = null;
		int file_interval = 50000;
		//int db_interval = 10;
		int db_interval = 2000000;
		int id_from = 0;
		int file_current = 0;
		
		int prev_id = 1;
		List<Integer[]> tmp_list = new ArrayList<Integer[]>(); 
		
		if (transpose) sql2 = get_secondary_sql_id_t();
		else sql2 = get_secondary_sql_id();
		
		ConnectorFactory cf = ConnectorFactory.instance();
		
		ResultSet rs = cf.execute(sql2 + " limit " + db_interval + " offset " + id_from);
		
	    int size =0;  
	    if (rs != null)   
	    {  
	      rs.beforeFirst();  
	      rs.last();  
	      size = rs.getRow();  
	    }  
		rs.beforeFirst();
		
		//boolean error = false;
		while(size>0){
			
			System.out.println("TICK !! " + id_from + " - " + (id_from + db_interval));
			System.out.println("size:" + size);
			System.out.println(list_hash_matrix.size());
			

			while (rs.next()){
				int ob_id 	  =   rs.getInt(1);
				int ob_id_val =   rs.getInt(2);
				int ob_id_count = rs.getInt(3);
				//System.out.println(ob_id + ", " +ob_id_val + ", " + ob_id_count);

				
				
				
				if (ob_id != prev_id){
					while (ob_id != prev_id){
						
						add_elements(tmp_list, prev_id);
						
						prev_id++;
						tmp_list = new ArrayList<Integer[]>();
						
						//System.out.println(list_hash_matrix.size());
						if (list_hash_matrix.size() >= file_interval){
							System.out.println("current: " + prev_id + ", " + ob_id );
						//save to file
							System.out.println("saving, len: " + list_hash_matrix.size());
							FileUtils.save_file(get_real_file_name(file_current), list_hash_matrix);
							file_current++;
							
							list_hash_matrix = new ArrayList<Object[]>();
						}
						
					}
				}
				if (ob_id == prev_id){
					//System.out.println(ob_id);
					Integer tmp[] = {ob_id_val, ob_id_count};
					tmp_list.add(tmp);
				}
			}

			id_from = id_from + db_interval;
			//new ids to process
			rs = cf.execute(sql2 + " limit " + db_interval + " offset " + id_from);
			if (rs != null)   
		    {  
			      rs.beforeFirst();  
			      rs.last();  
			      size = rs.getRow();  
			    }  
			rs.beforeFirst();
		}
		add_elements(tmp_list, prev_id);
		System.out.println("saving, len: " + list_hash_matrix.size());
		FileUtils.save_file(get_real_file_name(file_current), list_hash_matrix);

		
	}
	
	public int get_current_interval(){
		int size = list_hash_matrix.size();
		if ( size - current_list_matrix_elem < max_interval ) 
			return size - current_list_matrix_elem;
		return max_interval;
	}
	
	
	/*
	 * stworz maciez o: 
	 * szerokosci - szerokosc tabeli (maksymalny indeks) 
	 * wysokosci = min: start_index-end_index | wysokosc_tabeli - current start indeks
	 * 
	 * wypelnij macierz danymi
	 * 
	 */
	@SuppressWarnings("unchecked")
	public SparseDoubleMatrix2D get_part_matrix() {
		//sprawdzenie czy nie ma juz odserializowanego obiektu jako pola
		if (list_hash_matrix == null){
			System.gc();
			current_filename = get_real_file_name(current_file_count);
			System.gc();
			list_hash_matrix = (List<Object[]>)FileUtils.open_file(current_filename); // odczytac zawartosc pliku
			if (list_hash_matrix == null){
				//sprawdzic czy nie trzeba dorobic reszty macierzy:
				//
				if (current_global_row_element >= get_actual_rows()){
				//	System.out.println("FILE IS NULL");
				//	System.out.println("rows: " +  current_global_row_element);
				//	System.out.println("max: " + get_actual_rows());
					return null;
				}
				else {
					// wybrac co jest wieksze: interval czy reszta elementów,
					int current_rows = 0;
					//System.out.println("rows: "+ current_global_row_element + ", max inter: " + max_interval + ", max: " + get_actual_rows());
					if (current_global_row_element + max_interval <= get_actual_rows() )
						current_rows = max_interval;
					else
						current_rows = (int)(get_actual_rows() - (long)current_global_row_element);
					
					SparseDoubleMatrix2D matrix_object = new SparseDoubleMatrix2D(
							current_rows, 
							(int)get_actual_col()); //row/col
					//dodac current rows do current_global_row_element
					current_global_row_element = current_global_row_element + current_rows;
					//zwrocic macierz o takich paramatrach
					matrix_object.trimToSize();
					return matrix_object;
					
					
				}
					
			}
			current_list_matrix_elem = 0;
			current_file_count++;
		}
		
		// obliczyc obiecny interval
		int current_interval = get_current_interval();
		//System.out.println("HASH MATRIX SIZE: " + list_hash_matrix.size());
	
		
		SparseDoubleMatrix2D matrix_object = new SparseDoubleMatrix2D(
				current_interval, 
				(int)get_actual_col()); //row/col
		
		int end_element =  current_list_matrix_elem + current_interval - 1;
		int current_row = 0;
		while (current_list_matrix_elem <= end_element ){

			Object[] tmp = list_hash_matrix.get(current_list_matrix_elem);
			Object[] elem = (Object[])tmp[1];
			//System.out.println("current row:" + current_row);
			for (Object column : elem) {
				int[] c = (int[])column;

				//System.out.println(c[0]-1);
				
				matrix_object.set(current_row, c[0]-1, c[1]);
				//matrix_object.trimToSize();
			} 
			current_list_matrix_elem++;
			current_global_row_element++;
			current_row++;
			//matrix_object.trimToSize();
			//System.gc();
			
		}
		if (current_list_matrix_elem == list_hash_matrix.size())
			list_hash_matrix = null;
		
		matrix_object.trimToSize();
		return matrix_object;
	}
	
	//use in init
	void calculate_interval(){
		int max = Integer.MAX_VALUE;
		double row_cols = get_max_row() * get_max_col();
		if (transpose)
			max_interval = (int)(max/get_max_row()); //row jest columna
		else max_interval = (int)(max/get_max_col()); //col jest normalnie columna
		
	}
	
	public long get_max_row(){
		return max_row;
	}
	public long get_max_col(){
		return max_col;
	}
	public void setTranspose(boolean transpose){
		this.transpose = transpose;
	}
	public long get_actual_rows(){
		if (transpose) return get_max_col();
		return get_max_row();
	}
	
	public long get_actual_col(){
		if (transpose) return get_max_row();
		return get_max_col();
	}
	
	
}
