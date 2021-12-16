import java.awt.*;
import java.awt.image.BufferedImage;

public class Shop extends GameObject{

    private BufferedImage netherPortal;

    public Shop(float x, float y, ID id, BufferedImage netherPortal) {
        super(x, y, id);
        this.netherPortal = netherPortal;
    }

    public void tick() {

    }

    public void render(Graphics g) {
        g.drawImage()
    }

    public Rectangle getBounds() {
        return null;
    }
}
