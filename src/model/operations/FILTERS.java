package model.operations;

/**
 * Given enum represents filter methods like blur and sharpe.
 * It also contains kernel value for each filter.
 */
public enum FILTERS {
  BLUR(new double[][]{{1d / 16, 1d / 8, 1d / 16},
      {1d / 8, 1d / 4, 1d / 8},
      {1d / 16, 1d / 8, 1d / 16}}),
  SHARPE(new double[][]{{-1d / 8, -1d / 8, -1d / 8, -1d / 8, -1d / 8},
      {-1d / 8, 1d / 4, 1d / 4, 1d / 4, -1d / 8},
      {-1d / 8, 1d / 4, 1, 1d / 4, -1d / 8},
      {-1d / 8, 1d / 4, 1d / 4, 1d / 4, -1d / 8},
      {-1d / 8, -1d / 8, -1d / 8, -1d / 8, -1d / 8}});

  private final double[][] kernel;

  /**
   * Given constructor initialize the kernel.
   *
   * @param kernel the default kernel array
   */
  FILTERS(double[][] kernel) {
    this.kernel = kernel;
  }

  /**
   * Fetch kernel value for given filter.
   *
   * @return the kernel value of the filter
   */
  public double[][] getKernel() {
    return this.kernel;
  }
}
