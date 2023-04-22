package model;

import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.operations.FILTERS;
import model.operations.Image;
import model.operations.ImageInterface;
import model.operations.Pixel;
import model.operations.PixelInterface;
import model.operations.TRANSFORMATIONS;

/**
 * Given class will extend Model class
 * and add new image processing and manipulation methods
 * like filter, color-transformation and dithering.
 */
public class EnhancedModel extends Model implements EnhancedModelInterface {

  /**
   * Given method will apply filter on image according to the
   * filter type provided.
   *
   * @param filterType   the filter type of filter needs to be applied
   * @param fileName     the file name  of the image
   * @param destFileName destination name of image
   */
  @Override
  public void filter(String filterType, String fileName, String destFileName) {
    ImageInterface image = database.getImage(fileName);
    FILTERS filter = getFilter(filterType);

    if (filter != null) {
      database.storeImage(destFileName,
              getFilterMultiplication(filter.getKernel(), image));
    }
  }

  /**
   * Given method will transform the image according to the
   * color transformation type provided.
   *
   * @param transformationType the transformation needs to be applied
   * @param fileName           the file name of the image
   * @param destFileName       destination name of the image
   */
  @Override
  public void colorTransformation(String transformationType, String fileName, String destFileName) {
    ImageInterface image = database.getImage(fileName);
    TRANSFORMATIONS transformation = getTransformation(transformationType);

    if (transformation != null) {
      database.storeImage(destFileName,
              getColorTransformations(transformation.getKernel(), image));
    }
  }

  /**
   * Given method will convert image to the dither image.
   *
   * @param fileName     filename of the image
   * @param destFileName destination name of the image
   */
  @Override
  public void dither(String fileName, String destFileName) {
    colorTransformation("greyscale", fileName, destFileName);
    ImageInterface image = database.getImage(destFileName);

    List<ArrayList<PixelInterface>> imagePixels = image.getPixels();
    int width = image.getWidth();
    int height = image.getHeight();

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        int old_color = image.getPixel(row, col).getR();
        int new_color = old_color < 128 ? 0 : 255;
        int error = old_color - new_color;

        setValueToPixel(row, col, new_color, imagePixels);

        if (col < width - 1) {
          int newC = image.getPixel(row, col + 1).getR() + (int) (7.0 / 16.0 * error);
          setValueToPixel(row, col + 1, newC, imagePixels);
        }

        if (row < height - 1 && col > 0) {
          int newC = image.getPixel(row + 1, col - 1).getR() + (int) (3.0 / 16.0 * error);
          setValueToPixel(row + 1, col - 1, newC, imagePixels);
        }

        if (row < height - 1) {
          int newC = image.getPixel(row + 1, col).getR() + (int) (5.0 / 16.0 * error);
          setValueToPixel(row + 1, col, newC, imagePixels);
        }

        if (row < height - 1 && col < width - 1) {
          int newC = image.getPixel(row + 1, col + 1).getR() + (int) (1.0 / 16.0 * error);
          setValueToPixel(row + 1, col + 1, newC, imagePixels);
        }

      }
    }
    image = new Image(width, height, image.getMaxVal(), imagePixels);

    database.storeImage(destFileName, image);
  }


  /**
   * The helper method to perform filter multiplication
   * on the kernel value and each pixel of the pixel array.
   *
   * @param kernel the kernel array
   * @param image  the image needs to be filtered
   * @return filtered image after the multiplication
   */
  private Image getFilterMultiplication(double[][] kernel, ImageInterface image) {

    List<ArrayList<PixelInterface>> imagePixels = new ArrayList<ArrayList<PixelInterface>>();
    int kernelSize = kernel.length / 2;
    int width = image.getWidth();
    int height = image.getHeight();

    for (int row = 0; row < height; row++) {
      imagePixels.add(new ArrayList<PixelInterface>());
      for (int col = 0; col < width; col++) {
        int r = 0;
        int g = 0;
        int b = 0;

        for (int y = -kernelSize; y <= kernelSize; y++) {
          for (int x = -kernelSize; x <= kernelSize; x++) {
            int tempX = x + col;
            int tempY = y + row;

            if (tempX >= 0 && tempY >= 0 && tempX < width && tempY < height) {
              r += kernel[y + kernelSize][x + kernelSize] * image.getPixel(tempY, tempX).getR();
              g += kernel[y + kernelSize][x + kernelSize] * image.getPixel(tempY, tempX).getG();
              b += kernel[y + kernelSize][x + kernelSize] * image.getPixel(tempY, tempX).getB();
            }
          }
        }

        r = Math.max(0, Math.min(255, r));
        g = Math.max(0, Math.min(255, g));
        b = Math.max(0, Math.min(255, b));

        imagePixels.get(row).add(col, new Pixel(r, g, b));
      }
    }

    return new Image(width, height, image.getMaxVal(), imagePixels);
  }

  /**
   * The helper method to perform color transformation multiplication
   * on the kernel value and each pixel.
   *
   * @param kernel the kernel array
   * @param image  the image needs to the transformed
   * @return image after the color transformation
   */
  private Image getColorTransformations(double[][] kernel, ImageInterface image) {
    List<ArrayList<PixelInterface>> imagePixels = new ArrayList<ArrayList<PixelInterface>>();

    for (int row = 0; row < image.getHeight(); row++) {
      imagePixels.add(new ArrayList<PixelInterface>());
      for (int col = 0; col < image.getWidth(); col++) {
        int rVal = image.getPixel(row, col).getR();
        int gVal = image.getPixel(row, col).getG();
        int bVal = image.getPixel(row, col).getB();

        int r = (int) (kernel[0][0] * rVal + kernel[0][1] * gVal + kernel[0][2] * bVal);
        int g = (int) (kernel[1][0] * rVal + kernel[1][1] * gVal + kernel[1][2] * bVal);
        int b = (int) (kernel[2][0] * rVal + kernel[2][1] * gVal + kernel[2][2] * bVal);

        r = Math.max(0, Math.min(255, r));
        g = Math.max(0, Math.min(255, g));
        b = Math.max(0, Math.min(255, b));

        imagePixels.get(row).add(col, new Pixel(r, g, b));
      }
    }

    return new Image(image.getWidth(), image.getHeight(), image.getMaxVal(), imagePixels);
  }


  /**
   * Get filter type from the FILTER enum.
   *
   * @param filterType the filter type needs to be fetched
   * @return FILTER value according to the filter type
   */
  private FILTERS getFilter(String filterType) {
    switch (filterType) {
      case "blur":
        return FILTERS.BLUR;
      case "sharpe":
        return FILTERS.SHARPE;
      default:
        break;
    }
    return null;
  }

  /**
   * Get transformation type from the TRANSFORMATIONS enum.
   *
   * @param transformationType the transformation type needs to be fetched
   * @return TRANSFORMATIONS value according to the transformation type
   */
  private TRANSFORMATIONS getTransformation(String transformationType) {
    switch (transformationType) {
      case "greyscale":
        return TRANSFORMATIONS.GREYSCALE;
      case "sepia":
        return TRANSFORMATIONS.SEPIA;
      default:
        break;
    }
    return null;
  }

  /**
   * Given helper method will set new value to the pixel.
   *
   * @param row         the row of the pixel
   * @param col         the column of the pixel
   * @param newVal      the new value needs to be set
   * @param imagePixels the pixel array
   */
  private void setValueToPixel(int row, int col, int newVal,
                               List<ArrayList<PixelInterface>> imagePixels) {
    newVal = Math.max(0, Math.min(255, newVal));

    imagePixels.get(row).get(col).setR(newVal);
    imagePixels.get(row).get(col).setG(newVal);
    imagePixels.get(row).get(col).setB(newVal);
  }

  @Override
  public DefaultCategoryDataset histogram(String fileName) {
    BufferedImage image = getCurrentImage(fileName);
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    int[] pixels = image.getRGB(0,
            0, image.getWidth(), image.getHeight(),
            null, 0, image.getWidth());

    int[] redArray = new int[256];
    Arrays.fill(redArray, 0);
    int[] greenArray = new int[256];
    Arrays.fill(greenArray, 0);
    int[] blueArray = new int[256];
    Arrays.fill(blueArray, 0);
    int[] intensityArray = new int[256];
    Arrays.fill(intensityArray, 0);

    for (int i = 0; i < pixels.length; i++) {
      int r = (pixels[i] >> 16) & 0xFF;
      int g = (pixels[i] >> 8) & 0xFF;
      int b = pixels[i] & 0xFF;
      int avg = (r + g + b) / 3;
      redArray[r] = redArray[r] + 1;
      greenArray[g] = greenArray[g] + 1;
      blueArray[b] = blueArray[b] + 1;
      intensityArray[avg] = intensityArray[avg] + 1;
    }


    createDataset(dataset, redArray, "red");
    createDataset(dataset, greenArray, "green");
    createDataset(dataset, blueArray, "blue");
    createDataset(dataset, intensityArray, "intensity");


    return dataset;
  }

  private void createDataset(DefaultCategoryDataset dataset,
                             int[] dataList, String series) {
    String[] arr = new String[256];
    for (int i = 0; i < 256; i++) {
      arr[i] = String.valueOf(i);
    }
    for (int i = 0; i < 256; i++) {
      dataset.addValue(dataList[i], series, arr[i]);
    }
  }




  @Override
  public BufferedImage getCurrentImage(String imageName) {
    ImageInterface image = database.getImage(imageName);
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
    return bufferedImage;
  }

}
