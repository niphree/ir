package ir.rank.socialpagerank;

import ir.rank.socialpagerank.model.AbstractMatrixSource;
import cern.colt.matrix.DoubleFactory1D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;

public class MatrixVectorMultiplier {

	
	public static DenseDoubleMatrix1D multiple( DenseDoubleMatrix1D vector, 
												AbstractMatrixSource matrix, 
												boolean trans){
		
		//fabryka do dodawania wektorow
		DoubleFactory1D vector_factory = DoubleFactory1D.dense;
		//wektor defaultowy
		DenseDoubleMatrix1D return_vector = null;
		//pobieramy dane z abstract matrix source:
		// init object - zainicjalizuj wysokosc/szerokosc maciezy
		matrix.setTranspose(trans);
		matrix.init();
		//pobieramy fragment macierzy, mnozymy przez wektor, 
		//dodajemy go do return_vectora na koniec
		// pobieramy kolejny fragment macierzy.
		
		
		
		return return_vector;
		
	}
}
