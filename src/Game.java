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
import java.io.IOException;
import java.util.ArrayList;

public class Game extends Canvas implements Runnable {

    public static int WIDTH;
    public static int HEIGHT;

    private Thread thread;
    private boolean running = false;
    private Handler handler;
    private HUD hud;
    private Menu menu;

    ArrayList<JLabel> labelList = new ArrayList<>();

    //pages menu screen
    public enum STATE {
        Menu,
        Play,
        Options,
        Game
    }

    public STATE gameState = STATE.Menu;

    public Game(){
        Window window = new Window("Creeper Party", this);
        WIDTH = window.getWIDTHWINDOW();
        HEIGHT = window.getHEIGHTWINDOW();
        handler = new Handler();
        menu = new Menu(this, handler, hud, window.getFrame());
        hud = new HUD();

        this.addKeyListener(new KeyInput(handler));
        this.addMouseListener(menu);

        handler.addObject(new Player(WIDTH/2-32, HEIGHT/2-32, ID.Player, handler));
        handler.addObject(new Enemy(WIDTH/2-32, HEIGHT/2-32, ID.Enemy, handler));
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
        handler.tick();
        if(gameState == STATE.Menu){
            menu.tick();
        }
        hud.tick();
    }

    private void render() throws IOException, FontFormatException {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }

        //zwarte achtergrond
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        if (gameState == STATE.Play){
            handler.render(g);
            hud.render(g);
        }else if(gameState == STATE.Menu || gameState == STATE.Options){
            menu.render(g);
            //handler.render(g);
        }

        g.dispose();
        bs.show();
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

    public void addJLabel(JLabel jLabel){
        labelList.add(jLabel);
    }

    public static void main(String[] args) {
        new Game();
    }
}
