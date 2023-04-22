package view;

import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.image.BufferedImage;

import features.Features;

/**
 * Interface class for the View class implementation.
 */
public interface JFrameView {

  /**
   * Adds action listeners methods for the respective buttons.
   *
   * @param features object of the controller.
   */
  void addFeatures(Features features);

  /**
   * Method to display the required error messages to the
   * user whenever an invalid action is performed.
   *
   * @param errorMsg message to be printed.
   */
  void showErrorMsg(String errorMsg);

  /**
   * Method to reload the image once updated.
   *
   * @param image to be reloaded.
   */
  void refreshImage(BufferedImage image);

  /**
   * Method to reload the histogram.
   *
   * @param dataset to be reloaded.
   */
  void refreshHistogram(DefaultCategoryDataset dataset);
}
