package controller.commands;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import controller.utils.ImageUtilsImpl;
import model.ModelInterface;
import model.operations.ImageInterface;

/**
 * Given command will save the image on the file path
 * provided in the given file type.
 *
 * @param <T> the type of value
 */
public class Save<T extends ModelInterface> implements CommandInterface<T> {

  private final String path;
  private final String fileName;
  private final String fileType;
  private final ImageUtilsImpl utils;

  /**
   * Given constructor will initialize the path,
   * filename and file type.
   *
   * @param path     the path provided
   * @param fileName the name of the image
   * @param fileType the type of the file
   */
  public Save(String path, String fileName, String fileType) {
    this.path = path;
    this.fileName = fileName;
    this.fileType = fileType;
    this.utils = new ImageUtilsImpl();
  }

  /**
   * Given method execute the command.
   *
   * @param model the model of type T
   */
  @Override
  public void execute(T model) throws IOException, ClassNotFoundException {

    InputStream imageStream = model.save(fileName);
    ObjectInputStream objectInputStream = new ObjectInputStream(imageStream);
    ImageInterface image = (ImageInterface) objectInputStream.readObject();
    if (fileType.equalsIgnoreCase("ppm")) {
      utils.savePPMImage(path, image);
    } else {
      utils.saveImage(path, image, fileType);
    }
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
