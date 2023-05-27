package bots;

import java.awt.Color;

import game.*;
import game.VisualHelper.Renderer;

public class WalliBot3 implements BotInterface{
    private boolean isRed = false;
    private Puck[][] boardCopy;

    /**
     * Creates a WalliBot3 object.
     */
    public WalliBot3() {
    }

    /**
     * Sets the piece color the bot is playing. Method will be called at the start of each game.
     * @param isRed     playing red if true, playing black if false.
     */
    public void setColor(boolean isRed) {
        this.isRed = isRed;
    }

    /**
     * Returns the column index of the bot's next move based off the current gameBoard.
     * @param gameBoard     a copy of the most recent gameBoard, represented as a 2D array of Puck objects. (null if empty.)
     * @return              Returns the column index you want to drop your next puck.
     */
    public int takeTurn(Puck[][] gameBoard) {
        boardCopy = getClone(gameBoard);
        int win = checkForWin(boardCopy, isRed);
        if(win != -1) return win;

        int lose = checkForWin(boardCopy, !isRed);
        if(lose != -1) return lose;

        int random = (int) (Math.random() * 7.0);
        if(isValid(boardCopy, random)) return random;

        for(int i = 0; i < gameBoard.length; i++) {
            if(isValid(boardCopy, i)) return i;
        }

        return (int) (Math.random() * 7.0);
    }

    /**
     * Returns the column index of a winning move.
     * @return      -1 if no win, or the column index of a winning move.
     */
    private static int checkForWin(Puck[][] gameBoard, boolean isRed) {
        Puck[][] tempBoard = getClone(gameBoard);
        for (int i = 0; i < tempBoard[0].length; i++) {
            if(colWins(tempBoard, i, isRed)) return i;
        }
        return -1;
    }

    /**
     * Tests if a puck placement wins.
     * @param column    column placement to test for win in.
     * @param isRed     put true if puck color to test is red.
     * @return          false if placement doesn't win, true if play wins.
     */
    private static boolean colWins(Puck[][] gameBoard, int column, boolean isRed) {
        Puck[][] tempBoard = getClone(gameBoard);
        int rowPlaced = deepestOpen(tempBoard, column);
        if(rowPlaced == -1) return false;

        if(placePuck(tempBoard, column, isRed)) return false;
        int maxRIndex = tempBoard.length-1;
        int maxCIndex = tempBoard[0].length-1;

        int inARow = 0;
        for (int r = Math.max(rowPlaced-3, 0); r <= Math.min(rowPlaced+3, maxRIndex); r++) {
            if(isColorPuck(tempBoard[r][column], isRed)) {
                if(inARow++ >= 3) return true;
            } else {
                inARow = 0;
            }
        }

        inARow = 0;
        for (int c = Math.max(column-3, 0); c <= Math.min(column+3, maxCIndex); c++) {
            if(isColorPuck(tempBoard[rowPlaced][c], isRed)) {
                if(inARow++ >= 3) return true;
            } else {
                inARow = 0;
            }
        }

        inARow = 0;
        for (int i = -3; i <= 3; i++) {
            if(rowPlaced+i >= 0 && column+i >= 0 && rowPlaced+i <= maxRIndex && column+i <= maxCIndex) {
                if(isColorPuck(tempBoard[rowPlaced+i][column+i], isRed)) {
                    if(inARow++ >= 3) return true;
                } else {
                    inARow = 0;
                }
            }
        }

        inARow = 0;
        for (int i = -3; i <= 3; i++) {
            if(rowPlaced-i >= 0 && column+i >= 0 && rowPlaced-i <= maxRIndex && column+i <= maxCIndex) {
                if(isColorPuck(tempBoard[rowPlaced-i][column+i], isRed)) {
                    if(inARow++ >= 3) return true;
                } else {
                    inARow = 0;
                }
            }
        }

        return false;
    }

    /**
     * Returns the deepest open row's index of gameboard's column.
     * @param gameBoard     gameBoard to test
     * @param column        column to test
     * @return              returns -1 if column is full, index of deepest open slot otherwise.
     */
    private static int deepestOpen(Puck[][] gameBoard, int column) {
        for (int i = gameBoard.length-1; i >= 0; i--) {
            if(gameBoard[i][column] == null) return i;
        }
        return -1;
    }

    /**
     * Places a puck object of specified color in a specified column, checking for legality.
     * @param column    column to place puck in.
     * @param isRed     places a red puck if true, black if false.
     * @return          returns true if unsuccessful placing puck, false if successful.
     */
    private static boolean placePuck(Puck[][] gameBoard, int column, boolean isRed) {
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

    /**
     * returns true if a puck object is a particular color.
     * @param puck      puck object.
     * @param checkRed  if true, checks for if it is red; if false, checks if it is black.
     * @return          returns true if puck exists and is the specified color.
     */
    private static boolean isColorPuck(Puck puck, boolean checkRed) {
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
     * Clones array.
     * @param array     array of puck objects.
     * @return          clone of array.
     */
    private static Puck[][] getClone(Puck[][] array) {
        Puck[][] clone = new Puck[array.length][array[0].length];
        for(int i = 0; i < clone.length; i++) {
            for(int j = 0; j < clone[0].length; j++) {
                clone[i][j] = array[i][j];
            }
        }
        return clone;
    }

    public static void main(String[] args) {
        WalliBot3 test = new WalliBot3();
        Puck[][] board = new Puck[6][7];

        WalliBot3.placePuck(board, 0, false);
        WalliBot3.placePuck(board, 0, false);
        WalliBot3.placePuck(board, 0, false);
        WalliBot3.placePuck(board, 0, true);
        WalliBot3.placePuck(board, 1, false);
        WalliBot3.placePuck(board, 1, false);
        WalliBot3.placePuck(board, 1, true);
        WalliBot3.placePuck(board, 2, false);
        WalliBot3.placePuck(board, 2, true);
        System.out.println(checkForWin(board, true));

        VisualInterface rend = new Renderer(Color.GRAY);
        rend.updateVisuals(board);

    }
}