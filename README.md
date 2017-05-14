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

Next, we have to setup a git remote in order for this energy measurement to work as currently, the only possible way of
obtaining the files are via jenkins and gitlab.

#### Running the application

Having installed the application and opening it, you will arrive at the GUI interface which is empty.
In order to start running and calculating the energy consumption, go to
```
File > Caclulate Energy
Shortcut: alt+1
```

The folder directory will be the root directory where your java package lies, while the main java filename is the file where
the main code exists i.e. the start of your entire java application.
### Changes

Latest changes from 12/5 - 16/5
- Changed getClassLoader() to obtaining of FXML in preparation for bundling of package
- Added filechoose regardless of location for obtaining of files
- Implemented the program for Windows. 


- Added wrapper such that any java app  that runs will work (unable to implement)
