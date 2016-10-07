package game_engine;

import java.awt.*;
import input.InputHandler;

/**
 * Created by dmr5bq on 9/8/16.
 */
public class Sprite extends DisplayObject {

    public Sprite(String id, String filename) {
        super(id, filename);
    }

    public Sprite(String id) {
        super(id);
    }

    public void update(InputHandler input) {
        super.update(input);
    }

    public void draw(Graphics g) {
        super.draw(g);
    }

}
