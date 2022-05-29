package game;

import engine.Engine;
import engine.scene.Scene;
import engine.scene.Collisions;

public final class TowerDefenseGame
{
    private static TowerDefenseGame towerDefenseGame;
    
    private TowerDefenseGame() { }

    public void start()
    {
        Engine engine = new Engine(1280, 720, 60, 60, new String[] { "main" });
        
        engine.setActiveScene(new Scene(1, new Collisions.LayerCollision[] { new Collisions.LayerCollision(0, 1, true) }));
        engine.activate();
    }

    public static TowerDefenseGame getInstance()
    {
        if (towerDefenseGame == null)
            towerDefenseGame = new TowerDefenseGame();

        return towerDefenseGame;
    }
}
