package ui;

import model.Text;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class TextComponent extends JComponent {
    public TextComponent(Text text) {
        setBorder(new LineBorder(Color.red, 4));
        setLayout(new FlowLayout());

        JLabel t = new JLabel();
        t.setText(text.getText());
        add(t);
    }
}
