package bots;

import java.util.ArrayList;
import java.util.List;

import game.BotInterface;
import game.Puck;

/**
 * A bot that tries to win, or block the opponent from winning, if possible.
 * Otherwise, it makes a random move (inspired by WalliBots)
 * 
 * @author natpeterson@gmail.com with ChatGPT ;-)
 */
public class TryHardBot implements BotInterface {

    private boolean isRed;
    private Puck[][] board;

    @Override
    public void setColor(boolean isRed) {
        this.isRed = isRed;
        System.out.println("TryHardBot is " + (isRed ? "red" : "black") + "!");
    }

    @Override
    public int takeTurn(Puck[][] gameBoard) {
        this.board = gameBoard;
        int move = decideMove();
        if (move == -1) {
            throw new UnsupportedOperationException("I give up!");
        }
        return move;
    }

    private Puck[][] copyBoard(Puck[][] gameBoard) {
        Puck[][] copy = new Puck[gameBoard.length][gameBoard[0].length];
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[0].length; j++) {
                copy[i][j] = gameBoard[i][j];
            }
        }
        return copy;
    }

    public int getLowestEmptyRow(Puck[][] tempGame, int col) {
        for (int row = tempGame.length - 1; row >= 0; row--) {
            if (tempGame[row][col] == null) {
                return row;
            }
        }
        return -1;
    }

    public int decideMove() {
        // Check if there is a winning move available
        for (int col = 0; col < board.length; col++) {
            Puck[][] tempGame = copyBoard(board);

            // Simulate dropping a disc in the column
            int row = getLowestEmptyRow(tempGame, col);
            if (row != -1) {
                tempGame[row][col] = new Puck(isRed);

                // Check if the move results in a win for the current player
                if (checkWin(tempGame, row, col)) {
                    return col; // Winning move found, return the column
                }
            }
        }

        // Check if there is a move to block the opponent from winning
        for (int col = 0; col < board.length; col++) {
            Puck[][] tempGame = copyBoard(board); // Create a copy of the game board

            // Simulate dropping a disc in the column
            int row = getLowestEmptyRow(tempGame, col);
            if (row != -1) {
                tempGame[row][col] = new Puck(!isRed);

                // Check if the move results in a win for the opponent
                if (checkWin(tempGame, row, col)) {
                    return col; // Blocking move found, return the column
                }
            }
        }

        // If no winning or blocking move, make a random move
        List<Integer> availableColumns = new ArrayList<>();
        for (int col = 0; col < board[0].length; col++) {
            if (board[0][col] == null) {
                availableColumns.add(col);
            }
        }
        if (!availableColumns.isEmpty()) {
            int randomIndex = (int) (Math.random() * availableColumns.size());
            return availableColumns.get(randomIndex);
        }

        // If all columns are full, return -1 indicating no valid move
        return -1;
    }

    public boolean checkWin(Puck[][] tempBoard, int row, int col) {

        boolean checkRed = tempBoard[row][col].isRed();

        // Check horizontally
        int count = 0;
        for (int j = 0; j < tempBoard[row].length; j++) {
            if (tempBoard[row][j] != null && tempBoard[row][j].isRed() == checkRed) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                count = 0;
            }
        }

        // Check vertically
        count = 0;
        for (int i = 0; i < tempBoard.length; i++) {
            if (tempBoard[i][col] != null && tempBoard[i][col].isRed() == checkRed) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                count = 0;
            }
        }

        // Check diagonally (from bottom-left to top-right)
        int startRow = row - Math.min(row, col);
        int startCol = col - Math.min(row, col);
        count = 0;
        while (startRow < tempBoard.length && startCol < tempBoard[0].length) {
            if (tempBoard[startRow][startCol] != null && tempBoard[startRow][startCol].isRed() == checkRed) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                count = 0;
            }
            startRow++;
            startCol++;
        }

        return false;

    }

}
