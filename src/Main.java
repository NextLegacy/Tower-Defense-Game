import engine.Engine;
import engine.math.Vector;
import engine.scene.Collisions;
import engine.scene.GameObject;
import engine.scene.Scene;
import engine.scene.Collisions.LayerCollision;
import test.TestGameObject;
import test.TestGameObject2;

public class Main
{
    
    private static final int WIDTH = 1280, HEIGHT = 720;
    private static final String[] renderLayers = new String[] {"Background", "Test"};
    
    public static void main(String[] args)
    {
        Engine engine = new Engine(WIDTH, HEIGHT, 60, 60, renderLayers);
        
        Scene scene = new Scene(2, new LayerCollision[] {new Collisions.LayerCollision(0, 1, true)});
        
        
        
        engine.setActiveScene(scene);
        engine.start();
    }
    
}