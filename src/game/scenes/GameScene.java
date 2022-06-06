package game.scenes;

import engine.math.Vector;
import engine.scene.Scene;
import engine.scene.Collisions.LayerCollision;
import engine.window.RenderLayer;
import game.gameObjects.DebugGameObject;
import game.gameObjects.enemies.Enemy;
import game.gameObjects.tower.TowerMenu;

public class GameScene extends Scene
{
    public final static Vector GAME_AREA_START = new Vector(0, 0);
    public final static Vector GAME_AREA_END = new Vector(1000, 720);

    public final static Vector GAME_AREA_SIZE = new Vector(1000, 720);
    
    public final static Vector MENU_AREA_START = new Vector(1000, 0);
    public final static Vector MENU_AREA_SIZE = new Vector(280, 720);
    
    public final static Vector PLACEABLE_TOWER_SIZE = new Vector(60, 60);

    public final static Vector PLACEABLE_TOWER_AREA_START = new Vector(1000, 0);
    public final static Vector PLACEABLE_TOWER_AREA_SIZE = new Vector(280, 410);
    
    public final static Vector upgradeSize = new Vector(90, 90);
    public final static Vector upgradeMenuBegin = new Vector(1010, 310);
    public final static Vector upgradeMenuEnd = new Vector(260, 400);

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
        addObject(new TowerMenu());
        
        // load map
        map = new Map(mapName);
        map.addGameObjects(this);
        
        //addObject(new TestMouseCollider()); // debug
        
        addObject(new Enemy()); // debug
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
