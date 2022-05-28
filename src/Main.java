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
        /*Window window = new Window(WIDTH, HEIGHT);
        for(String name : renderLayers) window.addLayer(name);*/
        
        Engine engine = new Engine(WIDTH, HEIGHT, renderLayers);
        
        Scene scene = new Scene(2, new LayerCollision[] {new Collisions.LayerCollision(0, 1, true)});
        
        GameObject go1 = new TestGameObject();
        GameObject go2 = new TestGameObject2();
        go1.position = new Vector(100, 100);
        go2.position = new Vector(350, 150);
        scene.addObject(go1);
        scene.addObject(go2);
        
        engine.loadScene(scene);
    }
    
}