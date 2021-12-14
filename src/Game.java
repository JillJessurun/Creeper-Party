import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.IOException;

public class Game extends Canvas implements Runnable {

    public static int WIDTH;
    public static int HEIGHT;

    private Thread thread;
    private boolean running = true;
    private Handler handler;
    private HUD hud;

    public Game(){
        Window window = new Window("Creeper Party", this);
        WIDTH = window.getWIDTHWINDOW();
        HEIGHT = window.getHEIGHTWINDOW();

        handler = new Handler();
        this.addKeyListener(new KeyInput(handler));

        hud = new HUD();

        handler.addObject(new Player(WIDTH/2-32, HEIGHT/2-32, ID.Player, handler));
        handler.addObject(new Player(WIDTH/2-32, HEIGHT/2-32, ID.Player2, handler));
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
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1){
                tick();
                delta--;
            }
            if(running){
                render();
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

    private void tick(){
        handler.tick();
        hud.tick();
    }

    private void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        handler.render(g);
        hud.render(g);

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

    public static void main(String[] args) {
        new Game();
    }
}
