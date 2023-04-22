package controller;

/**
 * Given interface provides summary of the operations
 * performed in the Image controller.
 * <p>
 * Image controller act as a mediator between ImageView
 * and ImageModel.
 * It took commands from the ImageView and processCommand
 * using logic defined in ImageModel.
 * It remove dependency between Model and View.
 * </p>
 */
public interface ImageController {

  /**
   * Given method ask controller to start taking
   * command line inputs in iterative manner.
   * It will stop taking inputs once it receives
   * 'exit' command.
   */
  void executeController();

}
