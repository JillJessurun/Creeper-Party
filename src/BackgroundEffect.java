import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class BackgroundEffect extends GameObject{

    private Handler handler;
    private Menu menu;
    Random r = new Random();
    public static BufferedImage image;
    private BufferedImage creeperHead;

    public BackgroundEffect(float x, float y, ID id, Handler handler, Menu menu, BufferedImage image, BufferedImage creeperHead) throws IOException {
        super(x, y, id);

        //creeper head settings
        //BufferedImageLoader loader = new BufferedImageLoader();
        //image = loader.loadImage("C:\\Users\\pc\\IdeaProjects\\Creeper-Party\\src\\Image\\creeperHead.png");
        //Image image = new Image(Game.image);
        //creeperHead = image.grabImage();
        //creeperHead = image.resizeImage(creeperHead, Game.WIDTH, Game.HEIGHT);

        this.image = image;
        this.creeperHead = creeperHead;
        this.handler = handler;
        this.menu = menu;
        //velX = r.nextInt(1, 5);
        //velY = r.nextInt(1, 5);

        velX = (float) r.nextDouble(2, 20);
        velY = (float) r.nextDouble(2, 20);
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 30, 30);
    }

    public void tick() {
        x += velX;
        y += velY;

        if (y<= 0 || y >= Game.HEIGHT - 32) {
            velY *= -1;
        }
        if (x<= 0 || x >= Game.WIDTH - 32) {
            velX *= -1;
        }

    }

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        float alpha = 0.15f; // between 1 and 0 --> the lower the more transparant
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);
        g2d.setComposite(ac);

        g2d.drawImage(creeperHead, (int)x, (int)y, null);
    }

}
