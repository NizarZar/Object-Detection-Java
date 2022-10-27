

public class Point3D {

    private double x;
    private double y;
    private double z;
    private int label;
    public Point3D(double x, double y, double z){
        setLabel(0);
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

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }


    public double distance(Point3D pt){
        return Math.sqrt((Math.pow(this.x - pt.getX(), 2)) + (Math.pow(this.y-pt.getY(), 2)) + (Math.pow(this.z - pt.getZ(), 2)));
    }

   @Override
   public String toString(){
        return (this.x + "" + this.y + "" + this.z + "");
   }

}
