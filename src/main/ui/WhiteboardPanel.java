package ui;

import model.Text;
import model.Whiteboard;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class WhiteboardPanel extends JPanel {
    private Whiteboard board;

    public WhiteboardPanel(Whiteboard board) {
        this.board = board;
        setBorder(new LineBorder(Color.green, 4));
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        TextComponent t = new TextComponent(20, 20);
        add(t);
        JLabel text = new JLabel();
        text.setText("Hi");
        add(text);
    }
}
