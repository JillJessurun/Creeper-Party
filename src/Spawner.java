import java.awt.image.BufferedImage;
import java.util.Random;

public class Spawner {

    private Handler handler;
    private HUD hud;
    private Random r = new Random();
    private int scoreKeep = 0;
    private Game game;
    private Menu menu;
    public static BufferedImage image;
    private BufferedImage creeperEnemy;

    public Spawner(Handler handler, HUD hud, Game game, Menu menu, BufferedImage image, BufferedImage creeperEnemy) {
        this.handler = handler;
        this.hud = hud;
        this.game = game;
        this.menu = menu;
        this.image = image;
        this.creeperEnemy = creeperEnemy;
    }

    public void spawn(ID id, int amountOfSpawns){
        if(id.equals(ID.Enemy)) {
            for (int i = 0; i < amountOfSpawns; i++) {
                handler.addObject(new Enemy(Game.WIDTH/2-32, Game.HEIGHT/2-32, ID.Enemy, handler, menu, image, creeperEnemy));
            }
        }
    }

    public void tick() {
        scoreKeep++;

        if (scoreKeep >= 100) {
            scoreKeep = 0;

            if (!handler.object.isEmpty()) {
                spawn(ID.Enemy, 1);

            }else{
                hud.setLevel(1);
                hud.setHEALTH(100);
            }
        }
    }
}

