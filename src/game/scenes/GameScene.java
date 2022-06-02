package game.scenes;

import engine.scene.Scene;
import engine.scene.Collisions.LayerCollision;
import game.gameObjects.DebugGameObject;
import game.gameObjects.towers.towerSelection.TowerSelection;

public class GameScene extends Scene
{
    public GameScene() 
    {
        super(0, new LayerCollision[] { });
    }

    @Override
    public void init() 
    {
        addObject(new TowerSelection());
        addObject(new DebugGameObject());
    }
}
