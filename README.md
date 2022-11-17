# My Personal Project
## Project Proposal

The project that I propose to design this term is a whiteboard application. This application will allow the user to
place, edit, move, and delete lines of text that they have written on the whiteboard. I find this project interesting 
because I often wish I had a whiteboard nearby to organize my thoughts while I am working, and with this application I 
could have a **simple** whiteboard *anywhere I have my laptop*. This project is intended for anyone looking to organize
their thoughts on a **simple** whiteboard. This could include students, teachers, anyone giving a presentation,
and more.

## User Stories
Here are the user stories for my project:
- As a user, I want to be able to write some text on a whiteboard
- As a user, I want to be able to have multiple, independent fields of text on a whiteboard
- As a user, I want to be able to move some text that I have written on a whiteboard around
- As a user, I want to be able to edit or delete some text that I have written on a whiteboard
- As a user, I want to be able to resize my whiteboard
- As a user, I want to be able to save the state of my whiteboard (text and positions, size of whiteboard)
- As a user, I want to be able to open a whiteboard I have previously saved

## Instructions for Grader
- You can generate the first required event related to adding Xs to a Y by clicking on an empty space on the whiteboard,
entering some text, and clicking "OK". This will add text to the whiteboard and can be repeated many times
- You can generate the second required event related to adding Xs to a Y by clicking and dragging some text that you
have added to the whiteboard. This will move the text around the whiteboard
- You can (optionally) generate a third event related to adding Xs to a Y by clicking on some text that you have added
to the whiteboard. This will prompt you to change the text at this spot on the whiteboard. Note that leaving this prompt
blank or exclusively whitespace will delete the text object from the application. Enter your desired text and click "OK"
- You can locate my visual component by looking at the background of the whiteboard (it is a .png image of an actual
whiteboard)
- You can save the state of my application by clicking the "Save" button on the right, entering a name for your save
file, and clicking "Save"
- You can reload the state of my application by clicking the "Load" button on the right, selecting the save-file you
would like to open, and clicking "Open"