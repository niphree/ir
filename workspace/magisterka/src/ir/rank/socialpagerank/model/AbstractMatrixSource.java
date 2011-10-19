package ir.rank.socialpagerank.model;

import ir.hibernate.HibernateUtil;
import ir.util.FileUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;

import cern.colt.matrix.impl.SparseDoubleMatrix2D;

// leniwe pobieranie kolumn albo wierszy w zaleznosci od 
// tego czy chcemy transpose matrix czy niet. 
public abstract class AbstractMatrixSource {

	long max_row;
	long max_col;
	boolean transpose;
	int interval; 
	Map<Integer, int[]> hash_matrix = new HashMap<Integer, int[]>();
	
	int current = 0;
	/*
	 * this function calculate number of row/col
	 * intervals for trans=true/false
	 * 
	 */
	abstract String get_main_sql_id_t();
	abstract String get_main_sql_id();
	abstract String get_secondary_sql_id_t();
	abstract String get_secondary_sql_id();
	abstract String get_row_sql();
	abstract String get_col_sql();
	abstract String get_name();
	
	
	public void init(){
		System.out.println("matrix init");
		System.out.println("count col");
		init_max_col();
		System.out.println(get_max_col());
		System.out.println("count row");
		init_max_row();
		System.out.println(get_max_row());
		calculate_interval();
		
	}
	
	public int get_interval(){
		return interval;
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

	@SuppressWarnings("unchecked")
	public final void create_file(){
		hash_matrix = new HashMap<Integer, int[]>();
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		String sql1 = null;
		if (transpose) sql1 = get_main_sql_id_t();
		else sql1 = get_main_sql_id();
		
		List<Long>  objects_id = (List<Long>)session.
		createQuery(sql1).
			list();
		
		int counter = 0;
		int file_interval = 500000;
		int file_current = 0;
		
		for (long ob_id : objects_id){
			String sql2 = null;
			
			if (transpose) sql2 = get_secondary_sql_id_t();
			else sql2 = get_secondary_sql_id();
			
			List<Long>  objects_id2 = (List<Long>)session.
			createQuery(sql2).
				setLong(0, ob_id).
				list();
			
			int[] tmp = new int[objects_id2.size()];
			int i = 0;
			
			for (long ob_id2 : objects_id2){
				tmp[i] = (int)ob_id2;
				i++;
			}
			hash_matrix.put((int)ob_id, tmp);  
			if (counter >= file_interval){
				String file_name = null;
				if (transpose) file_name = get_name()+"_" + file_current + ".out";
				else file_name = get_name()+"_t_" + file_current + ".out";
				
				FileUtils.save_file(file_name, hash_matrix);
				counter = 0;
				file_current++;
				hash_matrix = new HashMap<Integer, int[]>();
				
			}
			counter++;
		}
		
		String file_name = null;
		if (transpose) file_name = get_name()+"_" + file_current + ".out";
		else file_name = get_name()+"_t_" + file_current + ".out";
		
		FileUtils.save_file(file_name, hash_matrix);
		
		tx.commit();
		session.close();
	}
	
	
	@SuppressWarnings("unchecked")
	public final SparseDoubleMatrix2D get_part_matrix() {
		SparseDoubleMatrix2D matrix_object = new SparseDoubleMatrix2D(interval, (int)get_max_col()); //row/col
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		//pobierz id glownych obiektow
		List<Long>  objects_id = (List<Long>)session.
			createQuery(get_main_sql_id()).
			setFirstResult(current).
			setMaxResults(interval).
			list();
		int counter = 0;
		for (long ob_id : objects_id){
			List<Long>  objects_id2 = (List<Long>)session.
				createQuery(get_secondary_sql_id()).
				setLong(0, ob_id).
				setFirstResult(current).
				setMaxResults(interval).
				list();
			int[] tmp = new int[objects_id2.size()];
			int i = 0;
			for (long ob_id2 : objects_id2){
				tmp[i] = (int)ob_id2;
				i++;
				//matrix. set((int)ob_id -current, (int)ob_id2, 1);
			}
			hash_matrix.put((int)ob_id, tmp);  
			if (counter >= 500000){
				//zapisz to do pliku
			}
			
		}
		matrix_object.trimToSize();
		
		tx.commit();
		session.close();
		return matrix_object;
	}

	@SuppressWarnings("unchecked")
	public final SparseDoubleMatrix2D get_part_t_matrix() {
		SparseDoubleMatrix2D matrix = new SparseDoubleMatrix2D(interval, (int)get_max_row()); //row/col
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		//pobierz id glownych obiektow
		List<Long>  objects_id = (List<Long>)session.
			createQuery(get_main_sql_id_t()).
			setFirstResult(current).
			setMaxResults(interval).
			list();
		for (long ob_id : objects_id){
			System.out.println(ob_id);
			List<Long>  objects_id2 = (List<Long>)session.
				createQuery(get_secondary_sql_id_t()).
				setLong(0, ob_id).
				setFirstResult(current).
				setMaxResults(interval).
				list();
			for (long ob_id2 : objects_id2){
				matrix.set((int)ob_id - current, (int)ob_id2, 1);
			}
		}
		matrix.trimToSize();
		
		tx.commit();
		session.close();
		return null;
	}
	
	
	/**
	 * @return null if all matrixes fetched
	 */
	public SparseDoubleMatrix2D get_matrix(){
		System.out.println("get matrix");
		System.out.println("current:" + current);
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
		double row_cols = get_max_row() * get_max_col();
		if (transpose)
			interval = (int)(max/get_max_row()); //row jest columna
		else interval = (int)(max/get_max_col()); //col jest normalnie columna
		
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
