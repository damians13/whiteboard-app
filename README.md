# Whiteboard App
## Purpose

This application was created as an academic project while learning JUnit testing, design patterns, and object-oriented
programming in Java.

## Usage
- You can add text to the whiteboard by clicking on an empty space on the whiteboard, entering some text, and clicking
"OK". This will add text to the whiteboard and can be repeated many times
- You can move text around the whiteboard by clicking and dragging some text that you have added to the whiteboard. This
will move the text around the whiteboard
- You can rename or delete text on the whiteboard by clicking on some text that you have added to the whiteboard. This
will prompt you to change the text at this spot on the whiteboard. Note that leaving this prompt blank or exclusively
whitespace will delete the text object from the application. Enter your desired text and click "OK"
- You can save the state of the whiteboard by clicking the "Save" button on the right, entering a name for your save
file, and clicking "Save"
- You can reload the state of a saved whiteboard by clicking the "Load" button on the right, selecting the save-file you
would like to open, and clicking "Open"

## TO-DO
- WhiteboardApp does not need a Whiteboard field (and probably shouldn't have one) since it has a WhiteboardPanel field
  which gives the GUI representation of the Whiteboard. I would remove this association and get the Whiteboard object from
  the WhiteboardPanel field wherever I need it
- I don't like how ButtonActionListener needs a reference to the WhiteboardApp. I would delete this class and replace
  references to it with an anonymous class implementing the ActionListener interface. This would overflow the method
  length limit, so I would extract the code related to the save and load buttons to a private helper method
- The WhiteboardApp class could be split into two classes to follow the single responsibility principle. Currently,
  this class has methods to run both the GUI (phase 3) and CLI (phase 2) versions of the application, which should be
  separated. This refactor would result in two classes which are each more cohesive than the original class
- To reduce coupling between whiteboard and text (and also their GUI components), an interface/abstract class could be
added which would outline the basic requirements for something to be on the whiteboard (examples could be toJson(), x
and y coordinate fields, mouse drag event listener, etc.). This would also allow for things other than text to be added
to the whiteboard in the future