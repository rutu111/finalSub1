package controller.commands;

import model.ModelInterface;

/**
 * Given command split provided image into
 * red, green and blue greyscale image.
 *
 * @param <T> type of value
 */
public class Split<T extends ModelInterface> implements CommandInterface<T> {

  private final String fileName;
  private final String redFileName;
  private final String greenFileName;
  private final String blueFileName;

  /**
   * constructor will initialize the value of filename,
   * red, green and blue file name where image needs to be stored.
   *
   * @param fileName      the filename provided
   * @param redFileName   the filename of red greyscale image
   * @param greenFileName the filename of green greyscale image
   * @param blueFileName  the filename of blue greyscale image
   */
  public Split(String fileName, String redFileName, String greenFileName, String blueFileName) {
    this.fileName = fileName;
    this.redFileName = redFileName;
    this.blueFileName = blueFileName;
    this.greenFileName = greenFileName;
  }

  /**
   * Given method execute the command.
   *
   * @param model the model of type T
   */
  @Override
  public void execute(T model) {
    model.splitRGB(fileName, redFileName, greenFileName, blueFileName);
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
