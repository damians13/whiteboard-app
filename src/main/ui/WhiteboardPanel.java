package ui;

import model.Text;
import model.Whiteboard;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
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
    private GridBagLayout layout;
    private JFrame frame;
    private GridBagConstraints constraints;

    // EFFECTS: create a new whiteboard panel and set up a mouse listener on it to add new text to whiteboard
    // MODIFIES: this
    // REQUIRES: frame not null
    public WhiteboardPanel(Whiteboard board, JFrame frame) {
        layout = new GridBagLayout();
        constraints = new GridBagConstraints();
        this.frame = frame;
        setBoard(board);
        setLayout(layout);
        addMouseListener(new MouseAdapter() {
            // EFFECTS: adds a new text label to whiteboard when clicked with text supplied by user via input dialog
            // MODIFIES: this
            @Override
            public void mouseClicked(MouseEvent e) {
                int columnClicked = e.getPoint().x / minX;
                int rowClicked = e.getPoint().y / minY;

                String inputText = JOptionPane.showInputDialog("Enter text to put here: ");
                if (inputText.trim().length() == 0) {
                    return;
                }
                addText(inputText, columnClicked, rowClicked);
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
    private void addText(String inputText, int columnClicked, int rowClicked) {
        TextComponent textComponent = doAddText(inputText, columnClicked, rowClicked);

        minX = Math.max(minX, textComponent.getPreferredSize().width);
        minY = Math.max(minY, textComponent.getPreferredSize().height);

        addSpacingComponents(minX, minY);
        frame.pack();
    }

    // EFFECTS: adds text to whiteboard panel based on given text object and returns the corresponding textcomponent
    // MODIFIES: this
    private TextComponent doAddText(Text text) {
        TextComponent textComponent = new TextComponent(text);
        constraints.insets = new Insets(0, 0, 0, 0);
        constraints.gridx = text.getXcoord();
        constraints.gridy = text.getYcoord();

        addTextComponentMouseDragListener(textComponent);
        addTextComponentMouseClickListener(textComponent);

        add(textComponent, constraints);
        return textComponent;

    }

    // EFFECTS: adds text to whiteboard and panel based on given parameters and returns the corresponding textcomponent
    // MODIFIES: board
    private TextComponent doAddText(String str, int x, int y) {
        return doAddText(board.addText(str, x, y));
    }

    // EFFECTS: adds a mouse drag listener to the textcomponent, clauses for which are specified below
    // MODIFIES: textComponent
    private void addTextComponentMouseDragListener(TextComponent textComponent) {
        textComponent.addMouseMotionListener(new MouseMotionAdapter() {
            // EFFECTS: moves the text component to the nearest grid cell to the cursor when it is being dragged
            // MODIFIES: textComponent, textComponent.getText()
            @Override
            public void mouseDragged(MouseEvent e) {
                int newX = e.getPoint().x / minX + textComponent.getText().getXcoord();
                int newY = e.getPoint().y / minY + textComponent.getText().getYcoord();

                // Keep text component on the whiteboard panel
                newX = Math.min(board.getWidth() - 1, Math.max(0, newX));
                newY = Math.min(board.getHeight() - 1, Math.max(0, newY));

                constraints.gridx = newX;
                constraints.gridy = newY;
                layout.setConstraints(textComponent, constraints);
                board.moveText(textComponent.getText(), newX, newY);
                revalidate();
            }
        });
    }

    // EFFECTS: adds a mouse click listener to the textcomponent, clauses for which are specified below
    // MODIFIES: textComponent
    private void addTextComponentMouseClickListener(TextComponent textComponent) {
        textComponent.addMouseListener(new MouseAdapter() {
            // EFFECTS: adds a mouse click listener which prompts the user to edit the text on the clicked textcomponent
            // MODIFIES: this, board, textComponent, textComponent.getText()
            @Override
            public void mouseClicked(MouseEvent e) {
                String text = JOptionPane.showInputDialog(
                        "Change \"" + textComponent.getText().getText() + "\" to..."
                );
                if (text.trim().length() == 0) {
                    remove(textComponent);
                    board.removeText(textComponent.getText());
                    repaint();
                } else {
                    textComponent.getText().setText(text);
                    textComponent.updateText();

                    minX = Math.max(minX, textComponent.getPreferredSize().width);
                    minY = Math.max(minY, textComponent.getPreferredSize().height);

                    addSpacingComponents(minX, minY);
                }
                frame.pack();
            }
        });
    }

    // EFFECTS: sets the whiteboard represented by this panel
    // MODIFIES: this
    public void setBoard(Whiteboard board) {
        this.board = board;
    }

    // EFFECTS: draws this panel and the text components inside it
    public void drawWhiteboardPanel() {
        removeAll();
        minX = MIN_SIZE;
        minY = MIN_SIZE;

        for (int index = 0; index < board.getNumTextLinesOnBoard(); index++) {
            Text text = board.getTextAtIndex(index);
            TextComponent textComponent = doAddText(text);

            minX = Math.max(minX, textComponent.getPreferredSize().width);
            minY = Math.max(minY, textComponent.getPreferredSize().height);
        }
        addSpacingComponents(minX, minY);
    }

    // EFFECTS: adds rectangular components to each row and column so that the grid holds its shape
    private void addSpacingComponents(int minX, int minY) {
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
