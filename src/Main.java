import engine.Engine;
import engine.scene.Scene;
import test.TestScene;

public class Main
{
    
    private static final int WIDTH = 1280, HEIGHT = 720;
    private static final String[] renderLayers = new String[] {"Background", "Test"};
    
    public static void main(String[] args)
    {
        Engine engine = new Engine(WIDTH, HEIGHT, 120, 120, renderLayers);
        
        Scene scene = new TestScene();
        
        engine.setActiveScene(scene);
        engine.activate();
    }
    
}