package game.scenes;

import engine.math.Vector;
import engine.utils.Sprite;

public class Map
{
    public String name;
    public Sprite background;
    //public ArrayList<Obstacle> obstacles;
    public Path path;
    
    public Map(String name)
    {
        this.name = name;
        importMap();
    }
    
    private void importMap()
    {
        //import(mapName);
        
        // for testing only
        background = new Sprite("backgrounds/test_bg");
        path = new Path(
            new Vector(690, 420),
            new Vector(300, 50),
            new Vector(300, 500),
            new Vector(100, 500),
            new Vector(100, 200),
            new Vector(500, 200)
        );
    }
    
    // the background and the obstacles are GameObjects and must be added to the Scene
    public void addGameObjects(GameSceneNicht scene)
    {
        //add background and obstacles
        scene.addObject(path); // debug
    }
}