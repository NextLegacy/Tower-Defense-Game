import engine.Engine;
import engine.scene.Scene;
import game.scenes.GameScene;

public class Main
{
    
    private static final int WIDTH = 1280, HEIGHT = 720;
    private static final String[] renderLayers = new String[] {"background", "test", "test2", "debug"};
    
    public static void main(String[] args)
    {
        Engine engine = new Engine(WIDTH, HEIGHT, 120, 60, renderLayers);
        
        //Scene scene = new TestScene();
        Scene scene = new GameScene("test_map");
        
        engine.setActiveScene(scene);
        engine.activate();
        
        // Geht nicht, weil die Scene noch nicht initialisiert ist | verschoben nack -> GameSceneNicht.init() <-
        //scene.addObject(new Enemy());
    }
    
}