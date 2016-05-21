package mypackage;

import java.util.*;

import org.apache.commons.math.stat.descriptive.moment.Mean;
import org.apache.commons.math.stat.descriptive.moment.StandardDeviation;

/*
 * This is a class to store all utility functons for this project
 */

public class UtilFunctions {
	public static float [] [] listToMatrix(List<List<Float>> list){
		List <Float> temp = list.get(0);  //temp variable just to get number of columns needed
		float [] [] matrix = new float [list.size()] [temp.size()];  //matrix[which line] [which column]
		
		//iterate through the list
		for(int i = 0; i < list.size(); i++ ){
			List<Float> currentData = list.get(i);   //grab data from list
			for(int j = 0; j < currentData.size(); j++)
				matrix [i] [j] = (currentData.get(j));  //put data in matrix, converted to double
		}
		
		return matrix;
	}
	
	public static double[][] normalizeMatrix(double[][] matrix){
		//a varuable to store the normalized matrix. column -1 because don't want to normalize last column
		double [][] normalizedMatrix = new double[matrix.length][matrix[0].length];
		//get the z scale of each column except the last one
		for(int j = 0; j < matrix[0].length - 1; j++){
			//get the column (size should be matrix.length)
			double [] column = getColumn(matrix, j);
			//get the zscale of that column
			double [] normalizedColumn = zScale(column); 
			
			/*for(int k = 0; k < normalizedColumn.length; k++){
				System.out.print(normalizedColumn[k] + " ");
			}
			System.out.println("");*/
			
			for(int i = 0; i < matrix.length; i++){
				normalizedMatrix[i][j] = normalizedColumn[i];
			}
		}
		//fill the last column with same value
		for(int i = 0; i < matrix.length; i++){
			normalizedMatrix[i][matrix[0].length - 1] = matrix[i][matrix[0].length - 1];
		}
		
		return normalizedMatrix;
		
	}
	
	//calculate zscale of an array
	private static double[] zScale(double[] column) {
		// TODO Auto-generated method stub
		double[] normalizedColumn = new double[column.length];
		Mean mean = new Mean();
		StandardDeviation sd = new StandardDeviation();
		//find the mean and standard deviation of the column
		double[] columnDouble = new double[column.length];
		for(int j = 0; j < column.length; j++)
			columnDouble[j] = (double) column[j];
		
		double meanColumn = mean.evaluate(columnDouble);
		double sdColumn = sd.evaluate(columnDouble);
		
		//normalize every element of the column
		for(int i = 0; i< column.length; i++){
			normalizedColumn[i] = (columnDouble[i] - meanColumn) / sdColumn;
		}
		
		return normalizedColumn;
	}
	
	//calculate the mean of each column
	public static double[] getMean(double[][] matrix){
		double [] mean = new double[matrix[0].length - 1];
		Mean m = new Mean();
		
		for(int i = 0 ; i < matrix[0].length - 1; i++){
			double[] column = getColumn(matrix, i);
			mean[i] = m.evaluate(column);
		}
		
		return mean;
	}
	
	//calculate the standard deviation of each column
	public static double[] getSD(double[][] matrix){
		double [] standev = new double[matrix[0].length - 1];
		StandardDeviation sd = new StandardDeviation();
		
		for(int i = 0 ; i < matrix[0].length - 1; i++){
			double[] column = getColumn(matrix, i);			
			standev[i] = sd.evaluate(column);
		}
				
		return standev;
	}

	//extract a column from a 2D array
	private static double[] getColumn(double[][] matrix, int columnIndex) {
		// TODO Auto-generated method stub
		double[] column = new double[matrix.length];
		
		//iterated through each row, extracting the selected column
		for(int i = 0; i < matrix.length; i++)
			column[i] = matrix[i][columnIndex];
		
		return column;
	}
	
	public static double[] normalizeTest(double[] testVector, double[] mean, double[] sd){
		double [] normalizedTest = new double[testVector.length];
		//for each column in the test vector
		for(int i = 0; i < testVector.length - 1 ; i++){
			normalizedTest[i] = (testVector[i] - mean[i]) / sd[i];
		}		
		
		return normalizedTest;
	}
	
	//calculate the sdistance between 2 vector
	public static double getDistance(double[] d1, double[] d2){
		double distance = 0;
		for(int i = 0; i < d1.length; i++){
			distance = distance + (d1[i] - d2[i]) * (d1[i] - d2[i]); 
		}
		
		distance = Math.sqrt(distance);
		
		return distance;
	}
	
	//get the most frequent category in the queue
	public static double getMostFrequent(PriorityQueue <Pair> pqueue){
		
		Map <Double, Integer> map = new HashMap();
		
		while(!pqueue.isEmpty()){
			Pair <Double,Double> currentNode = pqueue.remove();
			
			
			//if the category is already seen in the map, increment the count of that category
			if(map.containsKey(currentNode.getSecond())){
				map.put(currentNode.getSecond(), map.get(currentNode.getSecond()) + 1);
			}
			else{
				map.put(currentNode.getSecond(), 1);
			}			
				
		}
		
		double category = 0;  // store the maximum category
		double tempCategory = 0;  
		int count = 0;
		int tempCount = 0;
		Iterator iter = map.entrySet().iterator();
		while(iter.hasNext()){
			Map.Entry<Double, Integer> pair = (Map.Entry)iter.next();   //pair contains <category, countOfCategory>
			tempCategory = pair.getKey();
			tempCount = pair.getValue();
			if(tempCount > count)
				category = tempCategory;
		}
		
		return category;
	}
	
}
