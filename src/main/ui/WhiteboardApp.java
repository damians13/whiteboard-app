package ui;

import model.EventLog;
import model.Text;
import model.Whiteboard;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

// Whiteboard application
public class WhiteboardApp extends JFrame {
    private Whiteboard board;
    private Scanner scanner;
    private boolean shouldRun;
    private WhiteboardPanel panel;
    private JPanel buttonPanel;
    private JButton saveButton;
    private JButton loadButton;

    // EFFECTS: run the whiteboard application
    public WhiteboardApp() {
        super("Whiteboard App");
        //runWhiteboardApp();
        runWhiteboardAppGUI();
    }

    // EFFECTS: handles the main loop of the GUI application
    private void runWhiteboardAppGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowCloseListener();
        setLayout(new FlowLayout());

        board = new Whiteboard(10, 10);
        panel = new WhiteboardPanel(board, this);
        panel.drawWhiteboardPanel();
        add(panel);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        saveButton = new JButton("Save");
        saveButton.addActionListener(new ButtonActionListener(this));
        buttonPanel.add(saveButton);
        loadButton = new JButton("Load");
        buttonPanel.add(loadButton);
        loadButton.addActionListener(new ButtonActionListener(this));

        add(buttonPanel);
        pack();

        setResizable(false);
        setVisible(true);
    }

    // EFFECTS: adds a listener to the frame that prints the contents of the event log to the console
    private void addWindowCloseListener() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                EventLog.getInstance().forEach((ev) -> {
                    System.out.println(ev.toString());
                });
            }
        });
    }

    // EFFECTS: handles the main loop of the CLI application
    private void runWhiteboardApp() {
        init();

        while (shouldRun) {
            System.out.println("Here is your whiteboard: ");
            displayWhiteboard(board);

            displayOptions();

            processCommand(scanner.next());
        }
    }

    // EFFECTS: initializes the whiteboard application
    // MODIFIES: this
    private void init() {
        scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");
        shouldRun = true;

        System.out.println("Welcome to the whiteboard application!\nHow wide would you like your whiteboard? ");
        int widthInput = scanner.nextInt();
        while (widthInput <= 0) {
            System.out.println("Your whiteboard has to be at least 1 entry wide. Please enter a new width.");
            widthInput = scanner.nextInt();
        }

        System.out.println("And how tall would you like your whiteboard? ");
        int heightInput = scanner.nextInt();
        while (heightInput <= 0) {
            System.out.println("Your whiteboard has to be at least 1 entry tall. Please enter a new height.");
            heightInput = scanner.nextInt();
        }

        board = new Whiteboard(widthInput, heightInput);
    }

    // EFFECTS: displays the user's whiteboard in the console
    // REQUIRES: board is not null
    private void displayWhiteboard(Whiteboard board) {
        ArrayList<ArrayList<String>> boardText = new ArrayList<>();
        // Fill each entry with a "." to start
        ArrayList<String> row;
        for (int y = 0; y < board.getHeight(); y++) {
            row = new ArrayList<>();
            for (int x = 0; x < board.getWidth(); x++) {
                row.add(".");
            }
            boardText.add(row);
        }
        // Add text to boardText matrix
        for (int i = 0; i < board.getNumTextLinesOnBoard(); i++) {
            Text text = board.getTextAtIndex(i);
            boardText.get(text.getYcoord()).set(text.getXcoord(), "\"" + text.getText() + "\"");
        }
        for (ArrayList<String> textRow : boardText) {
            for (String text : textRow) {
                System.out.print(text + " ");
            }
            System.out.println("\n");
        }
    }

    // EFFECTS: provides the user with their options to control the application
    private void displayOptions() {
        System.out.println("What would you like to do with your whiteboard?");
        System.out.println("\tquit\tquits the application");
        System.out.println("\tadd \tadds text to the whiteboard");
        System.out.println("\tmove\tmove text on the whiteboard");
        System.out.println("\tresize\tresize your whiteboard");
        System.out.println("\tdelete\tdelete text on the whiteboard");
        System.out.println("\tsave\tsave current whiteboard for later");
        System.out.println("\tload\tload a previously saved whiteboard");
    }

    // EFFECTS: processes the given command
    private void processCommand(String command) {
        switch (command.toLowerCase()) {
            case "quit":
                shouldRun = false;
                System.out.println("Bye!");
                break;
            case "add":
                addTextToBoard();
                break;
            case "move":
                moveTextOnBoard();
                break;
            case "delete":
                deleteTextOnBoard();
                break;
            case "resize":
                resizeWhiteboard();
                break;
            default:
                processCommandContinued(command);
                break;
        }
    }

    // EFFECTS: processes the given command (additional instructions that didn't fit in processCommand)
    private void processCommandContinued(String command) {
        switch (command) {
            case "save":
                saveWhiteboard();
                break;
            case "load":
                loadWhiteboard();
                break;
            default:
                System.out.println("Sorry! I don't recognize that command");
                break;
        }
    }

    // EFFECTS: prompts the user to see what text they would like to add and where, then it adds the text at that spot
    // MODIFIES: this
    private void addTextToBoard() {
        System.out.println("What text would you like to add?");
        String text = scanner.next();

        System.out.println("And where would you like to put \"" + text + "\"?");
        System.out.println("Enter the x-coordinate, then the y-coordinate.");
        int xinput = scanner.nextInt();
        while (xinput < 0 || xinput >= board.getWidth()) {
            System.out.println("That's off your whiteboard!\nPlease enter a new x-coordinate.");
            xinput = scanner.nextInt();
        }

        int yinput = scanner.nextInt();
        while (yinput < 0 || yinput >= board.getHeight()) {
            System.out.println("That's off your whiteboard!\nPlease enter a new y-coordinate.");
            yinput = scanner.nextInt();
        }

        board.addText(text, xinput, yinput);
    }

    // EFFECTS: prompts the user to determine which text they would like to move until it either reaches the desired
    // text to move (in which case it will move it) or until it reaches the end of the list
    // MODIFIES: this
    private void moveTextOnBoard() {
        System.out.println("Which text would you like to move? Enter it without the surrounding quotation marks.");
        String text = scanner.next();

        Text textOnBoard;
        for (int i = 0; i < board.getNumTextLinesOnBoard(); i++) {
            textOnBoard = board.getTextAtIndex(i);
            if (textOnBoard.getText().equals(text)) {
                if (doMoveText(textOnBoard)) {
                    break;
                } else {
                    System.out.println("Okay, I'll look for another line.");
                }
            }
        }
        System.out.println("I couldn't find that line of text on your whiteboard.");
    }

    // EFFECTS: if the user confirms moving the given Text, it will prompt them for the new location and return true
    // if the user does not wish to move the given text, it will return false and modify nothing
    // MODIFIES: this, textOnBoard
    // REQUIRES: textOnBoard is not null
    private boolean doMoveText(Text textOnBoard) {
        System.out.printf("I found \"%s\" at position (%d, %d) on the whiteboard.\n",
                textOnBoard.getText(),
                textOnBoard.getXcoord(),
                textOnBoard.getYcoord());
        System.out.println("Is this the text you want to move? Enter \"yes\" without quotation marks if so.");
        if (!scanner.next().equalsIgnoreCase("yes")) {
            return false;
        }

        System.out.println("Where should I move it? Enter the x-coordinate, then the y-coordinate.");

        int xinput = scanner.nextInt();
        while (xinput < 0 || xinput >= board.getWidth()) {
            System.out.println("That's off your whiteboard!\nPlease enter a new x-coordinate.");
            xinput = scanner.nextInt();
        }

        int yinput = scanner.nextInt();
        while (yinput < 0 || yinput >= board.getHeight()) {
            System.out.println("That's off your whiteboard!\nPlease enter a new y-coordinate.");
            yinput = scanner.nextInt();
        }

        board.moveText(textOnBoard, xinput, yinput);
        return true;
    }

    // EFFECTS: prompts the user to determine which text they would like to delete and removes it from the whiteboard
    // MODIFIES: this
    private void deleteTextOnBoard() {
        System.out.println("What text would you like to delete from your whiteboard?");
        String text = scanner.next();

        Text textOnBoard;
        for (int i = 0; i < board.getNumTextLinesOnBoard(); i++) {
            textOnBoard = board.getTextAtIndex(i);
            if (textOnBoard.getText().equals(text)) {
                System.out.printf("I found \"%s\" at position (%d, %d) on the whiteboard.\n",
                        textOnBoard.getText(),
                        textOnBoard.getXcoord(),
                        textOnBoard.getYcoord());
                System.out.println("Is this the text you want to delete? Enter \"yes\" without quotation marks if so.");
                if (scanner.next().equalsIgnoreCase("yes")) {
                    board.removeText(textOnBoard);
                    break;
                } else {
                    System.out.println("Okay, I'll look for another line.");
                }
            }
        }
        System.out.println("I couldn't find that line of text on your whiteboard.");
    }

    // EFFECTS: prompts the user for new whiteboard dimensions and then resizes the whiteboard
    // MODIFIES: this
    private void resizeWhiteboard() {
        System.out.printf("Your whiteboard is currently %d wide and %d tall.\n",
                board.getWidth(),
                board.getHeight());
        System.out.println("Please enter the new whiteboard width, then the new height.");

        int widthInput = scanner.nextInt();
        while (widthInput <= 0) {
            System.out.println("Your whiteboard has to be at least 1 entry wide. Please enter a new width.");
            widthInput = scanner.nextInt();
        }

        int heightInput = scanner.nextInt();
        while (heightInput <= 0) {
            System.out.println("Your whiteboard has to be at least 1 entry tall. Please enter a new height.");
            heightInput = scanner.nextInt();
        }

        board.setWidth(widthInput);
        board.setHeight(heightInput);
    }

    // EFFECTS: saves the current whiteboard for later use
    private void saveWhiteboard() {
        System.out.println("What name would you like to save this whiteboard under?");
        String name = scanner.next();
        JsonWriter writer = new JsonWriter("./data/" + name + ".json");
        try {
            writer.open();
        } catch (FileNotFoundException e) {
            System.out.println("That filename is invalid.");
            return;
        }
        writer.write(board);
        writer.close();
        System.out.println("Your whiteboard has been saved as " + name + ".json in the data directory.");
    }

    // EFFECTS: opens a popup GUI to save the current whiteboard
    public void openSaveWhiteboardGUI() {
        JFrame saveFrame = new JFrame();
        JFileChooser fileChooser = new JFileChooser("./data");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON Files", "json");
        fileChooser.setFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setDialogTitle("Save as...");
        if (fileChooser.showSaveDialog(saveFrame) == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            if (!filePath.endsWith(".json")) {
                filePath = filePath + ".json";
            }
            JsonWriter writer = new JsonWriter(filePath);
            try {
                writer.open();
            } catch (FileNotFoundException e) {
                System.out.println("That filename is invalid.");
                return;
            }
            writer.write(board);
            writer.close();
        }
    }

    // EFFECTS: loads a saved whiteboard, giving the user the option to save if they forgot to first
    // MODIFIES: this
    private void loadWhiteboard() {
        System.out.println("What is the name of the whiteboard you would like to load?");
        System.out.println("If you forgot to save your current whiteboard and wish to do so, enter | (vertical line)");
        String name = scanner.next();

        // Use "|" here because it is an invalid character for a file name, so this won't restrict choice of filename
        if (name.equals("|")) {
            saveWhiteboard();
            System.out.println("Now, what is the name of the whiteboard you would like to load?");
            name = scanner.next();
        }

        JsonReader reader = new JsonReader("./data/" + name + ".json");
        try {
            Whiteboard temporaryWhiteboard = reader.read();
            // No error, replace current whiteboard
            board = temporaryWhiteboard;
            System.out.println("Loaded " + name + ".json successfully.");
        } catch (IOException e) {
            System.out.println("I couldn't find a file with that name in the data directory.");
        }
    }

    // EFFECTS: loads a popup GUI to load a saved whiteboard
    // MODIFIES: this
    public void openLoadWhiteboardGUI() {
        JFrame loadFrame = new JFrame();
        JFileChooser fileChooser = new JFileChooser("./data");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON Files", "json");
        fileChooser.setFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setDialogTitle("Load...");
        if (fileChooser.showOpenDialog(loadFrame) == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            filePath = filePath.endsWith(".json") ? filePath : filePath + ".json";
            JsonReader reader = new JsonReader(filePath);
            try {
                Whiteboard temporaryWhiteboard = reader.read();
                board = temporaryWhiteboard;
                panel.setBoard(board);
                panel.drawWhiteboardPanel();
                pack();
            } catch (IOException e) {
                System.out.println("I couldn't find a file with that name in the data directory.");
            }
        }
    }
}
