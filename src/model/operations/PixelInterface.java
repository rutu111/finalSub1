package model.operations;

/**
 * Given interface contains the summary of pixel entity class.
 * It contains get and set method for R, G, B value
 * and get method for value, intensity and luma.
 */
public interface PixelInterface {

  /**
   * Get red component of the pixel.
   *
   * @return int value of red component
   */
  int getR();

  /**
   * Set red component of the pixel.
   *
   * @param r provided red component value
   */
  void setR(int r);

  /**
   * Get green component of the pixel.
   *
   * @return int value of green component
   */
  int getG();

  /**
   * Set green component of the pixel.
   *
   * @param g provided green component value
   */
  void setG(int g);

  /**
   * Get blue component of the pixel.
   *
   * @return int value of blue component
   */
  int getB();

  /**
   * Set blue component of the pixel.
   *
   * @param b provided blue component value
   */
  void setB(int b);

  /**
   * Get value component of the pixel.
   *
   * @return int value of value component
   */
  int getValue();

  /**
   * Get intensity component of the pixel.
   *
   * @return int value of intensity component
   */
  int getIntensity();

  /**
   * Get luma component of the pixel.
   *
   * @return int value of luma component
   */
  int getLuma();
}
