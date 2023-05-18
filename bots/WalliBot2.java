package bots;

import game.BotInterface;
import game.Puck;

public class WalliBot2 implements BotInterface{
    private boolean isRed = false;
    private int i = 0;

    public WalliBot2(int i) {
        this.i = i;
    }

    public void setColor(boolean isRed) {
        this.isRed = isRed;
    }

    public int move(Puck[][] gameBoard) {
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