package game;

import engine.Engine;
import game.scenes.MainMenuScene;

public final class TowerDefenseGame
{
    private static TowerDefenseGame towerDefenseGame;
    
    private TowerDefenseGame() { }

    public void start()
    {
        var layers = new String[] 
        {
            "background",
            "projectiles",
            "enemys",
            "towers",
            "ui",
            //"debug",
            "window"
        };

        Engine engine = new Engine(1280, 720, 120, 60, layers);
        
        engine.setActiveScene(new MainMenuScene());
        engine.activate();
    }

    public static TowerDefenseGame getInstance()
    {
        if (towerDefenseGame == null)
            towerDefenseGame = new TowerDefenseGame();

        return towerDefenseGame;
    }
}
