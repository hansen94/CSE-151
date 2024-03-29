package mypackage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Centroid {
	
	private List <Point> pointsMember;
	private List <Float> coordinate; 
	private int ID;
	
	public Centroid(int d, Point p){
		this.pointsMember = new ArrayList();
		
		this.coordinate = new ArrayList();
//		for(int i = 0; i < dimension; i++)
//			this.coordinate.add((float)Math.random());
		
		this.coordinate = p.getCoordinate();
		
		this.ID = d;		
	}
	
	public Centroid (Centroid c){
		this.pointsMember = c.getPointsMember();
		this.coordinate = c.getCoordinate();
		this.ID = c.getID();
	}
	
	public int getMemberSize(){
		return this.pointsMember.size();
	}

	public List getPointsMember() {
		return pointsMember;
	}

	public void addPointsMember(Point point) {
		this.pointsMember.add(point);
	}
	
	public List<Float> getCoordinate(){
		return this.coordinate;
	}
	
	public void setCoordinate( List<Float> coordinate){
		this.coordinate = coordinate;
	}
	
	public void setMembers(List members){
		this.pointsMember = members;
	}
	
	public int getID(){
		return ID;
	}
	
	public void clearPoints(){
		pointsMember.clear();
	}
	
	public boolean compareCentroid(Centroid c){
		return this.coordinate.equals(c.getCoordinate()) ;
	}
	
	public double[][] makeDoubleMatrix(){
		double [][] array = new double[this.pointsMember.size()] [this.pointsMember.get(0).getCoordinate().size()];
		
		for(int i = 0; i < this.pointsMember.size(); i++ ){
			for(int j = 0 ; j < this.pointsMember.get(0).getCoordinate().size(); j++){
				array[i][j] = Double.parseDouble(this.pointsMember.get(i).getCoordinate().get(j).toString());
			}
		}
		
		return array;
	}
}
