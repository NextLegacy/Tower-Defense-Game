package game.gameObjects.tower;

import engine.scene.CollisionGameObject;
import engine.utils.Sprite;
import engine.window.RenderLayer;
import game.scenes.GameScene;

public class TowerPlaceablePreview<T extends Tower> extends CollisionGameObject
{
    public final TowerPlaceable<T> towerPlaceable;
    public final Sprite sprite;

    private GameScene gameScene;

    //Implement can be placed here

    public TowerPlaceablePreview(TowerPlaceable<T> towerPlaceable)
    {
        super(1);
    
        this.towerPlaceable = towerPlaceable;
        this.sprite = towerPlaceable.towerSprite.deriveSprite();
    }

    @Override
    public void start() 
    {
        gameScene = (GameScene) scene;
    }

    @Override
    public void update(double deltaTime) 
    {
        position.lerp(
            sprite.position, 
            input.mouse().position().clamp(towerPlaceable.towerSprite.size.div(2), GameScene.GAME_AREA_SIZE.sub(towerPlaceable.towerSprite.size.div(2))), //TODO: add Spritesize of Tower to clamp
            deltaTime * 15 
        );

        sprite.position = position;

        if (input.left().isDown())
        {
            System.out.println("Tower placed");

            gameScene.money -= towerPlaceable.cost;

            Tower tower = towerPlaceable.createTower();
            tower.position = position;
            
            gameScene.addObject(tower);

            destroy();
        }
    }

    @Override
    public void render(RenderLayer layer, double deltaTime) 
    {
        if (layer.is("debug"))
            layer.renderSpriteCentered(sprite);
    }
}
