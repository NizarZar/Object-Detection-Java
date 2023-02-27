/*
 * Incomplete Experiment 1
 *
 * CSI2510 Algorithmes et Structures de Donnees
 * www.uottawa.ca
 *
 * Robert Laganiere, 2022
 *
 */
import java.security.InvalidParameterException;
import java.util.List;
import java.util.ArrayList;

import java.io.*;
import java.util.Scanner;

public class Exp2 {

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
        String type = args[0];
        double eps= Double.parseDouble(args[1]);
        int steps = Integer.parseInt(args[3]);
        // reads the csv file
        //exp1 read similiar
        List<Point3D> points= read(args[2]);
        

        // creates the NearestNeighbor instance
        NearestNeighbors nearestNeighborsLinear = new NearestNeighbors(points);
        NearestNeighborsKD nearestNeighborsKD = new NearestNeighborsKD(points);
        long totalTime = 0;
        for(int i = 0;i<points.size();i+=steps){
            Point3D currentPoint = points.get(i);
            if(type.equalsIgnoreCase("lin")){
                long startTime = System.nanoTime();
                nearestNeighborsLinear.rangeQuery(eps,currentPoint);
                totalTime+=System.nanoTime()-startTime;
            } else if (type.equalsIgnoreCase("kd")){
                long startTime = System.nanoTime();
                nearestNeighborsKD.rangeQuery(eps, currentPoint);
                totalTime += System.nanoTime() - startTime;
            } else {
                throw new Exception("Invalid parameter type!");
            }

        }
        System.out.println("Total time to find neighbors in the range query: " + totalTime+"ns");
        


    }
}
