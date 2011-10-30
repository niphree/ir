package ir.rank.adaptedpagerank.model;

import ir.rank.common.model.AbstractMatrixSource;
import ir.rank.socialpagerank.model.DocumentUserMatrixSource;
import ir.rank.socialpagerank.model.TagsDocumentsMatrixSource;
import ir.rank.socialpagerank.model.UsersTagsMatrixSource;
import ir.util.FileUtils;

import java.util.List;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.impl.SparseDoubleMatrix2D;
import cern.colt.matrix.linalg.Blas;
import cern.colt.matrix.linalg.SmpBlas;

public class DocUserTagMatrixSource extends AbstractMatrixSource{

	int doc_size;
	int user_size;
	int tag_size;
	
	public DocUserTagMatrixSource(int row, int col) {
		super(row, col);
	}

	public DocUserTagMatrixSource(int doc_size, int user_size, int tag_size) {
		super(doc_size + user_size + tag_size, doc_size + user_size + tag_size);
		this.doc_size = doc_size;
		this.user_size = user_size;
		this.tag_size = tag_size;
	}
	
	int main_row_counter = 0;
	
	public List<Object[]> list_hash_matrix_1 = null;
	public List<Object[]> list_hash_matrix_2 = null;
	
	String current_filename_1;
	String current_filename_2;
	
	int current_file_count_1 = 0;
	int current_file_count_2 = 0;
	
	
	public String get_real_file_name(int file_current, int column){
		//return (get_name()+"_t_" + file_current + ".out");
		if (column == 1){
			if ( main_row_counter < doc_size ){
				return DocumentUserMatrixSource.get_static_name()+ "_" + file_current + ".out";
			}
			if ( main_row_counter < doc_size + user_size ){
				return DocumentUserMatrixSource.get_static_name()+ "_t_" + file_current + ".out";
			}
			if ( main_row_counter >= doc_size + user_size ){
				return TagsDocumentsMatrixSource.get_static_name()+ "_" + file_current + ".out";
			}
		}
		else if (column == 2){
			if ( main_row_counter < doc_size ){
				return TagsDocumentsMatrixSource.get_static_name() + "_t_" + file_current + ".out";
			}
			if ( main_row_counter < doc_size + user_size ){
				return UsersTagsMatrixSource.get_static_name() + "_" + file_current + ".out";
			}
			if ( main_row_counter >= doc_size + user_size ){
				return UsersTagsMatrixSource.get_static_name() + "_t_" + file_current + ".out";
			}
		}
		return null;
	}

	public void add_elem(Object[] elem, SparseDoubleMatrix2D matrix_object, int current_row, int column_offset){
		Object[] tem_elem = (Object[])elem[1];
		
		for (Object column : tem_elem) {
			//System.out.println(column);
			//System.out.println(column.getClass());
			//System.out.println(column.getClass().getCanonicalName());
			int[] c = (int[])column;
			
				
			matrix_object.set(current_row, column_offset+c[0]-1, c[1]);
		} 
	}
	
	public int get_offset(int column){
		if (column == 1){
			if (main_row_counter < doc_size)
				return doc_size;
			if (main_row_counter >= doc_size)
				return 0;
			
		}
		else if (column == 2){
			if (main_row_counter < doc_size + user_size)
				return doc_size + user_size;
			if (main_row_counter >= doc_size + user_size)
				return doc_size;
		}
		return 0;
	}
	
	public int get_current_interval(){
		int size = list_hash_matrix_1.size();
		int size2 = list_hash_matrix_1.size();
		if (size != size2)
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		if ( size - current_list_matrix_elem < max_interval ) 
			return size - current_list_matrix_elem;
		return max_interval;
	}
	
	@SuppressWarnings("unchecked")
	public SparseDoubleMatrix2D get_part_matrix(){
		//   doc usr tag
		//doc 0
		//usr     0
		//tag         0
		if (main_row_counter >=doc_size+user_size+tag_size) return null;
		//boolean read_secound = false;
		if (list_hash_matrix_1 == null || list_hash_matrix_2 == null){
			if (main_row_counter == doc_size || 
					main_row_counter == doc_size + user_size ||
					main_row_counter == doc_size + user_size + tag_size )
				current_file_count = 0;
			current_filename_1 = get_real_file_name(current_file_count, 1);
			current_filename_2 = get_real_file_name(current_file_count, 2);
		
		//	System.out.println(list_hash_matrix_1);
			System.out.println("reading file:" + current_filename_1);
			list_hash_matrix_1 = (List<Object[]>)FileUtils.open_file(current_filename_1); // odczytac zawartosc pliku
			System.out.println("reading file:" + current_filename_2);
			list_hash_matrix_2 = (List<Object[]>)FileUtils.open_file(current_filename_2); // odczytac zawartosc pliku
			//read_secound = true;
			//list_hash_matrix_2 = list_hash_matrix_1;
			if (list_hash_matrix_1 == null || list_hash_matrix_2 == null){
				System.out.println("null file1: " + (list_hash_matrix_1 == null));
				System.out.println("null file2: " + (list_hash_matrix_2 == null));
				return null;
			}
			
			current_list_matrix_elem = 0;
			current_file_count++;
		}
		
		int offset_1 = get_offset(1);
		int offset_2 = get_offset(2);
				
		int current_interval = get_current_interval();
		SparseDoubleMatrix2D matrix_object = new SparseDoubleMatrix2D(
				current_interval, 
				(int)get_actual_col()); //row/col
		
		System.out.println("col:" + (int)get_actual_col());
		
		int end_element =  current_list_matrix_elem + current_interval - 1;
		int current_row = 0;
		System.out.println("creating partial matrix:");
		Blas blas = SmpBlas.smpBlas;
		while (current_list_matrix_elem <= end_element ){
			//System.out.println(current_list_matrix_elem);
			Object[] tmp_1 = list_hash_matrix_1.get(current_list_matrix_elem);
			Object[] tmp_2 = list_hash_matrix_2.get(current_list_matrix_elem);
			
			
			add_elem(tmp_1, matrix_object, current_row, offset_1);
			add_elem(tmp_2, matrix_object, current_row, offset_2);
			
			DoubleMatrix1D row = matrix_object.viewRow(current_row);
			double norm = blas.dnrm2(row);
			for (int column = 0; column<matrix_object.columns(); column++){
				matrix_object.set(
						current_row, 
						column, 
						matrix_object.get(current_row, column)/norm
						);
			}
			
			current_row++;
			main_row_counter++;
			current_list_matrix_elem++;
			matrix_object.trimToSize();
		}
		
		if (current_list_matrix_elem == list_hash_matrix_1.size()){
			list_hash_matrix_1 = null;
			list_hash_matrix_2 = null;
		}
		
			
		matrix_object.trimToSize();
		return matrix_object;
	}
	
	
	
	
	@Override
	public String get_main_sql_id_t() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String get_main_sql_id() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String get_secondary_sql_id_t() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String get_secondary_sql_id() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String get_row_sql() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String get_col_sql() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String get_name() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean nativ_sql() {
		// TODO Auto-generated method stub
		return false;
	}

	
	
}
