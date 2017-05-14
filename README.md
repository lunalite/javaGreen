# javaGreen App

### About

javaGreen Application is an application that generates a simple report of finding the energy consumption of java programs.
As of now, this application is in its most basic form with only the power measurement of an application obtained via
another machine through powertop.

### Installation

### How to run
#### Pre-setup

First, we have to set up a directory where your folder and package is located at. For our case, we'll have it at
```$xslt
D:\Documents\IdeaProjects\algorithms2
```

We will then move our java package/application into this particular directory with the classpath being as aforementioned.

Next, we have to setup a git remote in order for this energy measurement to work as currently, the only possible way of obtaining the files are via jenkins and gitlab.

Do note that when running this power measurement, the program will have to run for at least 25 seconds for an accurate measurement during its idle state. A wrapper is required to be added by to ensure that it the main method is called multiple times until a period of 25 seconds elapsed.

```java
public class Wrapper {
    public static void main(String[] args) {
        YourMainClass.main(args);
    }
}
```

If you are planning to implement it with this method, then do point the new main class to this particular file instead.

#### Running the application

Having installed the application and opening it, you will arrive at the GUI interface which is empty.
In order to start running and calculating the energy consumption, go to
```
File > Caclulate Energy
Shortcut: alt+1
```

The folder directory will be the root directory where your java package lies, while the main java filename is the file where the main code exists i.e. the start of your entire java application.

If the package compiles/decompiles successfully, then the decompiled bytecodes should show on the root GUI, else you will have to check if there is an issue with your package that prevents it from compiling.

### Changes

Latest changes from 12/5 - 16/5
- Changed getClassLoader() to obtaining of FXML in preparation for bundling of package
- Added filechoose regardless of location for obtaining of files
- Implemented the program for Windows. 
- Added wrapper to run main in a loop to ensure that it lasts throughout the time period of measurement
