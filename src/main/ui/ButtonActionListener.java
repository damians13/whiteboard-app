package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Action listener for save and load buttons
public class ButtonActionListener implements ActionListener {
    private WhiteboardApp appReference;

    // EFFECTS: creates a new button action listener with a reference to the whiteboard app
    // MODIFIES: this
    public ButtonActionListener(WhiteboardApp appReference) {
        this.appReference = appReference;
    }

    // EFFECTS: handles a button action event
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Save")) {
            appReference.openSaveWhiteboardGUI();
        } else if (command.equals("Load")) {
            appReference.openLoadWhiteboardGUI();
        }
    }
}
