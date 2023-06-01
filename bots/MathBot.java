package bots;

import game.BotInterface;
import game.Puck;

public class MathBot implements BotInterface{
    private boolean isRed = true;
        /**
     * Sets the piece color the bot is playing. Method will be called at the start of each game.
     * @param isRed     playing red if true, playing black if false.
     */
    public void setColor(boolean isRed){
        if (isRed = true){
            this.isRed = isRed;
        }
        else {
            this.isRed = isRed;
        }
    }

    /**
     * Returns the column index of the bot's next move based off the current gameBoard.
     * @param gameBoard     a copy of the most recent gameBoard, represented as a 2D array of Puck objects. (null if empty.)
     * @return              Returns the column index you want to drop your next puck
     */
    public int takeTurn(Puck[][] gameBoard){
        //if(column < 0 || column >= gameBoard[0].length) return 0;
        for(int r = gameBoard.length-1; r >= 0; r--) {
            
            if(gameBoard[5][3] == null) {
                gameBoard[5][3] = new Puck(isRed);
                return 3;
            }
            if(gameBoard[5][3] != null && gameBoard[4][3] == null){
                gameBoard[4][3] = new Puck(isRed);
                return 3;
            }
           //If code goes past this point, both 3.5 and 3.4 are taken.
            if(gameBoard[5][3] != null && gameBoard[4][3] != null && gameBoard[5][2] == null){
                gameBoard[5][2] = new Puck(isRed);
                return 2;
            }
            if(gameBoard[5][3] != null && gameBoard[4][3] != null && gameBoard[5][2] != null && gameBoard[4][2] == null){
                gameBoard[4][2] = new Puck(isRed);
                return 2;
            }
            if(gameBoard[5][3] != null && gameBoard[4][3] != null && gameBoard[5][4] == null && gameBoard[4][2] != null && gameBoard[4][4] == null){
                gameBoard[4][4] = new Puck(isRed);
                return 4;
            }
        }
    return (int) (Math.random() * 7.0);
    }
}