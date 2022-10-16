public class Point3D {

    private double x;
    private double y;
    private double z;
    public int label;
    public Point3D(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double distance(Point3D pt){
        return Math.sqrt(Math.pow(this.x,2) + Math.pow(this.y, 2) + Math.pow(this.z, 2));
    }
}
