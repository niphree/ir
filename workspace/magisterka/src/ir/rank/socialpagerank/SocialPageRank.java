package ir.rank.socialpagerank;

import ir.rank.socialpagerank.model.DocumentUserMatrixSource;
import ir.rank.socialpagerank.model.TagsDocumentsMatrixSource;
import ir.rank.socialpagerank.model.UsersTagsMatrixSource;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;

public class SocialPageRank {
	
	public static double MIN_DIFF = 0.1;
	
	DenseDoubleMatrix1D getRandomVector(){
		return null;
	}
	
	
	public void calcRank(){
		DenseDoubleMatrix1D po = getRandomVector();
		
		
		boolean end = false;
		while(!end){
			DenseDoubleMatrix1D temp = (DenseDoubleMatrix1D)po.copy();
			
			DenseDoubleMatrix1D users1 = MatrixVectorMultiplier.multiple(po, 
					new DocumentUserMatrixSource(), true);
			DenseDoubleMatrix1D tags1 = MatrixVectorMultiplier.multiple(users1, 
					new UsersTagsMatrixSource(), true);
			DenseDoubleMatrix1D docs1 = MatrixVectorMultiplier.multiple(tags1, 
					new TagsDocumentsMatrixSource(), true);
			tags1 = MatrixVectorMultiplier.multiple(docs1, 
					new TagsDocumentsMatrixSource(), false);
			
			users1= MatrixVectorMultiplier.multiple(tags1, 
					new UsersTagsMatrixSource(), false);
			
			po = MatrixVectorMultiplier.multiple(po, 
					new DocumentUserMatrixSource(), false);
			
			double diff = calc_diff(temp, po);
			System.out.println(diff);
			if (diff < MIN_DIFF){
				end = true;
			}
				
		}
	}
	
	public double calc_diff(DenseDoubleMatrix1D v1, DenseDoubleMatrix1D v2){
		return 0;
	}
	
}
