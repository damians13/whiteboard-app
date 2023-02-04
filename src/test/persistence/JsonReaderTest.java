package persistence;

import model.Text;
import model.Whiteboard;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest {
    private JsonReader reader;
    private Whiteboard board;

    @Test
    public void testReadWhiteboard() {
        reader = new JsonReader("./data/whiteboard1.json");
        try {
            board = reader.read();
        } catch(IOException e) {
            fail("Couldn't read emptyWhiteboard.json");
        }
        Whiteboard expected = new Whiteboard(8, 4);
        expected.addText("Hello, class!", 0, 0);
        expected.addText("hi", 5, 3);

        assertEquals(expected.getWidth(), board.getWidth());
        assertEquals(expected.getHeight(), board.getHeight());
        assertEquals(expected.getNumTextLinesOnBoard(), board.getNumTextLinesOnBoard());

        for (int index = 0; index < expected.getNumTextLinesOnBoard(); index++) {
            Text expectedText = expected.getTextAtIndex(index);
            Text actualText = board.getTextAtIndex(index);

            assertEquals(expectedText.getXcoord(), actualText.getXcoord());
            assertEquals(expectedText.getYcoord(), actualText.getYcoord());
            assertEquals(expectedText.getText(), actualText.getText());
        }
    }

    @Test
    public void testReadEmptyWhiteboard() {
        reader = new JsonReader("./data/emptyWhiteboard.json");
        try {
            board = reader.read();
        } catch(IOException e) {
            fail("Couldn't read emptyWhiteboard.json");
        }
        Whiteboard expected = new Whiteboard(32, 20);

        assertEquals(expected.getWidth(), board.getWidth());
        assertEquals(expected.getHeight(), board.getHeight());
        assertEquals(0, board.getNumTextLinesOnBoard());
    }

    @Test
    public void testReadInvalidFile() {
        reader = new JsonReader("./data/kjhsdfgkjfhds bkjvbhsdkjbhvkdjhvgfkdg,ghfdsgkvh.json");
        try {
            board = reader.read();
            fail("Should've thrown IOException");
        } catch (IOException e) {
            // Good
        }
    }
}