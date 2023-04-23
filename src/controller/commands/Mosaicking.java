package controller.commands;

import java.io.IOException;
import model.DoubleEnchancedModel;
import model.ModelInterface;

public class Mosaicking <T extends DoubleEnchancedModel> implements CommandInterface<T>{

  private final int seeds;
  private final String fileName;
  private final String destFileName;

  public Mosaicking(int seeds, String fileName, String destFileName) {
    this.seeds = seeds;
    this.fileName = fileName;
    this.destFileName = destFileName;
  }

  @Override
  public void execute(T model) throws IOException, ClassNotFoundException {
    model.mosaicking(seeds, fileName, destFileName);

  }

  @Override
  public boolean validateCommand(String[] command) {
    return command.length == 4;
  }
}
