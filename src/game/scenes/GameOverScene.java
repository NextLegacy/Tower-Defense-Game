package game.scenes;

import engine.math.Vector;
import engine.scene.Scene;
import engine.scene.Collisions.LayerCollision;
import engine.utils.Sprite;
import engine.window.RenderLayer;
import game.gameObjects.DebugGameObject;
import game.menu.CloseButton;
import game.menu.ContinueButton;

public class GameOverScene extends Scene
{
    private Sprite background;
    
    public GameOverScene(boolean win)
    {
        super(0, new LayerCollision[0]);
        
        background = win ? new Sprite("backgrounds/win_screen") : new Sprite("backgrounds/lose_screen");
    }
    
    @Override
    public void init()
    {
        addObject(new ContinueButton(new Vector(640, 600)));
        addObject(new DebugGameObject());
        addObject(new CloseButton());
    }
    
    @Override
    public void render(RenderLayer layer, double deltaTime)
    {
        if(layer.is("background"))
            layer.renderSprite(background);
        
        super.render(layer, deltaTime);
    }
}
