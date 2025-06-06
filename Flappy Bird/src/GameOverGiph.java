import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

public class GameOverGiph {
    private ImageIcon gif;
    private AffineTransform tx;

    int width = 600;
    int height = 600;
    int x, y;

    public GameOverGiph() {
        
        gif = new ImageIcon(getClass().getResource("/imgs/gmover.gif"));
        
        x = Display.WIDTH / 2 - width / 2;
        y = Display.HEIGHT / 2 - height / 2;

        tx = AffineTransform.getTranslateInstance(x, y);
        tx.scale(1.0, 1.0);
    }

    public void paint(Graphics g, JComponent parent) {
        Graphics2D g2 = (Graphics2D) g;
        tx.setToTranslation(x, y);
        tx.scale(1.0, 1.0);
        gif.paintIcon(parent, g, x, y);

        if (Display.debugging) {
            g.setColor(Color.GREEN);
            g.drawRect(x, y, width, height);
        }
    }

    private Image getImage(String path) {
        Image tempImage = null;
        try {
            URL imageURL = GameOverGiph.class.getResource(path);
            tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tempImage;
    }
}
