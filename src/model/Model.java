package model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.database.ImageData;
import model.database.ImageDataInterface;
import model.operations.COMPONENTS;
import model.operations.ClusterObject;
import model.operations.Image;
import model.operations.ImageInterface;
import model.operations.Pixel;
import model.operations.PixelInterface;
import java.util.concurrent.ThreadLocalRandom;
import model.operations.PixelPosition;


/**
 * Given implementation represents a model class. It contains all the business logic of image
 * processing and manipulation.
 * <p>Given Image model class will contains various image processing logics
 * like load, save image, flip an image, convert it to greyscale image based on various value
 * components, combine and  split image etc.</p> Also, it will use generalised methods from the
 * abstract class.
 */
public class Model implements ModelInterface {

  protected ImageDataInterface database;

  /**
   * Given constructor will initialize the database and the utility class.
   */
  public Model() {
    this.database = new ImageData();
  }

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
   * Method to load the image in the hashmap with the provided name.
   *
   * @param in           input stream of the image
   * @param destFileName destination name of the image
   * @throws IOException            input output exception
   * @throws ClassNotFoundException throws if class not found
   */
  @Override
  public void load(InputStream in, String destFileName) throws IOException, ClassNotFoundException {
    ObjectInputStream objectInputStream = new ObjectInputStream(in);
    ImageInterface image = (ImageInterface) objectInputStream.readObject();
    database.storeImage(destFileName, image);
  }

  /**
   * Save the image in the provided path from the hashmap.
   *
   * @param fileName the filename of the image
   * @return input stream of the image
   * @throws IOException input output exception
   */
  @Override
  public InputStream save(String fileName) throws IOException {
    ImageInterface image = database.getImage(fileName);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(baos);
    oos.writeObject(image);
    ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
    return bais;
  }

  /**
   * Given method creates greyscale image of the provided image based on the component given and
   * refer to it henceforth in the program by the given destination name.
   * <p>
   * Here, COMPONENTS is a enum type. It contains various value component like Red, Green, Blue,
   * Value, Intensity and luma.
   * </p>
   *
   * @param component    component value of the channels of the pixel
   * @param fileName     filename of the file needs to be referred
   * @param destFileName filename referred henceforth in the program
   */
  @Override
  public void visualizeComponent(String component, String fileName, String destFileName) {
    ImageInterface image = database.getImage(fileName).cloneImage();

    for (int row = 0; row < image.getHeight(); row++) {
      for (int col = 0; col < image.getWidth(); col++) {
        PixelInterface tempPixel = image.getPixel(row, col);

        COMPONENTS components = getComponent(component);
        int setValue = getValueBasedOnChannel(tempPixel, components);
        tempPixel.setR(setValue);
        tempPixel.setG(setValue);
        tempPixel.setB(setValue);
      }
    }

    database.storeImage(destFileName, image);
  }

  /**
   * Given helper method will get component value from the enum class according to the component.
   *
   * @param component the component passed
   * @return COMPONENTS enum value
   */
  private COMPONENTS getComponent(String component) {
    switch (component) {
      case "red-component":
        return COMPONENTS.RED;
      case "green-component":
        return COMPONENTS.GREEN;
      case "blue-component":
        return COMPONENTS.BLUE;
      case "value-component":
        return COMPONENTS.VALUE;
      case "intensity-component":
        return COMPONENTS.INTENSITY;
      case "luma-component":
        return COMPONENTS.LUMA;
      default:
        break;
    }
    return null;
  }

  /**
   * Given method flip image horizontally and refer to it henceforth in the program by the given
   * destination name..
   *
   * @param fileName     filename of the file needs to be referred
   * @param destFileName filename referred henceforth in the program
   */
  @Override
  public void flipHorizontally(String fileName, String destFileName) {
    ImageInterface image = database.getImage(fileName).cloneImage();

    for (int i = 0; i < image.getHeight(); i++) {
      Collections.reverse(image.getPixels().get(i));
    }

    database.storeImage(destFileName, image);
  }

  /**
   * Given method flip image vertically and refer to it henceforth in the program by the given
   * destination name..
   *
   * @param fileName     filename of the file needs to be referred
   * @param destFileName filename referred henceforth in the program
   */
  @Override
  public void flipVertically(String fileName, String destFileName) {
    ImageInterface image = database.getImage(fileName).cloneImage();

    int height = image.getHeight();
    List<ArrayList<PixelInterface>> pixels = image.getPixels();
    for (int i = 0; i < height / 2; i++) {
      ArrayList<PixelInterface> temp = new ArrayList<>(pixels.get(i));
      ArrayList<PixelInterface> temp2 = pixels.get(i);
      ArrayList<PixelInterface> temp3 = pixels.get(height - i - 1);
      ArrayList<PixelInterface> temp4 = new ArrayList<>(pixels.get(height - i - 1));

      temp2.clear();
      temp2.addAll(temp4);

      temp3.clear();
      temp3.addAll(temp);
    }

    database.storeImage(destFileName, image);
  }

  /**
   * Given method increase or decrease the brightness of the image and refer to it henceforth in the
   * program by the given destination name.
   * <p>
   * A positive integer value of the intensity will brighter the image and a negative integer value
   * of the intensity will darker the image.
   * </p>
   *
   * @param intensity    integer value of increment
   * @param fileName     filename of the file needs to be referred
   * @param destFileName filename referred henceforth in the program
   */
  @Override
  public void brightness(int intensity, String fileName, String destFileName) {
    ImageInterface image = database.getImage(fileName).cloneImage();

    for (int row = 0; row < image.getHeight(); row++) {
      for (int col = 0; col < image.getWidth(); col++) {
        PixelInterface tempPixel = image.getPixel(row, col);

        int redVal = Math.max(0, Math.min(255, tempPixel.getR() + intensity));
        int blueVal = Math.max(0, Math.min(255, tempPixel.getB() + intensity));
        int greenVal = Math.max(0, Math.min(255, tempPixel.getG() + intensity));

        tempPixel.setR(redVal);
        tempPixel.setB(blueVal);
        tempPixel.setG(greenVal);
      }
    }

    database.storeImage(destFileName, image);

  }

  /**
   * Given method split image in the greyscale image of red, green and blue component and refer to
   * it in the program by provided redFileName, greenFileName and blueFileName respectively.
   *
   * @param fileName      filename of the file needs to be referred
   * @param redFileName   red-component filename referred henceforth in the program
   * @param greenFileName green-component filename referred henceforth in the program
   * @param blueFileName  blue-component filename referred henceforth in the program
   */
  @Override
  public void splitRGB(String fileName, String redFileName,
      String greenFileName, String blueFileName) {

    visualizeComponent("red-component", fileName, redFileName);
    visualizeComponent("green-component", fileName, greenFileName);
    visualizeComponent("blue-component", fileName, blueFileName);

  }

  /**
   * Given method will combine red, green and blue greyscale image into one image.
   *
   * @param destFileName  filename referred henceforth in the program
   * @param redFileName   red-component filename of the file needs to be referred
   * @param greenFileName green-component filename of the file needs to be referred
   * @param blueFileName  blue-component filename of the file needs to be referred
   */
  @Override
  public void combineRGB(String destFileName,
      String redFileName, String greenFileName, String blueFileName) {

    ImageInterface redImage = database.getImage(redFileName);
    ImageInterface greenImage = database.getImage(greenFileName);
    ImageInterface blueImage = database.getImage(blueFileName);

    List<ArrayList<PixelInterface>> imagePixels = new ArrayList<ArrayList<PixelInterface>>();

    for (int row = 0; row < redImage.getHeight(); row++) {
      imagePixels.add(new ArrayList<PixelInterface>());
      for (int col = 0; col < redImage.getWidth(); col++) {
        int r = redImage.getPixel(row, col).getR();
        int g = greenImage.getPixel(row, col).getG();
        int b = blueImage.getPixel(row, col).getB();

        imagePixels.get(row).add(col, new Pixel(r, g, b));
      }
    }

    ImageInterface image = new Image(redImage.getWidth(),
        redImage.getHeight(), redImage.getMaxVal(), imagePixels);
    database.storeImage(destFileName, image);
  }

  /**
   * Get pixel value which needs to be updated from the component.
   *
   * @param pixel     the pixel which needs to be updated
   * @param component the component type
   * @return the pixel value which needs to be updated
   */
  private int getValueBasedOnChannel(PixelInterface pixel,
      COMPONENTS component) {
    int setValue = 0;

    switch (component) {
      case RED:
        setValue = pixel.getR();
        break;
      case GREEN:
        setValue = pixel.getG();
        break;
      case BLUE:
        setValue = pixel.getB();
        break;
      case VALUE:
        setValue = pixel.getValue();
        break;
      case LUMA:
        setValue = pixel.getLuma();
        break;
      case INTENSITY:
        setValue = pixel.getIntensity();
        break;
      default:
        break;
    }
    return setValue;
  }

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
