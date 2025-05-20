import javax.swing.JFrame;

public class Main {
	
	public static void main(String[] args) {
			
			JFrame panel = new JFrame("Tetris");
			panel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			panel.setResizable(false);
			
			//add game window
			Display d = new Display();
			panel.add(d);
			panel.pack();
			
			panel.setLocationRelativeTo(null);
			panel.setVisible(true);
			
			
		}
	
}
