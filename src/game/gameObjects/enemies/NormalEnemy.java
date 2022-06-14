package game.gameObjects.enemies;

import engine.math.Vector;
import engine.utils.SpriteSheet;

public class NormalEnemy extends Enemy
{
    public static SpriteSheet spriteSheet = new SpriteSheet("enemies/normal", new Vector(64, 64));
    
    public NormalEnemy(boolean goToPathStart, NormalEnemyType type, int tier)
    {
        super(goToPathStart);
        sprite = spriteSheet.getSprite(tier, type.spriteRow);
        size = sprite.size.clone();
        speed = type.baseSpeed + tier * type.tierSpeed;
        maxHealth = type.baseHealth + tier * type.tierHealth;
        health = maxHealth;
    }
    
    public static enum NormalEnemyType
    {
        BLUE ("blue",  0, 100, 20, 20, 10),
        GREEN("green", 1, 200, 30, 10,  5),
        RED  ("red",   2,  50, 15, 40, 15);
        
        public String label;
        public int spriteRow;
        public int baseSpeed;
        public int tierSpeed;
        public int baseHealth;
        public int tierHealth;
        private NormalEnemyType(String label, int spriteRow, int baseSpeed, int tierSpeed, int baseHealth, int tierHealth)
        {
            this.label = label;
            this.spriteRow = spriteRow;
            this.baseSpeed = baseSpeed;
            this.tierSpeed = tierSpeed;
            this.baseHealth = baseHealth;
            this.tierHealth = tierHealth;
        }
    }
}
