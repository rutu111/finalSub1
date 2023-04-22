# GRIME: Graphical Image Manipulation and Enhancement

Overview
-----------------

**GRIME is an advanced image processing application which extends previous MIME. It provides a
graphical user interface for the MIME application with some new features:
such as,**

1. Histogram of the current image.
2. Histogram shows the red, green, blue and intensity components.
3. Support to run the program via text commands, command script and Graphical User Interface.

## View

### Additions

**1. JFrameView** :

It is an interface which represents an Graphical User Interface. This interface
contains a summary of all the methods necessary for the graphical user interface.

**2. JFrameViewImpl** :

A class that implements the JFrameView interface. This class is used to build
the graphical user interface for the Image manipulation application. All the methods
such as flips, component visualization, greyscale, blurring, sharpening and sepia are
supported via a interactive user interface. To implement the GUI,
the application uses JFreeChart library.
This part of the program is complete.

## Controller

### Additions

**1. Features** :

It is an interface which represents a Graphical user interface controller. This interface
contains a summary of all the methods that are required by a controller to delegate the task
of load, manipulate and save an image.

This part of the program is complete.

**2. GUIBasedController** :

This class implements the Features interface. It contains all the business logic for the methods
required to load, manipulate and save an image. Object of this class in initialized in the main
method.

This part of the program is complete.

## Way to accept a script file as a command-line option

When we build the application a jar file is created which is located in the res folder.
Also, there is a script file which contains all the implemented commands in a res folder named
"command-script.txt".

Steps :

1. Open a command-prompt/terminal and navigate to the folder where the res folder is placed.
2. Based on the command passed it will run accordingly :
    1. `java -jar NameOfJARFile.jar -file command-script.txt`:
       <br/>It will execute all the commands written in the given script file.<br/>
       example: java -jar res/assign_4.jar -file res/command-script.txt
    2. `java -jar NameOfJARFile.jar -text`
       <br/>Using this command, we can make command line interactive with our application. One can
       write commands in the terminal and perform various image manipulation tasks interactively.
       One can run the script file using the "run res/command-script.txt" after running this
       interactive command line.<br/>
       example: java -jar res/assign_4.jar
    3. `java -jar NameOfJARFile.jar` :
       <br/>Using this command, we can use the Graphical User Interface for our application.
       <br/>The GUI, provides the privilege to load an image, perform operation by clicking
       respective buttons and also saving the image at the desired location.

Note:
1. We can also run jar file by double-clicking on it.

2. We have added sample images in the res/sample folder.


# MIME: More Image Manipulation and Enhancement

Overview
-----------------

**MIME is an advanced image processing application which extends previous IME. It helps to perform
image manipulation and enhancement effects
such as,**

1. Filtering such as image blur and image sharpening
2. Color transformations such as greyscale and sepia tone
3. Dithering
4. Support load and save various types of images such as jpg, jpeg, png and bmp
5. Running a command script file from the terminal

## Controller

### Additions

**1. Controller** :

We have added a command design pattern. The command pattern is a behavioural design pattern in which
an object is used to encapsulate all information needed to perform an action or trigger an event at
a later time. This information includes the method name, the object that owns the method and values
for the method parameters.

These changes will remove the dependency on the switch case statement which we have to update every
time we add new commands in the application. Using the new design we will be able to add new
commands without changing the already existing controller code. We have to create a new controller
containing the latest command-related changes which will further extend already existing controller
class.
This part of the program is complete.

**2. EnhanceController** :

Given controller class extends already existing controller named Controller. It further add new
commands like filter, color-transformation and dither in the command map named knownCommands. Adding
a new class is a good approach to adding new commands without changing already existing controller
class.
This part of the program is complete.

**3. commands** :

Given package contains a **CommandInterface**, the interface which contains a execute method and a
command validation method.

Given package also contains classes for each command which will implement the CommandInterface.
Here, execute method will call appropriate model methods according to the command and the
validateCommand method will validate if the parameter passed to the command is correct or not.
This part of the program is complete. If one need to add new command then one should add new class
for that command.

**4. utility** :

We have added methods to read and save various kinds of images such as jpg, jpeg, png and bmp. The
read method will load various types of images and save them to the database hash in the image type.
The save method will fetch an image from the database according to the filename provided and save it
to the provided location in form of provided type.
This part of the program is complete.

### Changes

**1. utility** :

We have moved the utility class, containing the business logic for reading and writing an image
file from the Operating System, from Model to the Controller. This ensures that the task of loading
and saving of an Image in handled by the controller. Also, inorder to save the image object to the
database(here Hashmap) we are passing an inputstream of object to the model class load function
which stores the image object to the hashmap. The save method in the model class returns an
Inputstream type of object, to the controller,which contains the object to be saved to the
Operating system at the desired path.

## Models

### Additions

**1. EnhancedModelInterface** :

It is an interface that extends ModelInterface. It contains a summary of image processing methods
like filtering, color transformation and dithering.
Adding a new interface is a good approach to adding new methods summary to the already existing
interface without changing it.
This part of the program is complete.

**2. EnhancedModel** :

It represents a class which implements the EnhancedImageModel interface, and it extends Model class.
It contains methods of advanced image processing and manipulation such as filtering, color
transformation and dithering.
Adding a new class is a good approach to adding new methods to the already existing class without
changing it.
This part of the program is complete.

**3. operations** :

This new package contains all the enum classes and entity classes. This part of the program is
complete.

Entity classes which represent Pixel and Image entity and Enum classes such as,

1. FILTERS :
   This enum class contains filter methods like blur and Sharpe. Also contains kernel values for
   each filter which will be further used in the business logic performed by the model.

2. TRANSFORMATIONS :
   This enum class contains color transformation methods like greyscale and sepia tone. Also
   contains kernel values for each transformation which will be further used in the business logic
   performed by the model.

### Changes

**1. database** :

We have moved the hashmap which represents the collection of images to ImageData class. ImageData
class represents a database that contains hashmap and also contains methods to get or set images
from the hashmap. This class interacts with Model class for database manipulation-related tasks.
This part of the program is complete.

Note :

1. We have renamed few classes for better understanding of what a class represents.
   Such as, ImageModel and ImageModelImpl to ModelInterface and Model respectively, Image and PPM to
   ImageInterface and Image respectively, and also remove the abstractImage class to remove
   redundancy.

2. We have moved the business logic from AbstractImage(currently Image) class to Model class.
   So deleted the AbstractImage class.

3. Our Image class represents an Image entity and contains the data representing the same.

### Main

**1. SimpleController** :

This main controller class will create a new object of model and view classes and then pass these
objects to the controller. Then relinquishes control to the controller (by calling its go method).

We have added functionality to support ability to accept a script file as a command-line option.
This part of the program is complete.

## Way to accept a script file as a command-line option

When we build the application a jar file is created which is located in the res folder.
Also, there is a script file which contains all the implemented commands in a res folder named
"command-script.txt".

Steps :

1. Open a command-prompt/terminal and navigate to the folder where the res folder is placed.
2. Based on the command passed it will run accordingly :
    1. `java -jar NameOfJARFile.jar -file command-script.txt`:
       <br/>It will execute all the commands written in the given script file.<br/>
       example: java -jar res/assign_4.jar -file res/command-script.txt
    2. `java -jar NameOfJARFile.jar` :
       <br/>Using this command, we can make command line interactive with our application. One can
       write commands in the terminal and perform various image manipulation tasks interactively.
       One can run the script file using the "run res/command-script.txt" after running this
       interactive command line.<br/>
       example: java -jar res/assign_4.jar

We can also run jar file by double-clicking on it.

# IME: Image Manipulation and Enhancement

Overview
------------------

**IME is an image processing application. It helps to perform image manipulation and enhancement
effects such as,**

1. Visualizing components
2. Image flipping - Horizontally or Vertically
3. Brightening or darkening an image
4. Converting color image to greyscale image based on various channels such as red, green, blue,
   value, intensity and luma.
5. Split the image into 3 greyscale images based on red, green and blue channels.
6. Combining 3 greyscale images based on red, green and blue channels into one color image.

## Implementation

IME is based on the MVC architecture which follows Model-View-Controller (MVC) design pattern.
In which,
Model contains the business logic for image processing.
Controller acts as a mediator between view and model. It takes commands from the view and processes
the command using logic developed in the model.
View is the user interaction layer which displays the output of the proceed command.

### Models

**1. ImageModel** :

It is an interface which represents an image model. This interface contains a summary of the
business logic performed on the image model.

**2. ImageModelImpl** :

It represents a model class which implements ImageModel interface. It contains methods of image
processing and manipulation such as loading the image, saving the image, flipping an image
horizontally or vertically, converting an image to a greyscale image, splitting an image into 3
greyscale images and combining 3 greyscale images into one color image.    
It also handles imageCollection. **imageCollection** is a hashmap which contains filename as a key
and Image object as a value. It will add and update the values according to the commands given.

**3. PixelInterface** :

It is an interface which represents Pixel entity. An image is a sequence of pixels. Each pixel has a
position in the image and a color.

**4. Pixel** :

This class represents entity class of pixel and implements PixelInterface interface. It contains
various methods performed on the pixels such as, get and set methods for R, G, B value. Also, get
methods for value, intensity and luma.

**5. Image** :

This interface contains summary of the generalized image processing and manipulation methods. It
also contains methods to fetch width, height, maxvalue and pixels of an image.

**6. AbstractImage** :

This class represents an abstract class of the image and implements Image interface. It also
contains generalized business logic for image processing and manipulation. It will be further
extended by the PPM class.

**7. PPM** :

This Class represents a PPM image file and it also extends AbstractImage abstract class. The PPM
format is a simple, text-based file format to store images. It contains a dump of the red, green and
blue values of each pixel, row-wise. This class override various processing and manipulation methods
on the PPM file.

**8. COMPONENTS** :

This Enum class COMPONENTS represent various channels of the pixels such as red, green, blue, value,
intensity and luma.

### Controller

**1. ImageController** :

It is a controller interface which contains summary of the operations performed in the Image
controller. Image controller act as a mediator between ImageView and ImageModel.

**2. ImageControllerImpl** :

This implementation is a controller class which implements ImageController interface. It took
commands from the terminal or script-file. It calls logic methods of the model according to the
command provided and sent appropriate output to the view. Hence, It removes dependency between model
and view.

### View

**1. ImageView** :

This interface contains summary of the methods used to interact with users. This is an interaction
layer of MVC.

**2. ImageViewImpl** :

It is a view class which implements ImageView interface. This class will contain the methods
implemented for user interaction.
It takes proceed output from the controller and displays it in the terminal.

### Utils

**1. ImageUtils** :

This interface contains summary of the helper utility class. It contains an overview of the save and
read image methods.

**2. ImageUtilsImpl** :

This utility helper class is used for image processing and implements ImageUtils interface. It
contains methods like read and save image.

### SimpleController

This main controller class will create a new object of model and view classes and then pass these
objects to the controller. Then relinquishes control to the controller (by calling its go method).

## Ways to input commands

There are two ways where user can pass the commands and manipulate the image.

**1. Command-line** :

User can use console to enter commands in the program. To use this way one need to use *
*SimpleController** which contains a main method. This method will get inputs using the scanner one
by one in iterative manner and send it to the ImageControllerImpl for the further processing. The
iteration will stop once the method receives **exit** command as a input. It also displays
appropriate output to the terminal once the command proceed.

**2. Script-file** :

User can send a sequence of script commands using a script file. One need to pass script file using
command **run script-file** where script-file is the path of the script file.

**For Example** : To run the script file named **script-file** in the given code. One needs to
follow steps given below:

1. Run main method of SimpleController.
2. enter **run script-file** command to the console where script-file is the path of the command
   file.
3. enter **exit** if wants to exit from the console.
4. enter the valid command if wants to perform actions further.

## Contributing

1. Create your feature branch: `git checkout -b new-branch-name`
2. Commit your changes: `git commit -am 'Add some feature'`
3. Push to the branch: `git push origin new-branch-name`
4. Submit a pull request

## Contributors

- Krina Devani
- Sarthak Kagliwal

## License

PPM file used in the application is created by us and we are authorizing it to use further in this
project.


  

