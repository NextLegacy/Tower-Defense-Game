package game.gameObjects.enemies;

import engine.math.Vector;
import engine.utils.SpriteSheet;

public class BossYellow extends Enemy
{
    public static SpriteSheet spriteSheet = new SpriteSheet("enemies/boss_yellow", new Vector(128, 128));
    
    private int depth;
    
    public BossYellow()
    {
        this(true, 3);
    }
    
    public BossYellow(boolean goToPathStart, int depth)
    {
        super(goToPathStart);
        
        this.depth = depth;
        sprite = spriteSheet.getSprite(depth, 0).deriveSprite();
        size = new Vector(96, 96);
        speed = (4 - depth) * 10; // 10, 20, 30, 40
        maxHealth = (int) (Math.pow(2, depth) * 100);
        health = maxHealth;
    }
    
    @Override
    protected void onKill()
    {
        if(depth == 0) return;
        
        // spawn splitting enemies
        Vector forward = nextPoint.sub(position).normalized();
        for(int i = 0; i < 3; i++)
        {
            BossYellow newEnemy = new BossYellow(false, depth - 1);
            newEnemy.position = position.add(forward.mul(i * -40));
            newEnemy.pathIndex = pathIndex;
            newEnemy.nextPoint = nextPoint;
            newEnemy.sprite.rotation = sprite.rotation;
            scene.addObject(newEnemy);
        }
    }
}
