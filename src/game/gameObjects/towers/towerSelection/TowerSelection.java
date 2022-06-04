package game.gameObjects.towers.towerSelection;

import java.awt.Color;
import java.awt.Font;

import engine.math.Vector;
import engine.scene.GameObject;
import engine.utils.Fonts;
import engine.utils.Sprite;
import engine.window.RenderLayer;

public class TowerSelection extends GameObject
{
    private Sprite sprite;

    @Override
    public void start()
    {
        
    }

    @Override
    public void update(double deltaTime) 
    {

    }

    @Override
    public void render(RenderLayer layer, double deltaTime) 
    {
        layer.graphics().setFont(Fonts.get("arial", 50, Font.PLAIN));

        layer.graphics().setColor(new Color(0x4aa1fa));

        layer.fillRect(window.v_size(0.8, 0), window.v_size(0.2, 1));

        layer.graphics().setColor(Color.black);

        layer.graphics().drawString("Towers", (int)window.width(0.83), (int)window.height(0.11));
    }
}
