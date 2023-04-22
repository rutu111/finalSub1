package view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.ButtonGroup;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.filechooser.FileNameExtensionFilter;

import features.Features;

/**
 * A java class for the graphical user interface implementation
 * image manipulation image.
 */
public class JFrameViewImpl extends JFrame implements JFrameView {

  private final JPanel mainPanel;
  private JPanel flipPanel;
  private JPanel componentPanel;
  private JPanel brightenPanel;
  private JPanel mosacPanel;

  private JPanel panel;
  private JButton loadButton;
  private JButton saveButton;
  private JButton manipulateButton;
  private JButton splitButton;
  private JButton combineButton;
  private JButton showButton;
  private JButton splitImageButton;
  private JLabel imageLabel;
  private JLabel redLabel;
  private JLabel redCLabel;
  private JLabel greenLabel;
  private JLabel greenCLabel;
  private JLabel blueLabel;
  private JLabel blueCLabel;
  private JComboBox<String> dropdown;
  private JDialog dialog;
  private JDialog splitDialog;
  private JDialog combineDialog;
  private JRadioButton vFlipButton;
  private JRadioButton hFlipButton;
  private JRadioButton redCButton;
  private JRadioButton greenCButton;
  private JRadioButton blueCButton;
  private JRadioButton valueCButton;
  private JRadioButton lumaCButton;
  private JRadioButton intensityCButton;
  private OPERATIONS operations;
  private ButtonGroup flipOption;
  private ButtonGroup componentOption;
  private JTextField brightenVal;

  private JTextField mosacVal;

  private ChartPanel chartPanel;
  private JFreeChart chart;
  private DefaultCategoryDataset dataset;

  /**
   * Constructor for the GUI class of the project.
   */
  public JFrameViewImpl() {
    super();

    this.setTitle("IME : Image Manipulation and Enhancement");
    this.setSize(1450, 650);
    this.setLocation(40, 40);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //this.setExtendedState(JFrame.MAXIMIZED_BOTH);

    mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

    //scroll bars around this main panel
    JScrollPane mainScrollPanel = new JScrollPane(mainPanel);
    add(mainScrollPanel);

    //set load and save button
    upperButtonPanel();

    //add Image panel
    imagePanel();

    // manipulate and split button
    bottomButtonPanel();

    //manipulation dialogue
    manipulationDialogue();

    // split dialogue
    splitDialogue();

    // combine dialogue
    combineDialog();

    internalActionListener();
    makeVisible();
    this.requestFocus();

  }

  /**
   * Helper method to add load and
   * save button to the main panel.
   */
  private void upperButtonPanel() {
    JPanel buttonPanel = new JPanel();
    this.add(buttonPanel, BorderLayout.SOUTH);

    //load button
    loadButton = new JButton("Load Image");
    loadButton.setActionCommand("Load Image");
    loadButton.setToolTipText("Ctrl+U");
    buttonPanel.add(loadButton);

    //save button
    saveButton = new JButton("Save Image");
    saveButton.setActionCommand("Save Image");
    saveButton.setToolTipText("Ctrl+S");
    saveButton.setEnabled(false);
    buttonPanel.add(saveButton);

    buttonPanel.setBackground(Color.WHITE);
    mainPanel.add(buttonPanel);
  }

  /**
   * Helper method to add image preview
   * and histogram to the main panel.
   */
  private void imagePanel() {
    JPanel imagePanel = new JPanel();
    imagePanel.setBorder(BorderFactory.createTitledBorder("Preview"));

    imageLabel = new JLabel();
    JScrollPane imageScrollPane = new JScrollPane(imageLabel);
    imageScrollPane.setPreferredSize(new Dimension(700, 500));
    imagePanel.add(imageScrollPane);

    //Histogram
    JFreeChart chart = createChart(dataset);

    chartPanel = new ChartPanel(chart);
    chartPanel.setPreferredSize(new Dimension(700, 500));
    // Add chart panel to frame
    imagePanel.add(chartPanel);

    imagePanel.setBackground(Color.WHITE);
    mainPanel.add(imagePanel);
  }

  /**
   * Helper method to add
   * manipulate, split and
   * combine images to the main panel.
   */
  private void bottomButtonPanel() {
    JPanel bottomButtonPanel = new JPanel();

    //add manipulation button
    showButton = new JButton("Manipulate image");
    showButton.setEnabled(false);
    showButton.addActionListener(e -> showDialogBox());
    showButton.setToolTipText("Ctrl+M");
    bottomButtonPanel.add(showButton);

    // split Image button
    splitImageButton = new JButton("Split Image");
    splitImageButton.setEnabled(false);
    splitImageButton.addActionListener(e -> showSplitDialogBox());
    splitImageButton.setToolTipText("Ctrl+X");
    bottomButtonPanel.add(splitImageButton);

    // combine Image button
    JButton combineImageButton = new JButton("Combine Image");
    combineImageButton.addActionListener(e -> showCombineDialogBox());
    combineImageButton.setToolTipText("Ctrl+C");
    bottomButtonPanel.add(combineImageButton);

    mainPanel.add(bottomButtonPanel);
  }

  /**
   * Helper method to add manipulation
   * dialogue.
   */
  private void manipulationDialogue() {
    dialog = new JDialog(this, "Manipulation options", true);

    panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
    panel.setPreferredSize(new Dimension(400, 200));

    // operation dropdown
    JPanel operationPanel = new JPanel();
    operationPanel.add(new JLabel("Select operations :"));

    String[] options = {"", "Flip",
      "Component visualization",
      "Brighten", "Blur", "Sharpen",
      "Greyscale", "Sepia", "Dither", "Mosaicking"};
    dropdown = new JComboBox<>(options);
    operationPanel.add(dropdown);

    panel.add(operationPanel);

    // flip operations
    flipPanel();

    // component visualization
    componentPanel();

    // Brighten
    brightenPanel();

    mosackPanel();

    manipulateButton = new JButton("Execute");
    manipulateButton.setActionCommand("Execute");
    panel.add(manipulateButton);

    dialog.setContentPane(panel);
  }

  /**
   * Helper method for flip panel.
   */
  private void flipPanel() {
    flipPanel = new JPanel();
    flipOption = new ButtonGroup();

    vFlipButton = new JRadioButton("Flip vertically");
    flipOption.add(vFlipButton);
    flipPanel.add(vFlipButton);

    hFlipButton = new JRadioButton("Flip horizontally");
    flipOption.add(hFlipButton);
    flipPanel.add(hFlipButton);
    flipPanel.setVisible(false);

    panel.add(flipPanel);
  }

  /**
   * Helper method for visualization
   * component panel.
   */
  private void componentPanel() {
    componentPanel = new JPanel();
    componentOption = new ButtonGroup();

    redCButton = new JRadioButton("Red component");
    componentOption.add(redCButton);
    componentPanel.add(redCButton);

    greenCButton = new JRadioButton("Green component");
    componentOption.add(greenCButton);
    componentPanel.add(greenCButton);

    blueCButton = new JRadioButton("Blue component");
    componentOption.add(blueCButton);
    componentPanel.add(blueCButton);

    valueCButton = new JRadioButton("Value component");
    componentOption.add(valueCButton);
    componentPanel.add(valueCButton);

    lumaCButton = new JRadioButton("Luma component");
    componentOption.add(lumaCButton);
    componentPanel.add(lumaCButton);

    intensityCButton = new JRadioButton("Intensity component");
    componentOption.add(intensityCButton);
    componentPanel.add(intensityCButton);

    componentPanel.setVisible(false);
    panel.add(componentPanel);
  }

  /**
   * Helper method for brighten
   * panel.
   */
  private void brightenPanel() {
    brightenPanel = new JPanel();
    brightenPanel.add(new JLabel("Brighten by :"));
    brightenVal = new JTextField(10);
    brightenPanel.add(brightenVal);

    brightenPanel.setVisible(false);
    panel.add(brightenPanel);
  }

  private void mosackPanel() {
    mosacPanel = new JPanel();
    mosacPanel.add(new JLabel("Mosaick by :"));
    mosacVal = new JTextField(10);
    mosacPanel.add(mosacVal);

    mosacPanel.setVisible(false);
    panel.add(mosacPanel);
  }

  /**
   * Helper method contains split
   * dialogue details.
   */
  private void splitDialogue() {
    splitDialog = new JDialog(this, "Split Image", true);

    JPanel mainSplitPanel = new JPanel();
    mainSplitPanel.setLayout(new BoxLayout(mainSplitPanel, BoxLayout.PAGE_AXIS));
    mainSplitPanel.setPreferredSize(new Dimension(500, 300));

    // Split image dialogue
    JPanel splitPanel = new JPanel();
    splitPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

    //red component panel
    JPanel redPanel = new JPanel();
    redPanel.add(new JLabel("Red component image path :"));
    JButton redbtn = new JButton("Select path");
    redLabel = new JLabel();
    redbtn.addActionListener(evt -> {
      redLabel.setText(saveImagePath());
    });
    redPanel.add(redbtn);
    redPanel.add(redLabel);
    splitPanel.add(redPanel);

    //green component panel
    JPanel greenPanel = new JPanel();
    greenPanel.add(new JLabel("Green component image path :"));
    JButton greenbtn = new JButton("Select path");
    greenLabel = new JLabel();
    greenbtn.addActionListener(evt -> {
      greenLabel.setText(saveImagePath());
    });
    greenPanel.add(greenbtn);
    greenPanel.add(greenLabel);
    splitPanel.add(greenPanel);

    //blue component panel
    JPanel bluePanel = new JPanel();
    bluePanel.add(new JLabel("Blue component image path :"));
    JButton bluebtn = new JButton("Select path");
    blueLabel = new JLabel();
    bluebtn.addActionListener(evt -> {
      blueLabel.setText(saveImagePath());
    });
    bluePanel.add(bluebtn);
    bluePanel.add(blueLabel);
    splitPanel.add(bluePanel);

    mainSplitPanel.add(splitPanel);

    splitButton = new JButton("Split");
    splitButton.setActionCommand("Split");
    mainSplitPanel.add(splitButton);

    splitDialog.setContentPane(mainSplitPanel);
  }

  /**
   * Helper method contains combine
   * dialogue details.
   */
  private void combineDialog() {
    combineDialog = new JDialog(this, "Combine Image", true);
    combineDialog.setLocation(500, 100);

    JPanel mainCombinePanel = new JPanel();
    mainCombinePanel.setLayout(new BoxLayout(mainCombinePanel, BoxLayout.PAGE_AXIS));
    mainCombinePanel.setPreferredSize(new Dimension(500, 300));


    // Split image dialogue
    JPanel combinePanel = new JPanel();
    combinePanel.setLayout(new FlowLayout(FlowLayout.LEFT));

    //red component panel
    JPanel redPanel = new JPanel();
    redPanel.add(new JLabel("Red component image path :"));
    JButton redbtn = new JButton("Select path");
    redCLabel = new JLabel();
    redbtn.addActionListener(evt -> {
      redCLabel.setText(getLoadedImagePath());
    });
    redPanel.add(redbtn);
    redPanel.add(redCLabel);
    combinePanel.add(redPanel);

    //green component panel
    JPanel greenPanel = new JPanel();
    greenPanel.add(new JLabel("Green component image path :"));
    JButton greenbtn = new JButton("Select path");
    greenCLabel = new JLabel();
    greenbtn.addActionListener(evt -> {
      greenCLabel.setText(getLoadedImagePath());
    });
    greenPanel.add(greenbtn);
    greenPanel.add(greenCLabel);
    combinePanel.add(greenPanel);

    //blue component panel
    JPanel bluePanel = new JPanel();
    bluePanel.add(new JLabel("Blue component image path :"));
    JButton bluebtn = new JButton("Select path");
    blueCLabel = new JLabel();
    bluebtn.addActionListener(evt -> {
      blueCLabel.setText(saveImagePath());
    });
    bluePanel.add(bluebtn);
    bluePanel.add(blueCLabel);
    combinePanel.add(bluePanel);

    mainCombinePanel.add(combinePanel);

    combineButton = new JButton("Combine");
    combineButton.setActionCommand("Combine");
    mainCombinePanel.add(combineButton);

    combineDialog.setContentPane(mainCombinePanel);
  }

  /**
   * Helper method to create chart.
   * @param dataset provided dataset
   * @return jfree chart
   */
  private JFreeChart createChart(CategoryDataset dataset) {
    chart = ChartFactory.createLineChart(
            "Histogram", // chart title
            "Category", // x axis label
            "Value", // y axis label
            dataset // data
    );
    return chart;
  }

  /**
   * method to make frame visible.
   */
  private void makeVisible() {
    this.setVisible(true);
  }

  /**
   * Method to popup dialogue box
   * for manipulation.
   */
  private void showDialogBox() {
    dropdown.setSelectedIndex(0);
    dialog.pack();
    dialog.setVisible(true);
  }

  /**
   * Method to popup split
   * dialogue box.
   */
  private void showSplitDialogBox() {
    redLabel.setText("");
    greenLabel.setText("");
    blueLabel.setText("");
    splitDialog.pack();
    splitDialog.setVisible(true);
  }

  /**
   * Method to popup combine
   * dialogue box.
   */
  private void showCombineDialogBox() {
    redLabel.setText("");
    greenLabel.setText("");
    blueLabel.setText("");
    combineDialog.pack();
    combineDialog.setVisible(true);
  }

  /**
   * Method to reset dialogue.
   */
  private void resetDialogue() {
    operations = null;
    flipPanel.setVisible(false);
    componentPanel.setVisible(false);
    brightenPanel.setVisible(false);
    brightenVal.setText("");
    mosacPanel.setVisible(false);
    mosacVal.setText("");
    flipOption.clearSelection();
    componentOption.clearSelection();
  }

  /**
   * Helper method to show further options.
   */
  private void showFurtherOptions() {
    resetDialogue();
    switch (dropdown.getSelectedItem().toString()) {
      case "Flip":
        flipPanel.setVisible(true);
        break;
      case "Component visualization":
        componentPanel.setVisible(true);
        break;
      case "Brighten":
        operations = OPERATIONS.BRIGHTEN;
        brightenPanel.setVisible(true);
        break;
      case "Mosaicking":
        operations = OPERATIONS.Mosaicking;
        mosacPanel.setVisible(true);
        break;
      case "Blur":
        operations = OPERATIONS.BLUR;
        break;
      case "Sharpen":
        operations = OPERATIONS.SHARPE;
        break;
      case "Greyscale":
        operations = OPERATIONS.GREYSCALE;
        break;
      case "Sepia":
        operations = OPERATIONS.SEPIA;
        break;
      case "Dither":
        operations = OPERATIONS.DITHER;
        break;
      case "":
        break;
      default:
        break;
    }
  }

  /**
   * Helper method to save image.
   * @return return image path
   */
  private String saveImagePath() {
    final JFileChooser fchooser = new JFileChooser(".");
    fchooser.setDialogTitle("Select path");
    int retvalue = fchooser.showSaveDialog(JFrameViewImpl.this);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      return f.getAbsolutePath();
    }
    return "";
  }

  /**
   * Get image path.
   * @return image path
   */
  private String getLoadedImagePath() {
    String loadedImagePath = "";

    final JFileChooser fchooser = new JFileChooser(".");
    fchooser.setDialogTitle("Select an image");
    while (true) {
      FileNameExtensionFilter filter = new FileNameExtensionFilter(
              "Image type", "jpg", "ppm", "jpeg", "png", "bmp");
      fchooser.setFileFilter(filter);
      int retvalue = fchooser.showOpenDialog(JFrameViewImpl.this);
      if (retvalue == JFileChooser.APPROVE_OPTION) {
        File f = fchooser.getSelectedFile();
        loadedImagePath = f.getAbsolutePath();
        String extension = loadedImagePath.contains(".")
                ? loadedImagePath.substring(loadedImagePath.lastIndexOf(".") + 1) :
                "";
        if (extension.equals("jpg") || extension.equals("ppm") || extension.equals("jpeg")
                || extension.equals("png") || extension.equals("bmp")) {
          return loadedImagePath;
        } else {
          showErrorMsg("Invalid image selected!");
        }
      } else {
        return "";
      }
    }
  }

  /**
   * Helper method to add action listener.
   */
  private void internalActionListener() {
    dropdown.addActionListener(evt -> showFurtherOptions());
    vFlipButton.addActionListener(evt -> {
      operations = OPERATIONS.VERTICAL_FLIP;
    });
    hFlipButton.addActionListener(evt -> {
      operations = OPERATIONS.HORIZONTAL_FLIP;
    });
    redCButton.addActionListener(evt -> {
      operations = OPERATIONS.RED_COMPONENT;
    });
    greenCButton.addActionListener(evt -> {
      operations = OPERATIONS.GREEN_COMPONENT;
    });
    blueCButton.addActionListener(evt -> {
      operations = OPERATIONS.BLUE_COMPONENT;
    });
    valueCButton.addActionListener(evt -> {
      operations = OPERATIONS.VALUE_COMPONENT;
    });
    lumaCButton.addActionListener(evt -> {
      operations = OPERATIONS.LUMA_COMPONENT;
    });
    intensityCButton.addActionListener(evt -> {
      operations = OPERATIONS.INTENSITY_COMPONENT;
    });
  }

  /**
   * Adds action listeners methods for the respective buttons.
   *
   * @param features object of the controller.
   */
  @Override
  public void addFeatures(Features features) {
    loadButton.addActionListener(evt -> {
      features.loadImage(getLoadedImagePath());
    });
    saveButton.addActionListener(evt -> features.saveImage(saveImagePath(), ""));

    splitButton.addActionListener(evt -> {
      if (redLabel == null || greenLabel == null || blueLabel == null
              || redLabel.getText().equals("")
              || greenLabel.getText().equals("")
              || blueLabel.getText().equals("")) {
        showErrorMsg("Please enter all path to save RGB greyscale images!");
      } else {
        features.splitImage(redLabel.getText(), greenLabel.getText(), blueLabel.getText());
        showErrorMsg("Images saved successfully!");
        splitDialog.setVisible(false);
      }
    });

    combineButton.addActionListener(evt -> {
      if (redCLabel == null || greenCLabel == null || blueCLabel == null
              || redCLabel.getText().equals("")
              || greenCLabel.getText().equals("")
              || blueCLabel.getText().equals("")) {
        showErrorMsg("Please enter all path to combine RGB greyscale images!");
      } else {
        features.combineImage(redCLabel.getText(), greenCLabel.getText(), blueCLabel.getText());
        combineDialog.setVisible(false);
      }
    });

    manipulateButton.addActionListener(evt -> {
      if (operations != null) {
        String commandText = operations.getCmd();

        if (operations == OPERATIONS.BRIGHTEN) {
          try {
            int getVal = (int) Double.parseDouble(brightenVal.getText());
            commandText = commandText + " " + getVal;
          } catch (Exception ex) {
            showErrorMsg("Please enter valid integer value!");
            return;
          }
        }

        features.manipulateImage(commandText);
        dialog.setVisible(false);
        resetDialogue();
      } else {
        showErrorMsg("Please select a manipulation methods!");
      }
    });

    manipulateButton.addActionListener(evt -> {
      if (operations != null) {
        String commandText = operations.getCmd();

        if (operations == OPERATIONS.Mosaicking) {
          try {
            int getVal = (int) Double.parseDouble(mosacVal.getText());
            commandText = commandText + " " + getVal;
          } catch (Exception ex) {
            showErrorMsg("Please enter valid integer value above 1000!");
            return;
          }
        }

        features.manipulateImage(commandText);
        dialog.setVisible(false);
        resetDialogue();
      } else {
        showErrorMsg("Please select a manipulation methods!");
      }
    });

    this.addKeyListener(new KeyListener() {

      /**
       * Method to support the key typed event.
       * @param e key event.
       */
      @Override
      public void keyTyped(KeyEvent e) {
        // not supportted for this operation.
      }

      @Override
      public void keyPressed(KeyEvent e) {
        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_U) {
          features.loadImage(getLoadedImagePath());
        } else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_S && saveButton.isEnabled()) {
          features.saveImage(saveImagePath(), "");
        } else if (e.isControlDown()
                && e.getKeyCode() == KeyEvent.VK_X
                && splitImageButton.isEnabled()) {
          showSplitDialogBox();
        } else if (e.isControlDown()
                && e.getKeyCode() == KeyEvent.VK_C
                && combineButton.isEnabled()) {
          showCombineDialogBox();
        } else if (e.isControlDown()
                && e.getKeyCode() == KeyEvent.VK_M
                && showButton.isEnabled()) {
          showDialogBox();
        }
      }

      /**
       * Method to support key typed functionalities.
       * @param e key event.
       */
      @Override
      public void keyReleased(KeyEvent e) {
        //not supported for this operation.
      }
    });
  }

  /**
   * Method to display the required error messages to the
   * user whenever an invalid action is performed.
   *
   * @param errorMsg message to be printed.
   */
  @Override
  public void showErrorMsg(String errorMsg) {
    JOptionPane.showMessageDialog(mainPanel, errorMsg, "Error", JOptionPane.WARNING_MESSAGE);
  }

  /**
   * Method to reload the image once updated.
   *
   * @param image to be reloaded.
   */
  @Override
  public void refreshImage(BufferedImage image) {
    try {
      ImageIcon newImageIcon = new ImageIcon(image);
      imageLabel.setIcon(newImageIcon);
      mainPanel.repaint();
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    }
    showButton.setEnabled(true);
    splitImageButton.setEnabled(true);
    saveButton.setEnabled(true);
  }

  /**
   * Method to reload the histogram.
   *
   * @param dataset to be reloaded.
   */
  @Override
  public void refreshHistogram(DefaultCategoryDataset dataset) {
    CategoryPlot plot = chart.getCategoryPlot();
    plot.setBackgroundPaint(Color.WHITE);
    plot.setRangeGridlinePaint(Color.BLACK);

    NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
    rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

    CategoryAxis domainAxis = plot.getDomainAxis();

    domainAxis.setCategoryMargin(0.20);
    domainAxis.setLowerMargin(0.0);
    domainAxis.setUpperMargin(0.0);

    chartPanel.getChart().getCategoryPlot().setDataset(dataset);
  }




}

