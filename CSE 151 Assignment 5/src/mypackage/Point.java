package mypackage;

import java.util.ArrayList;
import java.util.List;

public class Point {

	private List <Float> coordinate;
	private int centroidNumber;
	
	public Point (List <Float> Data) {
		// 0 to num of col
		this.coordinate = new ArrayList();
		
		this.centroidNumber = -1;
		for(int i = 0; i < Data.size(); i++)
			this.coordinate.add(Data.get(i));
	}
	 
	public List getCoordinate() {
		return this.coordinate;
	}
	
	public void setCentroid(int n) {
		this.centroidNumber = n;
	}
	
	public int getCentroid() {
		return this.centroidNumber;
	}
	
	public double distance(Centroid centroid) {
		double d = 0;
		// Distance formula
		for(int i = 0; i < this.coordinate.size(); i++) 
			d += Math.pow(centroid.getCoordinate().get(i) - this.coordinate.get(i), 2);
		
		return Math.sqrt(d);
	}
	
}





