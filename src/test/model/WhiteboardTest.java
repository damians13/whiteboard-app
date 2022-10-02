package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WhiteboardTest {
    private Whiteboard board;

    @BeforeEach
    public void setup() {
        board = new Whiteboard(32, 20);
    }

    @Test
    public void testWhiteboard() {
        assertEquals(0, board.getNumTextLinesOnBoard());
        assertEquals(32, board.getWidth());
        assertEquals(20, board.getHeight());
    }

    @Test
    public void test1By1Whiteboard() {
        Whiteboard oneByOne = new Whiteboard(1, 1);
        assertEquals(0, oneByOne.getNumTextLinesOnBoard());
        assertEquals(1, oneByOne.getWidth());
        assertEquals(1, oneByOne.getHeight());
    }

    @Test
    public void testAddSingleText() {
        board.addText("Hello, world!", 0, 13);

        Text line = board.getTextAtIndex(0);

        assertEquals("Hello, world!", line.getText());
        assertEquals(0, line.getXcoord());
        assertEquals(13, line.getYcoord());
    }
    @Test
    public void testMultipleText() {
        // Test after repeated additions, including corners
        board.addText("Hello, world!", 0, 13);
        board.addText("", 6, 20);
        board.addText("foo", 0, 0);
        board.addText("bar", 32, 20);

        Text line = board.getTextAtIndex(0);
        Text secondLine = board.getTextAtIndex(1);
        Text foo = board.getTextAtIndex(2);
        Text bar = board.getTextAtIndex(3);

        assertEquals("Hello, world!", line.getText());
        assertEquals(0, line.getXcoord());
        assertEquals(13, line.getYcoord());

        assertEquals("", secondLine.getText());
        assertEquals(6, secondLine.getXcoord());
        assertEquals(20, secondLine.getYcoord());

        assertEquals("foo", foo.getText());
        assertEquals(0, foo.getXcoord());
        assertEquals(0, foo.getYcoord());

        assertEquals("bar", bar.getText());
        assertEquals(32, bar.getXcoord());
        assertEquals(20, bar.getYcoord());
    }

    @Test
    public void testRemoveSingleText() {
        board.addText("foo", 1, 13);
        board.removeText("foo");
        assertEquals(0, board.getNumTextLinesOnBoard());
    }

    @Test
    public void testRemoveSingleDuplicateText() {
        board.addText("foo", 1, 13);
        board.addText("foo", 4, 4);
        board.removeText("foo");

        assertEquals(1, board.getNumTextLinesOnBoard());
        Text remaining = board.getTextAtIndex(0);
        assertEquals("foo", remaining.getText());
        assertEquals(4, remaining.getXcoord());
        assertEquals(4, remaining.getYcoord());
    }

    @Test
    public void testRemoveSingleTextFromMultiple() {
        board.addText("foo", 1, 13);
        board.addText("bar", 6, 7);
        board.addText("baz", 13, 5);
        // Remove middle entry
        board.removeText("bar");
        assertEquals(2, board.getNumTextLinesOnBoard());
        Text firstLine = board.getTextAtIndex(0);
        Text secondLine = board.getTextAtIndex(1);

        assertEquals("foo", firstLine.getText());
        assertEquals(1, firstLine.getXcoord());
        assertEquals(13, firstLine.getYcoord());

        assertEquals("baz", secondLine.getText());
        assertEquals(13, secondLine.getXcoord());
        assertEquals(5, secondLine.getYcoord());
    }

    @Test
    public void testRemoveAllTextFromMultiple() {
        board.addText("foo", 1, 13);
        board.addText("bar", 6, 7);
        board.addText("baz", 13, 5);

        board.removeText("foo");
        board.removeText("bar");
        board.removeText("baz");

        assertEquals(0, board.getNumTextLinesOnBoard());
    }

    @Test
    public void testMoveText() {
        board.addText("foo", 1, 13);
        Text foo = board.getTextAtIndex(0);
        board.moveText(foo, 5, 6);

        assertEquals(5, foo.getXcoord());
        assertEquals(6, foo.getYcoord());
    }

    @Test
    public void testMoveTextMultipleTimes() {
        board.addText("foo", 1, 13);
        Text foo = board.getTextAtIndex(0);
        board.moveText(foo, 5, 6);
        assertEquals(5, foo.getXcoord());
        assertEquals(6, foo.getYcoord());

        board.moveText(foo, 0, 0);
        assertEquals(0, foo.getXcoord());
        assertEquals(0, foo.getYcoord());

        board.moveText(foo, 31, 19);
        assertEquals(31, foo.getXcoord());
        assertEquals(19, foo.getYcoord());
    }

    @Test
    public void testMoveDuplicateText() {
        board.addText("foo", 1, 13);
        board.addText("foo", 4, 3);
        Text foo0 = board.getTextAtIndex(0);
        Text foo1 = board.getTextAtIndex(1);
        board.moveText(foo0, 5, 6);

        assertEquals(5, foo0.getXcoord());
        assertEquals(6, foo0.getYcoord());

        assertEquals(4, foo1.getXcoord());
        assertEquals(3, foo1.getYcoord());
    }

    @Test
    public void testMoveSingleTextFromMultiple() {
        board.addText("foo", 1, 13);
        board.addText("bar", 4, 3);
        board.addText("baz", 5, 6);
        Text foo = board.getTextAtIndex(0);
        Text bar = board.getTextAtIndex(1);
        Text baz = board.getTextAtIndex(1);
        board.moveText(bar, 5, 6);

        assertEquals(1, foo.getXcoord());
        assertEquals(13, foo.getYcoord());

        assertEquals(5, bar.getXcoord());
        assertEquals(6, bar.getYcoord());

        assertEquals(5, baz.getXcoord());
        assertEquals(6, baz.getYcoord());
    }

    @Test
    public void testSetWidth() {
        board.setWidth(40);
        assertEquals(40, board.getWidth());
    }

    @Test
    public void testSetHeight() {
        board.setHeight(50);
        assertEquals(50, board.getHeight());
    }

    @Test
    public void testTextMovesOnBoardResize() {
        board.addText("Testing 1, 2, 3!", 30, 18);
        Text text = board.getTextAtIndex(0);
        board.setHeight(18);
        assertEquals(1, board.getNumTextLinesOnBoard());
        assertEquals(30, text.getXcoord());
        assertEquals(17, text.getYcoord());

        board.setWidth(30);
        assertEquals(1, board.getNumTextLinesOnBoard());
        assertEquals(29, text.getXcoord());
        assertEquals(17, text.getYcoord());

        board.setHeight(10);
        board.setWidth(10);
        assertEquals(9, text.getXcoord());
        assertEquals(9, text.getYcoord());

        board.setHeight(1);
        board.setWidth(1);
        assertEquals(0, text.getXcoord());
        assertEquals(0, text.getYcoord());
    }

}