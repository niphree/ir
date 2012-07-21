package ir.rank.common;

import ir.rank.common.model.AbstractMatrixSource;
import cern.colt.matrix.DoubleFactory1D;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import cern.colt.matrix.impl.SparseDoubleMatrix2D;

public class MatrixVectorMultiplier {
	
	public static void read_matrix(AbstractMatrixSource matrix, 
										boolean trans){
		matrix.setTranspose(trans);
		if (matrix.nativ_sql())
			matrix.create_file_native();
		else matrix.create_file();
	}
	
	public static DenseDoubleMatrix1D multiple( DenseDoubleMatrix1D vector, 
												AbstractMatrixSource matrix, 
												boolean trans){
		
		//fabryka do dodawania wektorow
		DoubleFactory1D vector_factory = DoubleFactory1D.dense;
		//wektor defaultowy
		DoubleMatrix1D return_vector = new DenseDoubleMatrix1D(0);
		//pobieramy dane z abstract matrix source:
		// init object - zainicjalizuj wysokosc/szerokosc maciezy
		matrix.setTranspose(trans);
		matrix.init();
		SparseDoubleMatrix2D partial_matrix = matrix.get_part_matrix();
		System.out.println("columns: " + partial_matrix.columns());
		System.out.println("rows: " + partial_matrix.rows());
		long i = 0;
		
		while (partial_matrix != null && partial_matrix.size() != 0){
			//System.out.println("matrix multi part: " + i);
			//System.out.println("columns: " + partial_matrix.columns());
		//	System.out.println("rows: " + partial_matrix.rows());
			DenseDoubleMatrix1D temp_vector = new DenseDoubleMatrix1D(partial_matrix.rows());
		//	System.out.println("tmp " + temp_vector.size());
			partial_matrix.zMult(vector, temp_vector);
		//	System.out.println("tmp " + temp_vector.size());
			return_vector = DoubleFactory1D.dense.append(return_vector, temp_vector);
		//	System.out.println("return vector " + return_vector.size());
			partial_matrix = null;
			//System.gc();
			partial_matrix = matrix.get_part_matrix();
			i++;
			//break;
		}
		// pobieramy fragment macierzy, mnozymy przez wektor, 
		// dodajemy go do return_vectora na koniec
		// pobieramy kolejny fragment macierzy.
		
		
		return (DenseDoubleMatrix1D)return_vector;
		
	}
}
