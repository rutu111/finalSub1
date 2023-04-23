package model;

/**
 * This interface is to incorporate the newest edition of this
 * program.
 */
public interface DoubleEnchancedInterface extends EnhancedModelInterface {

  /**
   * This operation is used to Mosaic an image.
   * @param seeds number of seeds.
   * @param fileName name of the input file.
   * @param destFileName name of the destination/output file.
   */
  void mosaicking(int seeds, String fileName, String destFileName);
}
