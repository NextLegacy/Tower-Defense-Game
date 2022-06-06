package game.scenes;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import engine.math.Vector;
import engine.scene.GameObject;
import engine.utils.ResourceManager;
import engine.utils.Sprite;
import game.gameObjects.DiagonalObstacleObject;
import game.gameObjects.ObstacleObject;

public class Map
{
    public String name;
    public Sprite background;
    public ArrayList<GameObject> obstacles;
    public Path path;
    
    public Map(String name)
    {
        this.name = name;
        importMap();
    }
    
    private void importMap()
    {
        BufferedReader reader;
        try
        {
            ArrayList<Vector> pathPoints = new ArrayList<Vector>();
            obstacles = new ArrayList<GameObject>();
            
            String line;
            reader = ResourceManager.getReader("maps", name);
            while((line = reader.readLine()) != null)
            {
                if(line == "") continue;
                try
                {
                    String[] parts = line.split(" ");
                    switch(parts[0])
                    {
                        case "b":
                            background = new Sprite(parts[1]);
                            break;
                        
                        case "p":
                            pathPoints.add(new Vector(Double.parseDouble(parts[1]), Double.parseDouble(parts[2])));
                            break;
                        
                        case "o":
                            Vector pointA = new Vector(Double.parseDouble(parts[2]), Double.parseDouble(parts[3]));
                            Vector pointB = new Vector(Double.parseDouble(parts[4]), Double.parseDouble(parts[5]));
                            switch(parts[1])
                            {
                                case "b":
                                    obstacles.add(ObstacleObject.fromPoints(pointA, pointB));
                                    break;
                                case "h":
                                    obstacles.add(DiagonalObstacleObject.fromPoints(true, pointA, pointB, Double.parseDouble(parts[6])));
                                    break;
                                case "v":
                                    obstacles.add(DiagonalObstacleObject.fromPoints(false, pointA, pointB, Double.parseDouble(parts[6])));
                                    break;
                                default:
                                    throw new Exception("Invalid map file format!");
                            }
                            break;
                        
                        // default: line is a comment
                    }
                }
                catch(Exception e)
                {
                    reader.close(); // this is important
                    e.printStackTrace();
                }
            }
            reader.close();
            
            Vector[] pathPointsArray = new Vector[pathPoints.size()];
            for(int i = 0; i < pathPoints.size(); i++)
            {
                pathPointsArray[i] = pathPoints.get(i);
            }
            path = new Path(pathPointsArray);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    
    // the obstacles are GameObjects and must be added to the Scene
    public void addGameObjects(GameScene scene)
    {
        for(GameObject g : obstacles)
        {
            scene.addObject(g);
        }
        
        scene.addObject(path); // debug
    }
}