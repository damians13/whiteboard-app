package ui;

import model.Text;
import model.Whiteboard;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;

public class WhiteboardPanel extends JPanel {
    private Whiteboard board;
    private List<TextComponent> textComponents;
    private static final int MIN_SIZE = 10;

    public WhiteboardPanel() {
        setBorder(new LineBorder(Color.green, 4));
        setLayout(new GridBagLayout());
//        addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
//                System.out.println("Click!");
//            }
//        });
//        addMouseMotionListener(new MouseMotionAdapter() {
//            @Override
//            public void mouseDragged(MouseEvent e) {
//                super.mouseDragged(e);
//                System.out.println("Dragged!");
//            }
//        });
    }

    // EFFECTS: sets the whiteboard represented by this panel
    // MODIFIES: this
    public void setBoard(Whiteboard board) {
        this.board = board;
    }

    // EFFECTS: draws this panel and the text components inside it
    public void drawWhiteboardPanel() {
        removeAll();
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.insets = new Insets(0, 0, 0, 0);

        TextComponent textComponent;
        Text text;
        int minX = MIN_SIZE;
        int minY = MIN_SIZE;
        for (int index = 0; index < board.getNumTextLinesOnBoard(); index++) {
            text = board.getTextAtIndex(index);
            textComponent = new TextComponent(text);

            constraints.gridx = text.getXcoord();
            constraints.gridy = text.getYcoord();
            constraints.weightx = 1;
            constraints.weighty = 1;

            minX = Math.max(minX, textComponent.getPreferredSize().width);
            minY = Math.max(minY, textComponent.getPreferredSize().height);

            add(textComponent, constraints);
        }
        addSpacingComponents(constraints, minX, minY);
    }

    // EFFECTS: adds rectangular components to each row and column so that the grid holds its shape
    private void addSpacingComponents(GridBagConstraints constraints, int minX, int minY) {
        // Columns
        for (int x = 0; x < board.getWidth(); x++) {
            constraints.gridx = x;
            constraints.gridy = 0;

            add(Box.createRigidArea(new Dimension(minX, minY)), constraints);
        }
        // Rows
        for (int y = 1; y < board.getHeight(); y++) {
            constraints.gridx = 0;
            constraints.gridy = y;

            add(Box.createRigidArea(new Dimension(minX, minY)), constraints);
        }
    }
}
