import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

class Display extends JPanel{

	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	
	public Display() {
		//create display settings
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setBackground(Color.DARK_GRAY);
		this.setLayout(null);
	}
	
}
