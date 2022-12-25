import java.io.IOException;
import java.util.List;

public class Exp3 {

    // DBScan as in programming assignment 1
    public static void main(String[] args) throws IOException {
        long totalRun1 = System.currentTimeMillis();
        long csvReader = System.currentTimeMillis();
        List<Point3D> db = DBScan.read(args[0]); // the list of 3d points will be taken from reading the csv file, which will be the first argument when executing the program
        DBScan dbScan = new DBScan(db); // creating a dbscan using the db points read
        long csvReader2 = System.currentTimeMillis();
        long csvReaderFull = csvReader2-csvReader; // time it took to execute the read csv file algorithm
        System.out.println("Reading CSV in: " + csvReaderFull + "ms");
        dbScan.setEps(Double.parseDouble(args[1])); // the eps number will be the second argument given to the program to execute properly
        dbScan.setMinPts(Double.parseDouble(args[2])); // minPts number will be the third argument given to the program to execute properly
        long clusterFinder = System.currentTimeMillis();
        dbScan.findClusters(); // finding the clusters
        long clusterFinder2 = System.currentTimeMillis();
        long clusterFinderFull = clusterFinder2-clusterFinder; // find how much time it takes to run the findClusters algorithm
        System.out.println("Finding clusters in: " + clusterFinderFull +"ms");
        String filepath = "data"+"_clusters_"+ dbScan.getEps()+"_"+dbScan.getMinPts()+"_"+dbScan.getNumberOfClusters()+".csv"; // the save file format
        long fileSaver = System.currentTimeMillis();
        dbScan.save(filepath); // saving the results in a csv file
        long fileSaver2 = System.currentTimeMillis();
        long fileSaverTotal = fileSaver2-fileSaver;
        System.out.println("Saving file in: " + fileSaverTotal+"ms"); // how much it took to save the file
        dbScan.clustersSize(); // calling clustersSize method to get information about the clusters, noises and the sizes.
        long totalRun = System.currentTimeMillis() - totalRun1;
        System.out.println("The program took " + totalRun + "ms to run."); //total amount of time it took to run the program

    }
}
