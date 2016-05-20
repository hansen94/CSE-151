package mypackage;

import java.util.ArrayList;
import java.util.List;

public class KMeans {
	private List <Point> points;
	private List <Centroid>clusters;
	
	public KMeans(List<List<Float>> data, int k){
		// Init
		this.points = new ArrayList();
		this.clusters = new ArrayList();
		
		Point p;
		
		for(int i = 0; i < data.size();  i++){
			p = new Point(data.get(i));
			this.points.add(p);			
		}
		
		Centroid c;
		
		for(int i = 0; i < k ; i++){
			c = new Centroid(i , data.get(0).size() );
			this.clusters.add(c);			
		}
	}
	
	public void executeKMeans(){
		List<Centroid> oldCentroid = this.clusters;
		boolean change = true;
		
		while(change){
			assignPoints();
			calculateCentroids();
			
			change = false;
			// Compare old and new centroids
			for(int i = 0; i < this.clusters.size(); i++){
				if(this.clusters.get(i).compareCentroid(oldCentroid.get(i)))
						change = true;
			}
		}
		
		
	}
	
	public void assignPoints(){
		List <Centroid> centroids = this.clusters;
		
		for(int i = 0; i < centroids.size(); i++)
			this.clusters.get(i).clearPoints();
		
		// iterate through points
		for(int i = 0; i < this.points.size(); i++){
			Point p = this.points.get(i);
			double min = 9999999;
			int id = -1;
			
			// iterate through centroids
			for(int j = 0; j < centroids.size(); j++){
				// check distance between point p and all centroids
				if(min > p.distance(centroids.get(j)))
						min = p.distance(centroids.get(j));
						id = centroids.get(j).getID();
			}
			
			// set cluster number in point object
			this.points.get(i).setCentroid(id);
			// add points to the cluster
			this.clusters.get(id).addPointsMember(this.points.get(i));
		}
	}
	
	//calculate the new centroids from assinged points, assuming all points have been assigned 
	public void calculateCentroids(){
		
		//iterate through all clusters
		for( int i = 0; i < this.clusters.size(); i++){
			Centroid cluster = clusters.get(i);
			Point temp = (Point) cluster.getPointsMember().get(0);
			List <Float> sum = new ArrayList();
			
			for(int j = 0; j < temp.getCoordinate().size(); j++){
				sum.add(j,(float)0);
			}
			
			int nSize = cluster.getPointsMember().size();  //get the number of point members
			
			for(int j = 0 ; j < nSize; j++ ){  //iterate through each point
				Point p = (Point)cluster.getPointsMember().get(j);
				for(int k = 0; k < p.getCoordinate().size(); k++){  //iterate through each dimension
					sum.set(k, sum.get(k) + (Float)p.getCoordinate().get(k));  //sum each dimension
				}
			}
			
			
			if(nSize > 0){
				for(int j = 0; j < temp.getCoordinate().size(); j++){
					sum.set(j, sum.get(j)/nSize);  //divide the sum with nSize
				}
			}
			cluster.setCoordinate(sum);  //set it as new coordinate
		}
	}
	
	public List<Centroid> getClusters(){
		return this.clusters;
	}
	
}
