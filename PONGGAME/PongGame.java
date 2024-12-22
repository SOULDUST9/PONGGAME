import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * 
 * this Code was developed by Sebastian Janeczek 
 * 
 */ 

public class PongGame extends JPanel implements ActionListener, KeyListener {
    
    //VARS 

    private final int WIDTH = 800, HEIGHT = 600;
    private final int PADDLE_WIDTH = 20;
    private final int PADDLE_HEIGHT = 100
    private final int BALL_SIZE = 20;

    private int player1Y = HEIGHT / 2 - PADDLE_HEIGHT / 2, player2Y = HEIGHT / 2 - PADDLE_HEIGHT / 2;
    private int ballX = WIDTH / 2 - BALL_SIZE / 2, ballY = HEIGHT / 2 - BALL_SIZE / 2;
    private int ballVelX = -2, ballVelY = 2; // Ball movement speed
    private int player1Score = 0, player2Score = 0;

    private Timer timer;

    //GAME LOOP

    public PongGame() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        timer = new Timer(1000 / 60, this); // 60 FPS
        timer.start();
    }

    //gets the points 

    @Override
    public void actionPerformed(ActionEvent e) {
        // Ball movement
        ballX += ballVelX;
        ballY += ballVelY;

        // Ball bouncing off the top and bottom walls
        if (ballY <= 0 || ballY >= HEIGHT - BALL_SIZE) {
            ballVelY = -ballVelY;
        }

        // Ball bouncing off paddles
        if (ballX <= PADDLE_WIDTH && ballY >= player1Y && ballY <= player1Y + PADDLE_HEIGHT) {
            ballVelX = -ballVelX;
        }

        if (ballX >= WIDTH - PADDLE_WIDTH - BALL_SIZE && ballY >= player2Y && ballY <= player2Y + PADDLE_HEIGHT) {
            ballVelX = -ballVelX;
        }

        // Scoring
        if (ballX <= 0) {
            player2Score++;
            resetBall();
        } else if (ballX >= WIDTH - BALL_SIZE) {
            player1Score++;
            resetBall();
        }

        repaint();
    }

    //resets the position of ball

    private void resetBall() {
        ballX = WIDTH / 2 - BALL_SIZE / 2;
        ballY = HEIGHT / 2 - BALL_SIZE / 2;
        ballVelX = -ballVelX; // Change ball direction after scoring
        ballVelY = 2;
    }

    //shows the stuff on screen 

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw paddles
        g.setColor(Color.WHITE);
        g.fillRect(30, player1Y, PADDLE_WIDTH, PADDLE_HEIGHT); // Player 1 paddle
        g.fillRect(WIDTH - 50, player2Y, PADDLE_WIDTH, PADDLE_HEIGHT); // Player 2 paddle

        // Draw ball
        g.fillRect(ballX, ballY, BALL_SIZE, BALL_SIZE);

        // Draw scores
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString(String.valueOf(player1Score), WIDTH / 4, 50);
        g.drawString(String.valueOf(player2Score), 3 * WIDTH / 4, 50);
    }

    //gets the key used and outputs something on screen 

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_W && player1Y > 0) {
            player1Y -= 10; // Move player 1 paddle up
        } else if (keyCode == KeyEvent.VK_S && player1Y < HEIGHT - PADDLE_HEIGHT) {
            player1Y += 10; // Move player 1 paddle down
        }

        if (keyCode == KeyEvent.VK_UP && player2Y > 0) {
            player2Y -= 10; // Move player 2 paddle up
        } else if (keyCode == KeyEvent.VK_DOWN && player2Y < HEIGHT - PADDLE_HEIGHT) {
            player2Y += 10; // Move player 2 paddle down
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    //main 

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pong Game");
        PongGame pongGame = new PongGame();
        frame.add(pongGame);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
