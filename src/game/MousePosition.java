package game;

import java.awt.event.KeyEvent;

import engine.scene.GameObject;

public class MousePosition extends GameObject
{
    private boolean h = false;
    
    @Override
    public void update(double deltaTime)
    {
        if(input.left().isClicked())
            System.out.println((int) input.mouse().position().x + " " + (int) input.mouse().position().y);
        if(input.key(KeyEvent.VK_H).isDown())
            if(!h) System.out.println("h");
        h = input.key(KeyEvent.VK_H).isDown();
    }
}
