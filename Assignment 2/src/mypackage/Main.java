package mypackage;
import java.io.*;

import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;


public class Main {

	public static int DEBUG = 1;
	private double[][] doubleArray = null;

	private int user = 0;

	String file;

	public static void main(String[] args) throws Exception
	{
		if(Main.DEBUG == 1)
			System.setOut(new PrintStream(new FileOutputStream("DEBUG.txt")));


		String file;
		int user = 0; 	// 0 = Cindy, 1 = Ivy

		Samplier sample = new Samplier();


		if(user == 0){
			file = "src/mypackage/10percent-miscatergorization.csv";
		}
		else{
			file = "src/mypackage/3percent-miscategorization.csv";
		}

		sample.setCSV(file);

		Main p = new Main();
		p.normalize(p.runAbalone());
	}

	/* Returns a double[][] of parsed csv file */
	public double[][] run()
	{
		BufferedReader br = null;
		String file;

		int size = 0;

		if(user == 0){
			file = "src/mypackage/10percent-miscatergorization.csv";
		}
		else{
			file = "src/mypackage/3percent-miscategorization.csv";
		}

		try
		{

			Samplier sam = new Samplier();

			sam.setCSV(file);

			int numLines = sam.getLines();
			int numElements = 0;

			String csvFile = file;

			br = new BufferedReader(new FileReader(csvFile));
			String line = "";
			String cvssplit = ",";

			boolean first = true;




			while((line = br.readLine())!=null)
			{
				String[] data = line.split(cvssplit);
				double[] d = new double[data.length];
				numElements = data.length;



				if(first)
				{
					//System.out.println(" " +numElements + " " + numLines);
					doubleArray = new double[numLines][numElements];
					first = false;
				}



				for(int i=0;i<data.length;i++)
				{
					d[i]  = Double.parseDouble(data[i]);
					doubleArray[size][i] = d[i];

					if(DEBUG == 1){

						if(size == 0 && i == 0 )
							System.out.println("\n SHOWING run() \n");

						System.out.print(doubleArray[size][i] + " ");
						if(i == data.length - 1){
							System.out.println("");
						}


						if(size == doubleArray.length - 1 && i == doubleArray[0].length - 1 )
							System.out.println("\n FINISHED SHOWING run() \n");
					}

				}

				size++;


			}

		}

		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (br != null) {
				try
				{
					br.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		return doubleArray;

	}

	/* Run method used for the abalone dataset
	 * Takes the first column of the dataset and creates 2 extra proxy variables
	 *
	 * Returns double[][] array of parsed dataset
	 */
	public double[][] runAbalone(){

		BufferedReader br = null;
		String file;

		int size = 0;
		int proxyVariables = 2;

		if(user == 0){
			file = "src/mypackage/abalone.data.csv";
		}
		else{
			file = "src/mypackage/abalone.data.csv";
		}

		try
		{

			Samplier sam = new Samplier();

			sam.setCSV(file);

			int numLines = sam.getLines();
			int numElements = 0;

			String csvFile = file;

			br = new BufferedReader(new FileReader(csvFile));
			String line = "";
			String cvssplit = ",";

			boolean first = true;

			while((line = br.readLine())!=null)
			{
				String[] data = line.split(cvssplit);
				double[] d = new double[data.length];

				numElements = data.length;

				if(first)
				{
					//System.out.println(" " +numElements + " " + numLines);
					doubleArray = new double[numLines][numElements + proxyVariables];
					first = false;
				}



				/*
				 * For Proxy Variables:
				 * M = 1 0 0
				 * F = 0 1 0
				 * I = 0 0 1
				 */
				for(int i=0;i<data.length;i++)
				{

					// Skip first entry because it isn't a double
					if(i == 0){

						// Add in the proxy variables

						switch (data[0].toLowerCase()){

						case "m":

							doubleArray[size][0] = 1;
							doubleArray[size][1] = 0;
							doubleArray[size][2] = 0;

							break;

						case "f":
							doubleArray[size][0] = 0;
							doubleArray[size][1] = 1;
							doubleArray[size][2] = 0;

							break;

						case"i":

							doubleArray[size][0] = 0;
							doubleArray[size][1] = 0;
							doubleArray[size][2] = 1;

							break;

						default:

							break;

						}// end switch

						// Skip the 0
						continue;

					}// end if

					d[i]  = Double.parseDouble(data[i]);


					doubleArray[size][i + proxyVariables] = d[i];


				}// end for loop

				size++;


			}// end while


		}

		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (br != null) {
				try
				{
					br.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}

		if(DEBUG == 1){

			System.out.println("\n SHOWING runAbalone() \n");

			for(int i = 0; i < doubleArray.length; i++){

				System.out.print("Row: " + i );

				for(int j = 0; j < doubleArray[i].length; j++){

					if(j == 0)
						System.out.println(" ");

					System.out.print(doubleArray[i][j] + " ");

				}
			}

			System.out.println("\n FINISHED SHOWING runAbalone() \n");



		}


		return doubleArray;


	}

	public double[] sumOfColume(double[][] doubleArray)
	{

		double[][] dArray = new double[doubleArray.length][10];
		dArray = doubleArray;
		double[] sum = new double[10];
		double total = 0;

		int j=0;

		for(int i=0;i<doubleArray[j].length;i++)
		{
			total = 0;
			for(j=0;j<doubleArray.length-1;j++)
			{
				total = dArray[j][i]+total ;
			}
			sum[i] = total;
		}
		return sum;


	}

	public double[] std(double[][] doubleArray)
	{

		double[][] dArray = new double[doubleArray.length][10];
		double[][] columeArray = new double[10][doubleArray.length];

		dArray = doubleArray;
		double[] stdArray = new double[10];

		int j=0;
		StandardDeviation std = new StandardDeviation();

		for(int i=0;i<doubleArray[j].length;i++)
		{
			for(j=0;j<doubleArray.length-1;j++)
			{
				columeArray[i][j] = dArray[j][i];
				//System.out.print(columeArray[i][j]+ " ");
			}
			//System.out.println();
		}

		for(int k=0;k<columeArray.length;k++)
		{
			double standardD = std.evaluate(columeArray[k], 0, columeArray[k].length);
			stdArray[k] = standardD;
		}

		return stdArray;



	}

	/*
	 * Calculates Euclidian distance of elements from two
	 * double[] arrays.
	 */
	public double calculateDistance(double[] d1, double[] d2)
	{
		double distance = 0;

		for(int i = 0; i < d1.length; i++ )
		{
			 distance +=  Math.pow((d1[i] - d2[i]),2);
		}
		return Math.sqrt(distance);
	}

	/*
	 * z = (value of element - population mean)/ std dev
	 */
	public double[] zscore(double[] columnEntries)
	{
		double[] z = new double[columnEntries.length];
		double zscore;

		double mean = StatUtils.mean(columnEntries);
		double stdDev;

		StandardDeviation sd = new StandardDeviation();
		stdDev = sd.evaluate(columnEntries);

		for(int i = 0;i<columnEntries.length;i++)
		{
			 zscore = (double)(columnEntries[i] - mean) / (double) stdDev;

			 z[i] = zscore;

		}

		if(DEBUG == 1){

			System.out.println("\n Showing zscore(double[] column Entries) \n");

			for(int i = 0; i < z.length; i++)
			{
				System.out.println(z[i] + "" );
			}

			System.out.println("\n Finished showing zscore()\n");
		}

		return z;
	}

	/*
	 * Returns a double array of column entries columns from a double array.
	 */
	public double[] parseColumns(double[][] d, int colNum){

		double[] parsed = new double[d.length];

		for(int i = 0; i < d.length; i ++){

			parsed[i] = d[i][colNum];
		}

		return parsed;

	}

	public double[][] normalize(double[][] d){

		double[][] normalized = new double[d.length][d[0].length];
		double[] tempArray = new double[d.length];	// stores normalized column data

		for(int i = 0; i < d[0].length; i++){

			if(DEBUG == 1)
				System.out.println("Parsing column: " + (i + 1) );

			tempArray = zscore(parseColumns(d, i));


			for(int x = 0; x < d.length; x++){

				normalized[x][i] = tempArray[x];

			}

		}

		//System.err.println("Length: " + d.length + " Columns: " + d[0].length);

		if(DEBUG == 1){

			System.out.println("\n Showing normalized \n");

			for(int i = 0; i < d.length; i++){

				System.out.println("\n\nEntry: " + (i + 1));

				for(int j = 0; j< d[i].length; j++){

					if(j == 0)
						System.out.println(" ");

					System.out.print(normalized[i][j] + " ");

				}
			}

			System.out.println("\nFinished normalized \n");
		}

		return normalized;

	}
}
