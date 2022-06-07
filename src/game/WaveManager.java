package game;

import java.util.ArrayList;
import java.util.HashMap;

import engine.Engine;
import engine.utils.Lambda.Func0;
import game.gameObjects.enemies.Enemy;
import game.gameObjects.enemies.NormalEnemy;
import game.gameObjects.enemies.SplittingEnemy;
import game.gameObjects.enemies.NormalEnemy.NormalEnemyType;
import game.gameObjects.enemies.SplittingEnemy.SplittingEnumType;
import game.scenes.GameOverScene;
import game.scenes.GameScene;

public class WaveManager
{
    public int waveNumber;
    private Wave currentWave;
    public boolean started;
    
    private GameScene scene;
    private Engine engine;
    
    private ArrayList<Enemy> sceneEnemies;
    
    private static final HashMap<String, Func0<Enemy>> enemieTypes = getEnemies();
    private static final String[] waves = getWaves();
    
    public WaveManager(GameScene scene, Engine engine)
    {
        this.scene = scene;
        this.engine = engine;
        waveNumber = 1;
        
        sceneEnemies = new ArrayList<Enemy>();
    }
    
    public void startNextWave()
    {
        if(started) return;
        
        if(waveNumber >= waves.length)
        {
            engine.setActiveScene(new GameOverScene(true));
            return;
        }
        
        currentWave = new Wave(waves[waveNumber]);
        started = true;
    }
    
    public void update(double deltaTime)
    {
        if(!started) return;
        
        boolean stop = true; // used for checking if there are remaining subWaves
        
        for(SubWave subWave : currentWave.subWaves)
        {
            if(subWave.finished) continue;
            
            stop = false;
            
            if(subWave.firstDelay > 0)
                subWave.firstDelay -= deltaTime; // subwave didn't start yet
            else
            {
                subWave.remainingDelay -= deltaTime;
                while(subWave.remainingDelay <= 0) 
                {
                    subWave.remainingEnemies--;
                    
                    Enemy newEnemy = enemieTypes.get(subWave.enemyType).invoke();
                    scene.addObject(newEnemy); // spawn enemy
                    
                    if(subWave.remainingEnemies <= 0)
                    {
                        subWave.finished = true;
                        break;
                    }
                    subWave.remainingDelay += subWave.delay;
                }
                if(subWave.finished) continue;
            }
        }
        
        if(stop && sceneEnemies.size() == 0) waveEnd();
    }
    
    public void addEnemy(Enemy enemy)
    {
        sceneEnemies.add(enemy);
    }
    
    public void removeEnemy(Enemy enemy)
    {
        sceneEnemies.remove(enemy);
    }
    
    public void waveEnd()
    {
        started = false;
        currentWave = null;
        waveNumber++;
        
        if(waveNumber >= waves.length)
        {
            engine.setActiveScene(new GameOverScene(true));
        }
    }
    
    private static HashMap<String, Func0<Enemy>> getEnemies()
    {
        HashMap<String, Func0<Enemy>> map = new HashMap<String, Func0<Enemy>>();
        
        // normal enemies
        map.put("blue_0", () -> new NormalEnemy(true, NormalEnemyType.BLUE, 0));
        map.put("blue_1", () -> new NormalEnemy(true, NormalEnemyType.BLUE, 1));
        map.put("blue_2", () -> new NormalEnemy(true, NormalEnemyType.BLUE, 2));
        map.put("blue_3", () -> new NormalEnemy(true, NormalEnemyType.BLUE, 3));
        map.put("blue_4", () -> new NormalEnemy(true, NormalEnemyType.BLUE, 4));
        map.put("green_0", () -> new NormalEnemy(true, NormalEnemyType.GREEN, 0));
        map.put("green_1", () -> new NormalEnemy(true, NormalEnemyType.GREEN, 1));
        map.put("green_2", () -> new NormalEnemy(true, NormalEnemyType.GREEN, 2));
        map.put("green_3", () -> new NormalEnemy(true, NormalEnemyType.GREEN, 3));
        map.put("green_4", () -> new NormalEnemy(true, NormalEnemyType.GREEN, 4));
        map.put("red_0", () -> new NormalEnemy(true, NormalEnemyType.RED, 0));
        map.put("red_1", () -> new NormalEnemy(true, NormalEnemyType.RED, 1));
        map.put("red_2", () -> new NormalEnemy(true, NormalEnemyType.RED, 2));
        map.put("red_3", () -> new NormalEnemy(true, NormalEnemyType.RED, 3));
        map.put("red_4", () -> new NormalEnemy(true, NormalEnemyType.RED, 4));
        
        // splitting enemies
        map.put("blue_s_1", () -> new SplittingEnemy(true, SplittingEnumType.BLUE_1));
        map.put("blue_s_4", () -> new SplittingEnemy(true, SplittingEnumType.BLUE_4));
        map.put("green_s_1", () -> new SplittingEnemy(true, SplittingEnumType.GREEN_1));
        map.put("green_s_4", () -> new SplittingEnemy(true, SplittingEnumType.GREEN_4));
        map.put("red_s_1", () -> new SplittingEnemy(true, SplittingEnumType.RED_1));
        map.put("red_s_4", () -> new SplittingEnemy(true, SplittingEnumType.RED_4));
        
        // boss enemies
        
        return map;
    }
    
    private static String[] getWaves()
    {
        return new String[]
        {
            "",
            "blue_0, 5, 0, 2",
            "blue_0, 10, 0, 1; blue_1, 5, 10, 2",
            "blue_0, 2, 0, 1; blue_1, 2, 2, 1; blue_2, 2, 4, 1; blue_3, 2, 6, 1; blue_4, 2, 8, 1",
            "blue_0, 2, 0, 1; blue_1, 2, 2, 1; blue_2, 2, 4, 1; blue_3, 2, 6, 1; blue_4, 2, 8, 1; green_0, 2, 10, 1; green_1, 2, 12, 1; green_2, 2, 14, 1; green_3, 2, 16, 1; green_4, 2, 18, 1; red_0, 2, 20, 1; red_1, 2, 22, 1; red_2, 2, 24, 1; red_3, 2, 26, 1; red_4, 2, 28, 1",
            "blue_s_1, 1, 0, 0; blue_s_4, 1, 5, 0; green_s_1, 1, 10, 0; green_s_4, 1, 15, 0; red_s_1, 1, 20, 0; red_s_4, 1, 25, 0"
        };
    }
    
    public class Wave
    {
        public SubWave[] subWaves;
        
        public Wave(String s)
        {
            String[] subs = s.replaceAll(" ", "").split(";");
            subWaves = new SubWave[subs.length];
            for(int i = 0; i < subs.length; i++)
            {
                String[] properties = subs[i].split(",");
                subWaves[i] = new SubWave(properties[0], Integer.parseInt(properties[1]), Double.parseDouble(properties[2]), Double.parseDouble(properties[3]));
            }
        }
    }
    
    public class SubWave
    {
        public String enemyType;
        public int remainingEnemies;
        public double firstDelay;
        public double delay;
        
        public double remainingDelay;
        public boolean finished;
        
        public SubWave(String enemyType, int count, double firstDelay, double delay)
        {
            this.enemyType = enemyType;
            this.remainingEnemies = count;
            this.firstDelay = firstDelay;
            this.delay = delay;
            remainingDelay = 0;
            finished = false;
        }
    }
}
