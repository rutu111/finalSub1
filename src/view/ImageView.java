package view;

import java.io.OutputStream;

/**
 * Given Interface will contain summary of the
 * methods used to interact with users.
 */

public interface ImageView {

  /**
   * Given method will display output if any exception occurs.
   *
   * @param out    a generalized output stream
   * @param output an output message needs to be display
   */
  void displayOutput(OutputStream out, String output);
}
