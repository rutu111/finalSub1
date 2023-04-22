package model.operations;

import java.io.Serializable;

/**
 * Given class represents entity class of pixel object.
 * <p>
 * An image is a sequence of pixels.
 * Each pixel has a position in the image and a color.
 * Color of a pixel is represented by breaking it into individual components in R,G,B.
 * </p>
 * It contains get and set method for R, G, B value
 * and get method for value, intensity and luma.
 */
public class Pixel implements PixelInterface, Serializable {

  private int r;
  private int g;
  private int b;

  /**
   * Given constructor will initialize the R, G, B values.
   *
   * @param r provided value of red component
   * @param g provided value of green component
   * @param b provided value of blue component
   */
  public Pixel(int r, int g, int b) {
    this.r = r;
    this.g = g;
    this.b = b;
  }

  /**
   * Get red component of the pixel.
   *
   * @return int value of red component
   */
  @Override
  public int getR() {
    return r;
  }


  /**
   * Set red component of the pixel.
   *
   * @param r provided red component value
   */
  @Override
  public void setR(int r) {
    this.r = r;
  }

  /**
   * Get green component of the pixel.
   *
   * @return int value of green component
   */
  @Override
  public int getG() {
    return g;
  }

  /**
   * Set green component of the pixel.
   *
   * @param g provided green component value
   */
  @Override
  public void setG(int g) {
    this.g = g;
  }

  /**
   * Get blue component of the pixel.
   *
   * @return int value of blue component
   */
  @Override
  public int getB() {
    return b;
  }

  /**
   * Set blue component of the pixel.
   *
   * @param b provided blue component value
   */
  @Override
  public void setB(int b) {
    this.b = b;
  }

  /**
   * Get value component of the pixel.
   *
   * @return int value of value component
   */
  @Override
  public int getValue() {
    return Math.max(b, (Math.max(r, g)));
  }

  /**
   * Get intensity component of the pixel.
   *
   * @return int value of intensity component
   */
  @Override
  public int getIntensity() {
    return (r + g + b) / 3;
  }

  /**
   * Get luma component of the pixel.
   *
   * @return int value of luma component
   */
  @Override
  public int getLuma() {
    return (int) (0.2126 * r + 0.7152 * g + 0.0722 * b);
  }

}
