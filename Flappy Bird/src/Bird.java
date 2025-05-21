import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Bird extends JPanel implements ActionListener, KeyListener {

    // Bird physics
    private int birdY = 250;           // Vertical position
    private int birdVelocity = 0;      // Up/down speed
    private final int GRAVITY = 1;     // Gravity force
    private final int FLAP_STRENGTH = -12; // How much the bird jumps

    private Timer timer;

    public Bird() {
        setPreferredSize(new Dimension(400, 600));
        setBackground(Color.cyan);
        setFocusable(true);
        addKeyListener(this);

        // Start game loop (20 ms = 50 FPS)
        timer = new Timer(20, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        birdVelocity += GRAVITY;
        birdY += birdVelocity;

        // Prevent going above the screen
        if (birdY < 0) {
            birdY = 0;
            birdVelocity = 0;
        }

        // Stop bird at bottom (simulated ground)
        if (birdY > getHeight() - 40) {
            birdY = getHeight() - 40;
            birdVelocity = 0;
        }

        repaint(); // Redraw screen
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw ground
        g.setColor(Color.orange);
        g.fillRect(0, getHeight() - 100, getWidth(), 100);

        // Draw bird
        g.setColor(Color.red);
        g.fillOval(100, birdY, 40, 40); // (x, y, width, height)
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            birdVelocity = FLAP_STRENGTH; // Bird jumps
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Flappy Bird - Java");
        Bird game = new Bird();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(game);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }
}