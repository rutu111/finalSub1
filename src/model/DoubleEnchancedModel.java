package model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import model.operations.ClusterObject;
import model.operations.ImageInterface;
import model.operations.PixelInterface;
import model.operations.PixelPosition;

/**
 * This class is to incorporate the newest edition of this
 * program.
 */
public class DoubleEnchancedModel extends EnhancedModel implements DoubleEnchancedInterface {

  /**
   * This method isu used to find the smallest distance in a given array.
   * @param distances an array of distances.
   * @return the index of the smallest element in the array.
   */
  private static int FindSmallest(List<Double> distances) {//start method

    int minIndex = 0;

    for (int i = 1; i < distances.size(); i++) {
      if (distances.get(i) < distances.get(minIndex)) {
        minIndex = i;
      }
    }
    return minIndex;

  }

  /**
   * This function is used to find the euclidean distance between two points.
   * @param x1 x coorindate of point 1
   * @param y1 y coorindate of point 2
   * @param x2 x coorindate of point 2
   * @param y2 y coorindate of point 2
   * @return euclidean distance between two points.
   */
  private double distance(int x1, int y1, int x2, int y2) {
    //euclidean distance
    return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
  }

  @Override
  public void mosaicking(int seeds, String fileName, String destFileName)
      throws IllegalArgumentException {
    //1. seeds:
    //throw exception if seed < 1000. This is the minimum value possible.
    // with the number given, generate n seeds of row and col positions of the image.
    //save each seed as a cluster object.

    if (seeds < 1000) {
      throw new IllegalArgumentException("Seeds can only 1000 and above. ");
    }

    ImageInterface image = database.getImage(fileName).cloneImage();

    List<ClusterObject> randomSeeds = new ArrayList<>();

    for (int i = 0; i < seeds; i++) {
      int row = ThreadLocalRandom.current().nextInt(0, image.getHeight() - 1); //row
      int col = ThreadLocalRandom.current().nextInt(0, image.getWidth() - 1); //column
      PixelPosition pixel = new PixelPosition(image.getPixel(row, col), row, col);
      ClusterObject clusterObject = new ClusterObject(pixel);
      randomSeeds.add(clusterObject);
    }


    //2. Calaculate the distance
    // loop through each pixel in the image
    // calculate the distance between this pixel and all the seeds.
    // assign a clyster to this based based on shortest cluster
    // add this pixel to that cluster
    List<Double> distances;
    for (int row = 0; row < image.getHeight(); row++) {
      for (int col = 0; col < image.getWidth(); col++) {
        distances = new ArrayList<>();
        for (int cluster = 0; cluster < randomSeeds.size(); cluster++) {
          distances.add(distance(row, col, randomSeeds.get(cluster).getRow(),
              randomSeeds.get(cluster).getColumn()));
        }
        ClusterObject closestCluster = randomSeeds.get(FindSmallest(distances));
        PixelPosition pixelToAdd = new PixelPosition(image.getPixel(row, col), row, col);
        closestCluster.add(pixelToAdd);
      }
    }


    //3. calculate cluster mean for all the clusters.
    for (int cluster = 0; cluster < randomSeeds.size(); cluster++) {
      randomSeeds.get(cluster).clusterMean();
    }


    //4. build the new image.
    for (int cluster = 0; cluster < randomSeeds.size(); cluster++) {
      List<PixelPosition> pointsInCluster = randomSeeds.get(cluster).getAll();
      if (pointsInCluster.size() > 0) {
        for (int pixel = 0; pixel < pointsInCluster.size(); pixel++) {
          PixelInterface tempPixel = image.getPixel(pointsInCluster.get(pixel).getRow(),
              pointsInCluster.get(pixel).getColumn());
          tempPixel.setR(pointsInCluster.get(pixel).getPixel().getR());
          tempPixel.setG(pointsInCluster.get(pixel).getPixel().getG());
          tempPixel.setB(pointsInCluster.get(pixel).getPixel().getB());
        }
      }
    }

    //save the new image to database
    database.storeImage(destFileName, image);
  }
}
