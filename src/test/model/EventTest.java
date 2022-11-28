package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the Event
 * This class was taken from
 * https://github.students.cs.ubc.ca/CPSC210/AlarmSystem/blob/main/src/test/ca/ubc/cpsc210/alarm/test/EventLogTest.java
 * on November 25, 2022
 */
public class EventTest {
    private Event e;
    private Date d;

    //NOTE: these tests might fail if time at which line (2) below is executed
    //is different from time that line (1) is executed.  Lines (1) and (2) must
    //run in same millisecond for this test to make sense and pass.

    @BeforeEach
    public void runBefore() {
        e = new Event("Sensor open at door");   // (1)
        d = Calendar.getInstance().getTime();   // (2)
    }

    @Test
    public void testEvent() {
        assertEquals("Sensor open at door", e.getDescription());
        assertEquals(d, e.getDate());
    }

    @Test
    public void testToString() {
        assertEquals(d.toString() + "\n" + "Sensor open at door", e.toString());
    }

    // This is a static method to help test that events are logged with their expected descriptions
    public static void assertLastEventDescriptionEquals(String expected) {
        Iterator<Event> iterator = EventLog.getInstance().iterator();
        Event last = new Event("");
        while (iterator.hasNext()) {
            last = iterator.next();
        }
        assertEquals(expected, last.getDescription());
    }

    // This is a static method to help test that events are logged with their expected descriptions
    public static void assertSecondLastEventDescriptionEquals(String expected) {
        Iterator<Event> iterator = EventLog.getInstance().iterator();
        Event last = new Event("");
        Event secondLast = new Event("");
        while (iterator.hasNext()) {
            secondLast = last;
            last = iterator.next();
        }
        assertEquals(expected, secondLast.getDescription());
    }
}