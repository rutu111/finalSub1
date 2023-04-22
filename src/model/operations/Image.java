package model.operations;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Given class is an image entity class.
 * It also contains various methods performed on the image.
 */
public class Image implements ImageInterface, Serializable {

  private final int width;
  private final int height;
  private final int maxVal;
  private final List<ArrayList<PixelInterface>>
          pixels;

  /**
   * Given constructor will initialize the width, height, maxVal and
   * pixels of image.
   *
   * @param width  the width of the image
   * @param height the height of the image
   * @param maxVal the maxVal of the image
   * @param pixels the pixels of the image
   */
  public Image(int width, int height, int maxVal, List<ArrayList<PixelInterface>> pixels) {
    this.width = width;
    this.height = height;
    this.maxVal = maxVal;
    this.pixels = pixels;
  }

  /**
   * Given method will clone the image.
   *
   * @return the copied image
   */
  @Override
  public ImageInterface cloneImage() {
    return new Image(width, height, maxVal, makeCopyOfArray(pixels));
  }

  /**
   * Given method will make deep copied the pixels provided.
   *
   * @param pixels the array of pixels need to be copied
   * @return copied array of pixels
   */
  protected List<ArrayList<PixelInterface>> makeCopyOfArray(List<ArrayList<PixelInterface>>
                                                                    pixels) {
    List<ArrayList<PixelInterface>> newArray = new ArrayList<ArrayList<PixelInterface>>();
    for (ArrayList<PixelInterface> p : pixels) {
      ArrayList<PixelInterface> na = new ArrayList<>();
      for (PixelInterface pm : p) {
        na.add(new Pixel(pm.getR(), pm.getG(), pm.getB()));
      }
      newArray.add(na);
    }
    return newArray;
  }

  /**
   * Given method will get width of the given image.
   *
   * @return width of the image
   */
  @Override
  public int getWidth() {
    return width;
  }

  /**
   * Given method will get height of the given image.
   *
   * @return height of the image
   */
  @Override
  public int getHeight() {
    return height;
  }

  /**
   * Given method will get maximum value of the given image.
   *
   * @return maxVal of the image
   */
  @Override
  public int getMaxVal() {
    return maxVal;
  }

  /**
   * Given method will get array of the pixels of the given image.
   *
   * @return pixel arrays of the image
   */
  @Override
  public List<ArrayList<PixelInterface>> getPixels() {
    return pixels;
  }

  /**
   * Given method will get the pixel at the given position (row,col) of the image.
   *
   * @return pixel at the given position.
   */
  @Override
  public PixelInterface getPixel(int row, int col) {
    return pixels.get(row).get(col);
  }

}