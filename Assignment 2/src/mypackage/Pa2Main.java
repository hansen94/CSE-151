package mypackage;
import java.util.*;

public class Pa2Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int KVAL = 1;
		
		//read the file (provide address to the file) into a list
		List data = CsvReader.read("src/Seperable.csv");
		/*Iterator iter = data.iterator();
		for(int i = 0; i < data.size(); i++){
			String [] str = (String[])data.get(i);
			System.out.println(str[0]);
		}*/
		
		//choose training set and test set
		RandomNumberGenerator random = new RandomNumberGenerator(data.size());
		int nRemaining = data.size();   // size of data
		int nNeeded = nRemaining / 10;  // number of sample needed
		
		random.algorithm(nNeeded, nRemaining);  
		double [] chosenData = random.getData();  //get the result
		
		//split the data into training and test set
		ArrayList training = new ArrayList();
		ArrayList test = new ArrayList();
		
		for(int i = 0; i < data.size(); i++){  // iterate through the dataset
			//String[] currentData = (String[])data.get(i);
			if(chosenData[i] != 0)
				training.add(data.get(i));
			else
				test.add(data.get(i));
		}
		
		//convert the data from list to matrix
		double[][] trainingMatrix = UtilFunctions.listToMatrix(training);
		double[][] testMatrix = UtilFunctions.listToMatrix(test);
		
		//do z-scaling of the training set
		double [] meanTrain = UtilFunctions.getMean(trainingMatrix);   //store the mean until column n-1
		double [] sdTrain = UtilFunctions.getSD(trainingMatrix);    //store the SD until n-1
		double [] [] normalMatrix = UtilFunctions.normalizeMatrix(trainingMatrix);
		
		//initialize a max priority queue. This is the collection of neighbors 
		PriorityQueue <Pair> pqueue = new PriorityQueue <Pair>(100 , new Comparator<Pair>(){
				public int compare(Pair p1, Pair p2){
					if((double)p1.getFirst() > (double)p2.getFirst())
						return +1;
					if(p1.getFirst() == p2.getFirst())
						return 0;
					return -1;
				}
		});
		
		double [] predictedCategory = new double[testMatrix.length];   //an array storing the prediction category of test set
		
		//scale each test and clasify them with k-nearest neighbor
		for(int i = 0; i < testMatrix.length; i++){			
			double [] testVector = UtilFunctions.normalizeTest(testMatrix[i], meanTrain, sdTrain);
			// find the distance from this vector to all training vector
			for(int j = 0 ; j < normalMatrix.length ; j++){
				double distance = UtilFunctions.getDistance(testVector, normalMatrix[j]);
				//put the distance of this instance to a heap
				if(pqueue.size() > KVAL)
					pqueue.remove();
				Pair<Double,Double> pair = new Pair<Double,Double>(distance, normalMatrix[j][normalMatrix[0].length - 1] );
				pqueue.add(pair);
				
			}
			
			//vote for the likeliest category from the neighbors
			double testCategory = UtilFunctions.getMostFrequent(pqueue);
			predictedCategory[i] = testCategory;
		}
		
		//compare the predicted categories with the actual categories to produce confusion matrix and calculate accuracy
	}

}
