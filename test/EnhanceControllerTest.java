import org.jfree.data.category.DefaultCategoryDataset;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import controller.EnhanceController;
import controller.ImageController;
import model.EnhancedModelInterface;
import model.ModelInterface;
import view.ImageView;
import view.ImageViewImpl;

import static org.junit.Assert.assertEquals;

/**
 * Mock model testing for the enhanced model and controller.
 */
public class EnhanceControllerTest extends ControllerTest {

  /**
   * JUnit mock test to check if filter blur method is working correctly.
   */
  @Test
  public void filterBlur() {
    String input = "filter blur owl owl-blur";
    InputStream in = new ByteArrayInputStream(input.getBytes());


    OutputStream out = new ByteArrayOutputStream();

    StringBuilder sb = new StringBuilder();
    ModelInterface model = new MockModel(sb);
    ImageView view = new ImageViewImpl();
    ImageController controller = new EnhanceController(model, view, in, out);
    controller.executeController();
    assertEquals("Recieved blur,owl and owl-blur", sb.toString());
  }

  /**
   * JUnit mock test to check if filter sharpen method is working correctly.
   */
  @Test
  public void filterSharpen() {
    String input = "filter sharpen owl owl-sharpen";
    InputStream in = new ByteArrayInputStream(input.getBytes());


    OutputStream out = new ByteArrayOutputStream();

    StringBuilder sb = new StringBuilder();
    ModelInterface model = new MockModel(sb);
    ImageView view = new ImageViewImpl();
    ImageController controller = new EnhanceController(model, view, in, out);
    controller.executeController();
    assertEquals("Recieved sharpen,owl and owl-sharpen", sb.toString());
  }

  /**
   * JUnit mock test to check if color transformation
   * sepia method is working correctly.
   */
  @Test
  public void colorTransformSepia() {
    String input = "color-transformation sepia owl owl-sepia";
    InputStream in = new ByteArrayInputStream(input.getBytes());


    OutputStream out = new ByteArrayOutputStream();

    StringBuilder sb = new StringBuilder();
    ModelInterface model = new MockModel(sb);
    ImageView view = new ImageViewImpl();
    ImageController controller = new EnhanceController(model, view, in, out);
    controller.executeController();
    assertEquals("Recieved sepia,owl and owl-sepia", sb.toString());
  }

  /**
   * JUnit mock test to check if color transformation
   * greyscale method is working correctly.
   */
  @Test
  public void colorTransformGreyscale() {
    String input = "color-transformation greyscale owl owl-greyscale";
    InputStream in = new ByteArrayInputStream(input.getBytes());


    OutputStream out = new ByteArrayOutputStream();

    StringBuilder sb = new StringBuilder();
    ModelInterface model = new MockModel(sb);
    ImageView view = new ImageViewImpl();
    ImageController controller = new EnhanceController(model, view, in, out);
    controller.executeController();
    assertEquals("Recieved greyscale,owl and owl-greyscale", sb.toString());
  }

  /**
   * JUnit mock test to check if color transformation
   * sepia method is working correctly.
   */
  @Test
  public void ditherImage() {
    String input = "dither owl owl-dither";
    InputStream in = new ByteArrayInputStream(input.getBytes());


    OutputStream out = new ByteArrayOutputStream();

    StringBuilder sb = new StringBuilder();
    ModelInterface model = new MockModel(sb);
    ImageView view = new ImageViewImpl();
    ImageController controller = new EnhanceController(model, view, in, out);
    controller.executeController();
    assertEquals("Recieved owl and owl-dither", sb.toString());
  }

  /**
   * Implementing a MockModel class which implements
   * the EnhancedModelInterface.
   */
  public static class MockModel implements EnhancedModelInterface {

    private final StringBuilder sb;

    /**
     * A constructor to initialize a mock model.
     *
     * @param sb a string builder to manage exception logs
     */
    public MockModel(StringBuilder sb) {
      this.sb = sb;
    }

    @Override
    public void filter(String filterType,
                       String fileName, String destFileName) {
      sb.append("Recieved " + filterType + ","
              + fileName + " and " + destFileName);
    }

    @Override
    public void colorTransformation(String transformationType,
                                    String fileName, String destFileName) {
      sb.append("Recieved " + transformationType + ","
              + fileName + " and " + destFileName);
    }

    @Override
    public void dither(String fileName, String destFileName) {
      sb.append("Recieved " + fileName + " and " + destFileName);
    }


    @Override
    public void load(InputStream in,
                     String destFileName) throws IOException, ClassNotFoundException {
      sb.append("Recieved " + destFileName);
    }

    @Override
    public InputStream save(String fileName) throws IOException {
      sb.append("Recieved " + fileName);
      return null;
    }

    @Override
    public void visualizeComponent(String component,
                                   String fileName, String destFileName) {
      sb.append("Recieved " + component + " , " + fileName + " and "
              + destFileName);
    }

    @Override
    public void flipHorizontally(String fileName,
                                 String destFileName) {
      sb.append("Recieved " + fileName + " and " + destFileName);
    }

    @Override
    public void flipVertically(String fileName,
                               String destFileName) {
      sb.append("Recieved " + fileName + " and " + destFileName);
    }

    @Override
    public void brightness(int intensity, String fileName,
                           String destFileName) {
      sb.append("Recieved " + intensity + "," + fileName + " and " + destFileName);
    }

    @Override
    public void splitRGB(String fileName, String redFileName,
                         String greenFileName, String blueFileName) {
      sb.append("Recieved " + fileName + " , " + redFileName + " , "
              + redFileName + " and " + blueFileName);
    }

    @Override
    public void combineRGB(String destFileName, String redFileName,
                           String greenFileName, String blueFileName) {
      sb.append("Recieved " + destFileName + " , " + redFileName + " , "
              + redFileName + " and " + blueFileName);
    }

    @Override
    public DefaultCategoryDataset histogram(String fileName) {

      return null;
    }

    @Override
    public BufferedImage getCurrentImage(String imageName) {
      return null;
    }

  }
}