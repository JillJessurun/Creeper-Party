import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Enemy extends GameObject{
    private Handler handler;
    private Menu menu;
    Random r = new Random();
    public static BufferedImage image;
    private BufferedImage creeperEnemy;

    public Enemy(float x, float y, ID id, Handler handler, Menu menu, BufferedImage image, BufferedImage creeperEnemy) {
        super(x, y, id);
        this.handler = handler;
        this.menu = menu;
        this.image = image;
        this.creeperEnemy = creeperEnemy;
        velX = (float) r.nextDouble(2, 5);
        velY = (float) r.nextDouble(2, 5);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 40, 40);
    }

    public void tick() {

        x += velX;
        y += velY;

        if (y <= 0 || y >= Game.HEIGHT-15) {
            velY *= -1;
        }
        if (x <= 0 || x >= Game.WIDTH-15) {
            velX *= -1;
        }

    }

    public void render(Graphics g) {
        //g.setColor(Color.red);
        //g.fillRect((int) x, (int) y, 16, 16);

        g.drawImage(creeperEnemy, (int)x, (int)y, null);
    }
}
