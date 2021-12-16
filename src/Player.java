import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends GameObject{

    private Handler handler;
    private Menu menu;
    private BufferedImage creeperPlayer;
    private Game game;
    private HUDgames huDgames;

    public Player(float x, float y, ID id, Handler handler, Menu menu, BufferedImage creeperPlayer, Game game, HUDgames huDgames) {
        super(x, y, id);
        this.handler = handler;
        this.menu = menu;
        this.game = game;
        //this.image = image;
        this.creeperPlayer = creeperPlayer;
        this.huDgames = huDgames;
    }

    public Rectangle getBounds(){
        return new Rectangle ((int)x, (int)y, 50, 50);
    }

    public void tick() {
        //x += velX;
        //y += velY;
        x = x + velX;
        x = x + velX;

        y = y + velY;
        y = y + velY;

        x = Game.clamp(x, 0, Game.WIDTH-60);
        y = Game.clamp(y, 0, Game.HEIGHT-60);

        collision();
    }

    private void collision(){
        for (int i = 0; i < handler.object.size(); i++) {
            if (this.getId() == ID.Player || this.getId() == ID.Player2) {
                GameObject tempObject = handler.object.get(i);
                if (tempObject.getId() == ID.Enemy) {
                    if (getBounds().intersects(tempObject.getBounds())) {
                        //collision code (under here happens when colliding)
                        HUD.HEALTH -= 2;
                    }
                }else if (tempObject.getId() == ID.Portal) {
                    if (getBounds().intersects(tempObject.getBounds())) {
                        //collision code with portal
                        huDgames.setInPortal(true);
                        game.gameState = Game.STATE.HUDminigames;

                        //handler.clearEnemies();
                        //game.gameState = Game.STATE.DodgeGame;
                    }else{
                        huDgames.setInPortal(false);
                        game.gameState = Game.STATE.Lobby;
                    }
                }
            }
        }
    }

    public void render(Graphics g) {

        //g.setColor(Color.red);
        //g.fillRect((int) x, (int) y, 32, 32);

        g.drawImage(creeperPlayer, (int)x, (int)y, null);

    }
}
