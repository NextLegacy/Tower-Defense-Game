package game.scenes;

import engine.scene.GameObject;
import engine.scene.Scene;
import engine.scene.Collisions.LayerCollision;

public class GameScene extends Scene
{
    public GameScene() 
    {
        super(0, new LayerCollision[] { });
    }

    @Override
    public void init() 
    {
        
    }
}
