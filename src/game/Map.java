package game;

import java.util.ArrayList;

import engine.math.Vector;
import engine.scene.GameObject;

public class Map
{
    public String mapName;
    //public BackgroundObject background;
    //public ArrayList<Obstacle> obstacles;
    public Path path;
    
    public Map(String mapName)
    {
        this.mapName = mapName;
        importMap();
    }
    
    private void importMap()
    {
        //import(mapName);
        
        // for testing only
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
        scene.addObject(path);
    }
}