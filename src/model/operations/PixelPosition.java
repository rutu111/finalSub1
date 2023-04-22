package model.operations;

public class PixelPosition {

  private PixelInterface pixel;
  private int row;
  private int col;
  public PixelPosition(PixelInterface pixel, int row, int col) {
    this.pixel = pixel;
    this.row = row;
    this.col = col;
  }

  public PixelInterface getPixel() {
    return this.pixel;
  }

  public void setPixel(PixelInterface newPixel) {
    this.pixel = newPixel;
  }

  public int getRow() {
    return this.row;
  }

  public int getColumn() {
    return this.col;
  }



}
