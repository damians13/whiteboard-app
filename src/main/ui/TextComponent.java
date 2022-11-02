package ui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class TextComponent extends JComponent {
    public TextComponent(int xcoord, int ycoord) {
        setBorder(new LineBorder(Color.red, 4));
        setLayout(new FlowLayout());
        JLabel text = new JLabel();
        text.setText("Hello, world!");
        add(text);
        setLocation(3,4);
    }
}
