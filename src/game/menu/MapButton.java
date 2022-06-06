package game.menu;

import engine.math.Vector;
import engine.scene.GameObject;
import engine.utils.Images;
import engine.utils.Sprite;
import engine.window.RenderLayer;
import game.scenes.GameScene;

public class MapButton extends GameObject
{
    public MapMetadata map;
    private Sprite sprite;
    private Sprite preview;
    
    public MapButton(MapMetadata map, Vector positon)
    {
        this.map = map;
        sprite = new Sprite("ui/map_button");
        sprite.position = positon;
        preview = new Sprite(Images.getImage(map.previewImageName, 200, 144));
        preview.position = positon.add(25, 25);
    }
    
    @Override
    public void update(double deltaTime)
    {
        if(input.left().isHoldInBounds(sprite.position, sprite.size))
        {
            engine.setActiveScene(new GameScene(map.name));
        }
    }
    
    @Override
    public void render(RenderLayer layer, double deltaTime)
    {
        if(layer.is("ui"))
        {
            layer.renderSprite(sprite);
            layer.renderSprite(preview);
            layer.graphics().setFont(GameScene.MONEY_FONT.deriveFont(25));
            layer.drawStringCentered(map.name, sprite.position.add(125, 200));
            layer.graphics().setColor(map.difficulty.color);
            layer.drawStringCentered(map.difficulty.displayName, sprite.position.add(125, 250));
        }
    }
}
