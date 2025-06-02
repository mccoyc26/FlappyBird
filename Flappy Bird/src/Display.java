import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Display extends JPanel implements ActionListener, KeyListener{
	
	public static boolean debugging = false;
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	
	private boolean gameOver;
	private final int NUM_PIPES = 5; // or 6, depending on spacing
	private ArrayList<Pipe> pipes = new ArrayList<>();
	private int pipeSpawnTimer = 0;
	private final int PIPE_SPAWN_INTERVAL = 90; // frames (~1.5 seconds)
	private GameOverGiph gif = new GameOverGiph();
	private Background background = new Background(0, 0);
	private Foreground foreground = new Foreground(0, 620);
	

	
	//Pipe properties
		
		private Random rand;
		private Timer timer;
		
	Bird croc = new Bird(300,300);
	
	
	public Display() {
		//create display settings
		timer = new Timer(16, this); // ~60 FPS
		timer.start();
		rand = new Random();
		for (int i = 0; i < NUM_PIPES; i++) {
		    int startX = 800 + i * 300; // space them out horizontally
		    int gapY = 100 + rand.nextInt(300);
		    pipes.add(new Pipe(startX, gapY));
		}    
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setBackground(Color.DARK_GRAY);
		this.setLayout(null);
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);

	    background.paint(g);

	    for (Pipe p : pipes) {
	        p.paint(g);
	    }

	    foreground.paint(g);

	    croc.paint(g);
	    
	    if (gameOver) {
            gif.paint(g);
        }

	}
	
//	private void spawnPipe() {
//	    int gapY = 100 + rand.nextInt(300);
//	    pipes.add(new Pipe(WIDTH, gapY));
//	}

	
	@Override
	public void actionPerformed(ActionEvent arg0) {
	    if(!gameOver) {
			croc.update();
		    Rectangle birdRect = new Rectangle(320, croc.getY() + 5, 60, 40);
	
		    for (Pipe p : pipes) {
		        p.update();
	
		        // Recycle pipe if off screen
		        if (p.isOffScreen()) {
		            // Find the rightmost pipe's x
		            int maxX = 0;
		            for (Pipe other : pipes) {
		                if (other.x > maxX) maxX = other.x;
		            }
	
		            // Move this pipe to the right of the furthest pipe
		            p.x = maxX + 300; // 300 is pipe spacing
		            p.gapY = 100 + rand.nextInt(300);
		        }
	
		        // Collision check
		        if (p.collidesWith(birdRect)) {
		            gameOver = true;
		            System.out.println("GAME OVER");
		            break;
		        }
		    }
	
		}
	    repaint();
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == 32) {
	        croc.hop();
	    }
	    if (e.getKeyCode() == 82) {
	        resetGame();
	    }
	    if (e.getKeyCode() == 79) {
	    	timer.stop();
	    }
	    if (e.getKeyCode() == 73) {
	    	timer.start();
	    }
	}
	
	private void resetGame() {
	    // Reset bird position
	    croc.setY(300); // Or whatever your original Y position was

	    // Reset pipe positions and gaps
	    for (int i = 0; i < pipes.size(); i++) {
	        Pipe p = pipes.get(i);
	        p.x = 800 + i * 300; // spacing them out again
	        p.gapY = 100 + rand.nextInt(300);
	    }

	    // Reset game state
	    gameOver = false;

	    // Restart timer if it isn't running
	    if (!timer.isRunning()) {
	        timer.start();
	    }
	}

	
}
