package controller.commands;

import model.ModelInterface;

/**
 * This command is manage the brightness of the image.
 *
 * @param <T> the type of the value
 */
public class Brighten<T extends ModelInterface> implements CommandInterface<T> {

  private final int intensity;
  private final String fileName;
  private final String destFileName;

  /**
   * Given constructor will initialize the intensity,
   * filename and destination name.
   *
   * @param intensity    the intensity of the image
   * @param fileName     filename of the image
   * @param destFileName destination filename of the image
   */
  public Brighten(int intensity, String fileName, String destFileName) {
    this.intensity = intensity;
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
    model.brightness(intensity, fileName, destFileName);
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
