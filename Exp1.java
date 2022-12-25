/*
 * Incomplete Experiment 1 
 *
 * CSI2510 Algorithmes et Structures de Donnees
 * www.uottawa.ca
 *
 * Robert Laganiere, 2022
 *
*/ 
import java.util.List;
import java.util.ArrayList;

import java.io.*;
import java.util.Scanner;

public class Exp1 {
  
  // reads a csv file of 3D points (rethrow exceptions!)
  public static List<Point3D> read(String filename) throws Exception {
	  
    List<Point3D> points= new ArrayList<Point3D>(); 
	double x,y,z;
	
	Scanner sc = new Scanner(new File(filename));  
	// sets the delimiter pattern to be , or \n \r
	sc.useDelimiter(",|\n|\r");  

	// skipping the first line x y z
	sc.next(); sc.next(); sc.next();
	
	// read points
	while (sc.hasNext())  
	{  
		x= Double.parseDouble(sc.next());
		y= Double.parseDouble(sc.next());
		z= Double.parseDouble(sc.next());
		points.add(new Point3D(x,y,z));  
	}   
	
	sc.close();  //closes the scanner  
	
	return points;
  }
  
  public static void main(String[] args) throws Exception {
	  //args 0 reads linear or kd type
	  String type = args[0];

	double eps= Double.parseDouble(args[1]);

    // reads the csv file
    List<Point3D> points= Exp1.read(args[2]);

	Point3D query= new Point3D(Double.parseDouble(args[3]),
			Double.parseDouble(args[4]),
			Double.parseDouble(args[5]));
	
	// creates the NearestNeighbor instance
	  NearestNeighbors nnlinear = new NearestNeighbors(points);
	  NearestNeighborsKD nearestNeighborsKD = new NearestNeighborsKD(points);

	

	  long time = System.currentTimeMillis();
	if(type.equalsIgnoreCase("lin")) {
		List<Point3D> neighbors= nnlinear.rangeQuery(eps,query);
		for (Point3D p : neighbors) {
			System.out.println(p.toString());
		}
		long totalTime = System.currentTimeMillis() - time;
		System.out.println("number of neighbors= "+neighbors.size());
		System.out.println("Runtime: " + totalTime +"ms");
	} else {
		List<Point3D> neighbors = nearestNeighborsKD.rangeQuery(eps,query);
		long totalTime = System.currentTimeMillis() - time;
		for(Point3D p : neighbors){
			System.out.println(p.toString());
		}
		System.out.println("number of neighbors= "+neighbors.size());
		System.out.println("Runtime: " + totalTime +"ms");
	}

  }   
}
