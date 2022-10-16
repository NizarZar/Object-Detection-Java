import java.util.List;

public class DBScan {


    private double eps;
    private double minPts;
    List<Point3D> point;

    public DBScan(List<Point3D> point){
        this.point = point;

    }

    public void setEps(double eps) {
        this.eps = eps;

    }

    public void setMinPts(double minPts) {
        this.minPts = minPts;
    }
    public void findClusters(){
            int c = 0;
            for(Point3D p : point){
                if(p.label != 0){

                }
            }
    }

    public int getNumberOfClusters(){

        return 0;
    }

    public List<Point3D> getPoint() {
        return point;
    }
}
