import org.junit.Test;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;

import controller.Controller;
import controller.EnhanceController;
import controller.ImageController;
import model.EnhancedModel;
import model.EnhancedModelInterface;
import model.Model;
import model.ModelInterface;
import model.operations.Pixel;
import model.operations.PixelInterface;
import view.ImageView;
import view.ImageViewImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * A JUnit test to test SimpleController.
 * SimpleController is a main controller class which creates MVC object
 * and performs various tasks.
 */
public class SimpleControllerTest {

  /**
   * Given private method will return an array list of the pixels
   * of the image stored in the given file path.
   *
   * @param filePath file path of the image which needs to be referred
   * @return an array list of the image pixels
   */
  private List<ArrayList<PixelInterface>> getPPMImagePixels(String filePath) {
    Scanner sc;
    List<ArrayList<PixelInterface>> pixels = new ArrayList<ArrayList<PixelInterface>>();
    try {
      sc = new Scanner(new FileInputStream(filePath));
    } catch (FileNotFoundException e) {
      return null;
    }

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
    return pixels;
  }

  private List<ArrayList<PixelInterface>> getImagePixels(String path) throws IOException {
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
    return pixels;
  }

  /**
   * Given private method will check if provided array list of the pixels are same or not.
   *
   * @param storedPixels        an arraylist of the pixels of the stored image
   * @param originalImagePixels an arraylist of the pixels of the original image
   * @return true if both the arraylists are same else false
   */
  private boolean assertPixels(List<ArrayList<PixelInterface>> storedPixels,
                               List<ArrayList<PixelInterface>> originalImagePixels) {
    if (originalImagePixels == null) {
      return false;
    }
    for (int i = 0; i < originalImagePixels.size(); i++) {
      for (int j = 0; j < originalImagePixels.get(0).size(); j++) {
        int r = originalImagePixels.get(i).get(j).getR();
        int g = originalImagePixels.get(i).get(j).getG();
        int b = originalImagePixels.get(i).get(j).getB();
        if (r != storedPixels.get(i).get(j).getR() || g != storedPixels.get(i).get(j).getG()
                && b != storedPixels.get(i).get(j).getB()) {
          return false;
        }
      }
    }
    return (originalImagePixels.size() == storedPixels.size());
  }

  /**
   * Given private method will check if the
   * file stored in given path contains same pixels as stored.
   *
   * @param storedPixels an arraylist of the pixels of the stored image
   * @param path         path of the image which needs to be referred
   * @return true if image contains same pixels else false
   */
  private boolean assertImage(List<ArrayList<PixelInterface>> storedPixels, String path) {
    List<ArrayList<PixelInterface>> originalImagePixels = getPPMImagePixels(path);
    return assertPixels(storedPixels, originalImagePixels);
  }

  /**
   * Given private method will check if file stored in provided paths are the same or not.
   *
   * @param originalImagePath image path of the original image
   * @param savedImagePath    image path of the saved image
   * @return true if both the images in the provided path are the same else false
   * @throws IOException throws input output exception
   */
  private boolean assertImageFiles(String originalImagePath,
                                   String savedImagePath) throws IOException {

    String originalImageFileType = getExtension(originalImagePath);
    String savedImageFileType = getExtension(savedImagePath);

    List<ArrayList<PixelInterface>> originalImagePixels;
    List<ArrayList<PixelInterface>> savedImagePixels;

    if (originalImageFileType.equalsIgnoreCase("ppm")) {
      originalImagePixels = getPPMImagePixels(originalImagePath);
    } else {
      originalImagePixels = getImagePixels(originalImagePath);
    }

    if (savedImageFileType.equalsIgnoreCase("ppm")) {
      savedImagePixels = getPPMImagePixels(savedImagePath);
    } else {
      savedImagePixels = getImagePixels(originalImagePath);
    }
    return assertPixels(savedImagePixels, originalImagePixels);
  }

  /**
   * Method to get the extension of the image file provided.
   *
   * @param path name of the file.
   * @return type of extension.
   */
  private String getExtension(String path) {

    int i = path.lastIndexOf('.');
    if (i > 0) {
      return path.substring(i + 1);
    }
    return "";
  }


  /**
   * JUnit test to check if image successfully
   * loaded and saved using load and save command respectively.
   *
   * @throws IOException throws input output exception
   */
  @Test
  public void loadSaveImage() throws IOException {

    String input = "load res/owl.ppm owl\r\nsave test/images/owl.ppm owl\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();


    ModelInterface model = new Model();
    ImageView view = new ImageViewImpl();
    ImageController controller = new Controller(model, view, in, out);
    controller.executeController();
    assertTrue(assertImageFiles("res/owl.ppm", "test/images/owl.ppm"));

  }

  /**
   * JUnit test to check if image loaded, brighten the loaded image and then save it successfully.
   *
   * @throws IOException throws input output exception
   */
  @Test
  public void loadBrightenSaveImage() throws IOException {
    String input = "load res/owl.ppm owl\r\nbrighten 10 owl "
            + "owl-brighter\r\nsave test/images/owl-brighter.ppm owl-brighter\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();


    ModelInterface model = new Model();
    ImageView view = new ImageViewImpl();
    ImageController controller = new Controller(model, view, in, out);
    controller.executeController();
    assertTrue(assertImageFiles("res/owl-brighter.ppm", "test/images/owl-brighter.ppm"));
  }

  /**
   * JUnit test to check if image loaded, brighten the loaded image and then save it successfully.
   * If command passed is case-sensitive.
   *
   * @throws IOException throws input output exception
   */
  @Test
  public void testCaseSensitivityOfCommand() throws IOException {
    String input = "LOad res/owl.ppm owl\r\nbriGHten 10 owl "
            + "owl-brighter\r\nSAVE test/images/owl-brighter.ppm owl-brighter\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();


    ModelInterface model = new Model();
    ImageView view = new ImageViewImpl();
    ImageController controller = new Controller(model, view, in, out);
    controller.executeController();
    assertTrue(assertImageFiles("res/owl-brighter.ppm", "test/images/owl-brighter.ppm"));
  }

  /**
   * JUnit test to check if image loaded, darken the loaded image and then save it successfully.
   *
   * @throws IOException throws input output exception
   */
  @Test
  public void loadDarkenSaveImage() throws IOException {
    String input = "load res/owl.ppm owl\r\nbrighten "
            + "-50 owl owl-darken\r\nsave test/images/owl-darken.ppm "
            + "owl-darken\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();


    ModelInterface model = new Model();
    ImageView view = new ImageViewImpl();
    ImageController controller = new Controller(model, view, in, out);
    controller.executeController();
    assertTrue(assertImageFiles("res/owl-darken.ppm", "test/images/owl-darken.ppm"));
  }

  /**
   * JUnit test to check if image loaded, flipping vertically and then save it successfully.
   *
   * @throws IOException throws input output exception
   */
  @Test
  public void loadVerticalFlipSaveImage() throws IOException {
    String input = "load res/owl.ppm owl\r\nvertical-flip "
            + "owl owl-vertical\r\nsave test/images/owl-vertical.ppm owl-vertical\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();


    ModelInterface model = new Model();
    ImageView view = new ImageViewImpl();
    ImageController controller = new Controller(model, view, in, out);
    controller.executeController();
    System.out.println(out.toString());
    assertTrue(assertImageFiles("res/owl-vertical.ppm", "test/images/owl-vertical.ppm"));
  }

  /**
   * JUnit test to check if image loaded, flipping
   * horizontally and then save it successfully.
   *
   * @throws IOException throws input output exception
   */

  @Test
  public void loadHorizontalFlipSaveImage() throws IOException {
    String input = "load res/owl.ppm owl\r\nhorizontal-flip "
            + "owl owl-horizontal\r\nsave test/images/owl-horizontal.ppm owl-horizontal\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();


    ModelInterface model = new Model();
    ImageView view = new ImageViewImpl();
    ImageController controller = new Controller(model, view, in, out);
    controller.executeController();
    assertTrue(assertImageFiles("res/owl-horizontal.ppm", "test/images/owl-horizontal.ppm"));
  }

  /**
   * JUnit test to check if image loaded, convert to red
   * greyscale image and then save it successfully.
   *
   * @throws IOException throws input output exception
   */
  @Test
  public void greyscaleRedImage() throws IOException {
    String input = "load res/owl.ppm owl\r\ngreyscale "
            + "red-component owl owl-red\r\nsave test/images/owl-red.ppm owl-red\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();


    ModelInterface model = new Model();
    ImageView view = new ImageViewImpl();
    ImageController controller = new Controller(model, view, in, out);
    controller.executeController();
    assertTrue(assertImageFiles("res/owl-red.ppm",
            "test/images/owl-red.ppm"));
  }

  /**
   * JUnit test to check if image loaded,
   * convert to green greyscale image and then save it successfully.
   *
   * @throws IOException throws input output exception
   */
  @Test
  public void greyscaleGreenImage() throws IOException {
    String input = "load res/owl.ppm owl\r\ngreyscale"
            + " green-component owl owl-green\r\nsave "
            + "test/images/owl-green.ppm owl-green\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();


    ModelInterface model = new Model();
    ImageView view = new ImageViewImpl();
    ImageController controller = new Controller(model, view, in, out);
    controller.executeController();
    assertTrue(assertImageFiles("res/owl-green.ppm",
            "test/images/owl-green.ppm"));
  }

  /**
   * JUnit test to check if image loaded,
   * convert to blue greyscale image and then save it successfully.
   *
   * @throws IOException throws input output exception
   */
  @Test
  public void greyscaleBlueImage() throws IOException {
    String input = "load res/owl.ppm owl\r\ngreyscale "
            + "blue-component owl owl-blue\r\nsave test/images/owl-blue.ppm "
            + "owl-blue\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();


    ModelInterface model = new Model();
    ImageView view = new ImageViewImpl();
    ImageController controller = new Controller(model, view, in, out);
    controller.executeController();
    assertTrue(assertImageFiles("res/owl-blue.ppm",
            "test/images/owl-blue.ppm"));
  }

  /**
   * JUnit test to check if image loaded, convert to
   * greyscale image based on value and then save it successfully.
   *
   * @throws IOException throws input output exception
   */
  @Test
  public void greyscaleValueImage() throws IOException {
    String input = "load res/owl.ppm owl\r\ngreyscale "
            + "value-component owl owl-greyscale-value\r\nsave "
            + "test/images/owl-greyscale-value.ppm owl-greyscale-value\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();


    ModelInterface model = new Model();
    ImageView view = new ImageViewImpl();
    ImageController controller = new Controller(model, view, in, out);
    controller.executeController();
    assertTrue(assertImageFiles("res/owl-greyscale-value.ppm",
            "test/images/owl-greyscale-value.ppm"));
  }

  /**
   * JUnit test to check if image loaded, convert to
   * greyscale image based on luma and then save it successfully.
   *
   * @throws IOException throws input output exception
   */
  @Test
  public void greyscaleLumaImage() throws IOException {
    String input = "load res/owl.ppm owl\r\ngreyscale luma-component"
            + " owl owl-greyscale-luma\r\nsave test/images/owl-greyscale-luma.ppm "
            + "owl-greyscale-luma\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();


    ModelInterface model = new Model();
    ImageView view = new ImageViewImpl();
    ImageController controller = new Controller(model, view, in, out);
    controller.executeController();
    assertTrue(assertImageFiles("res/owl-greyscale-luma.ppm",
            "test/images/owl-greyscale-luma.ppm"));
  }

  /**
   * JUnit test to check if image loaded, convert to
   * greyscale image based on intensity and then save it successfully.
   *
   * @throws IOException throws input output exception
   */
  @Test
  public void greyscaleIntensityImage() throws IOException {
    String input = "load res/owl.ppm owl\r\ngreyscale intensity-component "
            + "owl owl-greyscale-intensity\r\nsave "
            + "test/images/owl-greyscale-intensity.ppm owl-greyscale-intensity\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();


    ModelInterface model = new Model();
    ImageView view = new ImageViewImpl();
    ImageController controller = new Controller(model, view, in, out);
    controller.executeController();
    assertTrue(assertImageFiles("res/owl-greyscale-intensity.ppm",
            "test/images/owl-greyscale-intensity.ppm"));
  }

  /**
   * JUnit test to check if split an image in to three greyscale image based on
   * red, green and blue channels works successfully.
   *
   * @throws IOException throws input output exception
   */
  @Test
  public void rgbSplitImage() throws IOException {
    String input = "load res/owl.ppm owl\r\nrgb-split owl owl-red owl-green owl-blue"
            + "\r\nsave test/images/owl-red.ppm owl-red\n"
            + "save test/images/owl-green.ppm owl-green\n"
            + "save test/images/owl-blue.ppm owl-blue\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();


    ModelInterface model = new Model();
    ImageView view = new ImageViewImpl();
    ImageController controller = new Controller(model, view, in, out);
    controller.executeController();
    assertTrue(assertImageFiles("res/owl-red.ppm",
            "test/images/owl-red.ppm"));
    assertTrue(assertImageFiles("res/owl-green.ppm",
            "test/images/owl-green.ppm"));
    assertTrue(assertImageFiles("res/owl-blue.ppm",
            "test/images/owl-blue.ppm"));
  }

  /**
   * JUnit test to check if combining of r,g,b greyscale
   * image into one color image works successfully.
   *
   * @throws IOException throws input output exception
   */
  @Test
  public void rgbCombine() throws IOException {
    String input = "load res/owl.ppm owl\r\nrgb-split owl owl-red owl-green owl-blue"
            + "\r\nrgb-combine owl-red-tint owl-red owl-green "
            + "owl-blue\r\nsave test/images/owl-red-tint.ppm owl-red-tint\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    ModelInterface model = new Model();
    ImageView view = new ImageViewImpl();
    ImageController controller = new Controller(model, view, in, out);
    controller.executeController();
    assertTrue(assertImageFiles("res/owl-red-tint.ppm",
            "test/images/owl-red-tint.ppm"));
  }

  /**
   * JUnit test to check if error throws when
   * command passed on not existing file name.
   */
  @Test
  public void fileNameNotStored() {
    String input = "load res/owl.ppm owl\r\nvertical-flip owl "
            + "owl-vertical\r\nsave test/images/owl-vertical.ppm owl-horizontal\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();


    ModelInterface model = new Model();
    ImageView view = new ImageViewImpl();
    ImageController controller = new Controller(model, view, in, out);
    controller.executeController();

    assertEquals("Invalid command passed :save "
            + "test/images/owl-vertical.ppm owl-horizontal"
            + System.lineSeparator(), out.toString());

  }

  /**
   * JUnit test to check if error throws when command passed is not valid.
   */
  @Test
  public void invalidCommandName() {
    //owl-horizontal is not stored anywhere in the
    // program so it will return error while saving the image.
    String input = "loaded res/owl.ppm owl";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();


    ModelInterface model = new Model();
    ImageView view = new ImageViewImpl();
    ImageController controller = new Controller(model, view, in, out);
    controller.executeController();

    assertEquals("Invalid command name passed "
            + ":loaded res/owl.ppm owl"
            + System.lineSeparator(), out.toString());

  }

  /**
   * JUnit test to check if error throws when command contains more
   * parameters than expected.
   */
  @Test
  public void moreParametersInCommand() {

    String input = "load test/images/owl.ppm owl koala\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    ModelInterface model = new Model();
    ImageView view = new ImageViewImpl();
    ImageController controller = new Controller(model, view, in, out);
    controller.executeController();
    assertEquals("Invalid arguments passed :load test/images/owl.ppm owl koala"
            + System.lineSeparator(), out.toString());

  }

  /**
   * JUnit test to check if error throws when command contains less
   * parameters than expected.
   */
  @Test
  public void lessParametersInCommand() {

    String input = "load test/images/owl.ppm\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    ModelInterface model = new Model();
    ImageView view = new ImageViewImpl();
    ImageController controller = new Controller(model, view, in, out);
    controller.executeController();
    assertEquals("Invalid arguments passed :load test/images/owl.ppm"
            + System.lineSeparator(), out.toString());
  }

  /**
   * JUnit test to check if error throws when only command is passed
   * without parameters.
   */
  @Test
  public void onlyCommandPassed() {

    String input = "load\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    ModelInterface model = new Model();
    ImageView view = new ImageViewImpl();
    ImageController controller = new Controller(model, view, in, out);
    controller.executeController();
    assertEquals("Invalid arguments passed :load"
            + System.lineSeparator(), out.toString());

  }

  /**
   * JUnit test to check if error throws when PPM image
   * is empty.
   */
  @Test
  public void emptyPPMImage() {

    String input = "load res/owl-empty.ppm owl\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());


    OutputStream out = new ByteArrayOutputStream();

    ModelInterface model = new Model();
    ImageView view = new ImageViewImpl();
    ImageController controller = new Controller(model, view, in, out);
    controller.executeController();
    assertEquals("File passed is empty :load res/owl-empty.ppm owl"
            + System.lineSeparator(), out.toString());
  }

  /**
   * JUnit test to check if error throws when PPM image
   * doesn't exist on file path.
   */
  @Test
  public void imageDontExistOnPath() {

    String input = "load test/images/koala.ppm owl\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    ModelInterface model = new Model();
    ImageView view = new ImageViewImpl();
    ImageController controller = new Controller(model, view, in, out);
    controller.executeController();
    assertEquals("Invalid command passed :load"
            + " test/images/koala.ppm owl", out.toString().trim());
  }

  /**
   * JUnit test to check if image brighten by decimal increment.
   */
  @Test
  public void brightenImageDecimalIncrement() throws IOException {
    String input = "load res/owl.ppm owl\r\nbrighten 33.3 owl "
            + "owl-brighter\r\nsave test/images/owl-brighterDecimal.ppm owl-brighter\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();


    ModelInterface model = new Model();
    ImageView view = new ImageViewImpl();
    ImageController controller = new Controller(model, view, in, out);
    controller.executeController();
    assertTrue(assertImageFiles("res/owl-brighterDecimal.ppm",
            "test/images/owl-brighterDecimal.ppm"));
  }

  /**
   * JUnit test to check if image darken by below lower constraint.
   */
  @Test
  public void brightenDarkenBelowLowerConstraint() throws IOException {
    String input = "load res/owl.ppm owl\r\nbrighten -300 owl "
            + "owl-darken2\r\nsave test/images/owl-darken2.ppm owl-darken2\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();


    ModelInterface model = new Model();
    ImageView view = new ImageViewImpl();
    ImageController controller = new Controller(model, view, in, out);
    controller.executeController();
    assertTrue(assertImageFiles("res/owl-darken2.ppm",
            "test/images/owl-darken2.ppm"));
  }

  /**
   * JUnit test to check if image brighten by highest constraint.
   */
  @Test
  public void brightenAboveHighestConstraint() throws IOException {
    String input = "load res/owl.ppm owl\r\nbrighten 300 owl "
            + "owl-brighten2\r\nsave test/images/owl-brighten2.ppm owl-brighten2\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();


    ModelInterface model = new Model();
    ImageView view = new ImageViewImpl();
    ImageController controller = new Controller(model, view, in, out);
    controller.executeController();
    assertTrue(assertImageFiles("res/owl-brighten2.ppm",
            "test/images/owl-brighten2.ppm"));
  }

  /**
   * JUnit test to check if image flip works.
   */
  @Test
  public void loadVerticalHorizontalFlipSaveImage() throws IOException {
    String input = "load res/owl.ppm owl\r\nvertical-flip owl owl-vertical\n"
            + "horizontal-flip owl-vertical owl-vertical-horizontal"
            + "\r\nsave test/images/owl-vertical-horizontal.ppm owl-vertical-horizontal\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();


    ModelInterface model = new Model();
    ImageView view = new ImageViewImpl();
    ImageController controller = new Controller(model, view, in, out);
    controller.executeController();
    System.out.println(out.toString());
    assertTrue(assertImageFiles("res/owl-vertical-horizontal.ppm",
            "test/images/owl-vertical-horizontal.ppm"));
  }

  /**
   * JUnit test to check if error throws when Invalid value
   * component passed.
   */
  @Test
  public void loadInvalidValueComponent() throws IOException {
    String input = "load res/owl.ppm owl\r\ngreyscale "
            + "block-component owl owl-greyscale-value\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();


    ModelInterface model = new Model();
    ImageView view = new ImageViewImpl();
    ImageController controller = new Controller(model, view, in, out);
    controller.executeController();
    assertEquals("Invalid command passed :greyscale block-component owl owl-greyscale-value"
            + System.lineSeparator(), out.toString());
  }

  /**
   * JUnit test to check if combinations of command works.
   */
  @Test
  public void loadRedLumaSave() throws IOException {
    String input = "load res/owl.ppm owl\r\ngreyscale red-component owl owl-greyscale-red\r\n"
            + "greyscale luma-component owl-greyscale-red owl-greyscale-red-luma\r\n"
            + "save test/images/owl-greyscale-red-luma.ppm owl-greyscale-red-luma\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();


    ModelInterface model = new Model();
    ImageView view = new ImageViewImpl();
    ImageController controller = new Controller(model, view, in, out);
    controller.executeController();

    assertTrue(assertImageFiles("res/owl-greyscale-red-luma.ppm",
            "test/images/owl-greyscale-red-luma.ppm"));
  }

  /**
   * JUnit test to check if error throws when save image
   * without load.
   */
  @Test
  public void saveWithoutLoad() throws IOException {
    String input = "save test/images/owl-greyscale-red-luma.ppm owl-greayscale-red-luma\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();


    ModelInterface model = new Model();
    ImageView view = new ImageViewImpl();
    ImageController controller = new Controller(model, view, in, out);
    controller.executeController();
    assertEquals("Invalid command passed :save "
            + "test/images/owl-greyscale-red-luma.ppm owl-greayscale-red-luma"

            + System.lineSeparator(), out.toString());
  }

  /**
   * JUnit test to check if run script file works.
   */
  @Test
  public void runScriptFile() throws IOException {
    String input = "run command-script.txt\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    ModelInterface model = new Model();
    ImageView view = new ImageViewImpl();
    ImageController controller = new Controller(model, view, in, out);
    controller.executeController();
    assertTrue("".equals(""));
  }

  //new test cases

  /**
   * JUnit test to blur an image.
   */
  @Test
  public void blurImage() throws IOException {
    String input = "load res/owl.ppm owl\nfilter blur owl owl-blur\r\n"
            + "save test/images/owl-blur.ppm owl-blur\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    EnhancedModelInterface model = new EnhancedModel();
    ImageView view = new ImageViewImpl();
    ImageController controller = new EnhanceController(model, view, in, out);
    controller.executeController();
    assertTrue(assertImageFiles("res/owl-blur.ppm",
            "test/images/owl-blur.ppm"));
  }

  /**
   * JUnit test to blur an image.
   */
  @Test
  public void blurImageAgain() throws IOException {
    String input = "load res/owl.ppm owl\nfilter blur owl owl-blur\r\n"
            + "filter blur owl-blur owl-blur2\r\n"
            + "save test/images/owl-blur2.ppm owl-blur2\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    EnhancedModelInterface model = new EnhancedModel();
    ImageView view = new ImageViewImpl();
    ImageController controller = new EnhanceController(model, view, in, out);
    controller.executeController();
    assertTrue(assertImageFiles("res/owl-blur2.ppm",
            "test/images/owl-blur2.ppm"));
  }

  /**
   * JUnit test to sharpe an image.
   */
  @Test
  public void sharpenImage() throws IOException {
    String input = "load res/owl.ppm owl\nfilter sharpe owl owl-sharper\r\n"
            + "save test/images/owl-sharpe.ppm owl-sharper\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    EnhancedModelInterface model = new EnhancedModel();
    ImageView view = new ImageViewImpl();
    ImageController controller = new EnhanceController(model, view, in, out);
    controller.executeController();
    assertTrue(assertImageFiles("res/owl-sharpe.ppm",
            "test/images/owl-sharpe.ppm"));
  }

  /**
   * JUnit test to sharpe an image.
   */
  @Test
  public void sharpenImageAgain() throws IOException {
    String input = "load res/owl-png.png owl\nfilter sharpe owl owl-sharper\r\n"
            + "filter sharpe owl-sharper owl-sharper2\r\n"
            + "save test/images/owl-sharpe2.png owl-sharper2\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    EnhancedModelInterface model = new EnhancedModel();
    ImageView view = new ImageViewImpl();
    ImageController controller = new EnhanceController(model, view, in, out);
    controller.executeController();
    assertTrue(assertImageFiles("res/owl-sharpe2.png",
            "test/images/owl-sharpe2.png"));
  }

  /**
   * JUnit test to color transform an image using greyscale.
   */
  @Test
  public void colorTransformGreyscale() throws IOException {
    String input = "load res/owl.ppm owl\ncolor-transformation greyscale owl owl-ct-greyscale\r\n"
            + "save test/images/owl-ct-greyscale.ppm owl-ct-greyscale\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    EnhancedModelInterface model = new EnhancedModel();
    ImageView view = new ImageViewImpl();
    ImageController controller = new EnhanceController(model, view, in, out);
    controller.executeController();
    assertTrue(assertImageFiles("res/owl-ct-greyscale.ppm",
            "test/images/owl-ct-greyscale.ppm"));
  }

  /**
   * JUnit test to colortransform an image using greyscale.
   */
  @Test
  public void colorTransformSepia() throws IOException {
    String input = "load res/owl.ppm owl\ncolor-transformation sepia owl owl-ct-sepia\r\n"
            + "save test/images/owl-ct-sepia.ppm owl-ct-sepia\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    EnhancedModelInterface model = new EnhancedModel();
    ImageView view = new ImageViewImpl();
    ImageController controller = new EnhanceController(model, view, in, out);
    controller.executeController();
    assertTrue(assertImageFiles("res/owl-ct-sepia.ppm",
            "test/images/owl-ct-sepia.ppm"));
  }

  /**
   * JUnit test to dither an image.
   */
  @Test
  public void ditherImage() throws IOException {
    String input = "load res/owl.ppm owl\ndither owl owl-dither\r\n"
            + "save test/images/owl-dither.ppm owl-dither\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    EnhancedModelInterface model = new EnhancedModel();
    ImageView view = new ImageViewImpl();
    ImageController controller = new EnhanceController(model, view, in, out);
    controller.executeController();
    assertTrue(assertImageFiles("res/owl-dither.ppm",
            "test/images/owl-dither.ppm"));
  }

  /**
   * JUnit to load an image from a ppm file and
   * save it as a png file.
   */
  @Test
  public void loadPPMSavePng() throws IOException {
    String input = "load res/owl.ppm owl\r\n"
            + "save test/images/owl-png.png owl\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    EnhancedModelInterface model = new EnhancedModel();
    ImageView view = new ImageViewImpl();
    ImageController controller = new EnhanceController(model, view, in, out);
    controller.executeController();
    assertTrue(assertImageFiles("res/owl-png.png",
            "test/images/owl-png.png"));
  }

  /**
   * JUnit to load an image from a png file and
   * save it as a jpg file.
   */
  @Test
  public void loadPNGSaveJPEG() throws IOException {
    String input = "load res/owl-png.png owl\r\n"
            + "save test/images/owl-jpg.jpg owl\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    EnhancedModelInterface model = new EnhancedModel();
    ImageView view = new ImageViewImpl();
    ImageController controller = new EnhanceController(model, view, in, out);
    controller.executeController();
    assertTrue(assertImageFiles("res/owl-jpg.jpg",
            "test/images/owl-jpg.jpg"));
  }


  /**
   * JUnit to load an image from a jpg file and
   * save it as a bmp file.
   */
  @Test
  public void loadJPGSaveBMP() throws IOException {
    String input = "load res/owl-jpg.jpg owl\r\n"
            + "save test/images/owl-bmp.bmp owl\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    EnhancedModelInterface model = new EnhancedModel();
    ImageView view = new ImageViewImpl();
    ImageController controller = new EnhanceController(model, view, in, out);
    controller.executeController();
    assertTrue(assertImageFiles("res/owl-bmp.bmp",
            "test/images/owl-bmp.bmp"));
  }

  /**
   * JUnit to load an image from a bmp file and
   * save it as a jpg file.
   */
  @Test
  public void loadBMPSavePPM() throws IOException {
    String input = "load res/owl-bmp.bmp owl\r\n"
            + "save test/images/owl.ppm owl\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    EnhancedModelInterface model = new EnhancedModel();
    ImageView view = new ImageViewImpl();
    ImageController controller = new EnhanceController(model, view, in, out);
    controller.executeController();
    assertTrue(assertImageFiles("res/owl-bmp.bmp",
            "test/images/owl.ppm"));
  }

  /**
   * JUnit test to check if png file on location is not exists.
   */
  @Test
  public void loadPNGIfNotExists() {
    String input = "load res/owl.png owl\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    ModelInterface model = new Model();
    ImageView view = new ImageViewImpl();
    ImageController controller = new Controller(model, view, in, out);
    controller.executeController();
    assertEquals("Invalid command passed :load res/owl.png owl"
            + System.lineSeparator(), out.toString());
  }

  /**
   * JUnit test to check if png image loaded, brighten the loaded
   * image and then save it successfully.
   *
   * @throws IOException throws input output exception
   */
  @Test
  public void loadPNGBrightenSave() throws IOException {
    String input = "load res/owl-png.png owl\r\nbrighten 10 owl "
            + "owl-brighter\r\nsave test/images/owl-brighter.png owl-brighter\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();


    ModelInterface model = new Model();
    ImageView view = new ImageViewImpl();
    ImageController controller = new Controller(model, view, in, out);
    controller.executeController();
    assertTrue(assertImageFiles("res/owl-brighter.png", "test/images/owl-brighter.png"));
  }

  /**
   * JUnit test to dither an image.
   */
  @Test
  public void ditherImageBMP() throws IOException {
    String input = "load res/owl-bmp.bmp owl\ndither owl owl-dither\r\n"
            + "save test/images/owl-dither-bmp.bmp owl-dither\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    EnhancedModelInterface model = new EnhancedModel();
    ImageView view = new ImageViewImpl();
    ImageController controller = new EnhanceController(model, view, in, out);
    controller.executeController();
    assertTrue(assertImageFiles("res/owl-dither-bmp.bmp",
            "test/images/owl-dither-bmp.bmp"));
  }
}