package controller.commands;

import java.io.IOException;

import model.EnhancedModelInterface;

/**
 * Given command dither the image provided.
 *
 * @param <T> the type of value
 */
public class Dither<T extends EnhancedModelInterface> implements CommandInterface<T> {
  private final String fileName;
  private final String destFileName;

  /**
   * Given constructor will set the file name and
   * destination file name.
   *
   * @param fileName     the file name of the image
   * @param destFileName the destination name of the image
   */
  public Dither(String fileName, String destFileName) {
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
    model.dither(fileName, destFileName);
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
