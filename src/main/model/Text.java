package model;

import org.json.JSONObject;

// Represents a line of text on a whiteboard with a position described by x and y coordinates
public class Text {
    private String text;
    private int xcoord;
    private int ycoord;

    // MODIFIES: this
    // EFFECTS: creates a new text object at the given x and y coordinates
    public Text(String text, int xcoord, int ycoord) {
        EventLog.getInstance().logEvent(
                new Event("New text \"" + text + "\" created at (" + xcoord + ", " + ycoord + ")"));
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
        EventLog.getInstance().logEvent(new Event("Text \"" + this.text + "\" updated to \"" + text + "\""));
        this.text = text;
    }

    public void setXcoord(int xcoord) {
        if (this.xcoord != xcoord) {
            EventLog.getInstance().logEvent(
                    new Event("Text \"" + text + "\" x-coordinate changed from " + this.xcoord + " to " + xcoord));
            this.xcoord = xcoord;
        }
    }

    public void setYcoord(int ycoord) {
        if (this.ycoord != ycoord) {
            EventLog.getInstance().logEvent(
                    new Event("Text \"" + text + "\" y-coordinate changed from " + this.ycoord + " to " + ycoord));
            this.ycoord = ycoord;
        }
    }

    // EFFECTS: returns a JSONObject representing this text object (text field and position on board)
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        object.put("text", text);
        object.put("x", xcoord);
        object.put("y", ycoord);
        return object;
    }
}
