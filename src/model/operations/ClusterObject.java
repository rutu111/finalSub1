package model.operations;

import java.util.ArrayList;
import java.util.List;

public class ClusterObject {

  private  PixelPosition clusterObject;

  private List<PixelPosition> randomSeeds = new ArrayList<>();



  public ClusterObject(PixelPosition clusterObject) {
    this.clusterObject = clusterObject;
  }

  public int getRow() {
    return this.clusterObject.getRow();
  }

  public int getColumn() {
    return this.clusterObject.getColumn();
  }

  public void add(PixelPosition pixel) {
    randomSeeds.add(pixel);
  }

  public void clusterMean() {
    //by the time we come here, we will have all the pixels that belong to this cluster including itself (as distance to self is 0)/
    //average of red green and blue
    if (randomSeeds.size() > 0) {
      int red = 0;
      int green = 0;
      int blue = 0;
      for (int pixel = 0; pixel < randomSeeds.size(); pixel++) {
        red += randomSeeds.get(pixel).getPixel().getR();
        green += randomSeeds.get(pixel).getPixel().getG();
        blue += randomSeeds.get(pixel).getPixel().getB();
      }

      int averageRed = (int) red / randomSeeds.size();
      int averageGreen = (int) green / randomSeeds.size();
      int averageBlue = (int) blue / randomSeeds.size();


      PixelInterface newPixel = new Pixel(averageRed, averageGreen, averageBlue);

      //now replace all the pixels in randomSeeds with the new pixel
      for (int pixel = 0; pixel < randomSeeds.size(); pixel++) {
        randomSeeds.get(pixel)
            .setPixel(newPixel); //now our randomseeds contains the averages, including the seed

      }
    }
  }

  public List<PixelPosition> getAll() {
    return randomSeeds; //must include self too.
  }
}
