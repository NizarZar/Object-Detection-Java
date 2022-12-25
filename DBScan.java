// Student name: NIZAR ZARIOUH
// Student number: 300263208

import java.io.*;
import java.util.*;
import java.util.List;

public class DBScan {

    // instanciate all required variables
    private double eps;
    private double minPts;
    private int c; // clusterCounter
    private int totalNoises;
    private HashMap<Integer, Double> hashMapR = new HashMap<>(); //hashmap that will contain R values of each cluster
    private HashMap<Integer, Double> hashMapG = new HashMap<>(); // hashmap that will contain G values of each cluster
    private HashMap<Integer, Double> hashMapB = new HashMap<>(); // hashmap that will contain B values of each cluster
    private List<Point3D> points;

    // DBScan constructor
    public DBScan(List<Point3D> points){
        this.points = points;
    }

    // EPS setter
    public void setEps(double eps) {
        this.eps = eps;

    }
    // minPts setter
    public void setMinPts(double minPts) {
        this.minPts = minPts;
    }
    /*
    cluster counter algorithm
    + Added hashmap data structure to store cluster counter and a random rgb value==
    storing number of total noises generated
    */
    public void findClusters(){
        c = 0;
        Stack<Point3D> stack = new Stack<>();
        Random random = new Random();
        totalNoises = 0;
        hashMapR.put(0, 0.0);
        hashMapG.put(0, 0.0);
        hashMapB.put(0, 0.0);
        NearestNeighborsKD neighbors = new NearestNeighborsKD(points);
        for(Point3D point : points){
            final int UNDEFINED = -1;
            if(point.getLabel() != UNDEFINED){
                continue;
            }
            List<Point3D> nearest = neighbors.rangeQuery(eps,point);
            final int NOISE = 0;
            if(nearest.size() < minPts){
                point.setLabel(NOISE);
                totalNoises++;
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
                nearest = neighbors.rangeQuery(eps,q);
                if(nearest.size() >= minPts){
                    for(Point3D point3D : nearest){
                        stack.push(point3D);
                    }
                }
            }
        }
    }


    /* created a method to make it cleaner and easier to create the 3D points after reading the file
    Assigning XYZ values depending of the string array indexes.
    */
    private static Point3D createPoint3D(String[] string){
        double x = Double.parseDouble(string[0]);
        double y = Double.parseDouble(string[1]);
        double z = Double.parseDouble(string[2]);

        return new Point3D(x,y,z);
    }
    // created a method to make it cleaner and easier to create a string that contains all coordinates, clusterCounter and the rgb values for saving the file
    private String createString(Point3D point){
        return point.getX() + "," + point.getY() + "," + point.getZ() + "," + point.getLabel() + "," + hashMapR.get(point.getLabel()) + "," + hashMapG.get(point.getLabel()) +
                "," + hashMapB.get(point.getLabel()) + " \n";
    }

    /*Method creates the first line which contains the variables
    iterating through all the points and creating the lines using the createString method we made then writing it into the file.
     */
    public void save(String filename) throws IOException {
            File csvFile = new File(filename);
            FileWriter fileWriter = new FileWriter(csvFile);
            String firstLine = "x" + "," + "y" + "," + "z" + "," + "C" + "," + "R" + "," + "G"+","+"B" +"\n";
            fileWriter.write(firstLine);
            for(Point3D data : getPoints()){
                String line = createString(data);
                fileWriter.write(line);
            }

            fileWriter.close();

    }
    /*
    Using a scanner to read the lines.
    Skipped the first line as it contains the variables of xyz
    since csv values are separated by "," we split each line from the camma and store it in a list of string
    we create the 3D points with the coordinates XYZ by using the createPoint3D method we made.
    we add the 3D Point to the List of 3D Points
     */
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
        scanner.close();

        return point3DList;
    }

    // Returns the largest cluster number
    public int getNumberOfClusters(){
        return c;
    }

    // Returns the list of 3D Points
    public List<Point3D> getPoints() {
        
        return points;

    }

    public double getEps(){
        return eps;
    }

    public double getMinPts() {
        return minPts;
    }

    /*
        This method will print the total number of clusters, total number of noises and the sizes of each cluster from largest to smallest
        We used a hashmap to store the points and then a LinkedHashMap to sort the hashmap in descending order (values not keys)
         */
    public void clustersSize(){
        HashMap<Integer, Integer> hashMapClustersSize = new HashMap<>();
        LinkedHashMap<Integer, Integer> linkedHashMap = new LinkedHashMap<>();

        for(Point3D point : points){
           hashMapClustersSize.merge(point.getLabel(),1,Integer::sum);
       }
        hashMapClustersSize.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEach(reverse -> linkedHashMap.put(reverse.getKey(),reverse.getValue()));
        for(int cluster : linkedHashMap.keySet()){
            System.out.println("Cluster: " + cluster + " has " + linkedHashMap.get(cluster) + " points");
        }
        System.out.println("Total number of clusters found: " + c);
        System.out.println("Total number of noises found: " + totalNoises);

    }




}


