package game.scenes;

import engine.math.Vector;
import engine.scene.Scene;
import engine.scene.Collisions.LayerCollision;
import engine.utils.Sprite;
import engine.window.RenderLayer;
import game.gameObjects.DebugGameObject;
import game.menu.CloseButton;
import game.menu.MapButton;
import game.menu.MapMetadata;
import game.menu.StartButton;

public class MainMenuScene extends Scene
{
    private Sprite background;
    
    public MainMenuScene()
    {
        super(0, new LayerCollision[0]);
        
        background = new Sprite("main_menu");
    }
    
    @Override
    public void init()
    {
        addObject(new StartButton(new Vector(640, 360)));
        addObject(new DebugGameObject());
        addObject(new CloseButton());
    }
    
    public void showMaps()
    {
        Vector[] mapButtonPositions = new Vector[]
        {
            new Vector(110, 50), new Vector(380, 50), new Vector(650, 50), new Vector(920, 50),
            new Vector(110, 370), new Vector(380, 370), new Vector(650, 370), new Vector(920, 370)
        };
        
        for(int i = 0; i < MapMetadata.maps.length; i++)
        {
            addObject(new MapButton(MapMetadata.maps[i], mapButtonPositions[i]));
        }
    }
    
    @Override
    public void render(RenderLayer layer, double deltaTime)
    {
        if(layer.is("background"))
            layer.renderSprite(background);
        
        super.render(layer, deltaTime);
    }
}
