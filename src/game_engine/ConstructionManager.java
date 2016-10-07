package game_engine;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by dmr5bq on 9/10/16.
 */


// constructor w/ filename sets file name atrribute
// construct
    // List list = processFile (this.source)
    // handleFile(list)
        // loop for i in range from 0 to len(list)
            // loop while line != CHANGE_STATE_MESSAGE:
                // DO d = generateNextDisplayObject
                // this.parent.displayManager.addChild(d) \ null check
            // this.parent.stateManager.registerNewState()
            // this.parent.displayManager.registerNewState() \ implement, just create the new ArrayList
            // this.parent.stateManager.nextState()
    // this.parent.stageManager.setState(this.parent.stateManager.getMinState())
    // * left with a totally built Array of Lists of Display Objects in the DisplayManager Class, all of the
    //  states have been added to the StateManager class, and the state is reset to the min_state

public class ConstructionManager {

    public static final String CHANGE_STATE_MESSAGE = "change state";

    private String source;
    private Game parent;

    public ConstructionManager(Game parent, String sourceFile) {
        this.parent = parent;
        this.source = sourceFile;
    }

    public void construct() {
        ArrayList<String> all_lines_from_file = this.processFile(this.source);
        int lines_in_file = all_lines_from_file.size();

        StateManager sm_ptr = this.parent.stateManager;
        DisplayManager dm_ptr = this.parent.displayManager;

        boolean sm_null = sm_ptr == null;
        boolean dm_null = dm_ptr == null;

        if (!sm_null && !dm_null) {
            for (int line_number = 0; line_number < lines_in_file; line_number++) {

                String line = all_lines_from_file.get(line_number);

                if (line == CHANGE_STATE_MESSAGE) {
                    // dm_ptr.registerNewState();
                    sm_ptr.registerNewState();
                    sm_ptr.nextState();
                } else {
                    DisplayObject new_display_object = this.generateNextDisplayObject(line);
                    dm_ptr.addChild(new_display_object);
                }

            }

            sm_ptr.setState(sm_ptr.getMinState());
        }
    }


    public DisplayObject generateNextDisplayObject(String img_line_from_file) {
        return null;
    }

    public void handleFileInput(ArrayList<String> all_lines_from_file) {

    }

    public ArrayList<String> processFile(String filename)  {

        ArrayList<String> retList = new ArrayList<>();

        String file_path = ("resources" + File.separator + filename);

        Scanner file_reader = null;

        try {
             file_reader = new Scanner(new File(file_path));

            while (file_reader.hasNextLine()) {
                String current_line = file_reader.nextLine();
                current_line = current_line.trim();
                retList.add(current_line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }



        return retList;
    }

}
