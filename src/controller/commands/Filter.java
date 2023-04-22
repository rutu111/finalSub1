package controller.commands;

import java.io.IOException;

import model.EnhancedModelInterface;

/**
 * Given command applies various filter on the image like
 * blur and sharpening.
 *
 * @param <T> the type of value
 */
public class Filter<T extends EnhancedModelInterface> implements CommandInterface<T> {
  private final String filterType;
  private final String fileName;
  private final String destFileName;

  /**
   * Given constructor will initialize the
   * filter type, file name and destination name.
   *
   * @param filterType   type of the filer
   * @param fileName     the file name of the image
   * @param destFileName the destination name of the image
   */
  public Filter(String filterType, String fileName, String destFileName) {
    this.filterType = filterType;
    this.fileName = fileName;
    this.destFileName = destFileName;
  }

  /**
   * Given method execute the command.
   *
   * @param model the model of type T
   */
  @Override
  public void execute(T model) throws IOException, ClassNotFoundException {
    model.filter(filterType, fileName, destFileName);
  }

  /**
   * Validate the command if argument of the command is as required.
   *
   * @param command the command passed which needs to be validated
   * @return true if valid command passed else false
   */
  @Override
  public boolean validateCommand(String[] command) {
    return command.length == 4;
  }

}
