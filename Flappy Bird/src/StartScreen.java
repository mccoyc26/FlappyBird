import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
public class StartScreen extends JPanel {
   private JFrame parentFrame;
   private Image backgroundImage;
   public StartScreen(JFrame frame) {
       this.parentFrame = frame;
       setLayout(null);
       loadImage();
       JButton startButton = new JButton("Start");
       startButton.setBounds(150, 400, 100, 50); // Adjust based on image layout
       add(startButton);
       startButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               startGame();
           }
       });
   }
   private void loadImage() {
       try {
           backgroundImage = ImageIO.read(new File("imgs/bgr.png")); // Adjust path as needed
       } catch (IOException e) {
           System.err.println("Could not load start screen image.");
           e.printStackTrace();
       }
   }
   private void startGame() {
       parentFrame.getContentPane().removeAll();
       parentFrame.add(new GamePanel()); // Your actual game panel here
       parentFrame.revalidate();
       parentFrame.repaint();
   }
   @Override
   protected void paintComponent(Graphics g) {
       super.paintComponent(g);
       if (backgroundImage != null) {
           g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
       } else {
           setBackground(Color.CYAN);
           g.setColor(Color.WHITE);
           g.setFont(new Font("Arial", Font.BOLD, 32));
           g.drawString("Flappy Bird", 120, 150);
       }
   }
}
