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
        text = new Text("foo", 3, 4);
    }

    @Test
    public void testText() {
        assertEquals("foo", text.getText());
        assertEquals(3, text.getXcoord());
        assertEquals(4, text.getYcoord());
    }

    @Test
    public void testSetText() {
        text.setText("bar");
        assertEquals("bar", text.getText());
    }

    @Test
    public void testSetTextEmpty() {
        text.setText("");
        assertEquals("", text.getText());
    }

    @Test
    public void testSetXcoord() {
        text.setXcoord(5);
        assertEquals(5, text.getXcoord());

        text.setXcoord(0);
        assertEquals(0, text.getXcoord());
    }

    @Test
    public void testSetYcoord() {
        text.setYcoord(5);
        assertEquals(5, text.getYcoord());

        text.setYcoord(0);
        assertEquals(0, text.getYcoord());
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
