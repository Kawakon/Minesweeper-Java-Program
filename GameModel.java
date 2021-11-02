import java.util.Random;

/**
 * The class <b>GameModel</b> holds the model, the state of the systems. 
 * It stores the following information:
 * - the state of all the ``dots'' on the board (mined or not, clicked
 * or not, number of neighbooring mines...)
 * - the size of the board
 * - the number of steps since the last reset
 *
 * The model provides all of this informations to the other classes trough 
 *  appropriate Getters. 
 * The controller can also update the model through Setters.
 * Finally, the model is also in charge of initializing the game
 *
 */
public class GameModel {


     // ADD YOUR INSTANCE VARIABLES HERE
	 private java.util.Random generator = new java.util.Random();
	 private int heightOfGame;
	 private DotInfo[][] information;
	 private int numberOfMines;
	 private int numberOfSteps;
	 private int numberUncovered;
	 private int widthOfGame;

    /**
     * Constructor to initialize the model to a given size of board.
     * 
     * @param width
     *            the width of the board
     * 
     * @param heigth
     *            the heigth of the board
     * 
     * @param numberOfMines
     *            the number of mines to hide in the board
     */
    public GameModel(int width, int heigth, int numberOfMines) {
        
    
		widthOfGame = width;
		heightOfGame = heigth;
		information = new DotInfo[heightOfGame+2][widthOfGame+2];
		this.numberOfMines = numberOfMines;
		numberOfSteps = 0;
		numberUncovered = 0;
		int minesGiven = 0; 
		for (int i = 0; i < heightOfGame+2; i++){
			for (int j = 0; j < widthOfGame+2; j++){
				information[i][j] = new DotInfo(j,i); 
				if ((i == 0)||(i == heightOfGame+1)||(j == 0)||(j == widthOfGame+1)){
					information[i][j].uncover();
				}
			}
		}
		while (minesGiven < numberOfMines){
			int x = generator.nextInt((widthOfGame-1)+1) + 1;
			int y = generator.nextInt((heightOfGame-1)+1) + 1;
			if (!(information[y][x].isMined())){
				information[y][x].setMined();
				minesGiven++;
				information[y-1][x-1].setNeighbooringMines(1);
				information[y][x-1].setNeighbooringMines(1);
				information[y+1][x-1].setNeighbooringMines(1);
				information[y-1][x].setNeighbooringMines(1);
				information[y+1][x].setNeighbooringMines(1);
				information[y-1][x+1].setNeighbooringMines(1);
				information[y][x+1].setNeighbooringMines(1);
				information[y+1][x+1].setNeighbooringMines(1);
			} else {
				information[y][x].setMined();
			}
		}
    }


 
    /**
     * Resets the model to (re)start a game. The previous game (if there is one)
     * is cleared up . 
     */
    public void reset(){

        
    
		information = new DotInfo[heightOfGame+2][widthOfGame+2]; 
		for (int i = 0; i < heightOfGame+2; i++){
			for (int j = 0; j < widthOfGame+2; j++){
				information[i][j] = new DotInfo(j,i); 
				if ((i == 0)||(i == heightOfGame+1)||(j == 0)||(j == widthOfGame+1)){
					information[i][j].uncover();
				}
			}
		}
		numberOfSteps = 0;
		numberUncovered = 0;
		int minesGiven = 0;
		while (minesGiven < numberOfMines){
			int x = generator.nextInt((widthOfGame-1)+1) + 1;
			int y = generator.nextInt((heightOfGame-1)+1) + 1;
			if (!(information[y][x].isMined())){
				information[y][x].setMined();
				minesGiven++;
				information[y-1][x-1].setNeighbooringMines(1);
				information[y][x-1].setNeighbooringMines(1);
				information[y+1][x-1].setNeighbooringMines(1);
				information[y-1][x].setNeighbooringMines(1);
				information[y+1][x].setNeighbooringMines(1);
				information[y-1][x+1].setNeighbooringMines(1);
				information[y][x+1].setNeighbooringMines(1);
				information[y+1][x+1].setNeighbooringMines(1);
			} else {
				information[y][x].setMined();
			}
		}
    }


    /**
     * Getter method for the heigth of the game
     * 
     * @return the value of the attribute heigthOfGame
     */   
    public int getHeigth(){
        
    
		return heightOfGame;
    }

    /**
     * Getter method for the width of the game
     * 
     * @return the value of the attribute widthOfGame
     */   
    public int getWidth(){
        
    
		return widthOfGame;
    }



    /**
     * returns true if the dot at location (i,j) is mined, false otherwise
    * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */   
    public boolean isMined(int i, int j){
        
    
		return information[i][j].isMined();
    }

    /**
     * returns true if the dot  at location (i,j) has 
     * been clicked, false otherwise
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */   
    public boolean hasBeenClicked(int i, int j){
        
    
		return information[i][j].hasBeenClicked();
    }

  /**
     * returns true if the dot  at location (i,j) has zero mined 
     * neighboor, false otherwise
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */   
    public boolean isBlank(int i, int j){
        
    
		if (information[i][j].getNeighbooringMines() == 0){
			return true;
		} else {
			return false;
		}
    }
    /**
     * returns true if the dot is covered, false otherwise
    * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */   
    public boolean isCovered(int i, int j){
        
    
		return information[i][j].isCovered();

    }

    /**
     * returns the number of neighbooring mines os the dot  
     * at location (i,j)
     *
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the number of neighbooring mines at location (i,j)
     */   
    public int getNeighbooringMines(int i, int j){
        
    
		return information[i][j].getNeighbooringMines();
    }


    /**
     * Sets the status of the dot at location (i,j) to uncovered
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     */   
    public void uncover(int i, int j){
        
    
		information[i][j].uncover();
		numberUncovered++;
    }

    /**
     * Sets the status of the dot at location (i,j) to clicked
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     */   
    public void click(int i, int j){
        
    
		information[i][j].click();
    }
     /**
     * Uncover all remaining covered dot
     */   
    public void uncoverAll(){
        
    
		for (int i = 1; i <= heightOfGame; i++){
			for (int j = 1; j <= widthOfGame; j++){
					information[i][j].uncover();
			}
		}
    }

 

    /**
     * Getter method for the current number of steps
     * 
     * @return the current number of steps
     */   
    public int getNumberOfSteps(){
        
    
		return numberOfSteps;
    }

  

    /**
     * Getter method for the model's dotInfo reference
     * at location (i,j)
     *
      * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     *
     * @return model[i][j]
     */   
    public DotInfo get(int i, int j) {
        
    
		return information[i][j];
    }


   /**
     * The metod <b>step</b> updates the number of steps. It must be called 
     * once the model has been updated after the payer selected a new square.
     */
     public void step(){
        
    
		numberOfSteps++;
    }
 
   /**
     * The metod <b>isFinished</b> returns true iff the game is finished, that
     * is, all the nonmined dots are uncovered.
     *
     * @return true if the game is finished, false otherwise
     */
    public boolean isFinished(){
        
    
		for (int i = 1; i <= heightOfGame; i++){
			for (int j = 1; j <= widthOfGame; j++){
				if (!(information[i][j].isMined())&&(information[i][j].isCovered())){
					return false;
				}
			}
		} return true;
    }


   /**
     * Builds a String representation of the model
     *
     * @return String representation of the model
     */
    public String toString(){
        
    
		return "Width of board: "+getWidth()+"\nHeight of Board: "+getHeigth()+"\nNumber of Mines: "+numberOfMines+"\nNumber of Steps: "+getNumberOfSteps();
    }
}
