package controller.commands;

import model.ModelInterface;

/**
 * Given command convert into greyscale image
 * according to the channel component provided.
 *
 * @param <T> the type of value
 */
public class VisualizeComponent<T extends ModelInterface> implements CommandInterface<T> {

  private final String component;
  private final String fileName;

  private final String destFileName;

  /**
   * Given constructor will initialize the component,
   * file name and destination name.
   *
   * @param component    the component type
   * @param fileName     the file name provided
   * @param destFileName provided destination name
   */
  public VisualizeComponent(String component, String fileName, String destFileName) {
    this.component = component;
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
    model.visualizeComponent(component, fileName, destFileName);
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
