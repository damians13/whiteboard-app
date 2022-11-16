package ui;

import model.Text;
import model.Whiteboard;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
    }

    // EFFECTS: adds a background image to the whiteboard to satisfy the graphical requirement
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            Image backgroundImage = ImageIO.read(new File("./data/whiteboard.png"))
                    .getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
            g.drawImage(backgroundImage, 0, 0, this);
        } catch (IOException e) {
            // Couldn't find whiteboard.png
        }
    }

    // EFFECTS: adds specified text to whiteboard at specified position and redraws the given frame
    // MODIFIES: this
    // REQUIRES: frame not null
    private void addText(String inputText, int columnClicked, int rowClicked, JFrame frame) {
        GridBagConstraints constraints = new GridBagConstraints();
        TextComponent textComponent = doAddText(inputText, columnClicked, rowClicked, constraints);

        minX = Math.max(minX, textComponent.getPreferredSize().width);
        minY = Math.max(minY, textComponent.getPreferredSize().height);

        addSpacingComponents(constraints, minX, minY);
        frame.pack();
    }

    // EFFECTS: adds text to whiteboard based on given parameters and returns the corresponding textcomponent
    private TextComponent doAddText(String str, int x, int y, GridBagConstraints cons) {
        Text text = new Text(str, x, y);
        TextComponent textComponent = new TextComponent(text);
        cons.insets = new Insets(0, 0, 0, 0);
        cons.gridx = x;
        cons.gridy = y;
        add(textComponent, cons);
        return textComponent;
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

        for (int index = 0; index < board.getNumTextLinesOnBoard(); index++) {
            Text text = board.getTextAtIndex(index);
            TextComponent textComponent = doAddText(text.getText(), text.getXcoord(),
                    text.getYcoord(), constraints);

            minX = Math.max(minX, textComponent.getPreferredSize().width);
            minY = Math.max(minY, textComponent.getPreferredSize().height);
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
