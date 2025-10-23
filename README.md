# Simple Media Player (Laboratory2)

This project demonstrates structural design patterns in a modular Java media player system.

## How to Run the Project

### Prerequisites
- Java Development Kit (JDK) installed.

### Using Command Line (PowerShell or CMD)
1. Navigate to the project root directory.
2. Compile the Java files:
   ```powershell
   javac -d out src\*.java
   ```
3. Run the demo:
   ```powershell
   java -cp out Main
   ```

### Using an IDE (e.g., IntelliJ IDEA, Eclipse, VS Code)
1. Open the project folder as a Java project.
2. Compile and run `Main.java` as the entry point.

## Running Tests
This project does not include automated unit tests. To verify functionality, run the demo as described above and interact with the command-line interface to test different media sources, plugins, and renderers.

## Demo Commands
After running the application, follow the prompts to:
- Choose a media source (1 for Local File, 2 for HLS Stream, 3 for Remote API).
- Enter a media identifier or press Enter for defaults.
- Select plugins (e.g., "subtitle, equalizer").
- Choose a renderer ("hardware" or "software").
- The player will simulate playback with the selected options.
