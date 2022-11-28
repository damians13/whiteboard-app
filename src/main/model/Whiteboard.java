package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

// Represents a whiteboard with a set width and height and a list of text written on it
public class Whiteboard {
    private ArrayList<Text> listOfText;
    private int width;
    private int height;

    // MODIFIES: this
    // EFFECTS: creates a new, blank whiteboard of size width x height
    // REQUIRES: width > 0 AND height > 0
    public Whiteboard(int width, int height) {
        EventLog.getInstance().logEvent(new Event("Whiteboard created with size " + width + "x" + height));
        listOfText = new ArrayList<>();
        this.width = width;
        this.height = height;
    }

    // MODIFIES: this
    // EFFECTS: writes text on the whiteboard at the provided x and y coordinates and returns the text object
    // REQUIRES: 0 <= xcoord < width AND 0 <= ycoord < height
    public Text addText(String text, int xcoord, int ycoord) {
        EventLog.getInstance().logEvent(
                new Event("Adding new text \"" + text + "\" to whiteboard at (" + xcoord + ", " + ycoord + ")"));
        Text textObject = new Text(text, xcoord, ycoord);
        listOfText.add(textObject);
        return textObject;
    }

    // MODIFIES: this
    // EFFECTS: removes the given text object from the whiteboard
    // (if there are duplicates, the first one will be removed)
    // REQUIRES: text is on the whiteboard
    public void removeText(Text text) {
        EventLog.getInstance().logEvent(new Event("Text \"" + text.getText() + "\" removed from whiteboard"));
        listOfText.remove(text);
    }

    // MODIFIES: text
    // EFFECTS: moves the given text object to the given new x and y coordinates
    // REQUIRES: 0 <= newx < width AND 0 <= newy < height
    public void moveText(Text text, int newx, int newy) {
        text.setXcoord(newx);
        text.setYcoord(newy);
        EventLog.getInstance().logEvent(
                new Event("Text \"" + text.getText() + "\" moved to (" + newx + ", " + newy + ")"));
    }

    // REQUIRES: 0 <= index <= listOfText.size() - 1
    public Text getTextAtIndex(int index) {
        return listOfText.get(index);
    }

    public int getNumTextLinesOnBoard() {
        return listOfText.size();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    // MODIFIES: this, text on this whiteboard
    // EFFECTS: sets the width of this whiteboard to the given width and moves any text lines that would be cut off to
    // the new edge
    // REQUIRES: width > 0
    public void setWidth(int width) {
        EventLog.getInstance().logEvent(new Event("Whiteboard width changed from " + this.width + " to " + width));
        this.width = width;

        for (Text text : listOfText) {
            if (text.getXcoord() >= width) {
                text.setXcoord(width - 1);
            }
        }
    }

    // MODIFIES: this, text on this whiteboard
    // EFFECTS: sets the height of this whiteboard to the given height and moves any text lines that would be cut off
    // to the new edge
    // REQUIRES: height > 0
    public void setHeight(int height) {
        EventLog.getInstance().logEvent(new Event("Whiteboard height changed from " + this.height + " to " + height));
        this.height = height;

        for (Text text : listOfText) {
            if (text.getYcoord() >= height) {
                text.setYcoord(height - 1);
            }
        }
    }

    // EFFECTS: returns a JSONObject representation of this whiteboard
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        JSONArray array = new JSONArray();

        for (Text text : listOfText) {
            array.put(text.toJson());
        }

        object.put("height", height);
        object.put("width", width);
        object.put("textLines", array);

        return object;
    }
}
