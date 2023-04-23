import controller.DoubleEnhanceController;
import controller.EnhanceController;
import controller.ImageController;
import model.DoubleEnchancedInterface;
import model.EnhancedModelInterface;
import model.ModelInterface;
import org.jfree.data.category.DefaultCategoryDataset;
import org.junit.Test;
import view.ImageView;
import view.ImageViewImpl;

import java.awt.image.BufferedImage;
import java.io.*;

import static org.junit.Assert.assertEquals;

public class DoubleEnhanceControllerTest extends EnhanceControllerTest{

  /**
   * JUnit mock test to check if mosaic method is working correctly.
   */
  @Test
  public void MosaicWorks() {
    String input = "mosaicking 1000 owl owl-mosaic";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();
    StringBuilder sb = new StringBuilder();
    ModelInterface model = new DoubleEnhanceControllerTest.MockModel(sb);
    ImageView view = new ImageViewImpl();
    ImageController controller = new DoubleEnhanceController(model, view, in, out);
    controller.executeController();
    assertEquals("Recieved 100,owl and owl-mosaic", sb.toString());
  }

  /**
   * JUnit mock test to check if mosaic method throws an exception.
   */
  @Test
  public void IfSeedlessthan50Works() {
    String input = "mosaicking 20 owl owl-mosaic";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();
    StringBuilder sb = new StringBuilder();
    ModelInterface model = new DoubleEnhanceControllerTest.MockModel(sb);
    ImageView view = new ImageViewImpl();
    ImageController controller = new DoubleEnhanceController(model, view, in, out);
    controller.executeController();
    assertEquals("Seeds can only 50 and above.", out.toString());
  }

  public static class MockModel implements DoubleEnchancedInterface {

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
    public void mosaicking(int seeds, String fileName, String destFileName) {
      sb.append("Recieved " + seeds + ","
          + fileName + " and " + destFileName);

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
