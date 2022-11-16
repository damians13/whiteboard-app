package ui;

import model.Text;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

// JComponent to display text on a whiteboard
public class TextComponent extends JComponent {
    // EFFECTS: creates a new text component for the whiteboard based on the given text object
    public TextComponent(Text text) {
        setBorder(new LineBorder(Color.red, 4));
        setLayout(new FlowLayout());

        JLabel t = new JLabel();
        t.setText(text.getText());
        add(t);
    }
}
