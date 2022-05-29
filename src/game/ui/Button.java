package game.ui;

import engine.scene.GameObject;

public class Button extends GameObject
{
    public double maxHoldTime; //Max time, mouse button ca be held down

    public boolean isClicked() 
    {
        return input.left().isDown() && 
               input.left().downTime() < maxHoldTime && 
               input.mouse().isInBounds(position, size);
    }
}
