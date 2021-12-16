import javax.sound.sampled.Port;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
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
    private BufferedImage creeperPlayer;
    private BufferedImage netherPortal;
    private HUDgames huDgames;

    public Menu(Game game, Handler handler, BufferedImage creeperPlayer, BufferedImage netherPortal, HUDgames huDgames){
        this.game = game;
        this.handler = handler;
        this.creeperPlayer = creeperPlayer;
        this.netherPortal = netherPortal;
        this.huDgames = huDgames;
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
        if (mouseOver(mx, my, (Game.WIDTH/2), (Game.HEIGHT/2)+250, 350, 100) && game.gameState == Game.STATE.Menu){
            handler.clearEnemies();
            System.exit(0);
        }

        //options button home
        if (mouseOver(mx, my, (Game.WIDTH/2), (Game.HEIGHT/2) + 75, 350, 100) && game.gameState == Game.STATE.Menu){
            handler.clearEnemies();
            game.gameState = Game.STATE.Options;
        }

        //back button options
        if (mouseOver(mx, my, (Game.WIDTH/2), (Game.HEIGHT/2)+250, 350, 100) && game.gameState == Game.STATE.Options){
            try {
                game.specialEffect();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            game.gameState = Game.STATE.Menu;
        }

        //play button home
        if (mouseOver(mx, my, (Game.WIDTH/2), (Game.HEIGHT/2)-100, 350, 100) && game.gameState == Game.STATE.Menu){
            handler.clearEnemies();
            handler.addObject(new Player(Game.WIDTH/2-32, Game.HEIGHT/2-32, ID.Player, handler, this, creeperPlayer, game, huDgames));

            handler.addObject(new Portal(0, 0, ID.Portal, netherPortal));

            game.gameState = Game.STATE.Lobby;
        }

        /*
        //play button home
        if (mouseOver(mx, my, (Game.WIDTH/2), (Game.HEIGHT/2)-100, 350, 100) && game.gameState == Game.STATE.Menu){
            handler.clearEnemies();
            handler.addObject(new Player(Game.WIDTH/2-32, Game.HEIGHT/2-32, ID.Player, handler, this, image, creeperPlayer));
            game.gameState = Game.STATE.Play;
        }

         */
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
        Color ColorDarkGreen = new Color(36, 197, 0);
        Color ColorLightGreen = new Color(216,255,203);

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
            g.setColor(ColorDarkGreen);
            g.drawString(title, (Game.WIDTH/2)-this.countLetter*33, 160);

            //buttons setup
            g.setFont(font2);
            g.setColor(ColorLightGreen);

            //Game.WIDTH/2)-this.countLetter*11 ==== 784

            //button play
            String playTest = "Play";
            this.countLetter2 = this.countLetters(playTest);
            g.drawRect((Game.WIDTH/2), (Game.HEIGHT/2)-100, 350, 100);
            g.drawString(playTest, (Game.WIDTH/2)+this.countLetter2*35, (Game.HEIGHT/2)-50);

            //button options
            String optionsTest = "Options";
            this.countLetter4 = this.countLetters(optionsTest);
            g.drawRect((Game.WIDTH/2), (Game.HEIGHT/2) + 75, 350, 100);
            g.drawString(optionsTest, (Game.WIDTH/2)+this.countLetter2*29, (Game.HEIGHT/2)+130);

            //button quit
            String quit = "Quit game";
            this.countLetter3 = this.countLetters(quit);
            g.drawRect((Game.WIDTH/2), (Game.HEIGHT/2)+250, 350, 100);
            g.drawString(quit, (Game.WIDTH/2)+this.countLetter2*26, (Game.HEIGHT/2)+305);

        }else if(game.gameState == Game.STATE.Options){
            //buttons setup
            g.setFont(font2);
            g.setColor(ColorLightGreen);

            //button back
            String quit = "Back";
            this.countLetter5 = this.countLetters(quit);
            g.drawRect((Game.WIDTH/2), (Game.HEIGHT/2)+250, 350, 100);
            g.drawString(quit, (Game.WIDTH/2)+this.countLetter5*35, (Game.HEIGHT/2)+305);
        }
    }

}
