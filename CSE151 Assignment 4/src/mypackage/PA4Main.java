package mypackage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.ejml.simple.SimpleMatrix;
//import org.python.modules.math;

public class PA4Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//get the data from the file 
		
		final int K = 16;
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
		
		training.clear();
		for(int i = 0; i < trainingMatrix.length; i++){
			List<Float> temp = new ArrayList();
			for(int j = 0; j < trainingMatrix[i].length; j++){
				temp.add(trainingMatrix[i][j]);
			}
			training.add(temp);
		}
		
		System.out.println("Done z-scaling...");
		
		KMeans kmeans = new KMeans(training, K);  // create a KMeans object
		
		System.out.println("Done making Kmeans object");
		
		kmeans.executeKMeans();   // execute the algorithm
		
		System.out.println("Done executing K-means...");
		
		System.out.println("K used is: " + K);
		
		List<Centroid> clusters = kmeans.getClusters();  //get the resulting centroid
		for(int i = 0; i < clusters.size(); i++){
			System.out.println("Centoid ID: " + clusters.get(i).getID());
			System.out.println(clusters.get(i).getCoordinate().toString());
		}	
		
		//calculate the mean and standard deviation of each cluster
		for(int i = 0; i < clusters.size(); i++){
			Centroid cluster = clusters.get(i);
			double[] mean = UtilFunctions.getMean(cluster.makeDoubleMatrix());
			
			double[] SD = UtilFunctions.getSD(cluster.makeDoubleMatrix());
			
			System.out.println("Centroid ID: " + cluster.getID());
			System.out.print("Mean: ");
			for(int j = 0; j < mean.length; j++){
				System.out.print(mean[j] + " ");
			}
			System.out.println("");
			System.out.print("Standard Deviation: ");
			for(int j = 0; j < SD.length; j++){
				System.out.print(SD[j] + " ");
			}
			System.out.println("");
		}
		
		System.out.println("\nThe WCSS of this run is: " + kmeans.calculateWCSS());	
		
		
		// run linear regression K times
		
		double sum = 0;
		
		for (int i = 0; i < K; i++){
			// Y = age of abalone = clusters.get(i).getMemberSize() x 1 matrix (last column of data)
			// X = clusters.get(i).getPointsMember()
			// solves for beta for each cluster
			// getCoordinate() returns List <Float>
			
			// X_Train
			Point tempPoint = (Point) clusters.get(i).getPointsMember().get(0);
			float[][] xFloat = new float [clusters.get(i).getMemberSize()] [tempPoint.getCoordinate().size()];
			double[][] xDouble = new double [clusters.get(i).getMemberSize()] [tempPoint.getCoordinate().size()];
			
			for(int j = 0; j < clusters.get(i).getMemberSize(); j++){
				Point p = (Point) clusters.get(i).getPointsMember().get(j);
				
				for(int k = 0; k < tempPoint.getCoordinate().size() - 1; k++){
					xFloat[j][k] = (float) p.getCoordinate().get(k);
				}
			}
			for(int j = 0; j < xFloat.length; j++){
				xDouble[j] = floatToDouble(xFloat[j]);
			}
			
			// Y_Train
			double[][] yDouble = new double [clusters.get(i).getMemberSize()] [1];
			
			for(int j = 0; j < clusters.get(i).getMemberSize(); j++){
				yDouble[j][0] = xDouble[j][xDouble[0].length-1];
			}
		
			SimpleMatrix xTrain = new SimpleMatrix(xDouble);
			SimpleMatrix yTrain = new SimpleMatrix(yDouble);

			// xTrain beta = yTrain ->  beta = pinv(xTrain) yTrain
			SimpleMatrix beta = (xTrain.pseudoInverse()).mult(yTrain);
			//System.out.println(beta);
			
			// X_Test & Y_Test
//			SimpleMatrix xTest = new SimpleMatrix(xtDouble);
//			SimpleMatrix yTest = new SimpleMatrix(ytDouble);
			
			//sum += mean((xTest.dot(beta) - yTest));	
		}
		
		// RMSE 
		// np.sqrt(np.mean(((np.dot(X_test, beta) - Y_test) + ... )** 2))
		double rmse = Math.sqrt((1/K)* Math.pow(sum,2));
		System.out.println("RMSE: " + rmse);
		
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
