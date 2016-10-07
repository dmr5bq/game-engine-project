package game_engine;
import input.InputHandler;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 * Main class for the game
 */
public class Game extends JFrame
{

    public static final int FRAME_RATE = 60;


    boolean isRunning = true;
    int windowWidth = 500;
    int windowHeight = 500;

    double currentTime = System.currentTimeMillis();

    Insets insets;

    Graphics canvas;

    InputHandler inputHandler;
    DisplayManager displayManager;
    StateManager stateManager;


    public static void main(String[] args)
    {
        Game game = new Game();
        game.run();
        System.exit(0);
    }

    /**
     * This method starts the game and runs it in a loop
     */
    public void run()
    {
        initialize();

        while(isRunning)
        {

            update(inputHandler);
            draw(canvas);

            //  delay for each frame  -   time it took for one frame
            currentTime = (1000 / FRAME_RATE) - (System.currentTimeMillis() - currentTime);

            if (currentTime > 0)
            {
                try
                {
                    Thread.sleep((long) currentTime);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }

        setVisible(false);
    }

    /**
     * This method will set up everything need for the game to run
     */
    void initialize()
    {
        // JFrame Attributes
        setTitle("");
        setSize(windowWidth, windowHeight);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        // Resizing for insets; want drawing to be the size of windowWidth, windowHeight, not frame.
        insets = getInsets();
        setSize(insets.left + windowWidth + insets.right,
                insets.top + windowHeight + insets.bottom);


        inputHandler =   new InputHandler(this);
        displayManager = new DisplayManager(this);
        canvas =         getGraphics();

        //test
        DisplayObject d1 = new DisplayObject("test", "kit.jpg");
        displayManager.addChild(d1);
    }

    /**
     * This method will check for input, move things
     * around and check for win conditions, etc
     */
    void update(InputHandler input)  {

        // tell the Display Manager to update
        displayManager.update(input);
    }

    /**
     * This method will draw everything
     */
    void draw(Graphics g) {

        // tell the DisplayManager to draw
        displayManager.draw(g);
    }
}