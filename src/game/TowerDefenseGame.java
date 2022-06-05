package game;

import engine.Engine;
import game.scenes.GameScene;

public final class TowerDefenseGame
{
    private static TowerDefenseGame towerDefenseGame;
    
    private TowerDefenseGame() { }
    public void start()
    {
        var layers = new String[] 
        {
            "game",
            "ui",
        };

        Engine engine = new Engine(1280, 720, 120, 60, layers);
        
        engine.setActiveScene(new GameScene("test_map"));
        engine.activate();
    }

    public static TowerDefenseGame getInstance()
    {
        if (towerDefenseGame == null)
            towerDefenseGame = new TowerDefenseGame();

        return towerDefenseGame;
    }
}
