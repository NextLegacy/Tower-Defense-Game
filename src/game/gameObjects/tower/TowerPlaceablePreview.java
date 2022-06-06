package game.gameObjects.tower;

import java.awt.Color;
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
        position.lerp(
            sprite.position, 
            input.mouse().position().clamp(
                towerPlaceable.towerSprite.size.div(2), 
                GameScene.GAME_AREA_SIZE.sub(towerPlaceable.towerSprite.size.div(2))
            ),
            deltaTime * 15 
        );

        sprite.position = position;

        if (input.left().isDown() && canBePlaced)
        {
            System.out.println(canBePlaced);

            gameScene.money -= towerPlaceable.cost;

            Tower tower = towerPlaceable.createTower();
            tower.position = position;
            
            gameScene.addObject(tower);

            collisions.setLayerCollisionActive(0, false);
            collisions.setLayerCollisionActive(1, false);

            destroy();
        }

        canBePlaced = true;
    }

    @Override
    public void render(RenderLayer layer, double deltaTime) 
    {
        if (layer.is("debug"))
            layer.renderSpriteCentered(sprite);

        if (canBePlaced)
            layer.graphics().setColor(Color.green);
        else
            layer.graphics().setColor(Color.red);
        
        layer.fillRectCentered(sprite.position, sprite.size);
    }

    @Override
    public void onCollision(ArrayList<CollisionGameObject> collisionObjects) 
    {
        canBePlaced = false;
    }
}
