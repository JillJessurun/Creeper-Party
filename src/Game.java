/*
author: Jill Jessurun
date: december 2021
goal: creating a game in java
source: https://www.youtube.com/watch?v=1gir2R7G9ws&list=PLWms45O3n--6TvZmtFHaCWRZwEqnz2MHa

ALL TUTORIALS PER SUBJECT:
tutorial 1: Create a window with a game loop
tutorial 2: Creating a handler, a GameObject class and a square as a player
tutorial 3: Keyboard input
tutorial 4: Enemies and HUD
tutorial 5: Collision, cool tail effect
tutorial 6: Creating a spawn system
tutorial 7: Fix bug sticky keys, create following enemy
tutorial 8: Make a boss
tutorial 9: Create a menu system using mouse input
tutorial 10: Cool menu background effect, making game end screen
tutorial 11: Implementing music into the game
tutorial 12: Pause menu in the game, and a difficulty system in the menu
tutorial 13: Adding textures into the game (png, jpg)
tutorial 14: Creating a shop for the game
tutorial 15: Finishing up the game: extract the files and creating an exe file
 */

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Game extends Canvas implements Runnable {

    public static int WIDTH;
    public static int HEIGHT;

    private Thread thread;
    private boolean running = false;
    private Handler handler;
    private HUD hud;
    private HUDgames hudGames;
    private Menu menu;
    private Lobby lobby;
    private Spawner spawner;
    private boolean menuCreated = false;

    //images
    private BufferedImage background;
    private BufferedImage creeperHead;
    private BufferedImage creeperPlayer;
    private BufferedImage creeperEnemy;
    private BufferedImage netherPortal;

    ArrayList<JLabel> labelList = new ArrayList<>();

    //pages menu screen
    public enum STATE {
        Menu,
        OcelotMadness,
        Options,
        Lobby,
        HUDminigames
    }

    public STATE gameState = STATE.Menu;

    public static BufferedImage image;  // background
    public static BufferedImage image2; // background creeper head effect
    public static BufferedImage image3; // player creeper head
    public static BufferedImage image4; // ocelot
    public static BufferedImage image5; // nether portal

    public Game() throws IOException {
        //ALL IMAGES SETUP
        BufferedImageLoader loader = new BufferedImageLoader();
        image = loader.loadImage("C:\\Users\\pc\\IdeaProjects\\Creeper-Party\\src\\Image\\creeper6.jpg");
        image2 = loader.loadImage("C:\\Users\\pc\\IdeaProjects\\Creeper-Party\\src\\Image\\creeperHead.png");
        image3 = loader.loadImage("C:\\Users\\pc\\IdeaProjects\\Creeper-Party\\src\\Image\\creeperHead2.png");
        image4 = loader.loadImage("C:\\Users\\pc\\IdeaProjects\\Creeper-Party\\src\\Image\\ocelotHead.jpg");
        image5 = loader.loadImage("C:\\Users\\pc\\IdeaProjects\\Creeper-Party\\src\\Image\\netherportal.png");
        Image image = new Image(Game.image);
        Image image2 = new Image(Game.image2);
        Image image3 = new Image(Game.image3);
        Image image4 = new Image(Game.image4);
        Image image5 = new Image(Game.image5);
        background = image.grabImage();
        creeperHead = image2.grabImage();
        creeperPlayer = image3.grabImage();
        creeperEnemy = image4.grabImage();
        netherPortal = image5.grabImage();

        handler = new Handler();
        hudGames = new HUDgames(this, handler);

        //resize image
        creeperPlayer = image.resizeImage(creeperPlayer, 60, 60);
        netherPortal= image.resizeImage(netherPortal, 100, 150);

        this.menu = new Menu(this, handler, creeperPlayer, netherPortal, hudGames);

        menuCreated = true;

        Window window = new Window("Creeper Party", this);
        WIDTH = window.getWIDTHWINDOW();
        HEIGHT = window.getHEIGHTWINDOW();

        //RESIZE IMAGES
        background = image.resizeImage(background, WIDTH, HEIGHT);
        creeperHead = image.resizeImage(creeperHead, 50, 50);
        creeperEnemy= image.resizeImage(creeperEnemy, 40, 40);

        hud = new HUD(this.menu);

        this.addKeyListener(new KeyInput(handler, this.menu));
        this.addMouseListener(this.menu);
        this.addMouseListener(this.hudGames);

        spawner = new Spawner(handler, hud, this, this.menu, this.image4, creeperEnemy);

        lobby = new Lobby();

        for (int i = 0; i < 40; i++) {
            handler.addObject(new BackgroundEffect(0, 0, ID.Backgroundeffect, handler, this.menu, this.image2, this.creeperHead));
        }
    }

    public synchronized void start(){
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop(){
        try{
            thread.join();
            running = false;
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        running = true;
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1){
                try {
                    tick();
                } catch (IOException | FontFormatException e) {
                    e.printStackTrace();
                }
                delta--;
            }
            if(running){
                try {
                    render();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (FontFormatException e) {
                    e.printStackTrace();
                }
            }
            frames++;

            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    private void tick() throws IOException, FontFormatException {
        if (menuCreated) {
            handler.tick();
            if (gameState == STATE.OcelotMadness) {
                hud.tick();
                spawner.tick();
            }else if (gameState == STATE.Menu) {
                menu.tick();
            }else if (gameState == STATE.Lobby) {
                lobby.tick();
            }else if (gameState == STATE.HUDminigames) {
                hudGames.tick();
            }
        }
    }

    private void render() throws IOException, FontFormatException {
        if (menuCreated) {
            BufferStrategy bs = this.getBufferStrategy();
            if (bs == null) {
                this.createBufferStrategy(3);
                return;
            }

            //zwarte achtergrond
            Graphics g = bs.getDrawGraphics();
            g.setColor(Color.black);
            g.fillRect(0, 0, WIDTH, HEIGHT);

            if (gameState == STATE.OcelotMadness) {
                handler.render(g);
                hud.render(g);
            } else if (gameState == STATE.Menu || gameState == STATE.Options) {
                //creeper achtergrond
                g.drawImage(background, 0, 0, null);

                menu.render(g);
                handler.render(g);
            }else if (gameState == STATE.Lobby) {
                handler.render(g);
                lobby.render(g);
            }else if (gameState == STATE.HUDminigames) {
                handler.render(g);
                hudGames.render(g);
            }

            g.dispose();
            bs.show();
        }
    }

    //clamp method: if the var is at the max, it stays at the max (same with the min)
    public static float clamp(float var, float min, float max){
        if(var >= max){
            return var = max;
        }else if(var <= min){
            return var = min;
        }else{
            return var;
        }
    }

    public void specialEffect() throws IOException {
        for (int i = 0; i < 40; i++) {
            handler.addObject(new BackgroundEffect(0, 0, ID.Backgroundeffect, handler, this.menu, this.image2, this.creeperHead));
        }
    }

    public static void main(String[] args) throws IOException {
        new Game();
    }
}
