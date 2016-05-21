package mypackage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
		
		Random random = new Random(0);
		
		for(int i = 0; i < k ; i++){
			int index = random.nextInt(points.size() - 1 - 0 + 1); 
			System.out.println("The initial index of centroid " + i + " is :" + index);
			c = new Centroid(i, points.get(index) );
			this.clusters.add(c);			
		}
	}
	
	public void executeKMeans(){
		
		boolean change = true;
		
		while(change){
			List<Centroid> oldCentroids = new ArrayList(this.clusters.size());
			for(Centroid c : this.clusters){
				oldCentroids.add(new Centroid(c));
			}
			
			
//			oldCentroids = (List<Centroid>) this.clusters.clone();
			assignPoints();
			calculateCentroids();
			
			change = false;
			// Compare old and new centroids
			for(int i = 0; i < this.clusters.size(); i++){
				//System.out.println(this.clusters.get(i).compareCentroid(oldCentroid.get(i)));
				
				if(!(this.clusters.get(i).compareCentroid(oldCentroids.get(i))))
						change = true;
			}
//			System.out.println("Old Centroids: ");			
//			for(int i = 0; i < oldCentroids.size(); i++){
//					System.out.println(oldCentroids.get(i).getCoordinate().toString());
//				
//			}
			
//			System.out.println("New Centroids: ");			
//			for(int i = 0; i < this.clusters.size(); i++){
//					System.out.println(this.clusters.get(i).getCoordinate().toString());
//				
//			}
			
			
			//System.out.println("inwhile.....");
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
				if(min > p.distance(centroids.get(j))){
						//System.out.println("New distance: " + p.distance(centroids.get(j)));
						min = p.distance(centroids.get(j));
						id = centroids.get(j).getID();
						//System.out.println("new ID: " + id );
				}
			}
			
			// set cluster number in point object
			this.points.get(i).setCentroid(id);
			// add points to the cluster
			this.clusters.get(id).addPointsMember(this.points.get(i));
//			System.out.println("adding to cluster " + id);
		}
//		for(int i = 0; i < centroids.size() ; i++)
//		System.out.println("Number of points in cluster " + i + " is: " + clusters.get(i).getPointsMember().size());
	}
	
	//calculate the new centroids from assinged points, assuming all points have been assigned 
	public void calculateCentroids(){
//		System.out.println("CALC CENT:: THIS Centroids: ");			
//		for(int i = 0; i < this.clusters.size(); i++){
//				System.out.println(this.clusters.get(i).getCoordinate().toString());
//			
//		}
		List <Centroid> newClusters = new ArrayList();
		//iterate through all clusters
		for( int i = 0; i < this.clusters.size(); i++){
			Centroid cluster = this.clusters.get(i);
//			if(cluster.getPointsMember().isEmpty()){
//				System.out.println("EMPTY!!!!");
//				//return;
//			}else{
//				System.out.println("Centroid coordinate changing...");
//			}
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
			newClusters.add(cluster);
		}
		
		this.clusters.clear();
		this.clusters = newClusters;
	}
	
	public List<Centroid> getClusters(){
		return this.clusters;
	}
	
}
