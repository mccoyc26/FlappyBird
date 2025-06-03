import java.awt.Graphics;
import java.awt.Rectangle;

public class Pipe {
    int x;
    int width = 80;
    int gapY;
    int gapHeight = 150;
    int speed = 5;
    int screenHeight = Display.HEIGHT;
    public boolean scored = false;

    private TopPipe top;
    private BottomPipe bottom;

    public Pipe(int startX, int gapY) {
        this.x = startX;
        this.gapY = gapY;

        // Create TopPipe and BottomPipe ONCE
        top = new TopPipe();
        bottom = new BottomPipe();
        updatePipePositions();
    }

    public void update() {
        x -= speed;
        updatePipePositions();
    }

    private void updatePipePositions() {
        top.setX(x);
        top.setY(gapY - top.getHeight());

        bottom.setX(x);
        bottom.setY(gapY + gapHeight);
    }

    public void paint(Graphics g) {
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

    public void setX(int newX) {
        this.x = newX;
        updatePipePositions();
    }

    public void setGapY(int newGapY) {
        this.gapY = newGapY;
        updatePipePositions();
    }
}
