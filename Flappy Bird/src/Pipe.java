import java.awt.Graphics;
import java.awt.Rectangle;

public class Pipe {
    int x;
    int width = 80;
    int gapY;
    int gapHeight = 150;
    
    public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	int speed = 5;
    int screenHeight = Display.HEIGHT;

    public Pipe(int startX, int gapY) {
        this.x = startX;
        this.gapY = gapY;
    }

    public void update() {
        x -= speed;
    }

    public void paint(Graphics g) {
        TopPipe top = new TopPipe(x, gapY - new TopPipe().getHeight());
        BottomPipe bottom = new BottomPipe(x, gapY + gapHeight);
        top.paint(g);
        bottom.paint(g);
    }

    public boolean isOffScreen() {
        return x + width < 0;
    }

    public boolean collidesWith(Rectangle bird) {
        Rectangle topRect = new Rectangle(x, 0, width, gapY);
        Rectangle bottomRect = new Rectangle(x, gapY + gapHeight, width, screenHeight - gapY - gapHeight - 100);
        return bird.intersects(topRect) || bird.intersects(bottomRect);
    }
}
