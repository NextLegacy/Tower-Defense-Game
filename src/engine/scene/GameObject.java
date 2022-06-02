package engine.scene;

import engine.Engine;
import engine.window.InputListener;
import engine.window.RenderLayer;
import engine.window.Window;

public abstract class GameObject extends Activateable
{
    //public Vector position;

    protected Scene scene;
    
    protected Engine engine;
    protected Window window;
    protected InputListener input;
    
    public GameObject()
    {
        //position = Vector.zero();
    }
    
    public final void setScene(final Engine engine, Scene scene)
    {
        this.scene = scene;
        this.engine = engine;
        this.window = engine.getWindow();
        this.input = engine.getInputListener();

        this.onSceneChange();
    }
    
    public final void destroy()
    {
        deactivate();
        onDestroy();
        scene.removeObject(this);
    }
    
    public void start() { }
    
    public void update(double deltaTime) { }
    
    public void render(RenderLayer layer, double deltaTime) { }

    public void onDestroy() { }

    public void onSceneChange() { }
}