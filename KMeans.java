import java.util.Arrays;
public class KMeans {
  public static void main(String[] args) {
    //Make a list of 500 random numbers between 0 and 100
    double[][] myData = new double[500][3]; //Each point has 3 dimensions
    for(int i = 0; i < myData.length; i++) {
      for(int j = 0; j < myData[i].length; j++) {
        myData[i][j] = Math.random() * 100;
      }
    }
    
    //Look at the first point, that's the number of dimensions we're working with
    int numDimensions = myData[0].length;
    
    //We have 4 centroids each with the number of dimensions in the first point
    double[][] centroids = new double[4][numDimensions];
    
    //Find the minimum and maximum value for each dimension
    //Randomly place the centroids within these bounds
    for(int i = 0; i < numDimensions; i++) {
      double max = myData[0][i];
      double min = myData[0][i];
      for(int j = 1; j < myData.length; j++) {
        if(myData[j][i] > max) {
          max = myData[j][i];
        }
        if(myData[j][i] < min) {
          min = myData[j][i];
        }
      }
      for(int j = 0; j < centroids.length; j++) {
        centroids[j][i] = (Math.random() * (max - min)) + min;
      }
    }
    
    //We have a list corresponding to each centroid to put the closest points in
    double[][][] centroidLists = new double[4][myData.length][numDimensions];
    
    //To keep track of how many points are in each list
    int[] listSizes = new int[4];
    boolean converged = false;
    
    //Until we converge
    while(!converged) {
      //Print out some info about the centroids for debugging
      for(int i = 0; i < centroids.length; i++) {
        System.out.println("Centroid " + (i + 1) + ": " + Arrays.toString(centroids[i]));
        System.out.println("List size: " + listSizes[i]);
      }
      System.out.println("---------------------------------------");
      //We're resetting the lists to add all the new points
      for(int i = 0; i < 4; i++) {
        listSizes[i] = 0;
      }
      //Find the minimum distance for each point to each centroid
      for(int i = 0; i < myData.length; i++) {
        double min = distance(myData[i], centroids[0]);
        int closest = 0;
        for(int j = 1; j < centroids.length; j++) {
          double distance = distance(myData[i], centroids[j]);
          if(distance < min) {
            min = distance;
            closest = j;
          }
        }
        //Put the point in the appropriate list
        centroidLists[closest][listSizes[closest]] = myData[i];
        listSizes[closest]++;
      }
      //Assume we've converged
      converged = true;
      //We're gonna get the average value for each dimension in each ccentroid
      for(int i = 0; i < centroids.length; i++) {
        for(int j = 0; j < numDimensions; j++) {
          double mean = 0;
          for(int k = 0; k < listSizes[i]; k++) {
            mean += centroidLists[i][k][j];
          }
          //This finds the mean
          mean /= listSizes[i];
          //Now we compare it against the value that's already there
          if(Math.abs(centroids[i][j] - mean) > .001) {
            //If they're not close enough together, we haven't converged
            converged = false;
          }
          centroids[i][j] = mean;
        }
      }
    }
    
  }
  
  //Return the euclidian distance between 2 points
  public static double distance(double[] p1, double[] p2) {
    double sum = 0;
    for(int i = 0; i < p1.length; i++) {
      sum += Math.pow(p1[i] - p2[i], 2);
    }
    return Math.sqrt(sum);
  }
}