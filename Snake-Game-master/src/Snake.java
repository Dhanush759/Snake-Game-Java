package src;
import java.awt.Color;
import java.awt.Graphics;
public class Snake {
	// Determine the position of the snake
    int[] snakexLength = new int[750];
    int[] snakeyLength = new int[750];

 // The length of the snake and whether the snake has started moving or not
    int lengthOfSnake;
    int moves;

 // Direction of the snake
    boolean left;
    boolean right;
    boolean up;
    boolean down;

 // Check whether the snake is dead or not
    boolean death;

 // Constructor
    public Snake(){
        this.left=false;
        this.right=false;
        this.up=false;
        this.down=false;
        this.death=false;
        this.lengthOfSnake=6;
        this.moves=0;
    }

 // Move to the right
    public void moveRight(){
        if (this.moves != 0 && !this.death) {
                this.moves++;
                if (!this.left) {
                    this.right = true;
                } else {
                    this.right = false;
                    this.left = true;
                }
                this.up = false;
                this.down = false;
        }
    }

    /// Move to the left
    public void moveLeft(){
        if (this.moves != 0 && !this.death) {
            this.moves++;
            if (!this.right) {
                this.left = true;
            } else {
                this.left = false;
                this.right = true;
            }
            this.up = false;
            this.down = false;
        }
    }

 // Move upwards
    public void moveUp(){
        if (this.moves != 0 && !this.death) {
            this.moves++;
            if (!this.down) {
                this.up = true;
            } else {
                this.up = false;
                this.down = true;
            }
            this.left = false;
            this.right = false;
        }
    }

 // Move downwards
    public void moveDown(){
        if (this.moves != 0 && !this.death) {
            this.moves++;
            if (!this.up) {
                this.down = true;
            } else {
                this.down = false;
                this.up = true;
            }
            this.left = false;
            this.right = false;
        }
    }

 // Function for handling death to avoid redundant code
    public void dead() {
    	// Make the snake unable to move
        this.right = false;
        this.left = false;
        this.up = false;
        this.down = false;
        this.death = true;
    }

 // Snake movement to the right
    public void movementRight(){
    	// Shift the head position to the next index
        for (int i = this.lengthOfSnake - 1; i >= 0; i--) {
        	// Shift the snakeyLength position
            this.snakeyLength[i + 1] = this.snakeyLength[i];
        }
        for (int i = this.lengthOfSnake - 1; i >= 0; i--) {
        	// Shift the snakexLength position
            if (i == 0) {
                this.snakexLength[i] = this.snakexLength[i] + 6;
            } else {
                this.snakexLength[i] = this.snakexLength[i - 1];
            }
         // If it goes past the right edge
            if (this.snakexLength[0] > 637) {
            	// Move the head back inside the board
                this.snakexLength[0] -= 6;
             // Dead
                dead();
            }
        }
    }

 // Snake movement to the left
    public void movementLeft(){
    	// Shift the head position to the next index
        for (int i = this.lengthOfSnake - 1; i >= 0; i--) {
        	// Shift the snakeyLength position
            this.snakeyLength[i + 1] = this.snakeyLength[i];
        }
        for (int i = this.lengthOfSnake - 1; i >= 0; i--) {
        	// Shift the snakexLength position
            if (i == 0) {
                this.snakexLength[i] = this.snakexLength[i] - 6;
            } else {
                this.snakexLength[i] = this.snakexLength[i - 1];
            }
         // If it goes past the left edge
            if (this.snakexLength[0] < 25) {
            	// Move the head back inside the board
                this.snakexLength[0] += 6;
             // Dead
                dead();
            }
        }
    }

 // Snake movement upwards
    public void movementUp(){
    	// Shift the head position to the next index
        for (int i = this.lengthOfSnake - 1; i >= 0; i--) {
        	// Shift the snakexLength position
            this.snakexLength[i + 1] = this.snakexLength[i];
        }
        for (int i = this.lengthOfSnake - 1; i >= 0; i--) {
        	// Shift the snakeyLength position
            if (i == 0) {
                this.snakeyLength[i] = this.snakeyLength[i] - 6;
            } else {
                this.snakeyLength[i] = this.snakeyLength[i - 1];
            }
         // If it goes past the top edge
            if (this.snakeyLength[0] < 73) {
            	// Move the head back inside the board
                this.snakeyLength[0] += 6;
             // Dead
                dead();
            }
        }
    }

 // Snake movement downwards
    public void movementDown(){
    	// Shift the head position to the next index
        for (int i = this.lengthOfSnake - 1; i >= 0; i--) {
        	// Shift the snakexLength position
            this.snakexLength[i + 1] = this.snakexLength[i];
        }
        for (int i = this.lengthOfSnake - 1; i >= 0; i--) {
        	// Shift the snakeyLength position
            if (i == 0) {
                this.snakeyLength[i] = this.snakeyLength[i] + 6;
            } else {
                this.snakeyLength[i] = this.snakeyLength[i - 1];
            }
         // If it goes past the bottom edge
            if (this.snakeyLength[0] > 679) {
            	// Move the head back inside the board
                this.snakeyLength[0] -= 6;
                // Dead
                dead();
            }
        }
    }
    public void drawSnake(Graphics g) {
        for (int i = 0; i < lengthOfSnake; i++) {
            if (i == 0) {
                // Draw the head in orange
                g.setColor(Color.ORANGE);
                g.fillRect(snakexLength[i], snakeyLength[i], 6, 6);
            } else {
            	// Draw the body in green
                g.setColor(Color.GREEN);
                g.fillRect(snakexLength[i], snakeyLength[i], 6, 6);
            }
        }
    }
}