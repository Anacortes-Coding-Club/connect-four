package bots;

import game.BotInterface;
import game.ConnectFour;
import game.Puck;

public class LogansMiniMaxBot implements BotInterface
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

        miniMax(gameBoard, 15, true);
        

        return (int) (Math.random() * 7.0);
    }

    private int miniMax(Puck[][] boardState, int depth, boolean maximizingPlayer)
    {
        boolean fullBoard = true;
        for (Puck[] pucks : boardState) 
        {
            for (Puck puck : pucks) 
            {
                if(puck != null)
                {
                    fullBoard = false;
                }
            }
        }
        if(depth == 0 || fullBoard || checkForWin(boardState, maximizingPlayer))
        {
            int val = 0;

            positionalEval(boardState, val);
            
            return val;
        }

        if(maximizingPlayer)
        {
            int maxEval = Integer.MIN_VALUE;

            for(int i = 0; i < boardState[0].length; i++)
            {
                if(boardState[boardState.length - 1][i] != null)
                {
                    placePuck(boardState, i, isRed);
                    int eval = miniMax(boardState, depth - 1, false);

                    maxEval = Math.max(maxEval, eval);
                }
            }

            return maxEval;
        }
        else
        {
            int minEval = Integer.MAX_VALUE;

            for(int i = 0; i < boardState[0].length; i++)
            {
                if(boardState[boardState.length - 1][i] != null)
                {
                    placePuck(boardState, i, !isRed);
                    int eval = miniMax(boardState, depth - 1, true);

                    minEval = Math.min(minEval, eval);
                }
            }

            return minEval;
        }

    }

    /**
     * Evaluates the state of a board.
     * @param boardState
     * @param val
     * 
     */
    private void positionalEval(Puck[][] boardState, int val)
    {
        for(int row = 0; row < boardState.length; row++)
            {
                for(int col = 0; col < boardState[row].length; col++)
                {
                    if(boardState[row][col] != null)
                    {
                        //cardinal directions
                        if(row+1 < boardState.length - 1)//check if direction has an open end
                            if(boardState[row+1][col] == null)
                            {
                                //entry point with open right
                                boolean isLineRed = boardState[row][col].isRed();
                                if(isRed) { if(boardState[row][col].isRed()) {val++;} else val--; } else { if(boardState[row][col].isRed()) {val--;} else {val++;} } //val add

                                if(row-1 > 0) //traversal 1
                                    if(boardState[row-1][col] != null)
                                        if(boardState[row-1][col].isRed() == isLineRed)
                                        {
                                            if(isRed) { if(boardState[row-1][col].isRed()) {val++;} else val--; } else { if(boardState[row-1][col].isRed()) {val--;} else {val++;} } //val add

                                            if(row-2 > 0) //traversal 2
                                                if(boardState[row - 2][col] != null)
                                                    if(boardState[row-2][col].isRed() == isLineRed)
                                                    {
                                                        if(isRed) { if(boardState[row-2][col].isRed()) {val++;} else val--; } else { if(boardState[row-2][col].isRed()) {val--;} else {val++;} } //val add
                                                    }
                                        }
                            }
                        
                        if(row-1 > 0)//check if direction has an open end
                            if(boardState[row-1][col] == null)
                            {
                                //entry point with open left
                                boolean isLineRed = boardState[row][col].isRed();
                                if(isRed) { if(boardState[row][col].isRed()) {val++;} else val--; } else { if(boardState[row][col].isRed()) {val--;} else {val++;} } //val add

                                if(row+1 < boardState.length - 1) //traversal 1
                                    if(boardState[row+1][col] != null)
                                        if(boardState[row+1][col].isRed() == isLineRed)
                                        {
                                            if(isRed) { if(boardState[row+1][col].isRed()) {val++;} else val--; } else { if(boardState[row+1][col].isRed()) {val--;} else {val++;} } //val add
                                            
                                            if(row+2 < boardState.length - 1) //traversal 2
                                                if(boardState[row+2][col] != null)
                                                    if(boardState[row+2][col].isRed() == isLineRed)
                                                    {
                                                        if(isRed) { if(boardState[row+2][col].isRed()) {val++;} else val--; } else { if(boardState[row+2][col].isRed()) {val--;} else {val++;} } //val add
                                                    }

                                        }
                            }


                        if(col-1 > 0)
                            if(boardState[row][col - 1] == null)
                            {
                                //entry point with open up
                                boolean isLineRed = boardState[row][col].isRed();
                                if(isRed) { if(boardState[row][col].isRed()) {val++;} else val--; } else { if(boardState[row][col].isRed()) {val--;} else {val++;} } //val add

                                if(col+1 < boardState[row].length - 1) //traversal 1
                                    if(boardState[row][col+1] != null)
                                        if(boardState[row][col+1].isRed() == isLineRed)
                                        {
                                            if(isRed) { if(boardState[row][col+1].isRed()) {val++;} else val--; } else { if(boardState[row][col+1].isRed()) {val--;} else {val++;} } //val add
                                            
                                            if(col+2 < boardState[row].length - 1) //traversal 2
                                                if(boardState[row][col+2] != null)
                                                    if(boardState[row][col+2].isRed() == isLineRed)
                                                    {
                                                        if(isRed) { if(boardState[row][col+2].isRed()) {val++;} else val--; } else { if(boardState[row][col+2].isRed()) {val--;} else {val++;} } //val add
                                                    }

                                        }
                            }

                        //diagonal directions
                        if(col-1 > 0 && row+1 < boardState.length - 1)
                            if(boardState[row + 1][col - 1] == null)
                            {
                                //entry point with open right and up
                                boolean isLineRed = boardState[row][col].isRed();
                                if(isRed) { if(boardState[row][col].isRed()) {val++;} else val--; } else { if(boardState[row][col].isRed()) {val--;} else {val++;} } //val add

                                if(col+1 < boardState[row].length && row-1 > 0) //traversal 1
                                if(boardState[row-1][col + 1] != null)
                                    if(boardState[row-1][col + 1].isRed() == isLineRed)
                                    {
                                        if(isRed) { if(boardState[row-1][col+1].isRed()) {val++;} else val--; } else { if(boardState[row-1][col+1].isRed()) {val--;} else {val++;} } //val add
                                        
                                        if(col+1 < boardState[row].length && row-1 > 0) //traversal 2
                                            if(boardState[row-2][col + 2] != null)
                                                if(boardState[row-2][col + 2].isRed() == isLineRed)
                                                {
                                                    if(isRed) { if(boardState[row-2][col+2].isRed()) {val++;} else val--; } else { if(boardState[row-2][col+2].isRed()) {val--;} else {val++;} } //val add
                                                }

                                    }
                            }

                        if(col-1 > 0 && row-1 > 0)
                            if(boardState[row-1][col-1] == null)
                            {
                                //entry point with open left and up
                                boolean isLineRed = boardState[row][col].isRed();
                                if(isRed) { if(boardState[row][col].isRed()) {val++;} else val--; } else { if(boardState[row][col].isRed()) {val--;} else {val++;} } //val add

                                if(col+1 < boardState[row].length && row+1 < boardState.length - 1) //traversal 1
                                if(boardState[row+1][col + 1] != null)
                                    if(boardState[row+1][col + 1].isRed() == isLineRed)
                                    {
                                        if(isRed) { if(boardState[row+1][col+1].isRed()) {val++;} else val--; } else { if(boardState[row+1][col+1].isRed()) {val--;} else {val++;} } //val add
                                        
                                        if(col+2 < boardState[row].length && row+2 < boardState.length - 1) //traversal 2
                                            if(boardState[row+2][col + 2] != null)
                                                if(boardState[row+2][col + 2].isRed() == isLineRed)
                                                {
                                                    if(isRed) { if(boardState[row+2][col+2].isRed()) {val++;} else val--; } else { if(boardState[row+2][col+2].isRed()) {val--;} else {val++;} } //val add
                                                }

                                    }
                            }

                        if(col+1 < boardState[row].length && row+1 < boardState.length - 1)
                            if(boardState[row+1][col + 1] == null)
                            {
                                //entry point with open right and down
                                boolean isLineRed = boardState[row][col].isRed();
                                if(isRed) { if(boardState[row][col].isRed()) {val++;} else val--; } else { if(boardState[row][col].isRed()) {val--;} else {val++;} } //val add

                                if(col-1 > 0 && row-1 > 0) //traversal 1
                                if(boardState[row-1][col - 1] != null)
                                    if(boardState[row-1][col - 1].isRed() == isLineRed)
                                    {
                                        if(isRed) { if(boardState[row-1][col-1].isRed()) {val++;} else val--; } else { if(boardState[row-1][col-1].isRed()) {val--;} else {val++;} } //val add
                                        
                                        if(col-2 > 0 && row-2 > 0) //traversal 2
                                            if(boardState[row-2][col - 2] != null)
                                                if(boardState[row-2][col - 2].isRed() == isLineRed)
                                                {
                                                    if(isRed) { if(boardState[row-2][col-2].isRed()) {val++;} else val--; } else { if(boardState[row-2][col-2].isRed()) {val--;} else {val++;} } //val add
                                                }

                                    }
                            }

                        if(col+1 < boardState[row].length && row-1 > 0)
                            if(boardState[row+1][col-1] == null)
                            {
                                //entry point with open left and down
                                boolean isLineRed = boardState[row][col].isRed();
                                if(isRed) { if(boardState[row][col].isRed()) {val++;} else val--; } else { if(boardState[row][col].isRed()) {val--;} else {val++;} } //val add

                                if(col-1 > 0 && row+1 < boardState.length - 1) //traversal 1
                                if(boardState[row-1][col + 1] != null)
                                    if(boardState[row-1][col + 1].isRed() == isLineRed)
                                    {
                                        if(isRed) { if(boardState[row-1][col+1].isRed()) {val++;} else val--; } else { if(boardState[row-1][col+1].isRed()) {val--;} else {val++;} } //val add
                                        
                                        if(col-1 > 0 && row+1 < boardState.length - 1) //traversal 2
                                            if(boardState[row-2][col + 2] != null)
                                                if(boardState[row-2][col + 2].isRed() == isLineRed)
                                                {
                                                    if(isRed) { if(boardState[row-2][col+2].isRed()) {val++;} else val--; } else { if(boardState[row-2][col+2].isRed()) {val--;} else {val++;} } //val add
                                                }

                                    }
                            }
                    }
                }
            }

        if(checkForWin(boardState, isRed))
            val += 6;
        if(checkForWin(boardState, !isRed))
            val -= 6;
    }

    private boolean placePuck(Puck[][] board, int column, boolean isRed)
    {
        if(column < 0 || column >= board[0].length) return true;
        for(int r = board.length-1; r >= 0; r--) {
            if(board[r][column] == null) {
                board[r][column] = new Puck(isRed);
                return false;
            }
        }
        return true;
    }

    private boolean checkForWin(Puck[][] board, boolean checkRed)
    {
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