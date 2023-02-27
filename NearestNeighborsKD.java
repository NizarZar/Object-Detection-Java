import java.util.ArrayList;
import java.util.List;

public class NearestNeighborsKD {

    KDtree tree;
    List<Point3D> points;
    public NearestNeighborsKD(List<Point3D> list){
        this.points=list;
        tree = new KDtree(list.get(0),0);
        for(int i = 1;i<list.size();i++){
            tree.insert(list.get(i),tree.getRoot(),0);
        }



    }

    // rangequery for the kd tree
    private void rangeQuery(Point3D p, double eps, List<Point3D> neighbors, KDtree.KDNode node){
        if(node == null){
            return;
        }
        if(p.distance(node.point) < eps){
            neighbors.add(node.point);
        }
        if(p.get(node.axis) - eps <= node.value){
            rangeQuery(p,eps,neighbors,node.left);
        }
        if(p.get(node.axis) + eps > node.value){
            rangeQuery(p,eps,neighbors,node.right);
        }

    }

    // range query that use recursive method to compute the neighbors
    public List<Point3D> rangeQuery(double eps, Point3D p){
        List<Point3D> neighbors = new ArrayList<>();
        rangeQuery(p,eps,neighbors,tree.getRoot());
        return neighbors;
    }

}
