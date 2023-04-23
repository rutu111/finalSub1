package controller;

import model.DoubleEnchancedInterface;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.OutputStream;

import features.Features;
import model.EnhancedModelInterface;
import view.ImageView;
import view.JFrameView;

/**
 * A GUI based controlled to support delegation of task to the graphical
 * user interface.
 */
public class GUIBasedController extends DoubleEnhanceController implements Features {

  private final static String currImgName = "CurrentImage";
  private final static String redCurrImgName = "RedCurrentImage";
  private final static String greenCurrImgName = "GreenCurrentImage";
  private final static String blueCurrImgName = "BlueCurrentImage";
  private final DoubleEnchancedInterface model2;
  private JFrameView jFrameview;

  /**
   * Given constructor will initialize model, view and
   * input output stream.
   *
   * @param model the model provided
   * @param view  the view provided
   * @param in    the input stream
   * @param out   the output stream
   */
  public GUIBasedController(DoubleEnchancedInterface model,
                            ImageView view, InputStream in, OutputStream out) {
    super(model, view, in, out);
    this.model2 = model;
  }

  /**
   * Method of the controller to initialize the GUI,
   * and pass the controlled to the view.
   *
   * @param view object of the GUI view.
   */
  @Override
  public void setView(JFrameView view) {
    this.jFrameview = view;
    jFrameview.addFeatures(this);
  }

  /**
   * Method of the controller which serves as an event listener,
   * for the GUI, whenever the load button is clicked.
   *
   * @param imagePath path from where the image is loaded.
   */
  @Override
  public void loadImage(String imagePath) {
    if (!imagePath.equals("")) {
      processCommand("load " + imagePath + " " + currImgName);
      refreshImage();
    }
  }

  /**
   * Method of the controller which serves as an event listener,
   * for the GUI, whenever the save button is clicked.
   *
   * @param imgPath   path to where the image is saved.
   * @param imageName name of the image to be saved.
   */
  @Override
  public void saveImage(String imgPath, String imageName) {
    if (!imgPath.equals("")) {
      if (imageName.equals("")) {
        imageName = currImgName;
      }
      processCommand("save " + imgPath + " " + imageName);
    }
  }

  /**
   * Method of the controller which serves as an event listener,
   * for the GUI, whenever a button is pressed to manipulate an
   * image.
   *
   * @param cmd commamnd to manipulate an image.
   */
  @Override
  public void manipulateImage(String cmd) {
    processCommand(cmd + " "
            + currImgName + " " + currImgName);
    refreshImage();
  }

  /**
   * Method of the controller which serves as an event listener,
   * for the GUI, whenever the split Image button is pressed.
   *
   * @param redPath   path where to save the red component.
   * @param greenPath path where to save the green component.
   * @param bluePath  path where to save the blue component.
   */
  @Override
  public void splitImage(String redPath, String greenPath, String bluePath) {
    processCommand("rgb-split "
            + currImgName + " " + redCurrImgName
            + " " + greenCurrImgName + " " + blueCurrImgName);
    saveImage(redPath, redCurrImgName);
    saveImage(greenPath, greenCurrImgName);
    saveImage(bluePath, blueCurrImgName);
  }

  /**
   * Method of the controller which serves as an event listener,
   * for the GUI, whenever the combine Image button is pressed.
   *
   * @param redPath   path where to load the red component.
   * @param greenPath path where to load the green component.
   * @param bluePath  path where to load the blue component.
   */
  @Override
  public void combineImage(String redPath, String greenPath, String bluePath) {
    processCommand("load " + redPath + " " + redCurrImgName);
    processCommand("load " + greenPath + " " + greenCurrImgName);
    processCommand("load " + bluePath + " " + blueCurrImgName);
    processCommand("rgb-combine "
            + currImgName + " " + redCurrImgName
            + " " + greenCurrImgName + " "
            + blueCurrImgName);
    refreshImage();
  }

  /**
   * Helper method to refresh image.
   */
  private void refreshImage() {
    BufferedImage image = model2.getCurrentImage(currImgName);
    jFrameview.refreshImage(image);
    plotHistogram();
  }

  /**
   * Method of the controller which serves as an event listener,
   * for the GUI, when the histogram has to plotted.
   */
  @Override
  public void plotHistogram() {
    //call model to return the processed histogram data
    DefaultCategoryDataset dataset = model2.histogram(currImgName);
    // pass this data to the view to display
    jFrameview.refreshHistogram(dataset);
  }
}
