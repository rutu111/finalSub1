package model;

import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.image.BufferedImage;

/**
 * Given interface will extend Model interface
 * and add summary of the new image processing and manipulation methods.
 */
public interface EnhancedModelInterface extends ModelInterface {

  /**
   * Given method will apply filter on image according to the
   * filter type provided.
   *
   * @param filterType   the filter type of filter needs to be applied
   * @param fileName     the file name  of the image
   * @param destFileName destination name of image
   */
  void filter(String filterType, String fileName, String destFileName);

  /**
   * Given method will transform the image according to the
   * color transformation type provided.
   *
   * @param transformationType the transformation needs to be applied
   * @param fileName           the file name of the image
   * @param destFileName       destination name of the image
   */
  void colorTransformation(String transformationType, String fileName, String destFileName);

  /**
   * Given method will convert image to the dither image.
   *
   * @param fileName     filename of the image
   * @param destFileName destination name of the image
   */
  void dither(String fileName, String destFileName);

  /**
   * Method to create three histograms of red,green and blue with intensity.
   *
   * @return the processed dataset.
   */
  DefaultCategoryDataset histogram(String fileName);

  /**
   * Method to get the current image which is previewed.
   *
   * @return a BufferedImage type of object.
   */
  BufferedImage getCurrentImage(String imageName);
}
