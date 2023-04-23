package model;

import java.io.IOException;
import java.io.InputStream;

/**
 * Given interface contains summary of the business logic contains in the image model.
 *
 * <p>Given model class contains all the business logic of image processing and manipulation.
 * It takes input command from the controller, do process according to the command
 * provided and return appropriate output to the controller.</p>
 */
public interface ModelInterface {

  /**
   * Method to load the image in the hashmap with the provided name.
   *
   * @param in           input stream of the image
   * @param destFileName destination name of the image
   * @throws IOException            input output exception
   * @throws ClassNotFoundException throws if class not found
   */
  void load(InputStream in, String destFileName) throws IOException, ClassNotFoundException;

  /**
   * Save the image in the provided path from the hashmap.
   *
   * @param fileName the filename of the image
   * @return input stream of the image
   * @throws IOException input output exception
   */
  InputStream save(String fileName) throws IOException;

  /**
   * Given method creates greyscale image of the provided image based on the component given
   * and refer to it henceforth in the program by the given destination name.
   * <p>
   * Here, COMPONENTS is a enum type.
   * It contains various value component like Red, Green, Blue, Value, Intensity and luma.
   * </p>
   *
   * @param component    component value of the channels of the pixel
   * @param fileName     filename of the file needs to be referred
   * @param destFileName filename referred henceforth in the program
   */
  void visualizeComponent(String component, String fileName, String destFileName);

  /**
   * Given method flip image horizontally
   * and refer to it henceforth in the program by the given destination name..
   *
   * @param fileName     filename of the file needs to be referred
   * @param destFileName filename referred henceforth in the program
   */
  void flipHorizontally(String fileName, String destFileName);

  /**
   * Given method flip image vertically
   * and refer to it henceforth in the program by the given destination name..
   *
   * @param fileName     filename of the file needs to be referred
   * @param destFileName filename referred henceforth in the program
   */
  void flipVertically(String fileName, String destFileName);

  /**
   * Given method increase or decrease the brightness of the image
   * and refer to it henceforth in the program by the given destination name.
   * <p>
   * A positive integer value of the intensity will brighter the image
   * and a negative integer value of the intensity will darker the image.
   * </p>
   *
   * @param intensity    integer value of increment
   * @param fileName     filename of the file needs to be referred
   * @param destFileName filename referred henceforth in the program
   */
  void brightness(int intensity, String fileName, String destFileName);

  /**
   * Given method split image in the greyscale image of red, green and blue component
   * and refer to it in the program by provided redFileName, greenFileName and
   * blueFileName respectively.
   *
   * @param fileName      filename of the file needs to be referred
   * @param redFileName   red-component filename referred henceforth in the program
   * @param greenFileName green-component filename referred henceforth in the program
   * @param blueFileName  blue-component filename referred henceforth in the program
   */
  void splitRGB(String fileName, String redFileName, String greenFileName, String blueFileName);

  /**
   * Given method will combine red, green and blue greyscale image into one image.
   *
   * @param destFileName  filename referred henceforth in the program
   * @param redFileName   red-component filename of the file needs to be referred
   * @param greenFileName green-component filename of the file needs to be referred
   * @param blueFileName  blue-component filename of the file needs to be referred
   */
  void combineRGB(String destFileName,
                  String redFileName, String greenFileName, String blueFileName);

}
