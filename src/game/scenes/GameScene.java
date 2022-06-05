package game.scenes;

import engine.scene.Scene;
import engine.scene.Collisions.LayerCollision;
import engine.window.RenderLayer;
import game.gameObjects.DebugGameObject;
import game.gameObjects.enemies.Enemy;

public class GameScene extends Scene
{
    private String mapName;
    public Map map;
    
    public GameScene(String mapName)
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

        addObject(new Enemy());
    }
    
    double time = 0;
    @Override
    public void update(double deltaTime)
    {
        time += deltaTime;
        if (time >= 1)
        {
            addObject(new Enemy());
            time = 0;
        }
        super.update(deltaTime);
    }
    
    @Override
    public void render(RenderLayer layer, double deltaTime) {
        if(layer.name() == "background")
            layer.renderSprite(map.background);
        super.render(layer, deltaTime);
    }
}
