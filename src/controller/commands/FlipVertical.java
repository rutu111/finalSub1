package controller.commands;

import model.ModelInterface;

/**
 * Given command flip the image vertically.
 *
 * @param <T> type of value
 */
public class FlipVertical<T extends ModelInterface> implements CommandInterface<T> {

  private final String fileName;

  private final String destFileName;

  /**
   * Constructor initialize the file name and destination file name.
   *
   * @param fileName     the filename provided
   * @param destFileName the name of the destination file
   */
  public FlipVertical(String fileName, String destFileName) {
    this.fileName = fileName;
    this.destFileName = destFileName;
  }

  /**
   * Given method execute the command.
   *
   * @param model the model of type T
   */
  @Override
  public void execute(T model) {
    model.flipVertically(fileName, destFileName);
  }

  /**
   * Validate the command if argument of the command is as required.
   *
   * @param command the command passed which needs to be validated
   * @return true if valid command passed else false
   */
  @Override
  public boolean validateCommand(String[] command) {
    return command.length == 3;
  }
}
