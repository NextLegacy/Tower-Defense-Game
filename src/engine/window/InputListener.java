package engine.window;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;

import engine.math.Vector;

@SuppressWarnings("unused") //Nur um nervige Fehlermeldungen "stumm zu schalten"
public class InputListener implements KeyListener, MouseListener, MouseWheelListener, MouseMotionListener
{
    private KeyListener keyListener;
    private MouseListener mouseListener;

    private HashMap<Integer, Key> keyMap;

    public InputListener()
    {
        this.initializeKeyDownMap();
    }

    public Key getKey(int keyEvent)
    {
        return this.keyMap.get(keyEvent);
    }

    private void initializeKeyDownMap()
    {
        Field[] fields = KeyEvent.class.getDeclaredFields();
    
        for (Field field : fields) 
        {
            if (Modifier.isStatic(field.getModifiers())) 
            {
                try 
                {
                    int event = (Integer) field.get(null);
                    this.keyMap.put(event, new Key(event));
                } 
                catch (IllegalArgumentException | IllegalAccessException e) 
                {
                    e.printStackTrace();
                }
            } 
        }   
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) 
    {
        Key key = keyMap.get(keyEvent.getKeyCode());

        key.isDown = true;
        key.timeOnStart = System.nanoTime();
        key.downTime = System.nanoTime() - key.downTime;
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) 
    {
        Key key = keyMap.get(keyEvent.getKeyCode());
        
        key.isDown = true;
        key.isTyped = false;
        key.timeOnStart = System.nanoTime();
        key.downTime = System.nanoTime() - key.downTime;
    }

    @Override
    public void mouseClicked(MouseEvent e) { }

    @Override
    public void mousePressed(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) { }

    @Override
    public void mouseDragged(MouseEvent e) { }

    @Override
    public void mouseMoved(MouseEvent e) { }

    public class Key
    {
        private final int keyEvent;
        private boolean isDown;
        private boolean isTyped;
        private double downTime;
        private double timeOnStart;

        public Key(int keyEvent)
        {
            this.keyEvent    = keyEvent;
            this.isDown      = false;
            this.isTyped     = false;
            this.downTime    = 0d;
            this.timeOnStart = 0d;
        }

        public boolean isDown()     { return this.isDown; }
        public boolean isTyped()    { return this.isTyped; }
        public double  downTime()   { return this.downTime; }
        public double  timeOnStart(){ return this.timeOnStart; }
    }

    public class Mouse
    {
        private Vector positionBefore;
        private Vector postionNow;
        private double mouseSpeed;

        public Mouse()
        {
            this.positionBefore = Vector.zero();
            this.postionNow = Vector.zero();
            this.mouseSpeed = 0d;
        }
    }

    public static class Wheel
    {
        private double mouseWheelBefore;
        private double mouseWheelNow;
        private double mouseWheelSpeed;
    }

    private  enum Button
    {
        LEFT(), RIGHT(), WHEEL();

        private boolean isDown;
        private boolean isTyped;
        private double downTime;
        private double timeOnStart;

        Button()
        {
            this.isDown = false;
            this.isTyped = false;
            this.downTime = 0d;
            this.timeOnStart = 0d;
        }
    }
}
