package game.gameObjects.tower;

import java.util.ArrayList;

import engine.scene.CollisionGameObject;
import engine.utils.Sprite;
import engine.window.RenderLayer;
import game.scenes.GameScene;

public class TowerPlaceablePreview<T extends Tower> extends CollisionGameObject
{
    public final TowerPlaceable<T> towerPlaceable;
    public final Sprite sprite;

    private GameScene gameScene;

    public boolean canBePlaced;

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

        size = sprite.size;

        collisions.setLayerCollisionActive(0, true);
        collisions.setLayerCollisionActive(1, true);
    }

    @Override
    public void update(double deltaTime) 
    {
        position = input.mouse().position().clamp(
            towerPlaceable.towerSprite.size.div(2), 
            GameScene.GAME_AREA_SIZE.sub(towerPlaceable.towerSprite.size.div(2))
        );

        if (input.left().isDown() && canBePlaced)
        {
            gameScene.money -= towerPlaceable.cost;

            Tower tower = towerPlaceable.createTower(position);
            
            scene.addObject(tower);

            collisions.setLayerCollisionActive(0, false);
            collisions.setLayerCollisionActive(1, false);

            destroy();
        }

        canBePlaced = true;
    }

    @Override
    public void render(RenderLayer layer, double deltaTime) 
    {
        if (layer.isNot("towers"))
            return;

        if (canBePlaced)
            layer.setColor(0);
        else
            layer.setColor(0xff0000a9);
        
        sprite.position = position;

        layer.drawRectCentered(sprite.position, sprite.size, 8);

        layer.renderSpriteCentered(sprite);

    }

    @Override
    public void onCollision(ArrayList<CollisionGameObject> collisionObjects) 
    {
        canBePlaced = false;
    }
}
