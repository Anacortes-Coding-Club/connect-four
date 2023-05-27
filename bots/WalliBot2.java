package bots;

import game.BotInterface;
import game.Puck;

public class WalliBot2 implements BotInterface{
    private int i = 0;

    /**
     * Creates a WalliBot2 object starting placements at index i.
     * @param i
     */
    public WalliBot2(int i) {
        this.i = i;
    }

    /**
     * Sets the piece color the bot is playing. Method will be called at the start of each game.
     * @param isRed     playing red if true, playing black if false.
     */
    public void setColor(boolean isRed) {}

    /**
     * Returns the column index of the bot's next move based off the current gameBoard.
     * @param gameBoard     a copy of the most recent gameBoard, represented as a 2D array of Puck objects. (null if empty.)
     * @return              Returns the column index you want to drop your next puck
     */
    public int takeTurn(Puck[][] gameBoard) {
        int loops = 0;
        while(!isValid(gameBoard, i % 7) && loops < 7) {
            i++;
            loops++;
        }
        return i++ % 7;
    }
    
    /**
     * Returns true is column on gameBoard is a valid move
     * @param gameBoard
     * @param column
     * @return
     */
    private boolean isValid(Puck[][] gameBoard, int column) {
        if(gameBoard[0][column] == null) {
            return true;
        }
        return false;
    }
}