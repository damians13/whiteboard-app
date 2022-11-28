package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TextTest {
    private Text text;

    @BeforeEach
    public void setup() {
        EventLog.getInstance().clear();
        text = new Text("foo", 3, 4);
    }

    @Test
    public void testText() {
        assertEquals("foo", text.getText());
        assertEquals(3, text.getXcoord());
        assertEquals(4, text.getYcoord());
        EventTest.assertLastEventDescriptionEquals("New text \"foo\" created at (3, 4)");
    }

    @Test
    public void testSetText() {
        text.setText("bar");
        assertEquals("bar", text.getText());
        EventTest.assertLastEventDescriptionEquals("Text \"foo\" updated to \"bar\"");
    }

    @Test
    public void testSetTextEmpty() {
        text.setText("");
        assertEquals("", text.getText());
        EventTest.assertLastEventDescriptionEquals("Text \"foo\" updated to \"\"");
    }

    @Test
    public void testSetXcoord() {
        text.setXcoord(5);
        assertEquals(5, text.getXcoord());
        EventTest.assertLastEventDescriptionEquals("Text \"foo\" x-coordinate changed from 3 to 5");

        text.setXcoord(0);
        assertEquals(0, text.getXcoord());
        EventTest.assertLastEventDescriptionEquals("Text \"foo\" x-coordinate changed from 5 to 0");
    }

    @Test
    public void testSetYcoord() {
        text.setYcoord(5);
        assertEquals(5, text.getYcoord());
        EventTest.assertLastEventDescriptionEquals("Text \"foo\" y-coordinate changed from 4 to 5");

        text.setYcoord(0);
        assertEquals(0, text.getYcoord());
        EventTest.assertLastEventDescriptionEquals("Text \"foo\" y-coordinate changed from 5 to 0");
    }

    @Test
    public void testToJson() {
        JSONObject expected = new JSONObject();
        expected.put("x", 3);
        expected.put("y", 4);
        expected.put("text", "foo");
        assertTrue(expected.similar(text.toJson()));
    }
}
