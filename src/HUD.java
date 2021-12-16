import java.awt.*;

public class HUD {

    private Menu menu;
    public static int HEALTH = 100;
    private int score = 0;
    private int level = 1;

    public HUD(Menu menu){
        this.menu = menu;
    }

    public void tick(){
        HEALTH = (int)Game.clamp(HEALTH, 0, 100);
    }

    public void render(Graphics g){
        g.setColor(Color.gray);
        g.fillRect(15, 15, 200, 50);
        g.setColor(Color.green);
        g.fillRect(15, 15, HEALTH * 2, 50);
        g.setColor(Color.white);
        g.drawRect(15, 15, 200, 50);

        g.drawString(HEALTH+"%", 225, 40);
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public float getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setHEALTH(int HEALTH) {
        HUD.HEALTH = HEALTH;
    }

}
