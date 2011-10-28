package ir.rank.common.model;

import ir.hibernate.HibernateUtil;
import ir.util.FileUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

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
	private int current_list_matrix_elem;
	
	int current = 0;
	
	String current_filename = null;
	int current_file_count = 0;
	/*
	 * this function calculate number of row/col
	 * intervals for trans=true/false
	 * 
	 */
	abstract public String get_main_sql_id_t();
	abstract public String get_main_sql_id();
	abstract public String get_secondary_sql_id_t();
	abstract public String get_secondary_sql_id();
	abstract public String get_row_sql();
	abstract public String get_col_sql();
	abstract public String get_name();
	abstract public boolean nativ_sql();
	
	public final void init(){
		System.out.println("calc interval");
		calculate_interval();
	}
	
	public final int get_max_interval(){
		return max_interval;
	}
	
	@SuppressWarnings("unchecked")
	final void  init_max_row() {
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		List<Long>  count = (List<Long>)session.
			createQuery(get_row_sql()).
			list();

		max_row = (long)count.get(0);
		
		tx.commit();
		session.close();
		
	}
	
	@SuppressWarnings("unchecked")
	final void init_max_col() {
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		List<Long>  count = (List<Long>)session.
			createQuery(get_col_sql()).
			list();

		max_col = (long)count.get(0);
		
		tx.commit();
		session.close();
		
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
	
	
	@SuppressWarnings("unchecked")
	public final void create_file_native(){
		list_hash_matrix = new ArrayList<Object[]>();
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		String sql2 = null;
		int file_interval = 500000;
		int id_from = 0;
		int file_current = 0;
		
		int prev_id = 1;
		List<Integer[]> tmp_list = new ArrayList<Integer[]>(); 
		
		if (transpose) sql2 = get_secondary_sql_id_t();
		else sql2 = get_secondary_sql_id();
		
		List<Object[]> objects_id = (List<Object[]>)session.
			createSQLQuery(sql2).
			setFirstResult(id_from).
			setMaxResults(file_interval).
			list();
		//boolean error = false;
		while(objects_id.size()>0){
			//if (error) break;
			System.out.println("TICK ! " + id_from + " - " + (id_from + file_interval));
			for (Object[] id_arrays :objects_id){
				int ob_id 	  = ((BigInteger)id_arrays[0]).intValue();
				int ob_id_val = ((BigInteger)id_arrays[1]).intValue();
				int ob_id_count = ((BigInteger)id_arrays[1]).intValue();
				//if (error) break;
				
				if (ob_id != prev_id){
					while (ob_id != prev_id){
						
						add_elements(tmp_list, prev_id);
						
						prev_id++;
						tmp_list = new ArrayList<Integer[]>();
						
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

			id_from = id_from + file_interval;
			//new ids to process
			objects_id = (List<Object[]>)session.
			createSQLQuery(sql2).
			setFirstResult(id_from).
			setMaxResults(file_interval).
			list();
		}
		add_elements(tmp_list, prev_id);
		System.out.println("saving, len: " + list_hash_matrix.size());
		FileUtils.save_file(get_real_file_name(file_current), list_hash_matrix);
		tx.commit();
		session.close();
		
		
	}
	@SuppressWarnings("unchecked")
	public final void create_file(){
		list_hash_matrix = new ArrayList<Object[]>();
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		String sql1 = null;
		if (transpose) sql1 = get_main_sql_id_t();
		else sql1 = get_main_sql_id();
		
		List<Long>  objects_id = (List<Long>)session.
		createQuery(sql1).
			list();
		
		int counter = 0;
		int main_counter = 1;
		int file_interval = 500000;
		int file_current = 0;
		
		for (long ob_id : objects_id){
			System.out.println(ob_id);
			if ((int)ob_id != main_counter){
				while ((int)ob_id != main_counter){
					System.out.println(main_counter + ", " + ob_id);
					// ten licznik pozwala na zachowanie kolejnosci w hashmapie
					Object[] tmp_array = {main_counter , new int[0]}; 
					list_hash_matrix.add(tmp_array);
					if (counter >= file_interval){
						System.out.println("saving, len: " + list_hash_matrix.size());
						FileUtils.save_file(get_real_file_name(file_current), list_hash_matrix);
						counter = 0;
						file_current++;
						list_hash_matrix = new ArrayList<Object[]>();
					}
					main_counter++;
					counter++;
					
				}
				System.out.println("!");
			}
			if ((int)ob_id == main_counter) {
				String sql2 = null;

				if (transpose) sql2 = get_secondary_sql_id_t();
				else sql2 = get_secondary_sql_id();
				
				List<Object[]>  objects_id2 = null;

				objects_id2 = (List<Object[]>)session.
				createQuery(sql2).
				setLong(0, ob_id).
				list();



				
				
				List<Object[]> tmp_list = new ArrayList<Object[]>();
				
				for (Object[] ob_id2 : objects_id2){
					Object[] t = new Object[2];
					if ((Long)ob_id2[0] != null ){
						t[0] = ((Long)ob_id2[0]).intValue();
						t[1] = ((Long)ob_id2[1]).intValue();
						tmp_list.add(t);
					}
					
				}
				Object[] tmp = new Object[tmp_list.size()];
				int i=0;
				for (Object[] ob: tmp_list){
					tmp[i] = ob;
				}
				
				Object[] tmp_array = {(int)ob_id , tmp}; 
				list_hash_matrix.add(tmp_array);
				if (counter >= file_interval){
					System.out.println("saving, len: " + list_hash_matrix.size());
					FileUtils.save_file(get_real_file_name(file_current), list_hash_matrix);
					counter = 0;
					file_current++;
					list_hash_matrix = new ArrayList<Object[]>();
				}
				main_counter++;
				counter++;
			}
		}
		System.out.println("saving, len: " + list_hash_matrix.size());
		FileUtils.save_file(get_real_file_name(file_current), list_hash_matrix);
		
		tx.commit();
		session.close();
	}
	
	int get_current_interval(){
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
	public final SparseDoubleMatrix2D get_part_matrix() {
		//sprawdzenie czy nie ma juz odserializowanego obiektu jako pola
		if (list_hash_matrix == null){
			current_filename = get_real_file_name(current_file_count);
			list_hash_matrix = (List<Object[]>)FileUtils.open_file(current_filename); // odczytac zawartosc pliku
			if (list_hash_matrix == null)
				return null;
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
			
			for (Object column : elem) {
			//	System.out.println(column);
			//	System.out.println(column.getClass());
			//	System.out.println(column.getClass().getCanonicalName());
				int[] c = (int[])column;
				matrix_object.set(current_row, c[0]-1, c[1]);
			} 
			current_list_matrix_elem++;
			current_row++;
			
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
