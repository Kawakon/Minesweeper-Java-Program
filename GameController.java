import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.*;


/**
 * The class <b>GameController</b> is the controller of the game. It is a listener
 * of the view, and has a method <b>play</b> which computes the next
 * step of the game, and  updates model and view.
 *
 */


public class GameController implements ActionListener {

    // ADD YOUR INSTANCE VARIABLES HERE
	private GameModel gameModel;
	private GameView gameView;

    /**
     * Constructor used for initializing the controller. It creates the game's view 
     * and the game's model instances
     * 
     * @param width
     *            the width of the board on which the game will be played
     * @param heigth
     *            the heigth of the board on which the game will be played
     * @param numberOfMines
     *            the number of mines hidden in the board
     */
    public GameController(int width, int heigth, int numberOfMines) {

    
		gameModel = new GameModel(width, heigth, numberOfMines);
		gameView = new GameView(gameModel, this);
    }


    /**
     * Callback used when the user clicks a button (reset or quit)
     *
     * @param e
     *            the ActionEvent
     */

    public void actionPerformed(ActionEvent e) {
        
    
		if (e.getActionCommand().equals("Reset")){
			reset();
			gameView.update();
		}
		else if (e.getActionCommand().equals("Quit")){
			System.exit(0);
		}
		else if (e.getSource() instanceof DotButton){
			DotButton button;
			button = (DotButton) e.getSource();
			int r = button.getRow();
			int c = button.getColumn();
			play(r,c);
		}
    }

    /**
     * resets the game
     */
    private void reset(){

    
		gameModel.reset();
    }

    /**
     * <b>play</b> is the method called when the user clicks on a square.
     * If that square is not already clicked, then it applies the logic
     * of the game to uncover that square, and possibly end the game if
     * that square was mined, or possibly uncover some other squares. 
     * It then checks if the game
     * is finished, and if so, congratulates the player, showing the number of
     * moves, and gives to options: start a new game, or exit
     * @param width
     *            the selected column
     * @param heigth
     *            the selected line
     */
    private void play(int width, int heigth){

    
		String[] options = {"Try Again!","Quit"};
		if (!(gameModel.isMined(width, heigth)) && !(gameModel.hasBeenClicked(width, heigth)) && (gameModel.isCovered(width, heigth))){
			System.out.println(gameModel.toString()+"\nClicking: "+heigth +" "+width);
			gameView.update();
			gameModel.step();
			gameModel.click(width, heigth);
			gameModel.uncover(width, heigth);
			if (gameModel.getNeighbooringMines(width, heigth) == 0){
				clearZone(gameModel.get(width, heigth));
				gameView.update();
			}
			gameModel.uncover(width, heigth);
			if (gameModel.isFinished()){
				gameModel.uncoverAll();
				gameView.update();
				int a = JOptionPane.showOptionDialog(new JFrame(), 
						"Yay! You won the game in "+gameModel.getNumberOfSteps()+" steps. Would you like to play again?", 
						"Winner!", 
						JOptionPane.YES_NO_OPTION,
						JOptionPane.PLAIN_MESSAGE,
						null, options, options[0]);
				if (a == 0){
					reset();
					gameView.update();
				} else {
					System.exit(0);
				}
			}
		} else if (gameModel.isMined(width, heigth)){
			gameModel.click(width, heigth);
			gameModel.step();
			gameModel.uncoverAll();
			gameView.update();
			int p = JOptionPane.showOptionDialog(new JFrame(), 
					"Oh no! You lost in "+gameModel.getNumberOfSteps()+" steps. Would you like to play again?", 
					"BOOM!", 
					JOptionPane.YES_NO_OPTION,
					JOptionPane.PLAIN_MESSAGE,
					null, options, options[0]);
			if (p == 0){
				reset();
				gameView.update();
			} else {
				System.exit(0);
			}
		}
		gameView.update();
    }

   /**
     * <b>clearZone</b> is the method that computes which new dots should be ``uncovered'' 
     * when a new square with no mine in its neighborood has been selected
     * @param initialDot
     *      the DotInfo object corresponding to the selected DotButton that
     * had zero neighbouring mines
     */
    private void clearZone(DotInfo initialDot) {


    
		Stack<DotInfo> dots;
		dots = new GenericArrayStack<DotInfo>((gameModel.getWidth())*(gameModel.getHeigth()));
		dots.push(initialDot);
		while (!(dots.isEmpty())){
			int initialX = dots.peek().getX();
			int initialY = dots.peek().getY();
			dots.pop();
			for (int i = initialY - 1; i <= initialY+ 1; i++){
				for (int j = initialX - 1; j <= initialX + 1; j++){
					if ((i == initialY)&&(j == initialX)){
						continue;
					} else {
						if ((gameModel.isCovered(i,j))){
							gameModel.uncover(i,j);
							if (gameModel.getNeighbooringMines(i,j) == 0){
								dots.push(gameModel.get(i,j));
							}
						}
					}
				}
			}
		}
    }
}
