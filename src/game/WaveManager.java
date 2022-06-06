package game;

import java.util.ArrayList;
import java.util.HashMap;

import engine.utils.Lambda.Func0;
import game.gameObjects.enemies.Enemy;
import game.scenes.GameScene;

public class WaveManager
{
    public int waveNumber;
    private Wave currentWave;
    public boolean started;
    
    private GameScene scene;
    
    private ArrayList<Enemy> sceneEnemies;
    
    private static final HashMap<String, Func0<Enemy>> enemieTypes = getEnemies();
    private static final String[] waves = getWaves();
    
    public WaveManager(GameScene scene)
    {
        this.scene = scene;
        waveNumber = 0;
        
        sceneEnemies = new ArrayList<Enemy>();
    }
    
    public void startNextWave()
    {
        if(started) return;
        
        waveNumber++;
        if(waveNumber >= waves.length)
        {
            System.out.println("no more waves");
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
                if(subWave.remainingDelay <= 0) 
                {
                    subWave.remainingEnemies--;
                    
                    Enemy newEnemy = enemieTypes.get(subWave.enemyType).invoke();
                    sceneEnemies.add(newEnemy);
                    scene.addObject(newEnemy); // spawn enemy
                    
                    if(subWave.remainingEnemies <= 0)
                    {
                        subWave.finished = true;
                        continue;
                    }
                    subWave.remainingDelay += subWave.delay;
                }
            }
        }
        
        if(stop && sceneEnemies.size() == 0) waveEnd();
    }
    
    public void removeEnemy(Enemy enemy)
    {
        sceneEnemies.remove(enemy);
    }
    
    public void waveEnd()
    {
        started = false;
        currentWave = null;
    }
    
    private static HashMap<String, Func0<Enemy>> getEnemies()
    {
        HashMap<String, Func0<Enemy>> map = new HashMap<String, Func0<Enemy>>();
        
        map.put("test_enemy_slow", () -> new Enemy(100));
        map.put("test_enemy", () -> new Enemy(200));
        map.put("test_enemy_fast", () -> new Enemy(400));
        
        return map;
    }
    
    private static String[] getWaves()
    {
        return new String[]
        {
            "",
            "test_enemy, 5, 0, 2",
            "test_enemy_slow, 11, 0, 2; test_enemy, 8, 6, 2; test_enemy_fast, 5, 12, 2"
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
