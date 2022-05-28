package engine;

import java.awt.Color;
import java.awt.Graphics2D;

import engine.scene.Activateable;
import engine.scene.Scene;
import engine.window.Window;

public class Engine extends Activateable
{
    private final Window window;

    private final GameLoop gameLoop;
    private Thread gameLoopThread;

    private Scene activeScene;

    public Engine(int width, int height, int tps, int fps, String[] renderLayers)
    {
        this.window = new Window(width, height);
        this.gameLoop = new GameLoop(tps, fps);
        this.gameLoopThread = new Thread(this.gameLoop);

        this.activeScene = Scene.EMPTY_SCENE;

        for(String name : renderLayers)
            window.addLayer(name);
    }

    public void start()
    {
        if (this.gameLoopThread.isAlive())
            throw new IllegalStateException("Engine did started already!");

        this.activate();

        this.gameLoopThread.start();
    }

    public void end()
    {
        if (!this.gameLoopThread.isAlive())
            throw new IllegalStateException("Engine did not started yet!");
        
        this.deactivate();

        //this.gameLoopThread.interrupt(); //Game Loop Thread
    }

    public void update(double deltaTime)
    {
        this.activeScene.update(deltaTime);
    }

    public void render(double deltaTime)
    {
        this.window.render((renderLayer) -> 
        {
            Graphics2D g2d = renderLayer.graphics();

            g2d.setColor(new Color(0, 0, 0, 0));

            g2d.fillRect(0, 0, renderLayer.width(), renderLayer.height());

            g2d.setColor(Color.black);

            this.activeScene.render(renderLayer, deltaTime);
        });
    }

    public void loadScene(Scene scene)
    {
        activeScene.destroy();

        activeScene = scene;
        activeScene.init();
        activeScene.start();
    }

    private final class GameLoop implements Runnable
    {
        private final int FPS;
        private final int TPS;

        public GameLoop(final int TPS, final int FPS)
        {
            this.TPS = TPS;
            this.FPS = FPS;
        }

        @Override
        public void run()
        {
            final double TICK_INTERVAL = 1_000_000_000.0d / this.TPS;
            final double FRAME_INTERVAL = 1_000_000_000.0d / this.FPS;

            double deltaT = 0.0d;
            double deltaF = 0.0d;

            double last = System.nanoTime();

            double time = System.nanoTime();

            int ticks = 0;
            int frames = 0;

            while(isActive())
            {
                final double now = System.nanoTime();

                final double elapsedTime = now - last;
                time += elapsedTime;

                deltaT += elapsedTime / TICK_INTERVAL;
                deltaF += elapsedTime / FRAME_INTERVAL;

                last = now;

                if(deltaT >= 1)
                {
                    update(elapsedTime);
                    ticks++;
                    deltaT--;
                }

                if(deltaF >= 1)
                {
                    render(elapsedTime);
                    frames++;
                    deltaF--;
                }

                if(time >= 1_000_000_000)
                {
                    //System.out.println("FPS: " + frames + " TPS: " + ticks); //Logging zeug wenn man es Braucht
                    time = ticks = frames = 0;
                }
                
                last = now;
            }
        }
    }
}
