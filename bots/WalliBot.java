package bots;

import game.BotInterface;
import game.Puck;

public class WalliBot implements BotInterface{
    private boolean isRed = false;
    private int i = 0;

    public WalliBot(int i) {
        this.i = i;
    }

    @Override
    public void setColor(boolean isRed) {
        this.isRed = isRed;
    }

    @Override
    public int move(Puck[][] gameBoard) {
        return i++;
        // return (int) (Math.random() * 7.0);
    }
    
}
