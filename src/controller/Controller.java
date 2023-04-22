package controller;

import controller.commands.Mosaicking;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Function;

import controller.commands.Brighten;
import controller.commands.Combine;
import controller.commands.CommandInterface;
import controller.commands.FlipHorizontal;
import controller.commands.FlipVertical;
import controller.commands.Load;
import controller.commands.Save;
import controller.commands.Split;
import controller.commands.VisualizeComponent;
import controller.utils.ImageUtils;
import controller.utils.ImageUtilsImpl;
import model.ModelInterface;
import view.ImageView;

/**
 * Given implementation is a controller class.
 *
 * <p>
 * Image controller act as a mediator between ImageView
 * and ImageModel.
 * It took commands from the ImageView and processCommand
 * using logic defined in ImageModel.
 * It remove dependency between Model and View.
 * </p>
 */
public class Controller implements ImageController {

  protected final ModelInterface model;
  protected final ImageView view;
  private final OutputStream out;
  private final ImageUtils utils;
  protected Map<String, Function<String[], CommandInterface>> knownCommands;
  private InputStream in;

  /**
   * A constructor will initialize Image model, Image view and input stream.
   *
   * @param model provided Image model
   * @param view  provided Image view
   * @param in    provided Input stream
   * @param out   provided Output stream
   */
  public Controller(ModelInterface model, ImageView view, InputStream in, OutputStream out) {
    this.model = model;
    this.view = view;
    this.in = in;
    this.out = out;
    this.utils = new ImageUtilsImpl();

    knownCommands = new HashMap<>();
    knownCommands.put("load", c -> {
      try {

        if (getExtension(c[1]).equalsIgnoreCase("ppm")) {
          return new Load<>(utils.readPPMImage(c[1]), c[2]);
        } else {
          return new Load<>(utils.readImage(c[1]), c[2]);
        }

      } catch (IOException e) {
        return null;
      }
    });
    knownCommands.put("brighten", c -> new Brighten<>((int) Double.parseDouble(c[1]), c[2], c[3]));
    knownCommands.put("vertical-flip", c -> new FlipVertical<>(c[1], c[2]));
    knownCommands.put("horizontal-flip", c -> new FlipHorizontal<>(c[1], c[2]));
    knownCommands.put("rgb-split", c -> new Split<>(c[1], c[2], c[3], c[4]));
    knownCommands.put("rgb-combine", c -> new Combine<>(c[1], c[2], c[3], c[4]));
    knownCommands.put("greyscale", c -> new VisualizeComponent<>(c[1], c[2], c[3]));
    knownCommands.put("mosaicking", c -> new Mosaicking<>((int) Double.parseDouble(c[1]), c[2], c[3]));
    knownCommands.put("save", c -> {
      return new Save<>(c[1], c[2], getExtension(c[1]));
    });
  }

  /**
   * Given method ask controller to start taking
   * command line inputs in iterative manner.
   * It will stop taking inputs once it receives
   * 'exit' command.
   */
  @Override
  public void executeController() {
    String input = "";
    Scanner sc = new Scanner(this.in);

    while (sc.hasNextLine()) {

      input = sc.nextLine();
      if (input.equals("exit")) {
        break;
      }
      processCommand(input);
    }
  }

  /**
   * Helper method to process the command given.
   *
   * @param commandLine the command passed
   */
  protected void processCommand(String commandLine) {
    try {
      String[] context = commandLine.trim().replaceAll(" +", " ").split(" ");
      String command = context[0];
      CommandInterface c;

      if (command.equals("run")) {
        List<String> inputCommands = takeInputFile(new FileInputStream(context[1]));
        for (String e : inputCommands) {
          processCommand(e);
        }
      } else {
        Function<String[], CommandInterface> cmd =
                knownCommands.getOrDefault(command, null);
        if (cmd == null) {
          view.displayOutput(this.out, "Invalid command name passed :"
                  + commandLine + System.lineSeparator());
          throw new Exception();
        } else {
          c = cmd.apply(context);

          if (!c.validateCommand(context)) {
            view.displayOutput(this.out, "Invalid arguments passed :"
                    + commandLine + System.lineSeparator());
            throw new Exception();
          }

          c.execute(model);
        }
      }
      view.displayOutput(this.out, "Command run successfully!");

    } catch (IllegalArgumentException ex) {
      view.displayOutput(this.out, "Invalid PPM file: plain RAW file should begin with P3"
              + System.lineSeparator());
    } catch (FileNotFoundException ex) {
      view.displayOutput(this.out, "File not found :"
              + commandLine + System.lineSeparator());
    } catch (NullPointerException ex) {
      view.displayOutput(this.out, "Invalid command passed :"
              + commandLine + System.lineSeparator());
    } catch (NoSuchElementException ex) {
      view.displayOutput(this.out, "File passed is empty :"
              + commandLine + System.lineSeparator());
    } catch (IndexOutOfBoundsException ex) {
      view.displayOutput(this.out, "Invalid arguments passed :"
              + commandLine + System.lineSeparator());
    } catch (IOException ex) {
      view.displayOutput(this.out, "File not found :"
              + commandLine + System.lineSeparator());
    } catch (RuntimeException ex) {
      if (ex.getMessage().contains("FileNotFoundException")
              || ex.getMessage().contains("IOException")) {
        view.displayOutput(this.out, "File not found :"
                + commandLine + System.lineSeparator());
      } else {
        view.displayOutput(this.out, commandLine
                + System.lineSeparator());
      }
    } catch (Exception ex) {
      //view.displayOutput(this.out, commandLine + System.lineSeparator());
    }
  }

  /**
   * Given method is a helper method which works on a script file.
   * Script file is a collection of commands. user can provide the file
   * using run command.
   *
   * @param in represents input stream of bytes
   * @return list of commands passed in the script file
   */
  private List<String> takeInputFile(InputStream in) {
    this.in = in;
    Scanner sc = new Scanner(this.in);
    List<String> inputCommands = new ArrayList<String>();
    while (sc.hasNextLine()) {
      String input = sc.nextLine();
      if (!input.isEmpty() && input.charAt(0) != '#') {

        inputCommands.add(input);
      }
    }
    return inputCommands;
  }

  /**
   * Given method get extension from the filename.
   *
   * @param fileName filename provided
   * @return get extension from the filename
   */
  protected String getExtension(String fileName) {

    int i = fileName.lastIndexOf('.');
    if (i > 0) {
      return fileName.substring(i + 1);
    }
    return "";
  }

}
