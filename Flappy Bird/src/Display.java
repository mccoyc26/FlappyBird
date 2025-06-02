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
	private ArrayList<Pipe> pipes = new ArrayList<>();
	private int pipeSpawnTimer = 0;
	private final int PIPE_SPAWN_INTERVAL = 90; // frames (~1.5 seconds)
	private GameOverGiph gif = new GameOverGiph();
	
	//Pipe properties
		
		private Random rand;
		private Timer timer;
		
	Bird croc = new Bird(300,300);
	
	
	public Display() {
		//create display settings
		timer = new Timer(16, this); // ~60 FPS
		timer.start();
		rand = new Random();
		spawnPipe();     
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setBackground(Color.DARK_GRAY);
		this.setLayout(null);
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);

	    Background bgr = new Background(0, 0);
	    bgr.paint(g);

	    for (Pipe p : pipes) {
	        p.paint(g);
	    }

	    Foreground fgr = new Foreground(0, 620);
	    fgr.paint(g);

	    croc.paint(g);
	    
	    if (gameOver) {
            gif.paint(g);
        }

	}
	
	private void spawnPipe() {
	    int gapY = 100 + rand.nextInt(300);
	    pipes.add(new Pipe(WIDTH, gapY));
	}

	
	@Override
	public void actionPerformed(ActionEvent arg0) {
	    croc.update();
	    Rectangle birdRect = new Rectangle(320, croc.getY() + 5, 60, 40);
	    // Update pipes
	    pipeSpawnTimer++;
	    if (pipeSpawnTimer >= PIPE_SPAWN_INTERVAL) {
	        spawnPipe();
	        pipeSpawnTimer = 0;
	    }

	    for (int i = 0; i < pipes.size(); i++) {
	        Pipe p = pipes.get(i);
	        p.update();
	        if (p.isOffScreen()) {
	        	p.setX(1280);
	        } else {
	            if (p.collidesWith(new Rectangle(320, croc.getY()+5, 60, 40))) {
	            	gameOver = true;
	            	timer.stop();
	                System.out.println("GAME OVER");
	                
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
	        timer.start();
	        gameOver = false;
	    }
	    if (e.getKeyCode() == 80) {
	    	timer.stop();
	    }
	}
	
	
	
}
