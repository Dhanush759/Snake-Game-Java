package src;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Score {
    private int score;

 // Constructor
    public Score(){
        this.score=0;
    }

 // Add score
    public void increaseScore(){
        this.score++;
    }

 // Reset the score
    public void resetScore(){
        this.score=0;
    }

 // To return the score value to the Gameplay display
    public int getScore(){
        return this.score;
    }

 // Function to retrieve the High Score
    public String getHighScore() {
        FileReader readFile = null;
        BufferedReader reader = null;
        try {
        	// Read the file `highscore.dat`
            readFile = new FileReader("highscore.dat");
            reader = new BufferedReader(readFile);

            String line = reader.readLine();
            String allLines = line;

            while (line != null) {
            	// Read line by line
                line = reader.readLine();
             // This is for error handling
                if (line == null)
                    break;
             // Combine the lines
                allLines = allLines.concat("\n" + line);
            }

         // Return a string exactly matching the content of `highscore.dat`
            return allLines;
        }
     // If `highscore.dat` does not exist
        catch (Exception e) {
            return "0\n0\n0\n0\n0\n0\n0\n0\n0\n0";
        } finally {
            try {
            	// Close the reader
                if (reader != null)
                    reader.close();
            } // If an exception occurs
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

 // Function to sort the high scores
    public void sortHighScore() {
        FileReader readFile = null;
        BufferedReader reader = null;
        FileWriter writeFile = null;
        BufferedWriter writer = null;
        List<Integer> list = new ArrayList<Integer>();
        try {
            readFile = new FileReader("highscore.dat");
            reader = new BufferedReader(readFile);

            String line = reader.readLine();

         // Move the content of `highscore.dat` into an array list
            while (line != null) {
                list.add(Integer.parseInt(line));
                line = reader.readLine();
            }

         // Sort the array list
            Collections.sort(list);

            // Reverse it to make it descending order
            Collections.reverse(list);

         // Write the sorted scores back to `highscore.dat`
            writeFile = new FileWriter("highscore.dat");
            writer = new BufferedWriter(writeFile);

            int size = list.size();

         // Only the top 10 scores will be written back
            for (int i = 0; i < 10; i++) {
            	// This is to fill other scores with 0
                if (i > size - 1) {
                    String def = "0";
                    writer.write(def);
                } else { // Retrieve scores one by one from the list
                    String str = String.valueOf(list.get(i));
                    writer.write(str);
                }
                if (i < 9) {// This prevents creating a blank line at the end of the file**
                    writer.newLine();
                }
            }
        } catch (Exception e) {
            return;
        } finally {
            try {
            	// Close the reader
                if (reader != null)
                    reader.close();
             // Close the writer
                if (writer != null)
                    writer.close();
            } // If an exception occurs
            catch (IOException e) {
                return;
            }
        }

    }

 // Function to write a new score to the file
    public void saveNewScore() {
        String newScore = String.valueOf(this.getScore());

        // Create a file to save the high scores
        File scoreFile = new File("highscore.dat");

     // If the file `highscore.dat` does not exist
        if (!scoreFile.exists()) {
            try {
            	// Create a new file
                scoreFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileWriter writeFile = null;
        BufferedWriter writer = null;

        try {
        	// Write the new score to the file
            writeFile = new FileWriter(scoreFile, true);
            writer = new BufferedWriter(writeFile);
            writer.write(newScore);
        } catch (Exception e) {
            return;
        } finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (Exception e) {
                return;
            }
        }

    }
}
