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

// Extension of JPanel to display a whiteboard
public class WhiteboardPanel extends JPanel {
    private Whiteboard board;
    private List<TextComponent> textComponents;
    private static final int MIN_SIZE = 10;
    private int minX;
    private int minY;

    // EFFECTS: create a new whiteboard panel and set up a mouse listener on it to add new text to whiteboard
    // MODIFIES: this
    // REQUIRES: frame not null
    public WhiteboardPanel(Whiteboard board, JFrame frame) {
        setBoard(board);
        setBorder(new LineBorder(Color.green, 4));
        setLayout(new GridBagLayout());
        addMouseListener(new MouseAdapter() {
            // EFFECTS: adds a new text label to whiteboard when clicked with text supplied by user via input dialog
            // MODIFIES: this
            @Override
            public void mouseClicked(MouseEvent e) {
                int columnClicked = e.getPoint().x / minX;
                int rowClicked = e.getPoint().y / minY;

                String inputText = JOptionPane.showInputDialog("Enter text to put here: ");
                addText(inputText, columnClicked, rowClicked, frame);
            }
        });
//        addMouseMotionListener(new MouseMotionAdapter() {
//            @Override
//            public void mouseDragged(MouseEvent e) {
//                super.mouseDragged(e);
//                System.out.println("Dragged!");
//            }
//        });
    }

    // EFFECTS: adds specified text to whiteboard at specified position and redraws the given frame
    // MODIFIES: this
    // REQUIRES: frame not null
    private void addText(String inputText, int columnClicked, int rowClicked, JFrame frame) {
        Text text = new Text(inputText, columnClicked, rowClicked);
        TextComponent textComponent = new TextComponent(text);
        GridBagConstraints constraints = new GridBagConstraints();

        minX = Math.max(minX, textComponent.getPreferredSize().width);
        minY = Math.max(minY, textComponent.getPreferredSize().height);

        addSpacingComponents(constraints, minX, minY);

        constraints.insets = new Insets(0, 0, 0, 0);
        constraints.gridx = columnClicked;
        constraints.gridy = rowClicked;

        add(textComponent, constraints);
        frame.pack();
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
        minX = MIN_SIZE;
        minY = MIN_SIZE;

        constraints.insets = new Insets(0, 0, 0, 0);

        TextComponent textComponent;
        Text text;
        for (int index = 0; index < board.getNumTextLinesOnBoard(); index++) {
            text = board.getTextAtIndex(index);
            textComponent = new TextComponent(text);

            constraints.gridx = text.getXcoord();
            constraints.gridy = text.getYcoord();

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
