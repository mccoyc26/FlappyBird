import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class TopPipe {
	
	private int x,y;
	private int vx,vy;
	private int width,height;
	private AffineTransform tx;
	private Image forward;
	private double scaleWidth = 0.35;		//change to scale image
	private double scaleHeight = 0.35; 		//change to scale image
	
	
	public TopPipe() {
		forward 	= getImage("/imgs/"+"pipeTop.png"); //load the image
		
		if (forward == null) {
		    System.out.println("Image failed to load!");
		}

		//width and height for hitbox
		width = 60;
		height = 483;
		//used for placement on the j frame
		x = 300;
		y = 300;
		//if movement will not be hopping
		vx = 0;
		vy = 0;
		
		tx = AffineTransform.getTranslateInstance(0, 0);
		
		init(x, y); 				//initialize the location of the image
	}
	
	public TopPipe(int x, int y) {
		//call the default constructor
		this(); //invokes default constructor
		//do the specific task for THIS constructor
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
		
	public int getWidth() {
		return width;
	}
		
	public int getHeight() {
		return height;
	}
	
	public void paint(Graphics g) {
		//these are the 2 lines of code needed draw an image on the screen
		Graphics2D g2 = (Graphics2D) g;
		//update x and y if using vx and vy variables
		x+=vx;
		y+=vy;	
		
		init(x,y);
		
		g2.drawImage(forward, tx, null);
			
		//draw hitbox based on x, y, width, height
//		if(Display.debugging) {
//			//draw hitbox only if debugging
//			g.setColor(Color.green);
//			g.drawRect(x+20, y+5, width, height);
//		}
		
	}
	
	private void init(double a, double b) {
		tx.setToTranslation(a, b);
		tx.scale(scaleWidth, scaleHeight);
	}
	
	private Image getImage(String path) {
		Image tempImage = null;
		try {
			URL imageURL = TopPipe.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}

	
}
