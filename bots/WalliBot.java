package bots;

import game.BotInterface;
import game.Puck;

public class WalliBot implements BotInterface{
    private boolean isRed = false;

    public WalliBot() {
    }

    public void setColor(boolean isRed) {
        this.isRed = isRed;
    }

    public int takeTurn(Puck[][] gameBoard) {
        return (int) (Math.random() * 7.0);
    }
    
}