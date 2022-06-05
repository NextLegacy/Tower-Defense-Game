package game.gameObjects.towers.towerSelection;

import java.awt.Color;

import engine.math.Vector;
import engine.scene.GameObject;
import engine.window.RenderLayer;

public class TowerSelection extends GameObject
{
    
    @Override
    public void render(RenderLayer layer, double deltaTime) 
    {
        if (layer.isNot("background")) return;

        layer.graphics().setColor(new Color(0x4aa1fa));

        layer.fillRect(new Vector(1000, 0), new Vector(1280, 720));
    }
}
