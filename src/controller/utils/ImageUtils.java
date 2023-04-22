package controller.utils;

import java.io.IOException;
import java.io.InputStream;

import model.operations.ImageInterface;

/**
 * Given interface summarize the utility class for the image processing.
 * It contains method overview of read and write images.
 */
public interface ImageUtils {

  /**
   * Given method will read the ppm image from the specified path
   * and return a new image object.
   * <p>
   * It reads image in pixels format.
   * Also, save their width, height and maxvalue.
   * </p>
   *
   * @param path file path provided to read the file
   * @return image object create by reading provided file
   */
  InputStream readPPMImage(String path) throws IOException, IllegalArgumentException;

  /**
   * Given method will read the various kinds of image
   * and convert it to image input stream.
   *
   * @param path file path provided to read the file
   * @return image in form of input stream
   */
  InputStream readImage(String path) throws IOException, IllegalArgumentException;

  /**
   * Save image provided in the specified path.
   *
   * @param path  provided path where file needs to be saved
   * @param image image which needs to be saved
   * @throws IOException throws input output exception
   */
  void savePPMImage(String path, ImageInterface image) throws IOException;

  /**
   * Save image in the specified path in form of provided file type.
   *
   * @param path     provided path where file needs to be saved
   * @param image    image which needs to be saved
   * @param fileType file type in which image needs to be saved
   * @throws IOException throws input output exception
   */
  void saveImage(String path, ImageInterface image, String fileType) throws IOException;
}
