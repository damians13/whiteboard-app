package ui;

import model.Text;

import javax.swing.*;
import java.awt.*;

// JComponent to display text on a whiteboard
public class TextComponent extends JComponent {
    private Text text;
    private JLabel textDisplay;

    // EFFECTS: creates a new text component for the whiteboard based on the given text object
    public TextComponent(Text text) {
        this.text = text;
        setLayout(new FlowLayout());

        textDisplay = new JLabel();
        updateText();
        add(textDisplay);
    }

    // EFFECTS: returns the text object this component represents
    public Text getText() {
        return text;
    }

    public void updateText() {
        textDisplay.setText(text.getText());
    }
}
