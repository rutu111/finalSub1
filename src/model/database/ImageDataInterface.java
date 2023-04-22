package model.database;

import model.operations.ImageInterface;

/**
 * Given interface represent summary of methods perform on the
 * database.
 * Here, database represent in the form of hashmap.
 */
public interface ImageDataInterface {

  /**
   * This method will store the image in the hashmap.
   *
   * @param imageName the image name which represent a key
   * @param img       the image which represents a value
   */
  void storeImage(String imageName, ImageInterface img);

  /**
   * This method will fetch image from image name.
   *
   * @param imageName the image name of the image
   * @return the image needs to be fetched
   */
  ImageInterface getImage(String imageName);
}
