import java.awt.*;

public class Player extends GameObject{

    private Handler handler;

    public Player(float x, float y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;
    }

    public Rectangle getBounds(){
        return new Rectangle ((int)x, (int)y, 32, 32);
    }

    public void tick() {
        //x += velX;
        //y += velY;
        x = x + velX;
        x = x + velX;

        y = y + velY;
        y = y + velY;

        x = Game.clamp(x, 0, Game.WIDTH-50);
        y = Game.clamp(y, 0, Game.HEIGHT-50);

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
                }
            }
        }
    }

    public void render(Graphics g) {
        if (this.getId() == ID.Player) {
            g.setColor(Color.red);
            g.fillRect((int) x, (int) y, 32, 32);
        }else if (this.getId() == ID.Player2) {
            g.setColor(Color.green);
            g.fillRect((int) x + 100, (int) y, 32, 32);
        }
    }
}
