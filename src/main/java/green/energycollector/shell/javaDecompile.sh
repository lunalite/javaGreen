#!/bin/bash
################################################################################################################################
#
#This bash files makes compiling, jar and decompiling of the java package easier while maintaining them in a neat single file location.
#
################################################################################################################################

#This compiles the required java files.
cd $FOLDER_INPUT
if [ ! -d "./build" ]; then
  mkdir build
else
  rm -rf ./build/*
fi
javac -d ./build ${MAIN_INPUT}

#This creates the manifest for preparation of jar
if [ ! -d "./build/META-INF" ]; then
  mkdir ./build/META-INF
fi

#This creates the file MANIFEST.MF
MAIN_INPUT_JAVA=${MAIN_INPUT//${FOLDER_INPUT}}
MAIN_INPUT_JAVA=${MAIN_INPUT_JAVA//.java}
MAIN_INPUT_JAVA=${MAIN_INPUT_JAVA/\/}
echo Main-class: ${MAIN_INPUT_JAVA} > build/META-INF/MANIFEST.MF

#This section jars the FOLDER_INPUT
cd build
jar cmvf META-INF/MANIFEST.MF target.jar *

#And obtain the decompilation of the main file
echo Obtaining decompilation...
javap -c ${MAIN_INPUT_JAVA}.class > decompiledMain
cat decompiledMain
