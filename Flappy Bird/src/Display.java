import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.JPanel;

public class Display extends JPanel{

	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	
	Image bird;
	Image tPipe;
	Image bPipe;
	Image bgr;
	
	public Display() {
		//create display settings
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setBackground(Color.DARK_GRAY);
		this.setLayout(null);
		
	}
	
	public void Paint() {
		
		
		
		
	}
	
}
