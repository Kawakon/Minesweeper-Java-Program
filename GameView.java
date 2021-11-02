import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * The class <b>GameView</b> provides the current view of the entire Game. It extends
 * <b>JFrame</b> and lays out a matrix of <b>DotButton</b> (the actual game) and 
 * two instances of JButton. The action listener for the buttons is the controller.
 *
 */

public class GameView extends JFrame {

     // ADD YOUR INSTANCE VARIABLES HERE
		private JLabel nbreOfStepsLabel;
		private GameModel gameModel;
		private DotButton[][] board;
    /**
     * Constructor used for initializing the Frame
     * 
     * @param gameModel
     *            the model of the game (already initialized)
     * @param gameController
     *            the controller
     */

    public GameView(GameModel gameModel, GameController gameController) {
        
    
		setTitle("Minesweeper");
		GridBagLayout grid = new GridBagLayout();
		GridBagConstraints k = new GridBagConstraints();
		setLayout(grid);
		this.gameModel = gameModel;
		
		
		JPanel mines = new JPanel();
		int width = gameModel.getWidth();
		int height = gameModel.getHeigth();
		mines.setLayout(new GridLayout(height, width));
		board = new DotButton[height][width];
		for (int i = 0; i < height; i++){
			for (int j = 0; j < width; j++){
				board[i][j] = new DotButton(j+1, i+1, DotButton.COVERED);
				board[i][j].addActionListener(gameController);
				board[i][j].setBorder(null);
				mines.add(board[i][j]);
			}
		}
		k.insets = new Insets(5,5,5,5);
		k.gridx = 3;
		k.gridy = 2;
		k.anchor = GridBagConstraints.CENTER;
		k.weightx = 1;
		add(mines, k);
		
		
		nbreOfStepsLabel = new JLabel();
		k.insets = new Insets(5,5,5,5); 
		k.anchor = GridBagConstraints.PAGE_END;	
		//k.weightx = 1;
		k.gridx = 2;
		k.gridy = 3;
		nbreOfStepsLabel.setText("Number of steps: "+gameModel.getNumberOfSteps());
		add(nbreOfStepsLabel, k);
		JButton reset = new JButton("Reset");
		reset.addActionListener(gameController);
		k.gridx = 3;
		add(reset, k);
		JButton quit = new JButton("Quit");
		quit.addActionListener(gameController);
		k.gridx = 4;
		k.weightx = 1;
		k.insets = new Insets(5,40,5,5); 
		add(quit, k);
		
		pack();
		setVisible(true);
		setResizable(false);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * update the status of the board's DotButton instances based 
     * on the current game model, then redraws the view
     */

    public void update(){
        
    
		nbreOfStepsLabel.setText("Number of steps: "+gameModel.getNumberOfSteps());
		int width = gameModel.getWidth();
		int height = gameModel.getHeigth();
		for (int i = 0; i < height; i++){
			for (int j = 0; j < width; j++){
				if (!(gameModel.isCovered(board[i][j].getRow(), board[i][j].getColumn()))){
					if ((gameModel.isMined(board[i][j].getRow(), board[i][j].getColumn())) && (gameModel.hasBeenClicked(board[i][j].getRow(), board[i][j].getColumn()))){
						board[i][j].setIconNumber(DotButton.CLICKED_MINE);
					} else if (gameModel.isMined(board[i][j].getRow(), board[i][j].getColumn())){
						board[i][j].setIconNumber(DotButton.MINED);
					} else {
						board[i][j].setIconNumber(getIcon(board[i][j].getRow(), board[i][j].getColumn()));
					}
				} else {
					board[i][j].setIconNumber(DotButton.COVERED);
				}
			}
		}	
    }

    /**
     * returns the icon value that must be used for a given dot 
     * in the game
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the icon to use for the dot at location (i,j)
     */   
    private int getIcon(int i, int j){
        
    
		return gameModel.getNeighbooringMines(i,j);
    }


}
