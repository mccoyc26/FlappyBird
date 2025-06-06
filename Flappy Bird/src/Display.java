import java.awt.Color;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
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

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
public class Display extends JPanel implements ActionListener, KeyListener{
	
	public static boolean debugging = false;
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	
	private boolean gameOver;
	private final int NUM_PIPES = 5;
	private ArrayList<Pipe> pipes = new ArrayList<>();
	private int pipeSpawnTimer = 0;
	private final int PIPE_SPAWN_INTERVAL = 90; // frames (~1.5 seconds)
	private GameOverGiph gif = new GameOverGiph();
	private Background background = new Background(0, 0);
	private Foreground foreground = new Foreground(0, 620);
	private ScoreManager scoreManager = new ScoreManager();
	private int score = 0;
	private boolean onTitleScreen = true;	
	private Random rand;
	private Timer timer;
	private Font gameFont;
	private Clip backgroundMusic;
	Bird croc = new Bird(300,300);
	
	

	private void loadFont() { //method to load the pixel font instead of a basic one
	    try {
	        InputStream is = getClass().getResourceAsStream("/assets/funFont.ttf");
	        if (is == null) {
	            throw new IOException("Font file not found");
	        }
	        gameFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(32f);
	        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	        ge.registerFont(gameFont);
	        is.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	        gameFont = new Font("Arial", Font.BOLD, 32); // fallback in case font cannot be loaded
	    }
	}

	 private void loadBackgroundMusic() {
	        try {
	            URL url = getClass().getResource("/assets/SHX4 - BOMBARDINO CROCODILO FUNK.wav");
	            if (url != null) {
	                AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
	                backgroundMusic = AudioSystem.getClip();
	                backgroundMusic.open(audioIn);
	                backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
	                backgroundMusic.start();
	            } else {
	                System.err.println("Background music file not found.");
	            }
	        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
	            e.printStackTrace();
	        }
	    }

	
	public Display() {
		//create display settings
		this.setFocusable(true);
		this.requestFocusInWindow();
		this.addKeyListener(this);

		loadFont();
		timer = new Timer(16, this); // runs at ~60 FPS
		timer.start();
		rand = new Random();
		for (int i = 0; i < NUM_PIPES; i++) {
		    int startX = 800 + i * 300; // space the pipes out horizontally
		    int gapY = 100 + rand.nextInt(300);
		    pipes.add(new Pipe(startX, gapY));
		}    
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setBackground(Color.DARK_GRAY);
		this.setLayout(null);
		loadBackgroundMusic();
	}
	
	@Override
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);

	    background.paint(g);
	    
	    if (onTitleScreen) {
	        g.setColor(Color.WHITE);
	        g.setFont(gameFont.deriveFont(32f));
	        g.drawString("Flappy Bird", 500, 120);
	        g.setFont(gameFont.deriveFont(24f));
	        g.drawString("Press SPACE to start", 470, 170);

	        g.drawString("High Scores:", 525, 240);
	        int y = 280;
	        for (ScoreManager.ScoreEntry entry : scoreManager.getScores()) {
	            g.drawString(entry.name + " - " + entry.score, 540, y);
	            y += 30;
	        }

	        return; // Skip drawing pipes and bird on title screen
	    }
	    
	    for (Pipe p : pipes) {
	        p.paint(g);
	    }

	    foreground.paint(g);

	    croc.paint(g);
	    
	    g.setColor(Color.WHITE);
	    g.setFont(gameFont.deriveFont(24f));
	    g.drawString("Score: " + score, 20, 40);
	    
	    if (gameOver) {
	    	gif.paint(g, this); //paint the game over GIF
            g.drawString("Press R to restart", 500, 400);
        }
	    
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
	    
		if (onTitleScreen || gameOver) {
			return;
		}
		if(!gameOver) {
			foreground.update();
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
		            
		            p.scored = false;  // reset scored flag on recycle
		        }
		        
		        // Score increment when bird passes pipe's right edge and not yet scored
		        if (!p.scored && croc.getX() > p.x + p.width) { // PIPE_WIDTH is the pipe image width (e.g., 60 or 80)
		            score++;
		            p.scored = true;
		        }
	
		        // Collision check
		        if (p.collidesWith(birdRect) || croc.getY() >= 580) { //bird dies if it hits the pipes or ground
		            gameOver = true;
		            System.out.println("GAME OVER");
		            
		            if (backgroundMusic != null && backgroundMusic.isRunning()) {
	                    backgroundMusic.stop();
	                }
		            
		            ArrayList<ScoreManager.ScoreEntry> highs = scoreManager.getScores();
		            if (highs.size() < 5 || score > highs.get(highs.size() - 1).score) {
		                String name = javax.swing.JOptionPane.showInputDialog("New High Score! Enter your name:");
		                if (name != null && !name.isEmpty()) {
		                    scoreManager.addScore(name, score);
		                }
		            }
		            
		            break;
		        }
		    }
	
		}
	    repaint(); //called outside all conditional statements so that the GIF and such can be drawn no matter what
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
			if (onTitleScreen) {
	            resetGame();
	            onTitleScreen = false;
	        } else {
	            croc.hop();
	        }
	    }
	    if (e.getKeyCode() == 82 && gameOver) {
	        resetGame();
	    }
	    if (e.getKeyCode() == 79) {
	    	timer.stop();
	    }
	    if (e.getKeyCode() == 73) {
	    	timer.start();
	    }
	    
	    if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
	        onTitleScreen = true;
	        repaint();
	    }
	}
	
	public void resetGame() {
		timer.stop();  // Stop any existing timer before restarting
	    timer.start();
		// Reset bird position
	    croc.setY(300);

	    // Reset pipe positions and gaps
	    for (int i = 0; i < pipes.size(); i++) {
	        Pipe p = pipes.get(i);
	        p.x = 800 + i * 300; // spacing them out again
	        p.gapY = 100 + rand.nextInt(300);
	    }

	    // Reset game state
	    gameOver = false;
	    
	    if (backgroundMusic != null && !backgroundMusic.isRunning()) {
            backgroundMusic.setFramePosition(0);
            backgroundMusic.start();
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
        }
	    
	    score = 0;
	}

	
}
