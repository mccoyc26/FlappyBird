import javax.swing.*;
import java.awt.*;
public class GamePanel extends JPanel {
   public GamePanel() {
       setBackground(Color.GREEN);
   }
   @Override
   protected void paintComponent(Graphics g) {
       super.paintComponent(g);
       g.setColor(Color.BLACK);
       g.setFont(new Font("Arial", Font.BOLD, 24));
       g.drawString("Game Started!", 120, 300);
   }
}
