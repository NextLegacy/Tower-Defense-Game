package engine;

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

    public void setActiveScene(Scene scene) { activeScene = scene; }
    
    public Scene getActiveScene() { return activeScene; }

    public Window getWindow() { return window;}

    public InputListener getInputListener() { return window.getInputListener(); }

    @Override
    public void onActivate()
    {
        this.gameLoopThread.start();
    }

    @Override
    public void onDeactivate() 
    {
        this.window.dispose();    
    }

    private void start()
    {
        this.activeScene.start();
    }

    private void update(double deltaTime)
    {
        this.activeScene.update(deltaTime);
    }

    private void render(double deltaTime)
    {
        this.window.render((renderLayer) -> 
        {
            renderLayer.clear();
            this.activeScene.render(renderLayer, deltaTime);
        });
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

            Scene currentScene = null;

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
                        
                        start();
                    }

                    update(elapsedTime / 1_000_000_000.0d);
                    ticks++;
                    deltaT--;
                }

                if (!isActive() || !getInputListener().isActive()) // After updates, engine might be deactivated, no need to continue
                    break;

                if(deltaF >= 1)
                {
                    render(elapsedTime / 1_000_000_000.0d);
                    frames++;
                    deltaF--;
                }
                
                if(time >= 1_000_000_000)
                {
                    System.out.println("FPS: " + frames + " TPS: " + ticks); //Logging zeug wenn man es Braucht
                    time = ticks = frames = 0;
                }
                
                last = now;
            }
            
            getWindow().dispose();
        }
    }
}
