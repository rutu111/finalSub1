package controller.commands;

import java.io.IOException;
import java.io.InputStream;

import model.ModelInterface;

/**
 * Given command load the image into the hashmap.
 *
 * @param <T> type of value
 */
public class Load<T extends ModelInterface> implements CommandInterface<T> {

  private final InputStream in;
  private final String destFileName;

  /**
   * Constructor which initialize the input stream and
   * destination filename.
   *
   * @param in           the input stream of the image
   * @param destFileName destination name of the file
   */
  public Load(InputStream in, String destFileName) {
    this.in = in;
    this.destFileName = destFileName;
  }

  /**
   * Given method execute the command.
   *
   * @param model the model of type T
   */
  @Override
  public void execute(T model) throws IOException, ClassNotFoundException {
    model.load(in, destFileName);
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
