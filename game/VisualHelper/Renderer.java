/*
 * @author DonnerTech
 */

package game.VisualHelper;

import java.awt.Color;
//import java.awt.Dimension;

import game.Puck;
import game.VisualInterface;

//import Turtle;
//import World;
//import MoveAdapter;

public class Renderer implements VisualInterface
{
    //**************************************************
    // Instance Variables
    //**************************************************

    private World habitat;
    private Turtle mainTurtl;
  
    private int screenW = 900;
    private int screenH = 600;

    //**************************************************
    // Constructor
    //**************************************************

    /**
    * Constructor that initializes a new {@link World} as habitat containing a new {@link Turtle} as mainTurtl.
    * Initializes screen color to {@link Color.BLUE}.
    *
    * This constructor also sets up several properties of mainTurtl.
    */
    public Renderer()
    {
        this(Color.BLUE);
    }
    public Renderer(Color col)
    {
        habitat = new World(screenW, screenH, col);
        habitat.setResizable(false);
        habitat.setDefaultCloseOperation(World.EXIT_ON_CLOSE);

        //MoveAdapter worldPos = new MoveAdapter();

        //habitat.addComponentListener(worldPos);

        habitat.setVisible(true);

        mainTurtl = new Turtle(habitat);

        //setup for turtle
        mainTurtl.setPenWidth(5);
        mainTurtl.penUp();
        mainTurtl.turnLeft(90);
        mainTurtl.setColor(new Color(0,0,0));


    }

    @Override
    public void updateVisuals(Puck[][] gameBoard)
    {
        //clear screen
        habitat.erase();

        // board scale
        int scale = 64;
        
        //draw board by row and col.
        for(int row = 0; row < gameBoard.length; row++)
        {
            for(int col = 0; col < gameBoard[row].length; col++)
            {
                //mainTurtl.goTo(((row * scale) - gameBoard.length * scale / 2) + scale/2, ((col * scale) - gameBoard[row].length * scale / 2) + scale/2);

                //board positioning and plotting
                mainTurtl.goTo(((col * scale) - gameBoard[row].length * scale / 2) + scale/2, (gameBoard.length * scale / 2 -(row * scale)) - scale/2);
                mainTurtl.drop("game/Sprites/frame.png",scale);
                
                //Stone spotting
                if(gameBoard[row][col] != null)
                {
                   if(gameBoard[row][col].isRed())
                   {
                        mainTurtl.drop("game/Sprites/red.png", scale);
                   }
                   else
                   {
                        mainTurtl.drop("game/Sprites/blue.png", scale);
                   }
                }
            }
        }

        //Update Screen
        habitat.turtleMoved();
    }
}