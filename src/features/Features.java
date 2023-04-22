package features;

import view.JFrameView;

/**
 * Interface class for the GUIcontroller which will act as an
 * action listener.
 */
public interface Features {

  /**
   * Method of the controller to initialize the GUI,
   * and pass the controlled to the view.
   *
   * @param view object of the GUI view.
   */
  void setView(JFrameView view);

  /**
   * Method of the controller which serves as an event listener,
   * for the GUI, whenever the load button is clicked.
   *
   * @param imagePath path from where the image is loaded.
   */
  void loadImage(String imagePath);


  /**
   * Method of the controller which serves as an event listener,
   * for the GUI, whenever the save button is clicked.
   *
   * @param imgPath   path to where the image is saved.
   * @param imageName name of the image to be saved.
   */
  void saveImage(String imgPath, String imageName);

  /**
   * Method of the controller which serves as an event listener,
   * for the GUI, whenever a button is pressed to manipulate an
   * image.
   *
   * @param cmd commamnd to manipulate an image.
   */
  void manipulateImage(String cmd);

  /**
   * Method of the controller which serves as an event listener,
   * for the GUI, whenever the split Image button is pressed.
   *
   * @param redPath   path where to save the red component.
   * @param greenPath path where to save the green component.
   * @param bluePath  path where to save the blue component.
   */
  void splitImage(String redPath, String greenPath, String bluePath);

  /**
   * Method of the controller which serves as an event listener,
   * for the GUI, whenever the combine Image button is pressed.
   *
   * @param redPath   path where to load the red component.
   * @param greenPath path where to load the green component.
   * @param bluePath  path where to load the blue component.
   */
  void combineImage(String redPath, String greenPath, String bluePath);

  /**
   * Method of the controller which serves as an event listener,
   * for the GUI, when the histogram has to plotted.
   */
  void plotHistogram();
}
