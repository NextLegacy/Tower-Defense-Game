package game.menu;

import engine.math.Vector;
import engine.scene.GameObject;
import engine.utils.Sprite;
import engine.window.RenderLayer;
import game.scenes.MainMenuScene;

public class ContinueButton extends GameObject
{
    public Sprite sprite;
    
    public ContinueButton(Vector position)
    {
        sprite = new Sprite("ui/main_menu");
        sprite.position = position;
    }
    
    @Override
    public void update(double deltaTime)
    {
        if(input.left().isClickedInBounds(sprite.position.sub(sprite.size.div(2)), sprite.size))
        {
            engine.setActiveScene(new MainMenuScene());
        }
    }
    
    @Override
    public void render(RenderLayer layer, double deltaTime)
    {
        if(layer.is("ui"))
            layer.renderSpriteCentered(sprite);
    }
}
