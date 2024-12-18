package src;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.Font;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    // Instantiate the snake object
    Snake snake = new Snake();

    // Instantiate the apple object
    Apple apple = new Apple();

    // Create the snake head image
    private ImageIcon snakeHead;

    private Timer timer;
    private int delay = 100;
    private ImageIcon snakeBody;

    AtomicBoolean speedUp = new AtomicBoolean(true);

    // Coordinates for the snake's head location
    private int snakeHeadXPos = 379;

    // Create the apple image
    private ImageIcon appleImage;

 // To generate random numbers
    private Random random = new Random();

    private int xPos = random.nextInt(100);
    private int yPos = random.nextInt(100);

 // Create the game title
    private ImageIcon titleImage;

 // Create the score display
    Score score = new Score();

 // Create the high score display
    private String highScore;

 // To display the controller instructions
    private ImageIcon arrowImage;
    private ImageIcon shiftImage;

    public Gameplay() {
    	// Check if the game has started
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {
    	// Border for the title
        if (snake.moves == 0) {
            for (int i = 0; i < 5; i++) {
                snake.snakexLength[i] = snakeHeadXPos;
                snakeHeadXPos -= 6;
                snake.snakeyLength[i] = 355;
            }
        }

     // Title
        g.setColor(Color.WHITE);
        g.drawRect(24, 10, 852, 55);

        
        titleImage = new ImageIcon("images/title.png");
        titleImage.paintIcon(this, g, 25, 11);

     // Border for the gameplay area
        g.setColor(Color.WHITE);
        g.drawRect(24, 71, 620, 614);

     // Background for the gameplay area
        g.setColor(Color.black);
        g.fillRect(25, 72, 619, 613);

     // Border for the leaderboard area
        g.setColor(Color.WHITE);
        g.drawRect(653, 71, 223, 614);

     // Background for the leaderboard area
        g.setColor(Color.black);
        g.fillRect(654, 72, 221, 613);

     // Display the score
        g.setColor(Color.white);
        g.setFont(new Font("Helvetica", Font.BOLD, 20));
        g.drawString("SCORE : " + score.getScore(), 720, 110);
        g.drawRect(653, 130, 221, 1);

     // Display the high score
        score.sortHighScore();
        highScore = score.getHighScore();
        g.drawString("HIGHSCORE", 705, 180);
        drawString(g, highScore, 705, 200);

     // Display the controller instructions
        g.drawRect(653, 490, 221, 1);
        g.setFont(new Font("Helvetica", Font.BOLD, 25));
        g.drawString("CONTROLS", 690, 530);

        arrowImage = new ImageIcon("images/keyboardArrow.png");
        arrowImage.paintIcon(this, g, 670, 560);
        g.setFont(new Font("Helvetica", Font.PLAIN, 16));
        g.drawString("Movement", 770, 590);

        shiftImage = new ImageIcon("images/shift.png");
        shiftImage.paintIcon(this, g, 695, 625);
        g.drawString("Boost", 770, 640);

     // Instantiate the image for the snake's head
        snakeHead = new ImageIcon("images/snakeHead4.png");
        snakeHead.paintIcon(this, g, snake.snakexLength[0], snake.snakeyLength[0]);

        for (int i = 0; i < snake.lengthOfSnake; i++) {
            if (i == 0 && (snake.right || snake.left || snake.up || snake.down)) {
                snakeHead = new ImageIcon("images/snakeHead4.png");
                snakeHead.paintIcon(this, g, snake.snakexLength[i], snake.snakeyLength[i]);
            }
            if (i != 0) {
                snakeBody = new ImageIcon("images/snakeimage4.png");
                snakeBody.paintIcon(this, g, snake.snakexLength[i], snake.snakeyLength[i]);
            }
        }

        appleImage = new ImageIcon("images/apple4.png");

     // If the snake eats the apple
        if ((apple.applexPos[xPos]) == snake.snakexLength[0] && (apple.appleyPos[yPos] == snake.snakeyLength[0])) {
            snake.lengthOfSnake++;
            score.increaseScore();
            xPos = random.nextInt(100);
            yPos = random.nextInt(100);

         // Speed up the snake every time the score reaches a multiple of 10
            if (score.getScore() % 5 == 0 && score.getScore()!= 0){
                if(delay > 100){
                    delay = delay - 100;
                }
                else if (delay == 100){
                    delay = delay - 50;
                }
                else if (delay <= 50 && delay > 20){
                    delay = delay - 10;
                }
                else {
                    delay = 20;
                }
                timer.setDelay(delay);
            }
        }

     // Before the user presses the spacebar, the apple is hidden
        if (snake.moves != 0) {
            appleImage.paintIcon(this, g, apple.applexPos[xPos], apple.appleyPos[yPos]);
        }

     // Display the text "Press Spacebar to Start the Game!"
        if (snake.moves == 0) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Courier New", Font.BOLD, 26));
            g.drawString("Press Spacebar to Start the Game!", 70, 300);
        }

     // Check if the snake's head collides with its body
        for (int i = 1; i < snake.lengthOfSnake; i++) {
        	// Check if the snake is dead
            if (snake.snakexLength[i] == snake.snakexLength[0] && snake.snakeyLength[i] == snake.snakeyLength[0]) {
            	// Method to display multi-line text on the screen using \n
                snake.dead();
            }
        }

        if (snake.death) {
            score.saveNewScore();

            g.setColor(Color.RED);
            g.setFont(new Font("Courier New", Font.BOLD, 50));
            g.drawString("Game Over!", 190, 340);

            g.setColor(Color.GREEN);
            g.setFont(new Font("Courier New", Font.BOLD, 18));
            g.drawString("Your Score : " + score.getScore(), 250, 370);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Courier New", Font.BOLD, 20));
            g.drawString("Press Spacebar to restart!", 187, 400);
        }
        g.dispose();
    }

    public void drawString(Graphics g, String text, int x, int y) {
        for (String line : text.split("\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	// TODO Auto-generated method stub
        timer.start();

     // For snake movement
        // Move the snake to the right
        if (snake.right) {
             snake.movementRight();
            repaint();
        }
     // Move the snake to the left
        if (snake.left) {
            snake.movementLeft();
            repaint();
        }
     // Move the snake upward
        if (snake.up) {
            snake.movementUp();
            repaint();
        }
     // Move the snake downward
        if (snake.down) {
            snake.movementDown();
            repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    	// TODO Auto-generated method stub

    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        snake.drawSnake(g);
    }

    @Override
    public void keyPressed(KeyEvent e) {
    	// Handle key press events
        switch (e.getKeyCode()) {
     // If the user presses the shift key
            case KeyEvent.VK_SHIFT:
                if (speedUp.compareAndSet(true, false)) {
                    if (delay > 100) {
                        timer.setDelay(delay/10);
                    }
                    else {
                        timer.setDelay(10);
                    }
                }
                break;
             
            case KeyEvent.VK_SPACE:
            	// If the user presses the spacebar
                if (snake.moves == 0) {
                    snake.moves++;
                    snake.right = true;
                }
             // Restart the game after dying
                if (snake.death) {
                    snake.moves = 0;
                    snake.lengthOfSnake = 5;
                    score.resetScore();
                    repaint();
                    snake.death = false;
                }
                break;
             // If the user presses the right arrow key
            case KeyEvent.VK_RIGHT:
            	// Call the function in the Snake class to move right
                snake.moveRight();
                break;
             // If the user presses the left arrow key
            case KeyEvent.VK_LEFT:
            	// Call the function in the Snake class to move left
                snake.moveLeft();
                break;
             // If the user presses the up arrow key
            case KeyEvent.VK_UP:
            	// Call the function in the Snake class to move upward
                snake.moveUp();
                break;
             // If the user presses the down arrow key
            case KeyEvent.VK_DOWN:
            	// Call the function in the Snake class to move downward
                snake.moveDown();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // If the user releases the shift key
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            speedUp.set(true);
            timer.setDelay(delay);
        }
    }

}
