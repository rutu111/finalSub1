package controller.utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;

import model.operations.Image;
import model.operations.ImageInterface;
import model.operations.Pixel;
import model.operations.PixelInterface;

/**
 * Utility class for the image processing.
 * This helper class will help to process various kinds of
 * operations on the image like save and read.
 */
public class ImageUtilsImpl implements ImageUtils {

  /**
   * Given method will read the ppm image from the specified path
   * and return a new image object.
   * <p>
   * It reads image in pixels format.
   * Also, save their width, height and maxvalue.
   * </p>
   *
   * @param path file path provided to read the file
   * @return image in form of input stream
   */
  @Override
  public InputStream readPPMImage(String path) throws FileNotFoundException, IOException {
    Scanner sc;
    List<ArrayList<PixelInterface>> pixels = new ArrayList<ArrayList<PixelInterface>>();

    sc = new Scanner(new FileInputStream(path));


    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (!s.isEmpty() && s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;
    token = sc.next();

    if (!token.equals("P3")) {
      throw new IllegalArgumentException();
    }
    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();

    for (int i = 0; i < height; i++) {
      pixels.add(new ArrayList<PixelInterface>());
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        PixelInterface pixel = new Pixel(r, g, b);
        pixels.get(i).add(j, pixel);
      }
    }
    return objectToInputStream(new Image(width, height, maxValue, pixels));
  }

  /**
   * Given method will read the various kinds of image
   * and convert it to image input stream.
   *
   * @param path file path provided to read the file
   * @return image in form of input stream
   */
  @Override

  public InputStream readImage(String path) throws IOException {
    BufferedImage bufImgs = ImageIO.read(new File(path));
    int height = bufImgs.getHeight();
    int width = bufImgs.getWidth();

    List<ArrayList<PixelInterface>> pixels = new ArrayList<ArrayList<PixelInterface>>();

    for (int i = 0; i < height; i++) {
      pixels.add(new ArrayList<PixelInterface>());
      for (int j = 0; j < width; j++) {
        int a = bufImgs.getRGB(j, i);
        Color c = new Color(a);
        PixelInterface pixel = new Pixel(c.getRed(), c.getGreen(), c.getBlue());
        pixels.get(i).add(j, pixel);
      }
    }
    return objectToInputStream(new Image(width, height, 255, pixels));
  }

  /**
   * Save image provided in the specified path.
   *
   * @param path  provided path where file needs to be saved
   * @param image image which needs to be saved
   * @throws IOException throws input output exception
   */
  @Override
  public void savePPMImage(String path, ImageInterface image) throws IOException {


    Path filePath = Paths.get(path);
    File directory = new File(filePath.getParent().toString());
    if (!directory.exists()) {
      directory.mkdir();
    }

    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path)));
    writer.write("P3");
    writer.newLine();
    writer.write(image.getWidth() + " " + image.getHeight());
    writer.newLine();
    writer.write(String.valueOf(image.getMaxVal()));
    for (int row = 0; row < image.getHeight(); row++) {
      for (int column = 0; column < image.getWidth(); column++) {
        writer.newLine();
        writer.write(String.valueOf(image.getPixel(row, column).getR()));
        writer.newLine();
        writer.write(String.valueOf(image.getPixel(row, column).getG()));
        writer.newLine();
        writer.write(String.valueOf(image.getPixel(row, column).getB()));
      }
    }
    writer.flush();
    writer.close();
  }

  /**
   * Save image in the specified path in form of provided file type.
   *
   * @param path     provided path where file needs to be saved
   * @param image    image which needs to be saved
   * @param fileType file type in which image needs to be saved
   * @throws IOException throws input output exception
   */
  @Override
  public void saveImage(String path, ImageInterface image, String fileType) throws IOException {
    int width = image.getWidth();
    int height = image.getHeight();
    List<ArrayList<PixelInterface>> pixels = image.getPixels();

    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    for (int i = 0; i < height; ++i) {
      for (int j = 0; j < width; ++j) {
        PixelInterface pixel = image.getPixel(i, j);
        int rgb = pixel.getR();
        rgb = (rgb << 8) + pixel.getG();
        rgb = (rgb << 8) + pixel.getB();
        bufferedImage.setRGB(j, i, rgb);
      }
    }
    ImageIO.write(bufferedImage, fileType, new File(path));
  }

  /**
   * Helper method to convert image object into input stream.
   *
   * @param image the provided image object.
   * @return converted input stream of the image
   * @throws IOException input output exception
   */
  private InputStream objectToInputStream(ImageInterface image) throws IOException {
    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      // Create an ObjectOutputStream to write the object to the ByteArrayOutputStream
      ObjectOutputStream oos = new ObjectOutputStream(baos);
      // Write the object to the ObjectOutputStream
      oos.writeObject(image);
      // Create a ByteArrayInputStream from the byte array in the ByteArrayOutputStream
      ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());

      return bais;
    } catch (IOException e) {
      System.out.println(e.toString());
      throw new IOException(e);
    }
  }

}
