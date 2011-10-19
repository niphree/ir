package ir.rank.socialpagerank;

import ir.rank.socialpagerank.model.AbstractMatrixSource;
import cern.colt.matrix.DoubleFactory1D;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import cern.colt.matrix.impl.SparseDoubleMatrix2D;

public class MatrixVectorMultiplier {
	
	public static void read_matrix(AbstractMatrixSource matrix, 
										boolean trans){
		matrix.setTranspose(trans);
		matrix.create_file();
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
		
		SparseDoubleMatrix2D partial_matrix = matrix.get_matrix();
		long i =0;
		while (partial_matrix != null){
			System.out.println("matrix multi iter:" + i);
			DenseDoubleMatrix1D temp_vector = new DenseDoubleMatrix1D(matrix.get_interval());
			partial_matrix.zMult(vector, temp_vector);
			return_vector = DoubleFactory1D.dense.append(return_vector, temp_vector);
			
			
			partial_matrix = matrix.get_matrix();
			i++;
		}
		// pobieramy fragment macierzy, mnozymy przez wektor, 
		// dodajemy go do return_vectora na koniec
		// pobieramy kolejny fragment macierzy.
		
		
		
		return (DenseDoubleMatrix1D)return_vector;
		
	}
}
