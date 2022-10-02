package model;

// Represents a line of text on a whiteboard with a position described by x and y coordinates
public class Text {
    private String text;
    private int xcoord;
    private int ycoord;

    // MODIFIES: this
    // EFFECTS: creates a new text object at the given x and y coordinates
    public Text(String text, int xcoord, int ycoord) {
        this.text = text;
        this.xcoord = xcoord;
        this.ycoord = ycoord;
    }

    public String getText() {
        return text;
    }

    public int getXcoord() {
        return xcoord;
    }

    public int getYcoord() {
        return ycoord;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setXcoord(int xcoord) {
        this.xcoord = xcoord;
    }

    public void setYcoord(int ycoord) {
        this.ycoord = ycoord;
    }
}
