// File Header: This file called Board.java lays out the foundations for creating 
//a 2048 game board. It contains the created board class, which has a 2-d number 
//array, the score of the game, size, probability of placing random tiles on the 
//board data contained within it. The tools (methods) used include the constructor 
//board which can read any file with board data specifications and create a new 
//board from that data, as well as initialize the current board to starting random 
// values, and addRandomTile, which is a tool that places two random number tiles 
//on the playing field. The file also contains tools which can save a current game, 
//check if a move is available, move a tile, and print a board. 

// class Header: Board classs contains a 2d int array with size GRID_SIZE, the 
//score of the current game, and a random object used to generate integers. 
//Methods include two constructors that initialize and read board files, as well
//as methods used to save the current status of the board, add tiles to the board 
//(initially), check if a move can be made, make a move into a free space, check 
//if the game is over, and also print out the current playing board. 

/**
 * Sample Board
 * <p/>
 * 0   1   2   3
 * 0   -   -   -   -
 * 1   -   -   -   -
 * 2   -   -   -   -
 * 3   -   -   -   -
 * <p/>
 * The sample board shows the index values for the columns and rows
 * Remember that you access a 2D array by first specifying the row
 * and then the column: grid[row][column]
 */

import java.util.*;
import java.io.*;

public class Board {
    public final int NUM_START_TILES = 2; 
    public final int TWO_PROBABILITY = 90;
    public final int GRID_SIZE;

    private final Random random; // a reference to the Random object, passed in 
                                 // as a parameter in Boards constructors
    private int[][] grid;  // a 2D int array, its size being boardSize*boardSize
    private int score;     // the current score, incremented as tiles merge 


    // This constructor method initializes the board data including size, score
    // It also places two random tiles on the board. Parameters are random varia
    // ble, and size of the board. 
    // Constructs a fresh board with random tiles
    public Board(Random random, int boardSize) {
    // initialize all the instance variables
        this.random = random; 
        this.GRID_SIZE = boardSize;
        this.grid=new int[boardSize][boardSize];
        this.score=0; 
        this.addRandomTile();
        this.addRandomTile();
    }

    // Reads a file containing data on board size, score, etc.
    // Then initializes variables in board class object into the ones in the fil
    // e.
    // Construct a board based off of an input file
    // assume board is valid
    public Board(Random random, String inputBoard) throws IOException {
    // create a new scanner that prints to the parameter file
        Scanner input=new Scanner(new File(inputBoard));
    // read the file in order of data and initialize instance variables
        this.GRID_SIZE=input.nextInt();
        this.score=input.nextInt();
        this.grid=new int[GRID_SIZE][GRID_SIZE];
        this.random =random;
        for (int i=0;i<this.grid.length;i++) {
            for (int j=0;j<this.grid.length;j++) {
                grid[i][j]=input.nextInt();
            }
        }
    }

    // Saves the current board to a file
    // This method creates a new file, then writes a board to it to save it. 
    public void saveBoard(String outputBoard) throws IOException {
        // create new file to save board data
        File out=new File(outputBoard);
        PrintWriter output=new PrintWriter(out);
        // write board data to new file
        output.print(this.GRID_SIZE);
        output.print("\n");
        output.print(this.score);
        //write the board itself to the file
        for (int i=0;i<this.GRID_SIZE;i++) {
            output.print("\n");
            for (int j=0;j<this.GRID_SIZE;j++) {
                output.print(this.grid[i][j]+" ");
            }
        }
        output.print("\n");
        output.close();
    }

    // Adds a random tile (of value 2 or 4) to a
    // random empty space on the board. First checks how many empty spaces there
    // are. 
    public void addRandomTile() {
        // count how many empty spaces exist on the board
        int count=0;
        for (int i=0;i<this.GRID_SIZE;i++) 
            for (int j=0;j<this.GRID_SIZE;j++) {
                if (this.grid[i][j]==0) {
                    count++;
                }
            }
        // if there are no empty spaces, simply return the original board
        if (count==0) {
            return;
        }
        // place two random tiles in random locations on the board, based on pr
        // obability, either a 2 or a 4
        int emptyCount=-1;
        int location=this.random.nextInt(count);
        int value=this.random.nextInt(100);
        for (int k=0;k<this.GRID_SIZE;k++) {
            for (int l=0;l<this.GRID_SIZE;l++) {
                if (this.grid[k][l]==0) {
                    emptyCount++;   
                }
                if (emptyCount==location) {
                    if (value<TWO_PROBABILITY) {
                        this.grid[k][l]=2;
                        return;
                    }
                    else {
                        this.grid[k][l]=4;
                        return;
                    }
                }
            }
        }
    }
    
    // determines whether the board can move in a certain direction
    // return true if such a move is possible
    public boolean canMove(Direction direction){
    // check if the input is UP, down left or right, execute accordingly
        if (direction==Direction.UP) {
           // check through the rows of the board, if a 0 exists above a nonzero, or if the values are            same return true
           for (int i=1;i<this.GRID_SIZE;i++) {
               for (int j=0;j<this.GRID_SIZE;j++) {
                   if (this.grid[i][j]!=0) {
                       if (this.grid[i-1][j]==this.grid[i][j]||this.grid[i-1][j]==0) { 
                           return true; 
                       }
                   }
               }
           }
           return false;
        }
        if (direction==Direction.DOWN) {
           // check through rows, if 0 exists below a non zero, or if the values are the same return tr
           // ue
           for (int i=0;i<this.GRID_SIZE-1;i++) {
               for (int j=0;j<this.GRID_SIZE;j++) {
                    if (this.grid[i][j]!=0) {
                        if (this.grid[i+1][j]==this.grid[i][j]||this.grid[i+1][j]==0) { 
                            return true; 
                        }
                    }
               }
           }
           return false;
        }
        // check through the columns, if a 0 exists left of a non zero or if the values are the same, re
        // turn true
        if (direction==Direction.LEFT) {
           for (int i=0;i<this.GRID_SIZE;i++) {
               for (int j=1;j<this.GRID_SIZE;j++) {
                    if (this.grid[i][j]!=0) {
                        if (this.grid[i][j-1]==this.grid[i][j]||this.grid[i][j-1]==0) { 
                            return true; 
                        }  
                    }
               }
           }
           return false;
        }
        // check through the columns, if a 0 exists right of a non zero or if the values are the same
        // return true
        if (direction==Direction.RIGHT) {
           for (int i=0;i<this.GRID_SIZE;i++) {
               for (int j=0;j<this.GRID_SIZE-1;j++) {
                    if (this.grid[i][j]!=0) {
                        if (this.grid[i][j+1]==this.grid[i][j]||this.grid[i][j+1]==0) { 
                            return true; 
                        }
                    }
               }
           }
           return false;
        }
        return false;
    }

    // move the board in a certain direction
    // return true if such a move is successful
    public boolean move(Direction direction) {
        // check if the direction input is up down left or right, execute accordingly
        if (direction==Direction.UP) {
            // check if a move can be made
            if (this.canMove(direction)) {
                // check through the entire grid, in each column
                for (int j=0;j<this.GRID_SIZE;j++) {     
                    for (int i=0;i<this.GRID_SIZE-1;i++) {
                        //  if a column has gaps with zero, fill in those gaps
                        if (this.grid[i][j]==0) {
			    for (int e=i;e<this.GRID_SIZE-1;e++) {
				int temp=this.grid[e][j];
				this.grid[e][j]=this.grid[e+1][j];
				this.grid[e+1][j]=temp;
			    }
                        }
		    }
		}
				// if two values are equal and non zero, combine them in the opposite direction
                // as the order of iteration
                // check through the entire grid, in each column
                for (int j=0;j<this.GRID_SIZE;j++) {     
                    for (int i=1;i<this.GRID_SIZE;i++) {
                           if ((this.grid[i][j]==this.grid[i-1][j])&&this.grid[i-1][j]!=0) {
                            int score=this.getScore();
                            this.score=score+this.grid[i][j]*2;
                            this.grid[i-1][j]*=2;
                            this.grid[i][j]=0;
                        }
                    }
		}
                        // for nxn grid, check through the grid n times and fill in all 0 gaps/complete all
                    // zero-nonzero shifts
                // check through the entire grid, in each column
                for (int k=0;k<this.GRID_SIZE;k++) {     
                    for (int h=0;h<this.GRID_SIZE-1;h++) {
                        //  if a column has gaps with zero, fill in those gaps
                        if (this.grid[h][k]==0) {
			    for (int f=h;f<this.GRID_SIZE-1;f++) {
				int temp2=this.grid[f][k];
				this.grid[f][k]=this.grid[f+1][k];
				this.grid[f+1][k]=temp2;
				
			    }
                        }
		    }
		}
 
		//return true if move is successful, else false
                return true;
            }
            return false;
	}
        if (direction==Direction.DOWN) {
            // check if the direction input is up down left or right, execute accordingly     
            if (this.canMove(direction)) {
                // check through the columns and rows
                for (int j=0;j<this.GRID_SIZE;j++) {
                    for (int i=this.GRID_SIZE-1;i>0;i--) {
                        // fill in the zero gaps between values and combine values if they are the same
                            if (this.grid[i][j]==0) {
				for (int e=i;e>0;e--) {
				    int temp=this.grid[e][j];
				    this.grid[e][j]=this.grid[e-1][j];
				    this.grid[e-1][j]=temp;
				}   
			    }
		    }
		}
                                     
      // if two values are equal and non zero, combine them in the opposite direction
                        // as the order of iteration
                for (int j=0;j<this.GRID_SIZE;j++) {
                    for (int i=this.GRID_SIZE-2;i>=0;i--) {
                        if (this.grid[i][j]==this.grid[i+1][j]&&this.grid[i][j]!=0) {
                            int score=this.getScore();
                            this.score=score+this.grid[i][j]*2;
                            this.grid[i+1][j]*=2;
                            this.grid[i][j]=0;
                        }
                    }
		}
                       // check through the columns and rows
                for (int k=0;k<this.GRID_SIZE;k++) {
                    for (int h=this.GRID_SIZE-1;h>0;h--) {
                        // fill in the zero gaps between values and combine values if they are the same
                            if (this.grid[h][k]==0) {
				for (int f=h;f>0;f--) {
				    int temp2=this.grid[f][k];
				    this.grid[f][k]=this.grid[f-1][k];
				    this.grid[f-1][k]=temp2;
				}   
			    }
		    }
		}
                             // return true if successful
                return true;
            }
            return false;
        }
        if (direction==Direction.LEFT) {
            // check if the board can move left
            if (this.canMove(direction)) {
                // check through the entire grid
                for (int i=0;i<this.GRID_SIZE;i++) {     
                    for (int j=0;j<this.GRID_SIZE-1;j++) {
                        // for each row, fill in the zero gaps and combine like values
                            if (this.grid[i][j]==0) {
				for (int e=j;e<this.GRID_SIZE-1;e++) {
				    int temp=this.grid[i][e];
				    this.grid[i][e]=this.grid[i][e+1];
				    this.grid[i][e+1]=temp;
				}
			    }
                    }
		}
                      // if two values are equal and non zero, combine them in the opposite direction
                        // as the order of iteration
                 for (int i=0;i<this.GRID_SIZE;i++) {     
                    for (int j=1;j<this.GRID_SIZE;j++) {
                        if (this.grid[i][j]==this.grid[i][j-1]&&this.grid[i][j]!=0) {
                            int score=this.getScore();
                            this.score=score+this.grid[i][j]*2;
                            this.grid[i][j-1]*=2;
                            this.grid[i][j]=0;
                        }
                    }
		 }
                // check through the entire grid
                for (int h=0;h<this.GRID_SIZE;h++) {     
                    for (int k=0;k<this.GRID_SIZE-1;k++) {
                        // for each row, fill in the zero gaps and combine like values
                            if (this.grid[h][k]==0) {
				for (int f=k;f<this.GRID_SIZE-1;f++) {
				    int temp2=this.grid[h][f];
				    this.grid[h][f]=this.grid[h][f+1];
				    this.grid[h][f+1]=temp2;
				}
			    }
                    }
		}

                // return true if successful
                return true;
            }
            return false;
        }
        if (direction==Direction.RIGHT) {
            // check if the board can move right
            if (this.canMove(direction)) {
                // iterate the entire board
                for (int i=0;i<this.GRID_SIZE;i++) {
                    for (int j=this.GRID_SIZE-1;j>0;j--) {
                        // for each row, fill in the zero gaps and combine like values
                            if (this.grid[i][j]==0) {
				for (int e=j;e>0;e--) {
				    int temp=this.grid[i][e];
				    this.grid[i][e]=this.grid[i][e-1];
				    this.grid[i][e-1]=temp;
				}   
			    }
		    }
		}
                                  
		// if two values are equal and non zero, combine them in the opposite direction
                        // as the order of iteration
                for (int i=0;i<this.GRID_SIZE;i++) {
                  for (int j=this.GRID_SIZE-2;j>=0;j--) {
                   if (this.grid[i][j]==this.grid[i][j+1]&&this.grid[i][j]!=0) {
                            int score=this.getScore();
                            this.score=score+this.grid[i][j]*2;
                            this.grid[i][j+1]*=2;
                            this.grid[i][j]=0;
                        }
                    }  
		}
		 // iterate the entire board
                for (int h=0;h<this.GRID_SIZE;h++) {
                    for (int k=this.GRID_SIZE-1;k>0;k--) {
                        // for each row, fill in the zero gaps and combine like values
                            if (this.grid[h][k]==0) {
				for (int f=k;f>0;f--) {
				    int temp2=this.grid[h][f];
				    this.grid[h][f]=this.grid[h][f-1];
				    this.grid[h][f-1]=temp2;
				}   
			    }
		    }
		}
		// return true if successful
                return true;
            }
            return false;
        }
        return false; 
    }

    // No need to change this for PSA3
    // Check to see if we have a game over
    public boolean isGameOver() {
        //if the board cannot be shifted in either direction, return false, else true
        if (this.canMove(Direction.UP)||this.canMove(Direction.DOWN) 
             ||this.canMove(Direction.LEFT)||this.canMove(Direction.RIGHT)) {
            return false;
        }
        return true;
    }

    // Return the reference to the 2048 Grid
    public int[][] getGrid() {
        return grid;
    }

    // Return the score
    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        StringBuilder outputString = new StringBuilder();
        outputString.append(String.format("Score: %d\n", score));
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int column = 0; column < GRID_SIZE; column++)
                outputString.append(grid[row][column] == 0 ? "    -" :
                        String.format("%5d", grid[row][column]));

            outputString.append("\n");
        }
        return outputString.toString();
    }
}
