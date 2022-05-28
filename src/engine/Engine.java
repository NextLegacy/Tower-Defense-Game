package engine;

import engine.scene.Scene;
import engine.window.Window;

public class Engine 
{
    
    private Window window;
    
    public Engine(int width, int height, String[] renderLayers)
    {
        window = new Window(width, height);
        for(String name : renderLayers) window.addLayer(name);
    }
    
    // TODO@El_Tigere: hilwe wie kann man SceneManager oder so implementieren???
    
}
