// Student name: NIZAR ZARIOUH
// Student number: 300263208

import java.util.ArrayList;
import java.util.List;

public class NearestNeighbors{

    // constructor
    List<Point3D> point3DList;
    public NearestNeighbors(List<Point3D> point3DList){
        this.point3DList = point3DList;
    }

    // range query algorithm
    public List<Point3D> rangeQuery(double eps, Point3D p){
        List<Point3D> sequence = new ArrayList<>();
        for (Point3D point : this.point3DList){
            if(point.distance(p) <= eps){
                sequence.add(point);
            }
        }
        return sequence;
    }
}
