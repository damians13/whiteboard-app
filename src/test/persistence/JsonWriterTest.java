package persistence;

import model.Text;
import model.Whiteboard;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonWriterTest {
    private JsonWriter writer;
    private JsonReader reader;
    private Whiteboard writeBoard;
    private Whiteboard readBoard;

    @Test
    public void testWriteWhiteboard() {
        writer = new JsonWriter("./data/whiteboard1.json");
        reader = new JsonReader("./data/whiteboard1.json");
        writeBoard = new Whiteboard(8, 4);

        writeBoard.addText("Hello, CPSC 210!", 0, 0);
        writeBoard.addText("hi", 5, 3);

        try {
            writer.open();
        } catch (FileNotFoundException e) {
            fail("Couldn't write whiteboard1.json");
        }
        writer.write(writeBoard);
        writer.close();

        try {
            readBoard = reader.read();
        } catch (IOException e) {
            fail("Couldn't read whiteboard1.json");
        }

        assertEquals(writeBoard.getWidth(), readBoard.getWidth());
        assertEquals(writeBoard.getHeight(), readBoard.getHeight());
        assertEquals(writeBoard.getNumTextLinesOnBoard(), readBoard.getNumTextLinesOnBoard());

        for (int index = 0; index < writeBoard.getNumTextLinesOnBoard(); index++) {
            Text expectedText = writeBoard.getTextAtIndex(index);
            Text actualText = readBoard.getTextAtIndex(index);

            assertEquals(expectedText.getXcoord(), actualText.getXcoord());
            assertEquals(expectedText.getYcoord(), actualText.getYcoord());
            assertEquals(expectedText.getText(), actualText.getText());
        }
    }

    @Test
    public void testWriteEmptyWhiteboard() {
        writer = new JsonWriter("./data/emptyWhiteboard.json");
        reader = new JsonReader("./data/emptyWhiteboard.json");
        writeBoard = new Whiteboard(32, 20);

        try {
            writer.open();
        } catch (FileNotFoundException e) {
            fail("Couldn't write emptyWhiteboard.json");
        }
        writer.write(writeBoard);
        writer.close();

        try {
            readBoard = reader.read();
        } catch(IOException e) {
            fail("Couldn't read emptyWhiteboard.json");
        }

        assertEquals(writeBoard.getWidth(), readBoard.getWidth());
        assertEquals(writeBoard.getHeight(), readBoard.getHeight());
        assertEquals(0, readBoard.getNumTextLinesOnBoard());
    }

    @Test
    public void testWriteInvalidFile() {
        writer = new JsonWriter("./data/|invalid;.json");
        try {
            writer.open();
            fail("Should've thrown FileNotFoundException");
        } catch (FileNotFoundException e) {
            // Good
        }
    }
}
