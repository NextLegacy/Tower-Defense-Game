package game.menu;

import engine.math.Vector;
import engine.scene.GameObject;
import engine.utils.Sprite;
import engine.window.RenderLayer;

public class CloseButton extends GameObject
{
    public Sprite sprite;
    
    public CloseButton()
    {
        sprite = new Sprite("ui/close");
    }
    
    @Override
    public void start()
    {
        sprite.position = new Vector(window.width() - sprite.size.x, 0);
    }
    
    @Override
    public void update(double deltaTime)
    {
        if(input.left().isClickedInBounds(sprite.position, sprite.size))
            engine.deactivate();
    }
    
    @Override
    public void render(RenderLayer layer, double deltaTime)
    {
        if(layer.is("window"))
            layer.renderSprite(sprite);
    }
}
