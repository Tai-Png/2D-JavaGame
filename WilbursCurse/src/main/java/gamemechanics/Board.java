package gamemechanics;

import characters.Butcher;
import characters.MovingCharacter;
import characters.Wilbur;
import environment.BearTrap;
import environment.MagicCorn;
import environment.MagicTruffle;
import environment.StationaryModifier;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;

import static gamemechanics.Direction.*;

/**
 * The main GUI application for the game. Holds the enemies, rewards
 * and main character, as well as displays the visuals, and handles the
 * input. Displays timers and score of the player.
 * Dispatches methods to allow different objects to interact.
 */
public class Board extends Application implements EventHandler<KeyEvent> {

    // This is used for the probability aspect of the tracking function
    protected Random randomNum = new Random();

    // Allows checking as to whether or not the timers should be stopped
    boolean timerOff = false;

    // Path to the current level file
    private String levelFileName = "levelfiles/level1.json";

    // Integer values corresponding to the game sequence to display
    private static final int DEATH = 0;
    private static final int END = 1;

    // Height and width of the board
    private final int height = 20;
    private final int width = 20;
    private int screenWidth = 500;
    private int screenHeight = 500;

    // Sets whether or not to redraw the pathGrid (doesn't redraw after first time)
    private boolean drawn = false;

    // Spaces to hold collision detection information
    private int spaces[][] = new int[height][width];

    // Coordinates of exit and entrance to the level points
    private Coordinate exitPoint;
    private Coordinate startPoint;

    // Wilbur object
    private Wilbur wilbur;

    // Timer for "ticks"
    private Timer gameTime;

    private Timeline gameLine;

    //Grid for the board path (does not get redrawn after first redraw)
    private GridPane pathGrid;

    //Grid for the characters and items on the board (gets redrawn)
    private GridPane componentGrid;

    //ArrayLists of necessary game elements
    private ArrayList<Butcher> butchers;
    private ArrayList<BearTrap> bearTraps;
    private ArrayList<MagicCorn> magicCornArray;
    private ArrayList<MagicTruffle> magicTruffles;
    private ArrayList<MagicTruffle> allMagicTruffles;
    private ArrayList<Barrier> barriers;

    // The available objects in the json.
    // Helps with reading the file to make the appropriate objects.
    String gameElements[] = {"MagicCorn", "MagicTruffle", "Butchers", "Traps", "Barriers"};

    // The time the game was started (helps with timer workings)
    private long startTime = 0;

    private long specialAppearancePeriod = 7000;
    private int secondsUntilAppear = 15;
    private int offSet = 5;
    private long appearAt = -1;
    private boolean trufflePickedUp = false;

    // The main stage to change what the user sees
    private Stage primaryStage;

    /**
     * Copies over an input stream to a created outputstream (here to create a temporary file from a resource
     * @param fileInputStream The input stream created from the json file resource
     * @param out The output stream to write data to (namely to a temporary file)
     * @throws IOException Exception for input or output
     */
    public static void copyFileInfo(InputStream fileInputStream, OutputStream out) throws IOException {
        byte[] databuffer = new byte[1024];
        int charsRead;
        while ((charsRead = fileInputStream.read(databuffer)) != -1) {
            out.write(databuffer, 0, charsRead);
        }
    }

    /**
     * Draws the board structure from a file
     * Reads and fills out the objects to add to the board
     * Allows file reading to create new levels
     * @param filename the name of the file to read
     */
    private void drawBoard(String filename)
    {
        // Create a new JSON object to hold the file contents
        JSONObject levelDetails = new JSONObject();

        // Create a JSON parser
        JSONParser jsonParser = new JSONParser();

        try {
            // Read the JSON file
            InputStream levelFileStream = this.getClass().getClassLoader().getResourceAsStream(filename);

            // Copy over the resource to a file
            File lvlFile = File.createTempFile("lvl1", "json");
            FileOutputStream out = new FileOutputStream(lvlFile);
            copyFileInfo(levelFileStream, out);
            out.close();

            FileReader fileReader = new FileReader(lvlFile);
            levelDetails = (JSONObject) jsonParser.parse(fileReader);

            // Instantiate the arrays to hold all the objects and characters
            // that can be interacted with
            butchers = new ArrayList<>();
            bearTraps = new ArrayList<>();
            magicCornArray = new ArrayList<>();
            magicTruffles = new ArrayList<>();
            barriers = new ArrayList<>();
            allMagicTruffles = new ArrayList<>();

            // Set Wilbur to the start position in the file
            // Wilbur should always be set to the start position when starting the level
            JSONObject wilburStartElement = (JSONObject) levelDetails.get("Start_Point");
            Long wilburStartPositionX = (Long) wilburStartElement.get("x");
            Long wilburStartPositionY = (Long) wilburStartElement.get("y");
            wilbur = new Wilbur(wilburStartPositionX.intValue(),wilburStartPositionY.intValue());
            // Set the start point on the board
            startPoint = new Coordinate(wilburStartPositionX.intValue(),wilburStartPositionY.intValue());

            // Get and set the end of the level position so that the game can be won
            JSONObject endLevel = (JSONObject) levelDetails.get("Exit_Point");
            Long endLevelX = (Long) endLevel.get("x");
            Long endLevelY = (Long) endLevel.get("y");
            exitPoint = new Coordinate(endLevelX.intValue(), endLevelY.intValue());

            // Instantiate basic objects for looping through
            JSONArray currentArray;
            JSONObject currentObject;

            // Loop through the JSON object and add the information to the ArrayLists
            for (int i = 0; i < gameElements.length; i++)
            {
                currentArray = (JSONArray) levelDetails.get(gameElements[i]);

                // Add the items to the according array based on the key
                for (int j = 0; j < currentArray.size(); j++)
                    switch (gameElements[i])
                    {
                        case "MagicCorn":
                            currentObject = (JSONObject) currentArray.get(j);
                            magicCornArray.add(new MagicCorn(((Long)currentObject.get("x")).intValue(), ((Long)currentObject.get("y")).intValue()));
                            break;
                        case "MagicTruffle":
                            currentObject = (JSONObject) currentArray.get(j);
                            allMagicTruffles.add(new MagicTruffle(((Long)currentObject.get("x")).intValue(), ((Long)currentObject.get("y")).intValue()));
                            break;
                        case "Butchers":
                            currentObject = (JSONObject) currentArray.get(j);
                            butchers.add(new Butcher(((Long)currentObject.get("x")).intValue(), ((Long)currentObject.get("y")).intValue()));
                            break;
                        case "Traps":
                            currentObject = (JSONObject) currentArray.get(j);
                            bearTraps.add(new BearTrap(((Long)currentObject.get("x")).intValue(), ((Long)currentObject.get("y")).intValue()));
                            break;
                        case "Barriers":
                            currentObject = (JSONObject) currentArray.get(j);
                            barriers.add(new Barrier(((Long)currentObject.get("x")).intValue(), ((Long)currentObject.get("y")).intValue()));
                            break;

                    }
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to change Wilbur's foot position as he changes coordinate to reflect a moving pig scenario
     */
    public void stepChanges() {
        // Determine which foot Wilbur will have infront as he moves. This gives the illusion that the
        // character is moving by having the Pig objects feet alternate.
        // Changes image when method is run

            if (wilbur.WilburFoot == 1) {
                wilbur.WilburFoot = 2;
            } else if (wilbur.WilburFoot == 2) {
                wilbur.WilburFoot = 3;
            } else if (wilbur.WilburFoot == 3) {
                wilbur.WilburFoot = 1;
            }
    }

    /**
     * Clears the board of all components (butchers, traps, corn, magicTruffle)
     * clears the spaces to be reconfigured based on character movement
     */
    private void clearBoard()
    {
        // Clear components and spaces
        componentGrid.getChildren().clear();
        spaces = new int[width][height];
    }

    /**
     * Simple method to see if all the corn (reward) on the board has been taken
     * @return whether or not the corn has all been taken
     */
    private boolean allCornTaken()
    {
        return magicCornArray.size() == 0;
    }

    /**
     * Runs the ending graphics when the player reaches the end successfully
     */
    private void exitEnding()
    {
        // Check if the corn has all been taken, if so then end the game
        if (allCornTaken()) {
            // Create a container for game end scene
            BorderPane endingMessageContainer = new BorderPane();
            // Initialize top text
            Text endingMessage = new Text();
            endingMessage.setText("Congratulations! The witch is defeated!\nWilbur is...\nTransforming!");
            endingMessage.setStrokeWidth(20);
            endingMessage.setFont(Font.font("verdana", FontWeight.BLACK, FontPosture.REGULAR, 20));
            endingMessage.setFill(Color.WHITE);
            endingMessage.setTextAlignment(TextAlignment.CENTER);
            // Initialize HBox to hold top text
            HBox topBox = new HBox();
            topBox.getChildren().add(endingMessage);
            topBox.setAlignment(Pos.CENTER);
            topBox.setPadding(new Insets(100,0,0,0));
            // Get Wilbur's image and add to imageview for end of game
            ImageView wilburVictory = new ImageView(new Image(getClass().getResourceAsStream("wilbur.png")));

            // Initialize the lower text
            Text oink = new Text();
            oink.setStrokeWidth(20);
            oink.setFont(Font.font("verdana", FontWeight.BLACK, FontPosture.REGULAR, 20));
            oink.setFill(Color.WHITE);
            oink.setTextAlignment(TextAlignment.CENTER);

            // Initialize HBox to hold bottom text
            HBox bottomBox = new HBox();
            bottomBox.getChildren().add(oink);
            bottomBox.setAlignment(Pos.CENTER);
            bottomBox.setPadding(new Insets(0,0,100,0));

            // Add an effect to Wilbur's image and set the scene
            wilburVictory.setEffect(new DropShadow(500, Color.WHITE));
            endingMessageContainer.setCenter(wilburVictory);
            endingMessageContainer.setTop(topBox);
            endingMessageContainer.setPadding(new Insets(20,20,20,20));
            endingMessageContainer.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, null)));
            endingMessageContainer.setBottom(bottomBox);

            // Get the maximum size wilbur should grow to
            final double maxSize = wilburVictory.getScaleX();
            // Get the shrink and grow rate
            double sizeMod = 0.25;
            // Number of times to grow and shrink
            int counterTimes = 5;
            // Make a TimerTask that will loop to shrink and grow Wilbur
            // counterTimes number of times
            TimerTask transform = new TimerTask() {
                int counter = 0;
                boolean shrinking = true;
                @Override
                public void run() {
                    // Shrink wilbur until 0 scale
                    if (shrinking && counter < counterTimes)
                    {
                        wilburVictory.setScaleX(wilburVictory.getScaleX()-sizeMod);
                        wilburVictory.setScaleY(wilburVictory.getScaleY()-sizeMod);
                        if (wilburVictory.getScaleX() <= 0)
                            shrinking = false;
                    }
                    // Grow wilbur until Wilbur is at max size
                    else if (!shrinking)
                    {
                        wilburVictory.setScaleX(wilburVictory.getScaleX()+sizeMod);
                        wilburVictory.setScaleY(wilburVictory.getScaleY()+sizeMod);
                        // If at max size increment counter and start to shrink
                        if (wilburVictory.getScaleX() >= maxSize) {
                            shrinking = true;
                            counter++;
                            // If at the end, switch the image to Wilbur as a man
                            if (counter == counterTimes)
                                wilburVictory.setImage(new Image(getClass().getResourceAsStream("wilbur_man.png")));
                        }
                    }
                    // Set the ending message if at the end and cancel the timer
                    if (counter == counterTimes) {
                        endingMessage.setText("Congratulations! The witch is defeated!\nWilbur is human again!");
                        oink.setText("Oink!\nI mean hi!\nPress Enter to return to the main menu!");
                        gameTime.cancel();
                    }
                }
            };

            // Schedule the transform task to run some amount of milliseconds
            gameTime.scheduleAtFixedRate(transform,0,75);

            // Set the stage to show the game end text
            Scene endScene = new Scene(endingMessageContainer, screenWidth, screenHeight);
            endScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    // If user presses enter, go to the main menu
                    if (keyEvent.getCode() == KeyCode.ENTER)
                    {
                        drawn = false;
                        gameTime.cancel();
                        gameTime = new Timer();
                        gameLine.stop();
                        primaryStage.close();
                        start(new Stage());
                        redraw();
                    }
                }
            });
            primaryStage.setScene(endScene);
            primaryStage.show();
        }
    }

    /**
     * Runs the death ending when the player dies
     */
    private void deathEnding()
    {
        // Create the container for the death scene
        BorderPane endingMessageContainer = new BorderPane();

        // Initialize the top text
        Text endingMessage = new Text();
        endingMessage.setText("UH OH! Wilbur has fallen to the witch!\nPress Enter to restart");
        endingMessage.setStrokeWidth(20);
        endingMessage.setFont(Font.font("verdana", FontWeight.BLACK, FontPosture.REGULAR, 20));
        endingMessage.setFill(Color.WHITE);
        endingMessage.setTextAlignment(TextAlignment.CENTER);

        // Initialize the HBox for the top text
        HBox topBox = new HBox();
        topBox.getChildren().add(endingMessage);
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(100,0,0,0));

        // Initialize the Wilbur defeat image
        ImageView wilburDefeat = new ImageView(new Image(getClass().getResourceAsStream("wilbur_dead.png")));

        // Initialize bottom text
        Text oink = new Text();
        oink.setText("......");
        oink.setStrokeWidth(20);
        oink.setFont(Font.font("verdana", FontWeight.BLACK, FontPosture.REGULAR, 20));
        oink.setFill(Color.WHITE);

        // Initialize the HBox for the bottom text
        HBox bottomBox = new HBox();
        bottomBox.getChildren().add(oink);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(0,0,100,0));

        // Set new effect of red around Wilbur's defeat image
        wilburDefeat.setEffect(new DropShadow(100, Color.RED));
        // Set the bottom text properties
        endingMessageContainer.setCenter(wilburDefeat);
        endingMessageContainer.setTop(topBox);
        endingMessageContainer.setPadding(new Insets(20,20,20,20));
        endingMessageContainer.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, null)));
        endingMessageContainer.setBottom(bottomBox);

        // Set the stage to show the game end text
        Scene endScene = new Scene(endingMessageContainer, screenWidth, screenHeight);
        primaryStage.setScene(endScene);
        primaryStage.show();
        endScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                // If the user presses Enter then restart the game
                if (keyEvent.getCode() == KeyCode.ENTER)
                {
                    // Restart the game
                    drawn = false;
                    gameTime.cancel();
                    gameTime = new Timer();
                    gameLine.stop();
                    createBoard();
                    redraw();
                }
            }
        });
    }

    /**
     * Draws a new window for exiting the level depending on whether the character
     * has died or has finished the level
     * @param gameSequence which sequence to play through (whether character has died or not)
     *                     DEATH or END
     */
    private void endGame(int gameSequence)
    {
        // Turn the timers off
        timerOff = true;

        // Check which type of ending to do. Did player win, or die
        if (gameSequence == END) {
            exitEnding();
        }
        // Runs if Wilbur has died
        else if (gameSequence == DEATH)
        {
            deathEnding();
        }
    }

    /**
     * Main method that launches the application
     * @param args arguments passed into the run command
     */
    public static void main(String[] args) {
        // Launches the Application
        launch(args);
    }

    /**
     * Method to start the game and draw the menu
     * @param primaryStage the stage (window) that will have the controls added
     *                     to it.
     */
    @Override
    public void start(Stage primaryStage) {

        Group menuRoot = new Group();
        Scene menuScene = new Scene(menuRoot, 800, 800);


        // Set the global primary stage to use later to modify the look
        this.primaryStage = primaryStage;

        ImageView menuScreen = new Menu().menuImage;
        menuScreen.setFitHeight(820);
        menuScreen.setFitWidth(810);

        Button button = new Button("Start Game");
        button.setMinWidth(80);
        button.setMinHeight(50);
        button.setTranslateX(360);
        button.setTranslateY(300);
        button.setStyle("-fx-background-color: transparent; -fx-border-width: 3px;"+
                "-fx-text-fill: white; -fx-border-color: white; -fx-border-radius: 5px; "+
                "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.8), 10, 0, 0, 0);");
        button.setOnMouseClicked(e -> {
            drawn = false;
            createBoard();

        });

        Label title = new Label("Wilbur's Curse");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        title.setMinWidth(200);
        title.setTextAlignment(TextAlignment.JUSTIFY);
        title.setTranslateX(300);
        title.setTranslateY(50);
        title.setStyle( "-fx-text-fill: white;");

        menuRoot.getChildren().add(menuScreen);
        menuRoot.getChildren().add(button);
        menuRoot.getChildren().add(title);
        primaryStage.setScene(menuScene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(e -> System.exit(0));
    }

    /**
     * Method that creates and draws the application layout when the
     * board is in game mode (the player may move)
     */
    public void createBoard()
    {
        // Set the title and board grid for the game
        primaryStage.setTitle("Wilbur's Curse");
        Group root = new Group();
        GridPane master = new GridPane();
        pathGrid = new GridPane();
        componentGrid = new GridPane();

        // Make the width and height of the component grid
        for (int i = 0; i < width; i++)
            componentGrid.getColumnConstraints().add(new ColumnConstraints(BoardSpace.block_space));

        for (int i = 0; i < height; i++)
            componentGrid.getRowConstraints().add(new RowConstraints(BoardSpace.block_space));

        // Add the path grid and componentGrid to root
        root.getChildren().add(pathGrid);
        root.getChildren().add(componentGrid);

        // Create the pane to hold the score and timer
        BorderPane metricGrid = new BorderPane();

        // Create the text box to hold the score
        Text score = new Text();
        score.setStrokeWidth(20);
        score.setFont(Font.font("verdana", FontWeight.BLACK, FontPosture.REGULAR, 20));

        // Create the text box to hold the timer
        Text timer = new Text();
        timer.setStrokeWidth(20);
        timer.setFont(Font.font("verdana", FontWeight.BLACK, FontPosture.REGULAR, 20));

        score.setFill(Color.WHITE);
        timer.setFill(Color.WHITE);

        // Add the score and timer to the metric grid and then add metric grid and the board to the master panes
        metricGrid.setLeft(score);
        metricGrid.setRight(timer);
        metricGrid.setPadding(new Insets(0,75,0,75));
        metricGrid.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, null)));
        master.add(metricGrid,0,0);
        master.add(root,0,1);

        // Add metric offset
        screenHeight = (int) (height * BoardSpace.block_space + 20);
        screenWidth = width * BoardSpace.block_space;

        // Instantiate the timer that is used for moving the butcher
        gameTime = new Timer();

        // Read the items and characters from the level file
        drawBoard(levelFileName);

        // Draw the grid on the board
        redraw();

        // Get new butcherProbabilities
        Butcher.generateNewProbabilities(butchers.size());

        // Instantiate the timeline that is used for moving the butcher
        gameLine = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
            // Loop through butchers and make them track Wilbur
            for(int i=0; i< butchers.size(); i++){

                butchers.get(i).tracking(wilbur,spaces, Butcher.getButcherProbability(i));
            }
            // Check if the truffle timer has been initialized or if we are past the end of the timer
            if (appearAt == -1 || appearAt + specialAppearancePeriod < System.currentTimeMillis())
            {
                // Shuffle to get  a new location for the magic truffle
                MagicTruffle firstMagicTruffle = allMagicTruffles.get(0);
                while(firstMagicTruffle == allMagicTruffles.get(0))
                    Collections.shuffle(allMagicTruffles);

                trufflePickedUp = false;
                // Set the time to appear at
                appearAt = System.currentTimeMillis() + (randomNum.nextInt(secondsUntilAppear) + offSet)*1000L;
            }
            redraw();
        }));

        gameLine.stop();
        gameLine.setCycleCount(Animation.INDEFINITE);
        gameLine.play();

        // Set the window and register key listening
        Scene scene = new Scene(master, screenWidth, screenHeight);
        scene.addEventFilter(KeyEvent.KEY_RELEASED, this);

        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(key -> System.exit(0));

        // Set the start time, to begin the timer
        startTime = System.currentTimeMillis();

        final int totalSize = magicCornArray.size();

        // Set the initial text for the score and timer
        score.setText("0 pts | 0/" + totalSize + " Magic Corn");
        timer.setText("0s");

        // TimerTask to update the score and clock
        TimerTask updateTimerAndScore = new TimerTask() {
            @Override
            public void run() {
                score.setText(wilbur.getPoints() + " pts | " + (totalSize-magicCornArray.size()) + "/" +  totalSize + " Magic Corn");

                // Get seconds and then set the timer to minutes (if applicable) and seconds
                long timerTime = (System.currentTimeMillis() - startTime)/1000;
                timer.setText( ((timerTime/60)==0)?(timerTime%60 + "s"):(timerTime)/60+"m "+ timerTime%60+"s");
            }
        };
        // Schedule updates to constantly update the timer
        gameTime.scheduleAtFixedRate(updateTimerAndScore,0,250);
    }

    /**
     * Draws an element from an arraylist on the board at the specified coordinate
     */
    public <T> void drawModifierElementOnBoard(ArrayList<T> listOfModifiers)
    {
        for (T modifierTemp : listOfModifiers)
        {
            StationaryModifier modifier = (StationaryModifier) modifierTemp;
            ImageView im = modifier.getImage();
            if (modifier instanceof BearTrap) {
                spaces[modifier.getxCoordinate()][modifier.getyCoordinate()] = BoardSpace.BEAR_TRAP;
                componentGrid.add(im, modifier.getxCoordinate(), modifier.getyCoordinate());
            }
            else if (modifier instanceof MagicCorn) {
                spaces[modifier.getxCoordinate()][modifier.getyCoordinate()] = BoardSpace.MAGIC_CORN;
                componentGrid.add(im, modifier.getxCoordinate(), modifier.getyCoordinate());
            }
            else if (modifier instanceof MagicTruffle) {

                // If we are in the time period that the truffle should appear for, then show the truffle and break
                // We break since we only show 1 truffle at a time
                if (System.currentTimeMillis() >= appearAt && System.currentTimeMillis() <= (appearAt + specialAppearancePeriod) && !trufflePickedUp)
                {
                    spaces[modifier.getxCoordinate()][modifier.getyCoordinate()] = BoardSpace.MAGIC_TRUFFLE;
                    componentGrid.add(im, modifier.getxCoordinate(), modifier.getyCoordinate());
                }
                break;
            }

        }
    }

    /**
     * Draws a character from an arraylist on the board at the specified coordinate
     */
    public <T> void drawCharacterElementOnBoard(ArrayList<T> listOfCharacters)
    {
        for (T characterTemp : listOfCharacters)
        {
            MovingCharacter character = (MovingCharacter) characterTemp;
            ImageView im = character.getImage();
            componentGrid.add(im, character.getxCoordinate(), character.getyCoordinate());
            if (character instanceof Butcher)
                spaces[character.getxCoordinate()][character.getyCoordinate()] = BoardSpace.BUTCHER;
            else if (character instanceof Wilbur)
                spaces[character.getxCoordinate()][character.getyCoordinate()] = BoardSpace.WILBUR;
        }
    }

    /**
     * Method to redraw the board with items, and characters at their new locations
     * after movement, or item stepped on
     */
    private void redraw()
    {
        // Clear the board
        clearBoard();

        // Draw Paths on board if they have not been drawn already
        if (!drawn) {
            // Loop through width and height and create an path at each space with
            // a random path image
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    Path p = new Path(i, j);
                    p.setRandomPathImage();
                    // Add paths to grid
                    pathGrid.add(p.getImageView(), p.getxCoordinate(), p.getyCoordinate());
                }
            }

            // Create a new farmhouse at origin of board for the style of this level
            // Add the new farmhouse to the board
            Barrier farmHouse = new Barrier(0, 0);
            farmHouse.setType(Barrier.FARM_HOUSE);
            ImageView im1 = farmHouse.getImageView();
            pathGrid.add(im1, farmHouse.getxCoordinate(), farmHouse.getyCoordinate());
        }


        // Draw BearTraps and add to spaces array and component grid
        drawModifierElementOnBoard(bearTraps);

        // Draw Butchers and add to spaces array and component grid
        drawCharacterElementOnBoard(butchers);

        // Draw MagicCorn and add to spaces array and component grid
        drawModifierElementOnBoard(magicCornArray);

        // Draw Barriers and add to spaces array and component grid
        for (Barrier b: barriers) {
            ImageView im = b.getImageView();
            componentGrid.add(im, b.getxCoordinate(), b.getyCoordinate());
            spaces[b.getxCoordinate()][b.getyCoordinate()] = BoardSpace.BARRIER;
        }

        // Draw MagicTruffles and add to spaces array and component grid
        drawModifierElementOnBoard(allMagicTruffles);

        // Wilbur will be drawn here
        ImageView im = wilbur.getImage();
        // Here we set up the possible images to draw for Wilbur
        wilbur.draw();

        // Wilbur will be drawn here
        drawCharacterElementOnBoard(new ArrayList<MovingCharacter>(List.of(wilbur)));
        // Draw something in the way of the exit until all corn is collected
        if (!allCornTaken())
        {
            Barrier exit = new Barrier(exitPoint.getxCoordinate(), exitPoint.getyCoordinate());
            ImageView exitImage = exit.getImageView();
            componentGrid.add(exitImage, exitPoint.getxCoordinate(), exitPoint.getyCoordinate());
            // Set the space in the array to the barrier value
            spaces[exitPoint.getxCoordinate()][exitPoint.getyCoordinate()] = BoardSpace.BARRIER;
        }
        else
        {
            // Set the space in the array to the exit value so that we can test if we are over it later
            spaces[exitPoint.getxCoordinate()][exitPoint.getyCoordinate()] = BoardSpace.EXIT;
        }

        // If this is the first time drawing the board then start the timer at the start time
        if (!drawn)
        {
            drawn = true;
            startTime = System.currentTimeMillis();
        }

        // If Wilbur's points are below zero then end the game
        if (wilbur.getPoints() < 0)
            endGame(DEATH);
        if (wilbur.isDead())
            endGame(DEATH);

        // Draw the flying Witch
        Image witchImage = new Image(getClass().getResourceAsStream("witch.png"));
        ImageView imWitch = new ImageView(witchImage);
        imWitch.setFitWidth(2*BoardSpace.block_space);
        imWitch.setFitHeight(2*BoardSpace.block_space);
        componentGrid.add(imWitch, 17, 1);
        ScaleTransition scale = new ScaleTransition();
        scale.setDuration(Duration.millis(3000));
        scale.setByX(2);
        scale.setByY(2);
        scale.setCycleCount(4);
        scale.setAutoReverse(false);
        scale.setNode(imWitch);
        scale.play();
    }

    /**
     * Dispatch a movement request to moveWilburInput() with the key input
     * from the user unless he is dead
     * @param event the key event to pass, depending on key pressed
     */
    @Override
    public void handle(KeyEvent event) {
        // Handle the key press and pass it to the moveWilburInput method unless wilbur is dead
        if (!wilbur.isDead())
        {
            moveWilburInput(event.getCode());
        }
    }

    /**
     * Main collision detection system based on integer array
     * Checks if Wilbur can move to the specified position or if there is
     * a barrier in his way.
     * @param code the keycode input from the user
     */
    public void moveWilburInput(KeyCode code)
    {
        // If UP is pressed
        if (KeyCode.UP == code)
        {
            // Check coordinate above Wilbur to see if we can move there
            // and change Wilbur's sprite direction
            wilbur.setDirection(Up);
            if (wilbur.getCoordinate().checkCoordUp(spaces)) {

                // Move Wilbur, change his sprite and do the collision interaction (if there is one)
                wilbur.moveUp();
                stepChanges();
                doInteraction(wilbur.getCoordinate().checkIfInteraction(spaces));
            }
        }
        // If DOWN is pressed
        else if (KeyCode.DOWN == code)
        {
            // Check coordinate below wilbur to see if we can move there
            // and change Wilbur's sprite direction
            wilbur.setDirection(Down);
            if (wilbur.getCoordinate().checkCoordDown(spaces))
            {
                // Move Wilbur, change his sprite and do the collision interaction (if there is one)
                wilbur.moveDown();
                stepChanges();
                doInteraction(wilbur.getCoordinate().checkIfInteraction(spaces));
            }
        }
        // If LEFT is pressed
        else if (KeyCode.LEFT == code)
        {
            // Check coordinate below Wilbur to see if we can move there
            // and change Wilbur's sprite direction
            wilbur.setDirection(Left);
            if (wilbur.getCoordinate().checkCoordLeft(spaces))
            {
                // Move Wilbur, change his sprite and do the collision interaction (if there is one)
                wilbur.moveLeft();
                stepChanges();
                doInteraction(wilbur.getCoordinate().checkIfInteraction(spaces));
            }
        }
        // If RIGHT is pressed
        else if (KeyCode.RIGHT == code)
        {
            // Check coordinate below wilbur to see if we can move there
            // and change Wilbur's sprite direction
            wilbur.setDirection(Right);
            if (wilbur.getCoordinate().checkCoordRight(spaces))
            {
                // Move Wilbur, change his sprite and do the collision interaction (if there is one)
                wilbur.moveRight();
                stepChanges();
                doInteraction(wilbur.getCoordinate().checkIfInteraction(spaces));
            }
        }

        // Redraw the board with new positions
        redraw();
    }

    /**
     * Finds and removes a stationary modifier once it is walked over from its array list
     * @param arrayList the list to remove the object from
     * @param item the item at the specified coordinate
     * @param <T> The type of item
     */
    public <T> void removeItem(ArrayList<T> arrayList, T item)
    {
        // Cast generic
        StationaryModifier stationaryModifier = (StationaryModifier) item;

        // Loop through and check which modifier we stepped over
        for (int i = 0; i < arrayList.size(); i++)
            // Cast arrayList at index i and item to Stationary modifier to check coordinates
            if (Coordinate.isCoordSame(((StationaryModifier)arrayList.get(i)).getCoordinate(), ((StationaryModifier) item).getCoordinate())) {
                // If we find the one we walked over then remove it
                arrayList.remove(i);
                break;
            }
        // Redraw the board (without the removed item)
        redraw();
    }

    /**
     * Completes interaction between wilbur and other entities on the board
     * @param type the type of entity at the space wilbur moves onto
     */
    private void doInteraction(int type)
    {
        // Cases based on the corresponding BoardSpace value
        switch (type)
        {
            case BoardSpace.MAGIC_CORN:
                // Do the corn interaction
                wilbur.pickupMagicCorn();
                // Remove the magic corn we stepped on from the array
                removeItem(magicCornArray, new MagicCorn(wilbur.getxCoordinate(), wilbur.getyCoordinate()));
                break;
            case BoardSpace.MAGIC_TRUFFLE:
                // Do the truffle interaction
                wilbur.pickupMagicTruffle(gameTime);
                trufflePickedUp = true;
                removeItem(allMagicTruffles, new MagicTruffle(wilbur.getxCoordinate(), wilbur.getyCoordinate()));
                break;
            case BoardSpace.BEAR_TRAP:
                // Do the BearTrap interaction
                if (wilbur.stepOnBearTrap())
                    endGame(DEATH);
                // Remove the bear trap from the board at the specified position
                removeItem(bearTraps, new BearTrap(wilbur.getxCoordinate(), wilbur.getyCoordinate()));
                break;
            case BoardSpace.BUTCHER:
                // Damage Wilbur if he is not invincible
                if (wilbur.attackedByButcher()){
                    endGame(DEATH);
                }
                break;
            case BoardSpace.EXIT:
                // Run the routine to end the game
                endGame(END);
                break;
        }
    }

}
