package engine;

import java.awt.Color;
import java.awt.Graphics2D;

import engine.scene.Activateable;
import engine.scene.Scene;
import engine.window.InputListener;
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
        this.gameLoop = new GameLoop(this, tps, fps);
        this.gameLoopThread = new Thread(this.gameLoop);

        this.activeScene = null;

        for(String name : renderLayers)
            window.addLayer(name);
    }

    public void setActiveScene(Scene scene)
    {
        activeScene = scene;
    }

    public void start()
    {
        this.activeScene.start();
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
    
    public Scene getActiveScene()
    {
        return activeScene;
    }

    public Window getWindow()
    {
        return window;
    }

    public InputListener getInputListener() { return window.getInputListener(); }

    @Override
    public void onActivate()
    {
        if (this.gameLoopThread.isAlive())
            throw new IllegalStateException("Engine did started already!");

        this.activate();

        this.gameLoopThread.start();
    }

    @Override
    public void onDeactivate()
    {
        if (!this.gameLoopThread.isAlive())
            throw new IllegalStateException("Engine did not started yet!");

        this.deactivate();
    }

    @Override
    public boolean isActive() 
    {
        return super.isActive() && this.window.isEnabled();
    }

    private final class GameLoop implements Runnable
    {
        private final int FPS;
        private final int TPS;
        
        private final Engine engine;
        
        public GameLoop(final Engine engine, final int TPS, final int FPS)
        {
            this.engine = engine;
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

            Scene currentScene = activeScene;

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
                    if (currentScene != activeScene)
                    {
                        if (currentScene != null)
                            currentScene.destroy();

                        currentScene = activeScene;
                        
                        currentScene.setEngine(engine);
                        currentScene.init();
                        currentScene.start();
                    }

                    update(elapsedTime);
                    ticks++;
                    deltaT--;
                }

                if (!isActive()) // After updates, engine might be deactivated, no need to continue
                    break;

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
