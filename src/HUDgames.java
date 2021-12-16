import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.IOException;

public class HUDgames extends MouseAdapter {

    private Game game;
    private Handler handler;
    private Boolean inPortal = false;

    public HUDgames(Game game, Handler handler){
        this.game = game;
        this.handler = handler;
    }

    public void mousePressed(MouseEvent e){
        int mx = e.getX();
        int my = e.getY();

        if (mouseOver(mx, my, 630, 295, 280, 40) && inPortal){
            handler.clearEnemies();
            game.gameState = Game.STATE.OcelotMadness;
        }
    }

    private boolean mouseOver(int mx, int my, int x, int y, int width, int height){
        if (mx > x && mx < x + width){
            if(my > y && my < y + height){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    public void tick() throws IOException, FontFormatException {
        BufferStrategy bs = game.getBufferStrategy();
        Graphics g = bs.getDrawGraphics();
        this.render(g);
    }

    public void render(Graphics g) throws IOException, FontFormatException {
        //colors
        Color color = new Color(21, 21, 21, 255);
        Color color2 = new Color(11, 122, 8, 255);

        //fonts
        Font minecraftFont = Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\pc\\IdeaProjects\\Creeper-Party\\src\\Font\\minecraft.ttf")).deriveFont(24f);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\pc\\IdeaProjects\\Creeper-Party\\src\\Font\\minecraft.ttf")));
        Font font = new Font("courier new", Font.ITALIC, 23);

        //pop up minigames menu (+ title)
        g.setColor(color);
        g.fillRect(620, 200,  300, 500);
        g.setColor(Color.white);
        g.drawRect(620, 200, 300, 500);
        g.setColor(color2);
        g.setFont(minecraftFont);
        g.drawString("Creeper  Games:", 650, 250);
        g.setColor(Color.white);
        g.drawLine(620, 285, 920, 285);

        //button Ocelot Madness
        g.setFont(font);
        g.drawRect(630, 295, 280, 40);
        g.setColor(Color.yellow);
        g.drawString("Ocelot Madness", 675, 320);
    }

    public Boolean getInPortal() {
        return inPortal;
    }

    public void setInPortal(Boolean inPortal) {
        this.inPortal = inPortal;
    }
}
