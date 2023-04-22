package model.operations;

import java.util.ArrayList;
import java.util.List;

/**
 * Given interface represents image entity.
 * It contains summary of various methods performed on the image.
 */
public interface ImageInterface {

  /**
   * Given method will get width of the given image.
   *
   * @return width of the image
   */
  int getWidth();

  /**
   * Given method will get height of the given image.
   *
   * @return height of the image
   */
  int getHeight();

  /**
   * Given method will get maximum value of the given image.
   *
   * @return maxVal of the image
   */
  int getMaxVal();

  /**
   * Given method will get array of the pixels of the given image.
   *
   * @return pixel arrays of the image
   */
  List<ArrayList<PixelInterface>> getPixels();

  /**
   * Given method will get the pixel at the given position (row,col) of the image.
   *
   * @return pixel at the given position.
   */
  PixelInterface getPixel(int row, int col);

  /**
   * Given method will clone the image.
   *
   * @return the copied image
   */
  ImageInterface cloneImage();
}
