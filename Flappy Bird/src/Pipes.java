import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Pipes extends JPanel implements ActionListener, KeyListener{
	
	//Bird physics
	private int birdY = 250;
	private int birdVelocity = 0;
	private final int GRAVITY = 1;
	private final int FLAP_STRENGTH = -12;
	
	//Pipe properties
	private int pipeX = 400;
	private int pipeWidth = 80;
	private int gapHeight = 150;
	private int gapY; //Y position of top of the gap
	
	private final int PIPE_SPEED = 5;
	private final int PIPE_RESET_X = 400;
	private Random rand;
	
	private Timer timer;
	
	public Pipes() {
		setPreferredSize(new Dimension(400, 600));
		setBackground(Color.cyan);
		setFocusable(true);
		addKeyListener(this);
		rand = new Random();
		resetPipe();
		timer = new Timer(20, this);
		timer.start();
	}
	
	private void resetPipe() {
		pipeX = PIPE_RESET_X;
		gapY = 100 + rand.nextInt(300); //random gap
	}
	
	public void actionPerformed(ActionEvent e) {
		birdVelocity += GRAVITY;
		birdY += birdVelocity;
		
		if(birdY < 0) {
			birdY = 0;
			birdVelocity = 0;
		}
		
		if(birdY > getHeight()-40) {
			birdY = getHeight() - 40;
			birdVelocity = 0;
		}
		
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
	

    private boolean checkCollision() {
        // Bird rectangle
        Rectangle birdRect = new Rectangle(100, birdY, 40, 40);
        // Top pipe
        Rectangle topPipe = new Rectangle(pipeX, 0, pipeWidth, gapY);
        // Bottom pipe
        Rectangle bottomPipe = new Rectangle(pipeX, gapY + gapHeight, pipeWidth, getHeight() - gapY - gapHeight - 100);

        return birdRect.intersects(topPipe) || birdRect.intersects(bottomPipe);
    }

    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw ground
        g.setColor(Color.orange);
        g.fillRect(0, getHeight() - 100, getWidth(), 100);

        // Draw bird
        g.setColor(Color.red);
        g.fillOval(100, birdY, 40, 40);

        // Draw pipes
        g.setColor(Color.green);
        g.fillRect(pipeX, 0, pipeWidth, gapY); // top pipe
        g.fillRect(pipeX, gapY + gapHeight, pipeWidth, getHeight() - gapY - gapHeight - 100); // bottom pipe
    }

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}