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

    private double currentFPS;
    private double currentTPS;

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

    public Window getWindow() { return window; }

    public InputListener getInputListener() { return window.getInputListener(); }

    public double currentFPS() { return currentFPS; }
    public double currentTPS() { return currentTPS; }

    @Override
    public void onActivate()
    {
        this.gameLoopThread.start();
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

            final double TICK_INTERVAL_S = 1.0d / this.TPS;
            final double FRAME_INTERVAL_S = 1.0d / this.FPS;

            double deltaT = 1.0d;
            double deltaF = 0.0d;

            long last = System.nanoTime();

            double time = 0;

            double ticks = TPS;
            double frames = FPS;          
            
            currentTPS = ticks;
            currentFPS = frames;

            Scene currentScene = null;

            while(isActive())
            {
                final long now = System.nanoTime();

                final double elapsedTime = now - last;
                final double elapsedTimeS = elapsedTime / 1_000_000_000.0d;

                time += elapsedTimeS;

                deltaT += elapsedTime / TICK_INTERVAL;
                deltaF += elapsedTime / FRAME_INTERVAL;

                last = now;

                //if (getInputListener().windowState() != Frame.ICONIFIED)
                //{
                //    getWindow().requestFocus();
                //}

                while (deltaT >= 1)
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
                    
                    update(TICK_INTERVAL_S);
                    ticks++;
                    deltaT--;
                }

                // After updates, engine might be deactivated, no need to continue
                if (!isActive() || !getInputListener().isActive())
                    break;

                if (deltaF >= 1)
                {
                    render(FRAME_INTERVAL_S);
                    frames++;
                    deltaF--;
                }
                
                if(time >= 1)
                {
                    currentTPS = ticks;
                    currentFPS = frames;
                    time = ticks = frames = 0;
                }
            }
            
            getWindow().dispose();
        }
    }
}
