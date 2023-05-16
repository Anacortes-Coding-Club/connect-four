package game;

public interface BotInterface {

    /**
     * Sets the piece color of bot is playing. Method will be run at the start of each game.
     * @param isRed     playing red if true, playing black if false.
     */
    public void setColor(boolean isRed);

    /**
     * 
     * @param gameBoard
     * @return              Returns the column index you want to drop your next puck
     */
    public int move(Puck[][] gameBoard);
}
