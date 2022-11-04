package ui;

import model.Text;
import model.Whiteboard;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;

public class WhiteboardPanel extends JPanel {
    private Whiteboard board;
    private List<TextComponent> textComponents;

    public WhiteboardPanel(Whiteboard board) {
        this.board = board;
        setBorder(new LineBorder(Color.green, 4));
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridwidth = board.getWidth() - 1;
        constraints.gridheight = board.getHeight() - 1;
        constraints.insets = new Insets(0, 0, 0, 0);

        TextComponent textComponent;
        Text text;
        for (int index = 0; index < board.getNumTextLinesOnBoard(); index++) {
            text = board.getTextAtIndex(index);
            textComponent = new TextComponent(text);

            constraints.gridx = text.getXcoord();
            constraints.gridy = text.getYcoord();

            add(textComponent, constraints);
        }
    }
}
