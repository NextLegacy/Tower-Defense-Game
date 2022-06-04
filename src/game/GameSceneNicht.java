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
    
    @Override
    public void init() {
        // add menus and stuff
        addObject(new DebugGameObject());
        // TODO: implement
        
        // load map
        map = new Map(mapName);
        map.addGameObjects(this);
    }
}
