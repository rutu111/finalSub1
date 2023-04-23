package controller;

import controller.commands.Mosaicking;
import java.io.InputStream;
import java.io.OutputStream;
import model.ModelInterface;
import view.ImageView;

public class DoubleEnhanceController extends EnhanceController{

  /**
   * Given constructor will initialize model, view and input output stream.
   *
   * @param model the model provided
   * @param view  the view provided
   * @param in    the input stream
   * @param out   the output stream
   */
  public DoubleEnhanceController(ModelInterface model, ImageView view,
      InputStream in, OutputStream out) {
    super(model, view, in, out);
    knownCommands.put("mosaicking", c -> new Mosaicking<>((int) Double.parseDouble(c[1]), c[2], c[3]));
  }
}
