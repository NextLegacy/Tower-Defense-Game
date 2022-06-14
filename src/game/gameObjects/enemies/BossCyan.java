package game.gameObjects.enemies;

import engine.utils.Sprite;

public class BossCyan extends Enemy
{
    public BossCyan()
    {
        super(true);
        
        sprite = new Sprite("enemies/boss_cyan");
        speed = 15;
        maxHealth = 1000;
        health = maxHealth;
    }
}
