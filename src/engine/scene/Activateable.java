package engine.scene;

public abstract class Activateable 
{
    private boolean isActive;

    public void setActive(boolean state) 
    { 
        if (isActive == state)
            return;
            
        isActive = state;

        if (isActive)
            onActivate();
        else
            onDeactivate();
    }

    public void activate()   { this.setActive(true); }
    public void deactivate() { this.setActive(false); }    

    public void onActivate() { }
    public void onDeactivate() { }

    public boolean isActive() { return isActive; }
}
