package model.database;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import model.operations.ImageInterface;

/**
 * Given class represent a database in form of a Hashmap.
 * Here, Hashmap is a key value pair
 * where image represent as a value and file name is a key.
 * <p>This contains method to fetch and store data
 * in the hashmap.</p>
 */
public class ImageData implements ImageDataInterface, Serializable {
  private final Map<String, ImageInterface> imageCollection;

  /**
   * A constructor which initialize the hashmap.
   */
  public ImageData() {
    this.imageCollection = new HashMap<>();
  }

  /**
   * This method will store the image in the hashmap.
   *
   * @param imageName the image name which represent a key
   * @param img       the image which represents a value
   */
  @Override
  public void storeImage(String imageName, ImageInterface img) {
    imageCollection.put(imageName, img);
  }

  /**
   * This method will fetch image from image name.
   *
   * @param imageName the image name of the image
   * @return the image needs to be fetched
   */
  @Override
  public ImageInterface getImage(String imageName) {
    return imageCollection.get(imageName);
  }
}
