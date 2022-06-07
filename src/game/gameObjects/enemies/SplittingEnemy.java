package game.gameObjects.enemies;

import engine.math.Vector;
import engine.utils.SpriteSheet;
import game.gameObjects.enemies.NormalEnemy.NormalEnemyType;

public class SplittingEnemy extends Enemy
{
    public static SpriteSheet spriteSheet = new SpriteSheet("enemies/splitting", new Vector(128, 128));
    
    private SplittingEnumType type;
    
    public SplittingEnemy(boolean goToPathStart, SplittingEnumType type)
    {
        super(goToPathStart);
        this.type = type;
        sprite = spriteSheet.getSprite(type.spriteColum, type.spriteRow);
        size = new Vector(96, 96);
        speed = type.speed;
        maxHealth = type.health;
        health = maxHealth;
    }
    
    @Override
    protected void onKill()
    {
        Vector forward = nextPoint.sub(position).normalized();
        
        for(int i = 0; i < type.splitCount; i++)
        {
            NormalEnemy newEnemy = new NormalEnemy(false, type.splitType, type.splitTier);
            newEnemy.position = position.add(forward.mul(i * -20));
            newEnemy.pathIndex = pathIndex;
            newEnemy.nextPoint = nextPoint;
            newEnemy.sprite.rotation = sprite.rotation;
            scene.addObject(newEnemy);
        }
    }
    
    public static enum SplittingEnumType
    {
        BLUE_1( "blue",  0, 0, 40,  120, NormalEnemyType.BLUE,  1, 4),
        BLUE_4( "blue",  1, 0, 50,  180, NormalEnemyType.BLUE,  4, 3),
        GREEN_1("green", 0, 1, 80,  60,  NormalEnemyType.GREEN, 1, 4),
        GREEN_4("green", 1, 1, 100, 90,  NormalEnemyType.GREEN, 4, 3),
        RED_1(  "red",   0, 2, 20,  220, NormalEnemyType.RED,   1, 4),
        RED_4(  "red",   1, 2, 25,  300, NormalEnemyType.RED,   4, 3);
        
        public String label;
        public int spriteColum;
        public int spriteRow;
        public int speed;
        public int health;
        public NormalEnemyType splitType;
        public int splitTier;
        public int splitCount;
        private SplittingEnumType(String label, int spriteColum, int spriteRow, int speed, int health, NormalEnemyType splitType, int splitTier, int splitCount)
        {
            this.label = label;
            this.spriteColum = spriteColum;
            this.spriteRow = spriteRow;
            this.speed = speed;
            this.health = health;
            this.splitType = splitType;
            this.splitTier = splitTier;
            this.splitCount = splitCount;
        }
    }
}
