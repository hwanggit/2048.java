/* FileHeader: Gui2048.java is file that contains classes used to make a beautiful looking game 2048.
 * Once you run the program, a window will pop up in which you can play the game to your enjoyment
 *
 * Class Header: Gui2048 class contains methods which create the graphics of the game, make number tiles move
 * visually by implementing tools taken from Board class, and it also contains instance variables which mainly create 
 * panes for the Gui, and one the store the board from Board Class.
 * this class also inherits from Application and overides methods such as Start and Handle
 */

import javafx.application.*;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import java.util.*;
import java.io.*;

public class Gui2048 extends Application
{
    private String outputBoard; // file to save board
    private Board board; // The 2048 Game Board
    private GridPane pane; // main GridPane used to place title, score, and gameboard
    private Text text; //textfield for tiles
    private StackPane parent; //main StackPane (puts panes on top of eachother)
    private GridPane root; //overlay pane over main gridpane (used for gameover)
    
    // Start method: creates stage, scene and operates elements of Gui2048
    // Parameter: the stage aka window that pops up
    @Override
    public void start(Stage primaryStage)
    {
        //initialize game board
	clearTiles();
	// Create the pane that will hold all of the visual objects
        pane = new GridPane();
        pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        pane.setStyle("-fx-background-color: rgb(187, 173, 160)");
        // Set the spacing between the Tiles
        pane.setHgap(15); 
        pane.setVgap(15);

        //create title of game and score and place on left and right side of scene side
        Label title = new Label("2048");
        title.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));
        title.setTextFill(Color.BLUE);
        Label score = new Label("Score: ");
        score.setFont(Font.font("Times New Roman", FontWeight.LIGHT, 20));
        pane.add(title,0,0);
        pane.add(score,board.GRID_SIZE-2,0);
        
        //create a stackpane and an overlay pane for game over function
        parent=new StackPane();
        root= new GridPane();
        root.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        // make overlay initially
        root.setStyle("-fx-background-color: rgb(238,228,218,0.73)");
	Text start=new Text("Press N to start");
	root.getChildren().add(start);
        root.setAlignment(Pos.CENTER);
 
        //add StackPane to scene, and add scene to state, set stage title to Gui2048
        Scene scene= new Scene(parent,475,520);
        parent.getChildren().addAll(pane,root);   
        primaryStage.setTitle("Gui2048");
        primaryStage.setScene(scene);
        primaryStage.show(); 
        
        // set the initial state of the board
        update(); 
        
        // whenever a key is presed, access meKeyHandler class and implement EventHandler
        scene.setOnKeyPressed(new myKeyHandler());
    }
    
    // Class Header: myKeyHandler class uses the interface EventHandler to help the Gui react to 
    // key preses. It also contains the handle method for key preses
    private class myKeyHandler implements EventHandler<KeyEvent> {
        //Handle method calls move methods and update methods, checks game over if up down left or
        //right key is presed
        //Parameter: The key presed
        @Override
        public void handle(KeyEvent e) { 
	    if (board.isGameOver()&&(e.getCode()!=KeyCode.N)) {
		return;
	    }
	    // if key pressed is UP Down left or right, move respectively
            if (e.getCode()==KeyCode.UP&&board.canMove(Direction.UP)) {
            	root.getChildren().clear(); 
		root.setStyle("-fx-background-color: rgb(187, 173, 160,0)");
		board.move(Direction.UP); 
                board.addRandomTile();
                // clear the board after each move
                pane.getChildren().clear();
                // update score and print to terminal moving direction
                Text scoreNum=new Text(Integer.toString(board.getScore()));
                pane.add(scoreNum,board.GRID_SIZE-1,0);
                // check game over then change transparency of overlay pane
                update();
                if (board.isGameOver()) {
                    Text gameOver=new Text("Game Over! Press N to restart");
                    root.getChildren().add(gameOver);
                    root.setAlignment(Pos.CENTER);
                    root.setStyle("-fx-background-color: rgb(238,228,218,0.73)");
                    return;
                }
            }
            else if (e.getCode()==KeyCode.DOWN&&board.canMove(Direction.DOWN)) {
		root.getChildren().clear(); 
		root.setStyle("-fx-background-color: rgb(187, 173, 160,0)");
		board.move(Direction.DOWN); 
                board.addRandomTile();
                 // clear the board after each move 
                pane.getChildren().clear();
                // update score and print to terminal moving direction 
                Text scoreNum=new Text(Integer.toString(board.getScore()));
                pane.add(scoreNum,board.GRID_SIZE-1,0);  
                 // check game over then change transparency of overlay pane 
                update();
                if (board.isGameOver()) {
                    Text gameOver=new Text("Game Over! Press N to restart");
                    root.getChildren().add(gameOver);  
                    root.setAlignment(Pos.CENTER);
                    root.setStyle("-fx-background-color: rgb(238,228,218,0.73)");
                    return;
                }
            }
            else if (e.getCode()==KeyCode.RIGHT&&board.canMove(Direction.RIGHT)) {          	
               root.getChildren().clear(); 
		root.setStyle("-fx-background-color: rgb(187, 173, 160,0)");
		board.move(Direction.RIGHT); 
                board.addRandomTile();
                  // clear the board after each move
                pane.getChildren().clear();
                 // update score and print to terminal moving direction
                Text scoreNum=new Text(Integer.toString(board.getScore()));
                pane.add(scoreNum,board.GRID_SIZE-1,0); 
                  // check game over then change transparency of overlay pane
                update();
                if (board.isGameOver()) {
                    Text gameOver=new Text("Game Over! Press N to restart");
                    root.getChildren().add(gameOver); 
                    root.setAlignment(Pos.CENTER);
                    root.setStyle("-fx-background-color: rgb(238,228,218,0.73)");
                    return;
                }
            }           
            else if (e.getCode()==KeyCode.LEFT&&board.canMove(Direction.LEFT)) {
		root.getChildren().clear(); 
		root.setStyle("-fx-background-color: rgb(187, 173, 160,0)");
		board.move(Direction.LEFT); 
                board.addRandomTile();
                  // clear the board after each move
                pane.getChildren().clear();
                 // update score and print to terminal moving direction
                Text scoreNum=new Text(Integer.toString(board.getScore()));
                pane.add(scoreNum,board.GRID_SIZE-1,0);
                // check game over then change transparency of overlay pane   
                update();
                if (board.isGameOver()) {
                    Text gameOver=new Text("Game Over! Press N to restart");
                    root.getChildren().add(gameOver); 
                    root.setAlignment(Pos.CENTER);
                    root.setStyle("-fx-background-color: rgb(238,228,218,0.73)");
                    return;
                }
            }
            // if S key is presed, save the board to myBoard.out
            else if (e.getCode()==KeyCode.N) {
		root.getChildren().clear(); 
		root.setStyle("-fx-background-color: rgb(187, 173, 160,0)");
		if (board.isGameOver()) {
		    pane.getChildren().clear();
		    clearTiles();
		    update();
		}
		return; 
	    }   
            // if another key is presed, return
            else {
                return;
            }
        }    
    }         
    // Update Method: updates the tile values of the game grid after each move
    private void update() {
        // store the board array
    	int [][] boardGrid=board.getGrid();
        // update the Gui each time
        Label title = new Label("2048");
        title.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));
        title.setTextFill(Color.BLUE);
        Label score = new Label("Score: ");
        score.setFont(Font.font("Times New Roman", FontWeight.LIGHT, 20));
        pane.add(title,0,0);
        pane.add(score,board.GRID_SIZE-2,0);
        // loop through the board array and cahnge the rectangles to their respective 
        // features based on the number in the array
        for (int i=0; i<board.GRID_SIZE;i++) {
            for (int j=0; j<board.GRID_SIZE;j++) {
            	if (boardGrid[i][j]==0) {
                    Rectangle thisTile = new Rectangle (100,100,Constants2048.COLOR_EMPTY);
                    pane.add(thisTile,j,i+1);
                }
                else if (boardGrid[i][j]==2) {
                    makeTile(i,j,2,Constants2048.COLOR_2,
                                     Constants2048.COLOR_VALUE_DARK,Constants2048.TEXT_SIZE_LOW);
                }
                else if (boardGrid[i][j]==4) {
                    makeTile(i,j,4,Constants2048.COLOR_4,
                                     Constants2048.COLOR_VALUE_DARK,Constants2048.TEXT_SIZE_LOW);
                } 
                else if (boardGrid[i][j]==8) {
                    makeTile(i,j,8,Constants2048.COLOR_8,
                                     Constants2048.COLOR_VALUE_LIGHT,Constants2048.TEXT_SIZE_LOW);
                }
                else if (boardGrid[i][j]==16) {
                    makeTile(i,j,16,Constants2048.COLOR_16,
                                     Constants2048.COLOR_VALUE_LIGHT,Constants2048.TEXT_SIZE_LOW);
                }
                else if (boardGrid[i][j]==32) {
                    makeTile(i,j,32,Constants2048.COLOR_32,
                                     Constants2048.COLOR_VALUE_LIGHT,Constants2048.TEXT_SIZE_LOW);
                }
                else if (boardGrid[i][j]==64) {
                    makeTile(i,j,64,Constants2048.COLOR_64,
                                     Constants2048.COLOR_VALUE_LIGHT,Constants2048.TEXT_SIZE_LOW);
                }
                else if (boardGrid[i][j]==128) {
                    makeTile(i,j,128,Constants2048.COLOR_128,
                                     Constants2048.COLOR_VALUE_LIGHT,Constants2048.TEXT_SIZE_MID);
                }
                else if (boardGrid[i][j]==256) {
                    makeTile(i,j,256,Constants2048.COLOR_256,
                                     Constants2048.COLOR_VALUE_LIGHT,Constants2048.TEXT_SIZE_MID);
                }
                else if (boardGrid[i][j]==512) {
                    makeTile(i,j,512,Constants2048.COLOR_512,
                                     Constants2048.COLOR_VALUE_LIGHT,Constants2048.TEXT_SIZE_MID);
                }
                else if (boardGrid[i][j]==1024) {
                    makeTile(i,j,1024,Constants2048.COLOR_1024,
                                     Constants2048.COLOR_VALUE_LIGHT,Constants2048.TEXT_SIZE_HIGH);
                }
                else if (boardGrid[i][j]==2048) {
                    makeTile(i,j,2048,Constants2048.COLOR_2048,
                                     Constants2048.COLOR_VALUE_LIGHT,Constants2048.TEXT_SIZE_HIGH);
                }  
                else {
                    makeTile(i,j,boardGrid[i][j],Constants2048.COLOR_OTHER,
                                     Constants2048.COLOR_VALUE_LIGHT,Constants2048.TEXT_SIZE_HIGH);
                }
            }
        }
    }
    // Helper method to update rectangles, makeTile method sets rectangle features
    // Parameters: sets position of rectangle, value, color of tile, and color and size of text
    private void makeTile(int x, int y, int number, Color tileColor,
                          Color textColor, int textSize) {
        // Create new rectangle
    	Rectangle thisTile = new Rectangle (100,100,tileColor);
        pane.add(thisTile,y,x+1);
        // create and add tile value 
        text= new Text();
        text.setText(Integer.toString(number));
        text.setFont(Font.font("Times New Roman", FontWeight.BOLD, textSize));
        text.setFill(textColor);
        // position value to center
        pane.setHalignment(text,HPos.CENTER);
        pane.setValignment(text,VPos.CENTER);
        // add text 
        pane.add(text,y,x+1);
    }

    private void clearTiles() {
	this.board = new Board(new Random(), 4);  
    }
  }
