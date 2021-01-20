# Software Engineering Project
 A Trafic System developed for the Software Engineering Course.

The Code as well as the API is documented in place.

The Unit tests are under the corresponding subsystems: “<Subsystem>/src/tests/”
Also, we did a lot of visual testing by the running system.

## General Information:
The Map is based on the street map of Linz.
Street information is fetched from OpenStreetMap.

In the Participants Debugging gui, each car is represented with a point. The point is red if the car slows down or is waiting (standing). The point is green if the car is moving by constant speed or speeds up.
Maintenance vehicles are shown as a blue dot.

## Implementation Structure:
Each Subsystem has one or more corresponding folder(s) in the implementation.
There exists one folder called “shared” which contains all common used classes.

## Start all subsystems
In the Terminal: gradlew bootrun
Alternative in Intellij: Gradleview -> Tasks -> applications -> bootrun

## Run all tests
	In the Terminal: gradlew test
	Alternative in Intellij: Gradleview -> Tasks -> verification -> test


## GUI’s:
Note: For the GUIs to work the other subsystems must already be up and running
Participants Debugging gui: http://localhost:8080/participants/gui
Control Center GUI: Terminal: gradlew run
Alternative in Intellij: Gradleview -> Applications -> run

If you think you have a powerful PC: You can choose between different cities/maps in MapRepository.java. Available files can be found in the resources folder of the control center’s subdirectory.;)
