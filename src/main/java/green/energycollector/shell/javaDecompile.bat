ECHO OFF
SET FOLDER_INPUT=D:\Documents\IdeaProjects\algorithms2
SET MAIN_INPUT=D:\Documents\IdeaProjects\algorithms2\algorithms\Main.java

cd %FOLDER_INPUT%
IF NOT EXIST .\build (
  mkdir build
) else (
  rd /s /q build
  mkdir build
)

::This compiles the required java files.
dir /s /B *.java > sources.txt
javac -d ./build @sources.txt
del sources.txt

::This creates the manifest for preparation of jar
if NOT EXIST .\build/META-INF (
  mkdir .\build\META-INF
) else (
  rf /s /q .\build\META-INF
  mkdir .\build\META-INF
)

::This creates the file MANIFEST.MF
set word=
set str=%MAIN_INPUT%
call set str=%%str:%FOLDER_INPUT%\=%word%%%
call set str=%%str:.java=%%
call set str=%%str:\=/%%
echo Main-class: %str% > build/META-INF/MANIFEST.MF

::This section jars the FOLDER_INPUT
cd build
jar cmvf META-INF/MANIFEST.MF target.jar *

::And obtain the decompilation of the main file
echo Obtaining decompilation...
javap -c %str%.class > decompiledMain
::type decompiledMain
