package game;

import engine.math.Vector;
import engine.scene.Scene;
import engine.scene.Collisions.LayerCollision;
import game.gameObjects.DebugGameObject;

public class GameSceneNicht extends Scene
{
    private String mapName;
    public Map map;
    
    public GameSceneNicht(String mapName)
    {
        super(4, new LayerCollision[] {new LayerCollision(1, 0, false), new LayerCollision(1, 1, false), new LayerCollision(3, 2, true)}); // collision layers: [map (obstacles), towers, enemies, projectiles]
        this.mapName = mapName;
    }

    double time = 0;
    @Override
    public void update(double deltaTime)
    {
        time += deltaTime;
        if (time >= 0.1)
        {
            addObject(new Enemy());
            time = 0;
        }
        super.update(deltaTime);
    }
    
    @Override
    public void init() {
        // add menus and stuff
        addObject(new DebugGameObject());
        // TODO: implement
        
        // load map
        map = new Map(mapName);
        map.addGameObjects(this);

        addObject(new Enemy());
    }
}
