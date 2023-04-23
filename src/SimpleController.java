import java.io.ByteArrayInputStream;
import java.io.InputStream;

import controller.EnhanceController;
import controller.GUIBasedController;
import controller.ImageController;
import model.EnhancedModel;
import model.EnhancedModelInterface;
import view.ImageView;
import view.ImageViewImpl;
import view.JFrameViewImpl;

/**
 * Given controller class is a main class.
 * In this class we create object of MVC (model, view, controller)
 * and pass control to the Image controller.
 */
public class SimpleController {

  /**
   * Given method will create object of model and view
   * and pass it to controller.
   * Then relinquishes control to the controller (by calling its go method).
   * <p>
   * It also contains implementation to accept command line arguments
   * from the terminal
   * </p>
   *
   * @param args an array of string
   */
  public static void main(String[] args) {

    String variableName = null;
    String value = null;
    EnhancedModelInterface model = new EnhancedModel();
    ImageView view = new ImageViewImpl();

    if (args.length != 0) {
      for (int i = 0; i < args.length; i++) {
        if (args[i].startsWith("-")) {
          // This is a command line argument
          variableName = args[i].substring(1); // Remove the leading '-'
          if (variableName.equals("file")) {
            value = args[++i];
            String input = "run " + value + "\r\nexit";
            InputStream in = new ByteArrayInputStream(input.getBytes());
            ImageController controller = new EnhanceController(model, view, in, System.out);
            controller.executeController();
          } else {
            ImageController controller = new EnhanceController(model, view, System.in, System.out);
            controller.executeController();
          }
        }
      }
    } else {
      JFrameViewImpl j = new JFrameViewImpl();
      GUIBasedController gui = new GUIBasedController(model, view, System.in, System.out);
      gui.setView(j);
    }
  }

}
