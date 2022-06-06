package game.menu;

import engine.math.Vector;
import engine.scene.GameObject;
import engine.utils.Sprite;
import engine.window.RenderLayer;
import game.scenes.MainMenuScene;

public class StartButton extends GameObject
{
    public Sprite sprite;
    
    public StartButton(Vector position)
    {
        sprite = new Sprite("ui/start");
        sprite.position = position;
    }
    
    @Override
    public void update(double deltaTime)
    {
        if(input.left().isClickedInBounds(sprite.position.sub(sprite.size.div(2)), sprite.size))
        {
            ((MainMenuScene) scene).showMaps();
            destroy();
        }
    }
    
    @Override
    public void render(RenderLayer layer, double deltaTime)
    {
        if(layer.is("ui"))
            layer.renderSpriteCentered(sprite);
    }
}
