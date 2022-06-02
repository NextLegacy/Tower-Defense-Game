package engine.scene;

public abstract class Activateable 
{
    private boolean isActive;

    public final void setActive(boolean state) 
    { 
        if (isActive == state)
            return;
            
        isActive = state;

        if (isActive)
            onActivate();
        else
            onDeactivate();
    }

    public final void activate()   { this.setActive(true); }
    public final void deactivate() { this.setActive(false); }    

    public void onActivate() { }
    public void onDeactivate() { }

    public final boolean isActive() { return isActive; }
}
