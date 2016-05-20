package mypackage;

import java.util.ArrayList;
import java.util.List;

public class PA4Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//get the data from the file 
		
		final int K = 1;
		System.out.println("Reading raw data...");
		List <List<Float>> data = CsvReader.read("src/mypackage/abalone.data.csv");
		
		//choose training set and test set
		RandomNumberGenerator random = new RandomNumberGenerator(data.size());
		int nRemaining = data.size();   // size of data
		int nNeeded = nRemaining / 10;  // number of sample needed
			
		random.algorithm(nNeeded, nRemaining);  
		double [] chosenData = random.getData();  //get the result
				
		//split the data into training and test set
		List<List<Float>> training = new ArrayList();
		List<List<Float>> test = new ArrayList();
				
		for(int i = 0; i < data.size(); i++){  // iterate through the dataset
			//String[] currentData = (String[])data.get(i);
			if(chosenData[i] != 0)
				training.add(data.get(i));
			else
				test.add(data.get(i));
		}
		
		System.out.println("Finished choosing training...");
			
		float[][] trainingMatrix = UtilFunctions.listToMatrix(training);
		float[][] testMatrix = UtilFunctions.listToMatrix(test);
				
		double[][] trainingMatrixDouble = new double[trainingMatrix.length] [trainingMatrix[0].length];
		
		for(int i = 0; i < trainingMatrix.length; i++){
			trainingMatrixDouble[i] = floatToDouble(trainingMatrix[i]);
		}			
				
				
		//do z-scaling of the training set
		double [] meanTrain = UtilFunctions.getMean(trainingMatrixDouble);   //store the mean until column n-1
		double [] sdTrain = UtilFunctions.getSD(trainingMatrixDouble);    //store the SD until n-1
		double [] [] normalMatrix = UtilFunctions.normalizeMatrix(trainingMatrixDouble);
				
		for(int i = 0; i < trainingMatrix.length; i++){
			trainingMatrix[i] = doubleToFloat(normalMatrix[i]);
		}	
		List<Float> temp = new ArrayList();
		training.clear();
		for(int i = 0; i < trainingMatrix.length; i++){
			temp.clear();
			for(int j = 0; j < trainingMatrix[0].length; j++){
				temp.add(trainingMatrix[i][j]);
			}
			training.add(temp);
		}
		
		System.out.println("Done z-scaling...");
		
		KMeans kmeans = new KMeans(training, K);  // create a KMeans object
		
		System.out.println("Done making Kmeans object");
		
		kmeans.executeKMeans();   // execute the algorithm
		
		System.out.println("Done executing K-means...");
		
		List<Centroid> clusters = kmeans.getClusters();  //get the resulting centroid
		for(int i = 0; i < clusters.size(); i++){
			System.out.println(clusters.get(i).getCoordinate().toString());
		}
				
				
				

	}
	
	private static double[] floatToDouble(float[] source){
		double[] result = new double[source.length];
		for(int i = 0; i < source.length; i++){
			result[i] = (double) source[i];
		}
		return result;
	}
	
	private static float[] doubleToFloat(double[] source){
		float[] result = new float[source.length];
		for(int i = 0; i < source.length; i++){
			result[i] = (float) source[i];
		}
		return result;
	}

}
