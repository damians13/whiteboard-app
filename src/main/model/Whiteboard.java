package model;

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
        listOfText = new ArrayList<Text>();
        this.width = width;
        this.height = height;
    }

    // MODIFIES: this
    // EFFECTS: writes text on the whiteboard at the provided x and y coordinates
    // REQUIRES: 0 <= xcoord < width AND 0 <= ycoord < height
    public void addText(String text, int xcoord, int ycoord) {
        listOfText.add(new Text(text, xcoord, ycoord));
    }

    // MODIFIES: this
    // EFFECTS: removes the given text object from the whiteboard
    // (if there are duplicates, the first one will be removed)
    // REQUIRES: text is on the whiteboard
    public void removeText(Text text) {
        listOfText.remove(text);
    }

    // MODIFIES: text
    // EFFECTS: moves the given text object to the given new x and y coordinates
    // REQUIRES: 0 <= newx < width AND 0 <= newy < height
    public void moveText(Text text, int newx, int newy) {
        text.setXcoord(newx);
        text.setYcoord(newy);
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
        this.height = height;

        for (Text text : listOfText) {
            if (text.getYcoord() >= height) {
                text.setYcoord(height - 1);
            }
        }
    }
}
