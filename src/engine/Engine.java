package engine;

import engine.scene.Scene;
import engine.window.Window;

public class Engine
{
    private Window window;
    
    public Engine(int width, int height, String[] renderLayers)
    {
        window = new Window(width, height);

        for(String name : renderLayers)
            window.addLayer(name);
    }
    
    public Scene activeScene;

    public void loadScene(Scene scene)
    {
        this.activeScene = null;
        this.activeScene.dispose();
        this.activeScene = scene;
        this.activeScene.start();
    }
}
