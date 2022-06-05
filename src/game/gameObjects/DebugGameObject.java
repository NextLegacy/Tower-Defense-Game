package game.gameObjects;

import java.awt.Color;
import java.awt.Font;

import engine.math.Vector;
import engine.scene.GameObject;
import engine.utils.Fonts;
import engine.window.RenderLayer;

public class DebugGameObject extends GameObject
{
    @Override
    public void render(RenderLayer layer, double deltaTime) 
    {
        layer.graphics().setColor(Color.red);
        layer.graphics().setFont(Fonts.get("Cascadia Code", 13, Font.PLAIN));

        layer.drawString(
            String.format("[TPS: %s] [FPS: %s]", engine.currentTPS(), engine.currentFPS()), 
            window.v_size(0.08, 0.0125)    
        );
    }
}
