package game;

import game.VisualHelper.*;
import bots.WalliBot;

public class ConnectFour {
    Puck[][] gameBoard = new Puck[6][7];
    BotInterface player1, player2;
    int wins; //0 if equal wins, +1 for each player1 win, 
    VisualInterface rend = new Renderer();

    public ConnectFour(BotInterface player1, BotInterface player2) {
        this.player1 = player1;
        this.player2 = player2;
        updateVisuals();
    }

    public void runGames() {
        runGame(true);
        runGame(false);
    }

    public void runGame(boolean p1IsRed) {
        player1.setColor(p1IsRed);
        player2.setColor(!p1IsRed);

        while(true) {
            takeTurn(player1, p1IsRed);
            takeTurn(player2, !p1IsRed);
            System.out.println(wins);
        }
    }

    private void takeTurn(BotInterface player, boolean isRed) {
        if(placePuck(player.move(gameBoard), isRed)) {
            if(isRed) {
                wins--;
                System.out.println("black wins");
            } else {
                wins++;
                System.out.println("red wins");
            }
            return;
        }
        updateVisuals();
        if(checkForWin(isRed)) {
            if(isRed) {
                wins++;
                System.out.println("black wins");
            } else {
                wins--;
                System.out.println("red wins");
            }
            return;
        }
    }

    /**
     * 
     * @param checkRed
     * @return          returns true if color checked won, false otherwise.
     */
    private boolean checkForWin(boolean checkRed) {
        for (int r = 0; r < 6; r++) {
            int inARow = 0;
            for (int c = 0; c < 7; c++) {
                if(isColorPuck(gameBoard[r][c], checkRed)) {
                    inARow++;
                } else {
                    inARow = 0;
                }
                if(inARow >= 4) return true;
            }
        }

        for (int c = 0; c < 7; c++) {
            int inARow = 0;
            for (int r = 0; r < 6; r++) {
                if(isColorPuck(gameBoard[r][c], checkRed)) {
                    inARow++;
                } else {
                    inARow = 0;
                }
                if(inARow >= 4) return true;
            }
        }

        for (int c = -2; c < 4; c++) {
            int inARow = 0;
            int start = 0;
            if(c < 0) start -= c;
            for (int r = start; r < 6 && c+r < 7; r++) {
                if(isColorPuck(gameBoard[r][c+r], checkRed)) {
                    inARow++;
                } else {
                    inARow = 0;
                }
                if(inARow >= 4) return true;
            }
        }

        for (int c = 3; c < 9; c++) {
            int inARow = 0;
            int start = 0;
            if(c >= 7) start += c-6;
            for (int r = start; r < 6 && c-r >= 0; r++) {
                if(isColorPuck(gameBoard[r][c-r], checkRed)) {
                    inARow++;
                } else {
                    inARow = 0;
                }
                if(inARow >= 4) return true;
            }
        }

        return false;
    }

    /**
     * 
     * @param puck
     * @param checkRed
     * @return          returns true if puck exists and is the specified color
     */
    private boolean isColorPuck(Puck puck, boolean checkRed) {
        if(puck != null) {
            if(checkRed) {
                if(puck.isRed()) {
                    return true;
                }
            } else {
                if(!puck.isRed()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 
     * @param column
     * @param isRed
     * @return          returns true if unsuccessful placing puck, false if successful.
     */
    private boolean placePuck(int column, boolean isRed) {
        for(int r = gameBoard.length-1; r >= 0; r--) {
            if(gameBoard[r][column] == null) {
                gameBoard[r][column] = new Puck(isRed);
                return false;
            }
        }
        return true;
    }

    public void updateVisuals() {
        rend.updateVisuals(gameBoard);
    }

    public static void main(String[] args) {
        ConnectFour match = new ConnectFour(new WalliBot(0), new WalliBot(4));
        match.runGame(true);

    }
}
