package persistence;

import model.Whiteboard;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;


// Represents a writer that writes JSON representation of workroom to file
// This class is based on a demo given in class (taken October 24, 2022)
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String filePath;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String filePath) {
        this.filePath = filePath;
    }

    // EFFECTS: opens writer and throws FileNotFoundException if destination file cannot be opened for writing
    // MODIFIES: this
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(filePath));
    }

    // EFFECTS: writes JSON representation of whiteboard to file
    // MODIFIES: this
    public void write(Whiteboard board) {
        JSONObject json = board.toJson();
        saveToFile(json.toString(TAB));
    }

    // EFFECTS: closes the writer
    // MODIFIES: this
    public void close() {
        writer.close();
    }

    // EFFECTS: writes a json string to file
    // MODIFIES: this
    private void saveToFile(String json) {
        writer.print(json);
    }
}