package bots;

import game.BotInterface;
import game.ConnectFour;
import game.Puck;

public class LogansMiniMaxBot extends BotInterface
{
    private boolean isRed;

    public LogansMiniMaxBot()
    {

    }

    public void setColor(boolean isRed) {
        this.isRed = isRed;
    }

    public int takeTurn(Puck[][] gameBoard)
    {
        
        // int numOfWaysToPlay = 0;

        // for(int i = 0; i < gameBoard[0].length; i++)
        // {
        //     if(gameBoard[gameBoard.length - 1][i] != null)
        //     {
        //         numOfWaysToPlay++;
        //     }
        // }

        

        return (int) (Math.random() * 7.0);
    }

    private int miniMax(Puck[][] boardState, int depth, bool maximizingPlayer)
    {
        boolean full = true;
        for (Puck[] pucks : boardState) {
            for (Puck puck : pucks) {
                if(puck != null)
                {
                    full = false;
                }
            }
        }
        if(depth == 0 || full || checkForWin(boardState, isRed))
        {
            int val = 0;
            if(checkForWin(boardState, isRed))
                val += 5;
            if(checkForWin(boardState, !isRed))
                val -= 5;
            
            return val;
        }

        if(maximizingPlayer)
        {
            int maxEval = Integer.MIN_VALUE;

            for(int i = 0; i < gameBoard[0].length; i++)
            {
                if(gameBoard[gameBoard.length - 1][i] != null)
                {
                    placePuck(gameBoard, i, isRed);
                    int eval = miniMax(boardState, depth - 1, false);

                    maxEval = Math.max(maxEval, eval);
                }
            }

            return maxEval;
        }
        else
        {
            int minEval = Integer.MAX_VALUE;

            for(int i = 0; i < gameBoard[0].length; i++)
            {
                if(gameBoard[gameBoard.length - 1][i] != null)
                {
                    placePuck(gameBoard, i, !isRed);
                    int eval = miniMax(boardState, depth - 1, true);

                    minEval = Math.min(minEval, eval);
                }
            }

            return minEval;
        }

    }

    private boolean placePuck(Puck[][] board, int column, boolean isRed) {
        if(column < 0 || column >= board[0].length) return true;
        for(int r = board.length-1; r >= 0; r--) {
            if(board[r][column] == null) {
                board[r][column] = new Puck(isRed);
                return false;
            }
        }
        return true;
    }

    private boolean checkForWin(Puck[][] board, boolean checkRed) {
        for (int r = 0; r < 6; r++) {
            int inARow = 0;
            for (int c = 0; c < 7; c++) {
                if(isColorPuck(board[r][c], checkRed)) {
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
                if(isColorPuck(board[r][c], checkRed)) {
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
                if(isColorPuck(board[r][c+r], checkRed)) {
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
                if(isColorPuck(board[r][c-r], checkRed)) {
                    inARow++;
                } else {
                    inARow = 0;
                }
                if(inARow >= 4) return true;
            }
        }

        return false;
    }

    private boolean isColorPuck(Puck puck, boolean checkRed)
    {
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
}