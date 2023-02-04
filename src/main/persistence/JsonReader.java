package persistence;

import model.Whiteboard;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads workroom from JSON data stored in file
// This class is based on a demo given in class (taken October 24, 2022)
public class JsonReader {
    private String filePath;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String filePath) {
        this.filePath = filePath;
    }

    // EFFECTS: reads whiteboard from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Whiteboard read() throws IOException {
        String jsonData = readFile(filePath);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWhiteboard(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses whiteboard from JSON object and returns it
    private Whiteboard parseWhiteboard(JSONObject jsonObject) {
        int width = jsonObject.getInt("width");
        int height = jsonObject.getInt("height");
        JSONArray textLines = jsonObject.getJSONArray("textLines");

        Whiteboard board = new Whiteboard(width, height);

        for (int index = 0; index < textLines.length(); index++) {
            addText(board, textLines.getJSONObject(index));
        }

        return board;
    }

    // EFFECTS: parses text from JSON object and adds it to whiteboard
    // MODIFIES: board
    private void addText(Whiteboard board, JSONObject jsonObject) {
        int xcoord = jsonObject.getInt("x");
        int ycoord = jsonObject.getInt("y");
        String text = jsonObject.getString("text");
        board.addText(text, xcoord, ycoord);
    }
}