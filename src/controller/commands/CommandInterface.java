package controller.commands;

import java.io.IOException;

/**
 * Given interface contains summary of the methods
 * performed on the commands.
 *
 * @param <T> the type of the value
 */
public interface CommandInterface<T> {

  /**
   * Given method execute the command.
   *
   * @param model the model of type T
   * @throws IOException            input output exception
   * @throws ClassNotFoundException if class not found
   */
  void execute(T model) throws IOException, ClassNotFoundException;

  /**
   * Validate the command if argument of the command is as required.
   *
   * @param command the command passed which needs to be validated
   * @return true if valid command passed else false
   */
  boolean validateCommand(String[] command);
}
