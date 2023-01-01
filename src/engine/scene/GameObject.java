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
        
        if (scene != null)
            scene.removeObject(this);
    }
    
    /**
     * start is called when a GameObject is added to a started scene or when a scene with a GameObjects is startet.
     */
    public void start() { }
    
    /**
     * update is called tps times every second. It is used for movement updates.
     * @param deltaTime time since last update
     */
    public void update(double deltaTime) { }
    
    /**
     * render is called fps times every second and for every render layer. It is used for rendering GameObjects to one RenderLayer.
     * @param layer current layer
     * @param deltaTime time since last render
     */
    public void render(RenderLayer layer, double deltaTime) { }
    
    /**
     * onDestroy is called before a GameObject is removed from the game.
     */
    public void onDestroy() { }
    
    public void onSceneChange() { }
}