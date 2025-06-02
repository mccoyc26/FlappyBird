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
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Display extends JPanel implements ActionListener, KeyListener{
	
	public static boolean debugging = true;
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	
	//Pipe properties
		private int pipeX = 800;
		private int pipeWidth = 80;
		private int gapHeight = 150;
		private int gapY; //Y position of top of the gap
		
		private final int PIPE_SPEED = 5;
		private final int PIPE_RESET_X = 800;
		private Random rand;
		private Timer timer;
		
	Bird croc = new Bird(300,300);
	
	
	public Display() {
		//create display settings
		timer = new Timer(16, this); // ~60 FPS
		timer.start();
		rand = new Random();
		resetPipe();     
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setBackground(Color.DARK_GRAY);
		this.setLayout(null);
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
        
        g.setColor(Color.green);
        //g.fillRect(pipeX, 0, pipeWidth, gapY); // top pipe
        TopPipe pipe1 = new TopPipe();
        int h = pipe1.getHeight();
        TopPipe pipe2 = new TopPipe(pipeX, gapY-h);
        pipe2.paint(g);
        
        TopPipe pipe3 = new TopPipe(pipeX+100, gapY-h);
        pipe3.paint(g);
        
        //g.fillRect(pipeX, gapY + gapHeight, pipeWidth, getHeight() - gapY - gapHeight - 100); // bottom pipe
        
        
        BottomPipe pipe = new BottomPipe(pipeX, gapY + gapHeight);
        pipe.paint(g);
        
        // Draw ground
        g.setColor(Color.orange);
        g.fillRect(0, getHeight() - 100, getWidth(), 100);
		
		croc.paint(g);
	}
	
	private boolean checkCollision() {
        // Bird rectangle
        Rectangle birdRect = new Rectangle(320, croc.getY()+5, 60, 40);
        // Top pipe
        Rectangle topPipe = new Rectangle(pipeX, 0, pipeWidth, gapY);
        // Bottom pipe
        Rectangle bottomPipe = new Rectangle(pipeX, gapY + gapHeight, pipeWidth, getHeight() - gapY - gapHeight - 100);

        return birdRect.intersects(topPipe) || birdRect.intersects(bottomPipe);
    }
	
	private void resetPipe() {
		pipeX = PIPE_RESET_X;
		gapY = 100 + rand.nextInt(300); //random gap
		//gapY = 300;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		croc.update();

		//pipe movement
		pipeX -= PIPE_SPEED;
		if(pipeX + pipeWidth < 0){
			resetPipe();
		}
				
		//collision
		if(checkCollision()) {
			timer.stop();
			System.out.println("GAME OVER");
		}
		
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
	    if (e.getKeyCode() == 32) {
	        croc.hop();
	    }
	    if (e.getKeyCode() == 82) {
	        timer.start();
	    	resetPipe();
	    }
	}


//	@Override
//	public void keyReleased(KeyEvent arg0) {
//		// TODO Auto-generated method stub
//		//System.out.println(arg0.getKeyCode());
//		if(arg0.getKeyCode()==32) {
//			//move char up
//			croc.hop(1);
//		}
//		
//		
//	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}