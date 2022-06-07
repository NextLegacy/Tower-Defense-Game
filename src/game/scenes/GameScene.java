package game.scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;

import engine.math.Vector;
import engine.scene.Scene;
import engine.scene.Collisions.LayerCollision;
import engine.utils.Fonts;
import engine.window.RenderLayer;
import game.WaveManager;
import game.gameObjects.DebugGameObject;
import game.gameObjects.tower.TowerMenu;
import game.menu.CloseButton;

public class GameScene extends Scene
{
    public static final Font MONEY_FONT = Fonts.get("[FONT NAME]", 25, Font.PLAIN);
    public static final Font TEXT_FONT = Fonts.get("[FONT NAME]", 25, Font.PLAIN);
    public static final Font DESCRIPTION_FONT = Fonts.get("[FONT NAME]", 25, Font.ITALIC);

    public static final Vector GAME_AREA_START = new Vector(0, 0);
    public static final Vector GAME_AREA_END = new Vector(1000, 720);

    public static final Vector GAME_AREA_SIZE = new Vector(1000, 720);
    
    public static final Vector MENU_AREA_START = new Vector(1000, 0);
    public static final Vector MENU_AREA_SIZE = new Vector(280, 720);
    
    public static final Vector PLACEABLE_TOWER_IN_MENU_SIZE = new Vector(60, 60);

    public static final Vector PLACEABLE_TOWER_AREA_START = new Vector(1000, 0);
    public static final Vector PLACEABLE_TOWER_AREA_SIZE = new Vector(280, 410);
    
    public static final Vector UPGRADE_BUTTON_SIZE = new Vector(60, 60);
    public static final Vector UPGRADE_MENU_START = new Vector(1010, 310);
    public static final Vector UPGRADE_MENU_SIZE = new Vector(260, 400);

    private String mapName;
    public Map map;
    public WaveManager waveManager;

    public TowerMenu towerMenu;

    public double money;
    public int health;
    
    public GameScene(String mapName)
    {
        super(4, new LayerCollision[] {new LayerCollision(1, 0, false), new LayerCollision(1, 1, false), new LayerCollision(3, 2, true)}); // collision layers: [map (obstacles), towers, enemies, projectiles]
        this.mapName = mapName;
        
        money = 500;
        health = 200;
    }

    @Override
    public void init() {
        // add menus and stuff
        addObject(new DebugGameObject());
        addObject(towerMenu = new TowerMenu());
        addObject(new CloseButton());
        
        // load map
        map = new Map(mapName);
        map.addGameObjects(this);
        
        waveManager = new WaveManager(this, engine);
        
        //addObject(new TestMouseCollider()); // debug
    }
    
    public void damage(int amount)
    {
        health -= amount;
        if(health <= 0)
            engine.setActiveScene(new GameOverScene(false));
    }
    
    @Override
    public void update(double deltaTime) {
        if(engine.getInputListener().key(KeyEvent.VK_S).isDown())
        {
            waveManager.startNextWave();
        }
        
        waveManager.update(deltaTime);
        super.update(deltaTime);
    }
    
    @Override
    public void render(RenderLayer layer, double deltaTime) {
        if(layer.is("background"))
            layer.renderSprite(map.background);
        
        if (layer.is("debug"))
        {
            layer.graphics().setFont(MONEY_FONT);
            layer.graphics().setColor(Color.YELLOW);
            layer.drawStringCentered((int)money+"$", MENU_AREA_START.add(MENU_AREA_SIZE.x / 2 + 20, 20));
            layer.graphics().setColor(Color.RED);
            layer.drawStringCentered(health + "♥", MENU_AREA_START.add(40, 20));
            
        }
            
        super.render(layer, deltaTime);
    }
}
