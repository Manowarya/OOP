javac --module-path ./lib;./lib/javafx-sdk-17.0.0.1/lib --add-modules javafx.controls,javafx.fxml,javafx.graphics,slf4j.api -sourcepath ./src -d bin src/edu/csf/oop/java/seaBattle/Game/Main.java src/module-info.java
cd bin
jar cvfe ../SeaBattle.jar edu.csf.oop.java.seaBattle.Main edu/
cd ..
