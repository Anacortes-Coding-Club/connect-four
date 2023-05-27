package bots;

import game.BotInterface;
import game.Puck;

public class WalliBot implements BotInterface{

    /**
     * Creates a WalliBot object.
     */
    public WalliBot() {}

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
        return (int) (Math.random() * 7.0);
    }
}