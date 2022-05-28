package engine;

import engine.scene.Scene;
import engine.window.Window;

public class Engine
{
    private Window window;
    public Scene activeScene;
    
    public Engine(int width, int height, String[] renderLayers)
    {
        window = new Window(width, height);

        for(String name : renderLayers)
            window.addLayer(name);
    }
    
    public void start()
    {
        //start game loop
    }
    
    public void loadScene(Scene scene)
    {
        if(activeScene != null)
        {
            activeScene.destroy();
            activeScene = null;
        }
        activeScene = scene;
        activeScene.init();
        activeScene.sceneStart();
    }
}
