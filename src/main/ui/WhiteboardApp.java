package ui;

import model.Text;
import model.Whiteboard;

import java.util.ArrayList;
import java.util.Scanner;

// Whiteboard application
public class WhiteboardApp {
    private Whiteboard board;
    private Scanner scanner;
    private boolean shouldRun;

    // EFFECTS: run the whiteboard application
    public WhiteboardApp() {
        runWhiteboardApp();
    }

    // EFFECTS: handles the main loop of the application
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
    private void displayWhiteboard(Whiteboard board) {
        ArrayList<ArrayList<String>> boardText = new ArrayList<ArrayList<String>>();
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
}
