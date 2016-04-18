package mypackage;
import java.util.Random;
import org.apache.commons.math.stat.descriptive.moment.Mean;
import org.apache.commons.math.stat.descriptive.moment.StandardDeviation;

public class RandomNumberGenerator {
	private static Random rand = new Random(31245750);
	private static double [] data;
	
	RandomNumberGenerator(int size){
		data = new double [size];
	}
	
	public double [] getData(){
		return data;
	}
	
	public void algorithm(float nn, float nr) {
		double max = 1.0;
		double min = 0.0;
		int count = 0;
		float n = nn;
		float r = nr;
		int [] temp = new int[(int) nr];
		

		for(int i = 0; i < r; i++) { // run 10% of nr times
			Double j = rand.nextDouble();
			nr--;
			if(j < (nn/nr)) { // threshold test
				temp[i]++;
				nn--;
				count++;
			}
		}
		
		for(int i = 0; i < r; i++) {
			data[i] += temp[i];
		}
	}
	
	public void resetData() {
		for(int i = 0; i < data.length; i++){
			data[i] = 0;
		}
	}
	
	public double print(int run) {
		Mean mean = new Mean();
		StandardDeviation sd = new StandardDeviation();
		int total = 0;
		// Calculate the number of times each observation is selected
		/*for(int i = 0; i < data.length; i++) {
			//System.out.println("i: " + i + ", val: " + (int)data[i]);
			total += data[i];
		}
		System.out.println("Total: " + total + "  nn:" + run*0.1*data.length);*/
		 
		// Find the mean / Standard deviation (SD) of these counts &
		// Normalize the mean and the SD (divide by the number of runs)
		double m = mean.evaluate(data);
		double s = sd.evaluate(data);
		System.out.println("Mean: " + m + "\nSD: " + s);
		System.out.println("Normalize Mean: " + m/run + "\nNormalize SD: " + s/run);
	
		return m;
	}
	
}
