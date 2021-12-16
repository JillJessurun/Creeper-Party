import java.awt.*;
import java.awt.image.BufferedImage;

public class Portal extends GameObject{

    private BufferedImage netherPortal;

    public Portal(float x, float y, ID id, BufferedImage netherPortal) {
        super(x, y, id);
        this.netherPortal = netherPortal;
    }

    public void tick() {

    }

    public void render(Graphics g) {
        g.drawImage(netherPortal, 80, Game.HEIGHT/2+285, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(80, Game.HEIGHT/2+285, 100, 150);
    }

}
