package game_engine;

import input.InputHandler;

import java.awt.*;
import java.io.*;
import javax.imageio.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


/**
 * Created by dmr5bq on 9/7/16.
 *
 * ____________________________
 *
 * Tested and Passed:
 * * Able to draw buffered image to screen from filename - 9/7/16, 08:03 AM
 * * Able to shift origin  - 9/7/16, 08:13 AM
 * * Able to translate   - 9/7/16, 08:13 AM
 * * Able to scale correctly (with translation and origin-shifting)   - 9/7/16, 08:13 AM
 * * Able to rotate about the origin - 9/17/16, 08:20 AM
 * ____________________________
 *
 * Tested and Failed / Problems to Address ASAP:
 * * Alpha only appears to last for one loop - 9/7/16, 8:57 AM
 * ____________________________
 *
 *
 */

public class DisplayObject {

    public String id;
    protected String filename;
    protected ArrayList<DisplayObject> children;
    protected BufferedImage displayImage;


    private boolean visible;
    private Point position; // position of origin relative to parent origin
    private Point origin;   // within frame of image
    private double scaleX;  // x-scale of img
    private double scaleY;  // y-scale of img
    private double rotation;// rotation around origin
    private float alpha;   // transparency of img


    public DisplayObject (String id, String filename) {
        this.id = id;
        this.filename = filename;
        this.children = new ArrayList<>();
        this.setImage(this.filename);

        this.visible = true;
        this.position = new Point(0, 0);
        this.origin = new Point(0, 0);
        this.scaleX = 1.0;
        this.scaleY = 1.0;
        this.rotation = 0.0;
        this.alpha = 1.0f;
    }

    public DisplayObject (String id) {
        this.id = id;
        this.children = new ArrayList<>();

        this.visible = true;
        this.position = new Point(0, 0);
        this.origin = new Point(0,0);
        this.scaleX = 1.0;
        this.scaleY = 1.0;
        this.rotation = 0.0;
        this.alpha = 1.0f;
    }

    public void addChild(DisplayObject child) {
        this.children.add(child);
    }

    public void removeChild(DisplayObject child) {
        this.children.remove(child);
    }

    protected void setImage(String imageName) {
        if (imageName == null) {
            return;
        }
        displayImage = readImage(imageName);
        if (displayImage == null) {
            System.err.println("[DisplayObject.setImage] ERROR: " + imageName + " does not exist!");
        }
    }

    public void setImage(BufferedImage image) {
        if(image == null) return;
        displayImage = image;
    }

    /**
     * Returns the unscaled width and height of this display object
     * */
    public int getUnscaledWidth() {
        if(displayImage == null) return 0;
        return displayImage.getWidth();
    }

    public int getUnscaledHeight() {
        if(displayImage == null) return 0;
        return displayImage.getHeight();
    }

    /**
     * Helper function that simply reads an image from the given image name
     * (looks in resources\\) and returns the buffered image for that filename
     * */
    public BufferedImage readImage(String imageName) {
        BufferedImage image = null;
        try {
            String file = ("resources" + File.separator + imageName);
            image = ImageIO.read(new File(file));
        } catch (IOException e) {
            System.out.println("[Error in DisplayObject.java:readImage] Could not read image " + imageName);
            e.printStackTrace();
        }
        return image;
    }


    public void update(InputHandler input) {
        for (DisplayObject child : this.children )
            child.update(input);
    }

    public void draw(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        if (this.visible) {
            this.applyTransformations(g2d);

            g2d.drawImage(this.displayImage, 0, 0, getUnscaledWidth(), getUnscaledHeight(), null);
            for (DisplayObject child : this.children)
                child.draw(g);

            this.reverseTransformations(g2d);
        }

    }

    public void applyTransformations(Graphics2D g2d) {
        /* *
        * Applies transformations to the Graphics object in the order:
        * * * scale
        * * * translate
        * * * rotate
        * * * set alpha
        */

        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, this.alpha);

        g2d.setComposite(ac);
        g2d.scale(this.scaleX, this.scaleY);
        g2d.translate((this.position.x - this.origin.x)/this.scaleX, (this.position.y - this.origin.y)/this.scaleY);
        g2d.rotate(this.rotation, this.origin.x, this.origin.y);


    }

    public void reverseTransformations(Graphics2D g2d) {

        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);

        g2d.rotate(-this.rotation, -this.origin.x, -this.origin.y);
        g2d.translate(- this.scaleX * (this.position.x - this.origin.x), - this.scaleY * (this.position.y - this.origin.y));
        g2d.scale(1/this.scaleX, 1/this.scaleY);
        g2d.setComposite(ac);
    }




    /* * * * * * * * * * * * * * * *
    * All Mutators and Inspectors  *
    * * * * * * * * * * * * * *  * */

    // Visibility Mutators and Inspectors
    public void setVisible(boolean val) {
        this.visible = val;
    }

    public boolean isVisible() {
        return this.visible;
    }

    // Position Mutators and Inspectors
    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public void setX(int new_x) {
        this.position = new Point(new_x, this.position.y);
    }

    public void setY(int new_y) {
        this.position = new Point(this.position.x, new_y);
    }

    public int getX() {
        return this.position.x;
    }

    public int getY() {
        return this.position.y;
    }

    public void shiftX(int shift) {
        this.setX(this.getX() + shift);
    }

    public void shiftY(int shift) {
        this.setY(this.getY() + shift);
    }

    // Origin Mutators and Inspectors
    public Point getOrigin() {
        return origin;
    }

    public void setOrigin(Point origin) {
        this.origin = origin;
    }

    // Scale Mutators and Inspectors
    public double getScaleX() {
        return scaleX;
    }

    public void setScaleX(double scaleX) {
        this.scaleX = scaleX;
    }

    public double getScaleY() {
        return scaleY;
    }

    public void setScaleY(double scaleY) {
        this.scaleY = scaleY;
    }

    public void setScale(double scale) {
        this.scaleX = scale;
        this.scaleY = scale;
    }

    // Rotation Mutators and Inspectors
    public double getRotationRadians() {
        return rotation;
    }

    public double getRotationDegrees() {
        double conv_angle = Math.toDegrees(this.rotation);
        return conv_angle;
    }

    private void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public void setRotationRadians(double rotation) {
        this.setRotation(rotation);
    }

    public void setRotationDegrees(double rotation) {
        double conv_angle = Math.toRadians(rotation);
        this.setRotation(conv_angle);
    }

    // Alpha Mutators and Inspectors
    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }
}
