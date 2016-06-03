package mypackage;

import org.ejml.simple.*;
import org.ejml.ops.*;
import java.lang.Object;

public class householder {
	
	public void qr_decompose(SimpleMatrix matrix){
		// is n the number of rows??
		int n = matrix.getNumElements();
		
		int d = matrix.numCols();
		
		SimpleMatrix R = matrix;
		
		SimpleMatrix Q = SimpleMatrix.identity(n);
		
		for(int i = 1; i < d+1; i++ ){
			//obtain target column
			SimpleMatrix zi = new SimpleMatrix(n-1+1, 1);   //will automatically initialize to 0
			
			int index = 0;
			
			for(int l = i; l < n+1; l++){
				zi.set(index, R.get(l-1, i-1)); 
				index++;				
			}
			
			SimpleMatrix E = new SimpleMatrix(n-1+1, 1);
			
			E.set(0, 1);
			
			
			//find vi
			if(zi.get(0) >= 0){
				E.set(0, - zi.normF() );
			}
			else{
				E.set(0, zi.normF());;
			}
			
			SimpleMatrix vi = E.minus(zi);
			
			//find householder matrix pi
			//viT * vi
			SimpleMatrix temp = vi.mult(vi.transpose());
			double denom = temp.get(0);
			
			//vi * viT
			SimpleMatrix top = vi.transpose().mult(vi);
			top = top.scale(2);
			top = top.divide(denom);
			
			//pi
			SimpleMatrix pi = SimpleMatrix.identity(n-1+1).minus(top);
			
			SimpleMatrix Qi = SimpleMatrix.identity(n);
			int r = i - 1;
			//don't know how to do this: Qi[r:r+pi.shape[0], r:r+pi.shape[1]] = pi
			R = Qi.mult(R);
			
			Q = Qi.mult(Q);
			
		}
		//print R
		
		Q = Q.transpose();
		//need to return a pair
		
	}
	
	
	
}
