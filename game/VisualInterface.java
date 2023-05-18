package game;

public interface VisualInterface {
    /**
     * Updates visuals to represent gamBoard
     * @param gameBoard     a copy of the most recent gameBoard, represented as a 2D array of Puck objects. (null if empty.)
     */
    public void updateVisuals(Puck[][] gameBoard);
}
