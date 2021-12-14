import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.IOException;

public class Menu extends MouseAdapter {

    private Game game;
    private Handler handler;
    private HUD hud;
    private JFrame jFrame;
    private int countLetter;
    private int countLetter2;
    private int countLetter3;
    private int countLetter4;
    private int countLetter5;

    public Menu(Game game, Handler handler, HUD hud, JFrame jFrame){
        this.game = game;
        this.handler = handler;
        this.hud = hud;
        this.jFrame = jFrame;
    }
    
    public int countLetters(String string){
        int counter = 0;
        for (int i = 0; i < string.length(); i++) {
            counter++;
        }
        return counter;
    }

    public void mousePressed(MouseEvent e){
        int mx = e.getX();
        int my = e.getY();

        //quit button home
        if (mouseOver(mx, my, (Game.WIDTH/2)-170, (Game.HEIGHT/2)+250, 350, 100) && game.gameState == Game.STATE.Menu){
            System.exit(0);
        }

        //options button home
        if (mouseOver(mx, my, (Game.WIDTH/2)-170, (Game.HEIGHT/2) + 75, 350, 100) && game.gameState == Game.STATE.Menu){
            game.gameState = Game.STATE.Options;
        }

        //quit button options
        if (mouseOver(mx, my, (Game.WIDTH/2)-170, (Game.HEIGHT/2)+250, 350, 100) && game.gameState == Game.STATE.Options){
            game.gameState = Game.STATE.Menu;
        }

        //play button home
        if (mouseOver(mx, my, (Game.WIDTH/2)-170, (Game.HEIGHT/2)-100, 350, 100) && game.gameState == Game.STATE.Menu){
            game.gameState = Game.STATE.Play;
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
        //g.drawString(title, (Game.WIDTH/2)-countLetter*20, (Game.HEIGHT/2)); ----> is midden met dit font
        Font font = new Font("comic sans ms", 1, 90);

        //minecraft font
        Font minecraftFont = Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\pc\\IdeaProjects\\Creeper-Party\\src\\Font\\minecraft.ttf")).deriveFont(90f);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\pc\\IdeaProjects\\Creeper-Party\\src\\Font\\minecraft.ttf")));

        //custom color
        Color customColor = new Color(0,105,35);

        //buttons menu draw
        //g.drawRect((Game.WIDTH/2)-countLetter*8, (Game.HEIGHT/2), 350, 100);
        Font font2 = new Font("courier new", 1, 25);

        //text in buttons
        //g.drawString(playButtonText, (Game.WIDTH/2)-countLetter2*6, 488);
        Font font3 = new Font("comic sans ms", 1, 25);

        Font font4 = new Font("simsun", 1, 30);

        if(game.gameState == Game.STATE.Menu) {
            //display title
            String title = "Creeper Party";
            this.countLetter = this.countLetters(title);

            g.setFont(minecraftFont);
            g.setColor(customColor);
            g.drawString(title, (Game.WIDTH/2)-this.countLetter*33, 160);

            //display background
            



            //buttons setup
            g.setFont(font2);
            g.setColor(Color.white);

            //Game.WIDTH/2)-this.countLetter*11 ==== 784

            //button play
            String playTest = "Play";
            this.countLetter2 = this.countLetters(playTest);
            g.drawRect((Game.WIDTH/2)-170, (Game.HEIGHT/2)-100, 350, 100);
            g.drawString(playTest, (Game.WIDTH/2)-this.countLetter2*8, (Game.HEIGHT/2)-50);

            //button options
            String optionsTest = "Options";
            this.countLetter4 = this.countLetters(optionsTest);
            g.drawRect((Game.WIDTH/2)-170, (Game.HEIGHT/2) + 75, 350, 100);
            g.drawString(optionsTest, (Game.WIDTH/2)-this.countLetter4*8, (Game.HEIGHT/2)+130);

            //button quit
            String quit = "Quit game";
            this.countLetter3 = this.countLetters(quit);
            g.drawRect((Game.WIDTH/2)-170, (Game.HEIGHT/2)+250, 350, 100);
            g.drawString(quit, (Game.WIDTH/2)-this.countLetter3*8, (Game.HEIGHT/2)+305);

        }else if(game.gameState == Game.STATE.Options){
            //buttons setup
            g.setFont(font2);
            g.setColor(Color.white);

            //button back
            String quit = "Back";
            this.countLetter5 = this.countLetters(quit);
            g.drawRect((Game.WIDTH/2)-170, (Game.HEIGHT/2)+250, 350, 100);
            g.drawString(quit, (Game.WIDTH/2)-this.countLetter5*8, (Game.HEIGHT/2)+305);
        }
    }

}
