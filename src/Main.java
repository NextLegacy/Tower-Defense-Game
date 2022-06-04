import engine.Engine;
import engine.scene.Scene;
import game.scenes.GameSceneNicht;

public class Main
{
    
    private static final int WIDTH = 1280, HEIGHT = 720;
    private static final String[] renderLayers = new String[] {"Background", "Test", "Test2"};
    
    public static void main(String[] args)
    {
        Engine engine = new Engine(WIDTH, HEIGHT, 120, 60, renderLayers);
        
        //Scene scene = new TestScene();
        Scene scene = new GameSceneNicht("");
        
        engine.setActiveScene(scene);
        engine.activate();
        
        // Geht nicht, weil die Scene noch nicht initialisiert ist | verschoben nack -> GameSceneNicht.init() <-
        //scene.addObject(new Enemy());
    }
    
}