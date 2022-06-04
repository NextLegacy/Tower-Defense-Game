package game.gameObjects;

import java.awt.Color;
import java.awt.Font;

import engine.scene.GameObject;
import engine.utils.Fonts;
import engine.window.RenderLayer;

public class DebugGameObject extends GameObject
{
    @Override
    public void render(RenderLayer layer, double deltaTime) 
    {
        layer.graphics().setColor(Color.green);
        layer.graphics().setFont(Fonts.get("Cascadia code", 13, Font.PLAIN));
        layer.graphics().drawString(
            String.format("[TPS: %s] [FPS: %s]", engine.currentTPS(), engine.currentFPS()), 
            (int)window.width(0.008),
            (int)window.height(0.065) 
        );
    }
}
