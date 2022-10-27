import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.Stack;


public class DBScan {

    private double eps;
    private double minPts;
    private int c;
    private HashMap<Integer, Double> hashMapR = new HashMap<>();
    private HashMap<Integer, Double> hashMapG = new HashMap<>();
    private HashMap<Integer, Double> hashMapB = new HashMap<>();
    private Random random = new Random();
    private List<Point3D> points;

    public DBScan(List<Point3D> points){
        this.points = points;


    }

    public void setEps(double eps) {
        this.eps = eps;

    }

    public void setMinPts(double minPts) {
        this.minPts = minPts;
    }


    public void findClusters(){
        NearestNeighbors N = new NearestNeighbors(points);
        Stack<Point3D> stack = new Stack<>();
        hashMapR.put(-1, random.nextDouble());
        hashMapG.put(-1, random.nextDouble());
        hashMapB.put(-1, random.nextDouble());
        c = 0;
        for(Point3D point : points){
            final int UNDEFINED = 0;
            if(point.getLabel() != UNDEFINED){
                continue;
            }
            List<Point3D> nearest = N.rangeQuery(eps,point);
            int NOISE = -1;
            if(nearest.size() < minPts){
                point.setLabel(NOISE);
                continue;
            }

            c++;
            hashMapR.put(c,random.nextDouble());
            hashMapG.put(c,random.nextDouble());
            hashMapB.put(c,random.nextDouble());
            point.setLabel(c);
            for(Point3D p : nearest){
                stack.push(p);
            }
            while(!stack.isEmpty()){
                Point3D q = stack.pop();
                if(q.getLabel() == NOISE){
                    q.setLabel(c);
                }
                if(q.getLabel() != UNDEFINED){
                    continue;
                }
                q.setLabel(c);
                nearest = N.rangeQuery(eps,q);
                if(nearest.size() >= minPts){
                    for(Point3D point3D : nearest){
                        stack.push(point3D);
                    }
                }
            }
        }
    }


    public static List<Point3D> read(String filename) throws IOException {
        Scanner scanner = new Scanner(new File(filename));
        List<Point3D> point3DList = new ArrayList<>();
        String line;
        scanner.nextLine();
        while(scanner.hasNextLine()) {
            line = scanner.nextLine();
            String[] values = line.split(",");
            Point3D point3D = createPoint3D(values);
            point3DList.add(point3D);


        }

        return point3DList;
    }

    private static Point3D createPoint3D(String[] string){
        double x = Double.parseDouble(string[0]);
        double y = Double.parseDouble(string[1]);
        double z = Double.parseDouble(string[2]);

        return new Point3D(x,y,z);
    }

    private String[] createString(Point3D point){
        String[] strings = new String[7];

        strings[0] = String.valueOf(point.getX());
        strings[1] = String.valueOf(point.getY());
        strings[2] = String.valueOf(point.getZ());
        strings[3] = String.valueOf(point.getLabel());
        strings[4] = String.valueOf(hashMapR.get(point.getLabel()));
        strings[5] = String.valueOf(hashMapG.get(point.getLabel()));
        strings[6] = String.valueOf(hashMapB.get(point.getLabel()));

        return strings;
    }

    public void save(String filename) throws IOException {
            File csvFile = new File(filename);
            FileWriter fileWriter = new FileWriter(csvFile);
            String firstLine = "x" + "," + "y" + "," + "z" + "," + "C" + "," + "R" + "," + "G"+","+"B" +"\n";
            fileWriter.write(firstLine);
            for(Point3D data : getPoints()){
                String line = Arrays.toString(createString(data)) +
                        "," +
                        "\n";
                line = line.substring(1);
                line = line.replaceAll("]","");
                fileWriter.write(line);
            }

            fileWriter.close();

    }

    public int getNumberOfClusters(){

        return c;
    }

    public List<Point3D> getPoints() {
        return points;
    }

    public static void main(String[] args) throws IOException {
        long csvReader = System.currentTimeMillis();
        List<Point3D> ps = read("Point_Cloud_1.csv");
        DBScan dbScan = new DBScan(ps);
        long csvReader2 = System.currentTimeMillis();
        long csvReaderFull = csvReader2-csvReader;
        System.out.println("Reading CSV in " + csvReaderFull + "ms");

        dbScan.setEps(1.2);
        dbScan.setMinPts(10);
        long clusterFinder = System.currentTimeMillis();
        dbScan.findClusters();
        long clusterFinder2 = System.currentTimeMillis();
        long clusterFinderFull = clusterFinder2-clusterFinder;
        System.out.println("Finding clusters in " + clusterFinderFull +"ms");
        String filepath = "data_clusters_"+ dbScan.eps+"_"+dbScan.minPts+"_"+dbScan.getNumberOfClusters()+".csv";
        long fileSaver = System.currentTimeMillis();
        dbScan.save(filepath);
        long fileSaver2 = System.currentTimeMillis();
        long fileSaverTotal = fileSaver2-fileSaver;
        System.out.println("Saving file in " + fileSaverTotal+"ms");
        System.out.println("Number of clusters " + dbScan.getNumberOfClusters());

    }
}


