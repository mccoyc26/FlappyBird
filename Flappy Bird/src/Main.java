import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

public class Main {
	
	public static void main(String[] args) {
			
			JFrame panel = new JFrame("Flappy");
			panel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			panel.setResizable(false);
			
			StartScreen startScreen = new StartScreen(panel);
			startScreen.setPreferredSize(new Dimension(1280, 720));
			panel.add(startScreen);
			panel.pack();
	        panel.setLocationRelativeTo(null);
			panel.setVisible(true);
		}
	
	
}
