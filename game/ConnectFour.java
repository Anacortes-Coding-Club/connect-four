package game;

import game.VisualHelper.*;

import java.awt.Color;
import java.time.format.FormatStyle;

import bots.*;

public class ConnectFour {
    Puck[][] gameBoard = new Puck[6][7];
    BotInterface player1, player2;
    int wins; //0 if equal wins, +1 for each player1 win, 
    VisualInterface rend;
    int delayMilliseconds = 0;
    boolean runInterface = true;

    /**
     * Creates ConnectFour match with 2 bots/players.
     * @param player1   bot/player class.
     * @param player2   bot/player class.
     */
    public ConnectFour(BotInterface player1, BotInterface player2) {
        this.player1 = player1;
        this.player2 = player2;
        rend = new Renderer(Color.GRAY);
        updateVisuals();
    }

    /**
     * Creates ConnectFour match with 2 bots/players with the option to disable the interface.
     * @param player1   bot/player class.
     * @param player2   bot/player class.
     */
    public ConnectFour(BotInterface player1, BotInterface player2, boolean runInterface) {
        this.player1 = player1;
        this.player2 = player2;
        this.runInterface = runInterface;
    }

    /**
     * Creates ConnectFour game with 2 bots/players with a delay between turns
     * @param player1   bot/player class.
     * @param player2   bot/player class.
     * @param delayMilliseconds     delay between turns in milliseconds.
     */
    public ConnectFour(BotInterface player1, BotInterface player2, int delayMilliseconds) {
        this.player1 = player1;
        this.player2 = player2;
        this.delayMilliseconds = delayMilliseconds;
        rend = new Renderer(Color.GRAY);
        updateVisuals();
    }

    /**
     * Runs two games, each with a different bot starting.
     */
    public void runGames() {
        runGame(true);
        runGame(false);
    }

    /**
     * Runs a ConnectFour game, specifying bot/player colors.
     * @param p1IsRed   true if player 1 is red, false if black.
     */
    public void runGame(boolean p1IsRed) {
        player1.setColor(p1IsRed);
        player2.setColor(!p1IsRed);

        boolean loop = true;
        while(loop) {
            loop = takeTurn(player1, p1IsRed);

            try {
                Thread.sleep(delayMilliseconds);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            if(loop) loop = takeTurn(player2, !p1IsRed);
            // System.out.println(wins);

            try {
                Thread.sleep(delayMilliseconds);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * Has the player object take a turn, checking for invalid/winning moves & updating visuals.
     * @param player    bot/player object which will take a turn.
     * @param isRed     the color of the bot/player object.
     * @return          returns true if the game should continue running.
     */
    private boolean takeTurn(BotInterface player, boolean isRed) {
        if(placePuck(player.takeTurn(gameBoard), isRed)) {
            if(isRed) {
                wins--;
                System.out.println("black wins, invalid move");
            } else {
                wins++;
                System.out.println("red wins, invalid move");
            }
            return false;
        }
        updateVisuals();
        if(checkForWin(isRed)) {
            if(isRed) {
                wins++;
                System.out.println("red wins, 4 in a row!");
            } else {
                wins--;
                System.out.println("black wins, 4 in a row!");
            }
            return false;
        }
        return true;
    }

    /**
     * Checks if a player won the game.
     * @param checkRed  if true, red is checked for a win. If false, black is checked.
     * @return          returns true if color checked won, false otherwise.
     */
    private boolean checkForWin(boolean checkRed) {
        for (int r = 0; r < 6; r++) {
            int inARow = 0;
            for (int c = 0; c < 7; c++) {
                if(isColorPuck(gameBoard[r][c], checkRed)) {
                    if(inARow++ >= 3) return true;
                } else {
                    inARow = 0;
                }
            }
        }

        for (int c = 0; c < 7; c++) {
            int inARow = 0;
            for (int r = 0; r < 6; r++) {
                if(isColorPuck(gameBoard[r][c], checkRed)) {
                    if(inARow++ >= 3) return true;
                } else {
                    inARow = 0;
                }
            }
        }

        for (int c = -2; c < 4; c++) {
            int inARow = 0;
            int start = 0;
            if(c < 0) start -= c;
            for (int r = start; r < 6 && c+r < 7; r++) {
                if(isColorPuck(gameBoard[r][c+r], checkRed)) {
                    if(inARow++ >= 3) return true;
                } else {
                    inARow = 0;
                }
            }
        }

        for (int c = 3; c < 9; c++) {
            int inARow = 0;
            int start = 0;
            if(c >= 7) start += c-6;
            for (int r = start; r < 6 && c-r >= 0; r++) {
                if(isColorPuck(gameBoard[r][c-r], checkRed)) {
                    if(inARow++ >= 3) return true;
                } else {
                    inARow = 0;
                }
            }
        }

        return false;
    }

    /**
     * returns true if a puck object is a particular color.
     * @param puck      puck object.
     * @param checkRed  if true, checks for if it is red; if false, checks if it is black.
     * @return          returns true if puck exists and is the specified color.
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
     * Places a puck object of specified color in a specified column, checking for legality.
     * @param column    column to place puck in.
     * @param isRed     places a red puck if true, black if false.
     * @return          returns true if unsuccessful placing puck, false if successful.
     */
    private boolean placePuck(int column, boolean isRed) {
        if(column < 0 || column >= gameBoard[0].length) return true;
        for(int r = gameBoard.length-1; r >= 0; r--) {
            if(gameBoard[r][column] == null) {
                gameBoard[r][column] = new Puck(isRed);
                return false;
            }
        }
        return true;
    }

    /**
     * Updates the game visuals.
     */
    public void updateVisuals() {
        if(runInterface) rend.updateVisuals(gameBoard);
    }

    public static void main(String[] args) {
        ConnectFour match = new ConnectFour(new WalliBot(), new WalliBot2(0), 500);
        // ConnectFour match = new ConnectFour(new WalliBot2(0), new WalliBot(), false);
        match.runGame(true);

    }
}
