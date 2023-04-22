package controller.commands;

import java.io.IOException;

import model.EnhancedModelInterface;

/**
 * This command apply color transformation on the image.
 *
 * @param <T> the type of the value
 */
public class ColorTransformations<T extends EnhancedModelInterface> implements CommandInterface<T> {
  private final String transformationType;
  private final String fileName;
  private final String destFileName;

  /**
   * Given constructor will initialize the transformation type,
   * filename and destination filename.
   *
   * @param transformationType the transformation type which needs to apply
   * @param fileName           file name of the image
   * @param destFileName       the destination filename
   */
  public ColorTransformations(String transformationType, String fileName, String destFileName) {
    this.transformationType = transformationType;
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
    model.colorTransformation(transformationType, fileName, destFileName);
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
