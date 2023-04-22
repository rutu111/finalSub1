package model.operations;

/**
 * Given enum represents color transformation
 * methods like grayscale and sepia.
 * It also contains kernel value for each transformation.
 */
public enum TRANSFORMATIONS {
  GREYSCALE(new double[][]{{0.2126, 0.7152, 0.0722},
      {0.2126, 0.7152, 0.0722},
      {0.2126, 0.7152, 0.0722}}),
  SEPIA(new double[][]{{0.393, 0.769, 0.189},
      {0.349, 0.686, 0.168},
      {0.272, 0.534, 0.131}});

  private final double[][] kernel;

  /**
   * Given constructor initialize the kernel.
   *
   * @param kernel the default kernel array
   */
  TRANSFORMATIONS(double[][] kernel) {
    this.kernel = kernel;
  }

  /**
   * Fetch kernel value for given transformation.
   *
   * @return the kernel value of the transformation
   */
  public double[][] getKernel() {
    return this.kernel;
  }
}
