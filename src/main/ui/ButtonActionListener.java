package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonActionListener implements ActionListener {
    private WhiteboardApp appReference;

    public ButtonActionListener(WhiteboardApp appReference) {
        this.appReference = appReference;
    }

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
