package controller;

import java.io.InputStream;
import java.io.OutputStream;

import controller.commands.ColorTransformations;
import controller.commands.Dither;
import controller.commands.Filter;
import model.ModelInterface;
import view.ImageView;

/**
 * Given controller class is the new controller.
 * It is created to add new command without changing already
 * existing controller.
 */
public class EnhanceController extends Controller {

  /**
   * Given constructor will initialize model, view and
   * input output stream.
   *
   * @param model the model provided
   * @param view  the view provided
   * @param in    the input stream
   * @param out   the output stream
   */
  public EnhanceController(ModelInterface model, ImageView view, InputStream in, OutputStream out) {
    super(model, view, in, out);
    knownCommands.put("filter", c -> new Filter<>(c[1], c[2], c[3]));
    knownCommands.put("color-transformation", c -> new ColorTransformations<>(c[1], c[2], c[3]));
    knownCommands.put("dither", c -> new Dither<>(c[1], c[2]));
  }


}
