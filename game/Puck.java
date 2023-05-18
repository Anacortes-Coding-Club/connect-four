package game;

public class Puck {
    private final boolean isRed;
    //Representing with a boolean makes comparisons computationally easier. The same system will be
    //used in check3rs as it requires far more comparisons, and adding it here make an easier to adapt bots.

    /**
     * Creates a puck as black or red.
     * @param isRed     sets color as red if true, black if false;
     */
    public Puck(boolean isRed) {
        this.isRed = isRed;
    }

    /**
     * Returns true if object is red.
     * @return  Returns true if object is red.
     */
    public boolean isRed() {
        return isRed;
    }

    /**
     * String representing the puck.
     * @return  String representing the puck.
     */
    public String toString() {
        String out = "(";
        if(isRed) {
            out += "red";
        } else {
            out += "black";
        }
        
        return out += " puck)";
    }
}
