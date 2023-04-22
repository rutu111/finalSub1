package controller.commands;

import model.ModelInterface;

/**
 * Given command is used to combine provided
 * grey scale images of channel red, green and blue.
 *
 * @param <T> the type of the value
 */
public class Combine<T extends ModelInterface> implements CommandInterface<T> {

  private final String destFileName;
  private final String redFileName;
  private final String greenFileName;
  private final String blueFileName;

  /**
   * Given constructor set the value to the parameter
   * dest filename, redFileName, greenFileName and
   * blueFileName.
   *
   * @param destFileName  the destination name of the file
   * @param redFileName   the red greyscale file
   * @param greenFileName the green greyscale file
   * @param blueFileName  the blue greyscale file
   */
  public Combine(String destFileName,
                 String redFileName, String greenFileName, String blueFileName) {
    this.destFileName = destFileName;
    this.redFileName = redFileName;
    this.greenFileName = greenFileName;
    this.blueFileName = blueFileName;
  }

  /**
   * Given method execute the command.
   *
   * @param model the model of type T
   */
  @Override
  public void execute(T model) {
    model.combineRGB(destFileName, redFileName, greenFileName, blueFileName);
  }

  /**
   * Validate the command if argument of the command is as required.
   *
   * @param command the command passed which needs to be validated
   * @return true if valid command passed else false
   */
  @Override
  public boolean validateCommand(String[] command) {
    return command.length == 5;
  }
}
