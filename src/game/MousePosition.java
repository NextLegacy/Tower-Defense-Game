package game;

import engine.scene.GameObject;

public class MousePosition extends GameObject
{
    @Override
    public void update(double deltaTime)
    {
        if(input.left().isClicked())
            System.out.println("p " + (int) input.mouse().position().x + " " + (int) input.mouse().position().y);
    }
    
}
