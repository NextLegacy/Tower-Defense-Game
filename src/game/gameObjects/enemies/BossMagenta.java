package game.gameObjects.enemies;

import engine.utils.Sprite;

public class BossMagenta extends Enemy
{
    private double regenCoolDown;
    private static final double REGEN_DELAY = 1;
    private static final int REGEN_HEALTH = 25;
    
    public BossMagenta()
    {
        super(true);
        
        sprite = new Sprite("enemies/boss_magenta");
        speed = 30;
        maxHealth = 500;
        health = maxHealth;
        
        regenCoolDown = REGEN_DELAY;
    }
    
    @Override
    public void update(double deltaTime)
    {
        super.update(deltaTime);
        
        regenCoolDown -= deltaTime;
        
        if(regenCoolDown < 0)
        {
            regenCoolDown = REGEN_DELAY;
            health = Math.min(health + REGEN_HEALTH, maxHealth);
        }
    }
}
