package view;

/**
 * Enum class which contains object for all the
 * operations to be performed on an image.
 */
public enum OPERATIONS {

  BRIGHTEN("brighten"),
  VERTICAL_FLIP("vertical-flip"),
  HORIZONTAL_FLIP("horizontal-flip"),
  RED_COMPONENT("greyscale red-component"),
  GREEN_COMPONENT("greyscale green-component"),
  BLUE_COMPONENT("greyscale blue-component"),
  VALUE_COMPONENT("greyscale value-component"),
  LUMA_COMPONENT("greyscale luma-component"),
  INTENSITY_COMPONENT("greyscale intensity-component"),
  BLUR("filter blur"),
  SHARPE("filter sharpe"),
  GREYSCALE("color-transformation greyscale"),
  SEPIA("color-transformation sepia"),
  DITHER("dither"),
  Mosaicking("mosaicking");

  private final String operationCmd;

  /**
   * Contructor for the enum class.
   *
   * @param operationCmd value that enum represent.
   */
  OPERATIONS(String operationCmd) {
    this.operationCmd = operationCmd;
  }

  /**
   * Get the text command for the respective command.
   *
   * @return the text for the respective command.
   */
  public String getCmd() {
    return this.operationCmd;
  }
}
