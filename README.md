# Prerequisites
Install OPEN JDK 17 and MVN

# How to Run in IDEA 
Import the project, open the class named Main, and click on the run icon next to the main method.

Additionally the application can be run using configuration: 

clean javafx:run

As the maven command

  * ## How to test in IDEA
    * Right click on the java folder in intellij and click on "Run 'All Tests'". The java folder is located under src/main/test.
# How to Run outside IDEA
Run the project with a mvn command (This assumes that the Main class is configured in pom.xml)  
mvn clean javafx:run

## How to test outside IDEA
use command: mvn test
within the WilbursCurse folder

### How to run Integration tests (dependencies)
mvn failsafe:integration-test

# Create the Javadocs

  * ## To create the Javadocs in IDEA
    * use command : javadoc:javadoc

  * ## To create the Javadocs outside IDEA
    * use command : mvn javadoc:javadoc

# To create a Jar file 
  * use command : package -DskipTests
  * (For mac add this classifier tag to the end of the org.openjfx dependency to make the jar)
  *     <classifier>mac</classifier>

# Link to a tutorial and showcase of the Game
https://youtu.be/4pYkhd0mIfY
