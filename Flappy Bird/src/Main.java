import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

public class Main {
	
	public static void main(String[] args) {
			
			JFrame panel = new JFrame("Flappy");
			panel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			panel.setResizable(false);
			
			//add game window
			Display d = new Display();
			panel.add(d);
			panel.pack();
			d.setFocusable(true);
			d.requestFocusInWindow();
			d.addKeyListener(d);
			panel.setLocationRelativeTo(null);
			panel.setVisible(true);
			
			StartScreen startScreen = new StartScreen(panel);
			panel.add(startScreen);
			panel.setVisible(true);

			
			
		}
	
	
}
