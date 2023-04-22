package view;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Given class will contain the methods implemented
 * for user interaction.
 * It displays output when exception occurs.
 * <p>
 * It will further pass command to controller for handling it.
 * </p>
 */
public class ImageViewImpl implements ImageView {

  /**
   * Given method will display output if any exception occurs.
   *
   * @param out    a generalized output stream
   * @param output an output message needs to be display
   */
  @Override
  public void displayOutput(OutputStream out, String output) {
    PrintStream outStream = new PrintStream(out);
    outStream.println(output.trim());
  }
}
