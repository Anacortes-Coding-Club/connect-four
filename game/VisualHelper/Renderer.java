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
    
    private World habitat;
    private Turtle mainTurtl;
  
    private int screenW = 900;
    private int screenH = 600;

    public Renderer()
    {
        habitat = new World(screenW, screenH, Color.BLUE);
        habitat.setResizable(false);
        habitat.setDefaultCloseOperation(World.EXIT_ON_CLOSE);

        //MoveAdapter worldPos = new MoveAdapter();

        //habitat.addComponentListener(worldPos);

        habitat.setVisible(true);

        mainTurtl = new Turtle(habitat);
        mainTurtl.setPenWidth(5);
        mainTurtl.penUp();
        mainTurtl.turnLeft(90);
        mainTurtl.setColor(new Color(0,0,0));


    }

    @Override
    public void updateVisuals(Puck[][] gameBoard)
    {
        int scale = 64;
        
        //draw board
        for(int row = 0; row < gameBoard.length; row++)
        {
            for(int col = 0; col < gameBoard[row].length; col++)
            {
                mainTurtl.goTo(((row * scale) - gameBoard.length * scale / 2) + scale/2, ((col * scale) - gameBoard[row].length * scale / 2) + scale/2);
                mainTurtl.drop("game/Sprites/frame.png",scale);
                
                if(gameBoard[row][col] != null)
                {
                   if(gameBoard[row][col].isRed())
                   {
                     mainTurtl.drop("game/Sprites/red.png",scale);
                   }
                   else
                   {
                     mainTurtl.drop("game/Sprites/blue.png",scale);
                   }
                }
            }
        }

        //Update Screen
        habitat.turtleMoved();
    }
}