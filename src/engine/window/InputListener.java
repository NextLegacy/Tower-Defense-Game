package engine.window;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowEvent;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;

import engine.math.Vector;

//@SuppressWarnings("unused") //Nur um nervige Fehlermeldungen "stumm zu schalten"
public class InputListener extends InputAdapter
{
    private final HashMap<Integer, Key> KEY_MAP;

    private boolean isWindowFocused;
    private boolean isWindowActive;

    private int windowState;

    private final Mouse MOUSE;
    private final Wheel WHEEL;

    private final Button LEFT;
    private final Button RIGHT;
    
    public InputListener()
    {
        this.LEFT  = new Button();
        this.RIGHT = new Button();
        this.WHEEL = new Wheel();

        this.MOUSE = new Mouse();

        this.KEY_MAP = new HashMap<>();

        this.isWindowFocused = false;
        this.isWindowActive = false;

        this.windowState = Frame.NORMAL;

        this.initializeKeyDownMap();
    }

    public Key key(int keyEvent) { return this.KEY_MAP.get(keyEvent); }

    public Mouse mouse()  { return MOUSE; }

    public Button left()  { return LEFT; }
    public Button right() { return RIGHT; }

    public Wheel wheel()  { return WHEEL; }

    public int windowState() { return windowState; }

    public boolean isActive() { return this.isWindowActive; }
    public boolean isFocused() { return isWindowFocused; }

    public void initializeKeyDownMap()
    {
        Field[] fields = KeyEvent.class.getDeclaredFields();
    
        for (Field field : fields) 
        {
            if (Modifier.isPrivate(field.getModifiers()) || !Modifier.isStatic(field.getModifiers())) 
                continue;

            try 
            {
                Object fieldValue = field.get(null);
                if (!(fieldValue instanceof Integer)) continue;
                
                int event = (Integer) field.get(null);
                this.KEY_MAP.put(event, new Key(event));
            } 
            catch (IllegalArgumentException | IllegalAccessException e) 
            {
                e.printStackTrace();
            }
        }   
    }

    @Override
    public void keyPressed(KeyEvent e) 
    {
        Key key = KEY_MAP.get(e.getKeyCode());

        key.isDown = true;
        key.timeOnStart = System.nanoTime();
    }

    @Override
    public void keyReleased(KeyEvent e) 
    {
        Key key = KEY_MAP.get(e.getKeyCode());
        
        key.isDown = true;
        key.timeOnStart = System.nanoTime();
    }

    @Override
    public void mousePressed(MouseEvent e) 
    { 
        if (e.getButton() == MouseEvent.BUTTON1)
        {
            LEFT.isDown = true;
            LEFT.timeOnStart = System.nanoTime();
            return;
        }
        else if (e.getButton() == MouseEvent.BUTTON3)
        {
            RIGHT.isDown = true;
            RIGHT.timeOnStart = System.nanoTime();
            return;
        }
        else if (e.getButton() == MouseEvent.BUTTON2)
        {
            WHEEL.isDown = true;
            WHEEL.timeOnStart = System.nanoTime();
            return;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) 
    { 
        if (e.getButton() == MouseEvent.BUTTON1)
        {
            LEFT.isDown = false;
            LEFT.timeOnStart = 0.0d;
            return;
        }
        else if (e.getButton() == MouseEvent.BUTTON3)
        {
            RIGHT.isDown = false;
            RIGHT.timeOnStart = 0.0d;
            return;
        }
        else if (e.getButton() == MouseEvent.BUTTON2)
        {
            WHEEL.isDown = false;
            WHEEL.timeOnStart = 0.0d;
            return;
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) 
    {
        WHEEL.mouseWheelBefore = WHEEL.mouseWheelNow;
        // WheelRotation -> int 'full' clicks only | PreciseWheelRotation -> double 'fraction + full' click
        WHEEL.mouseWheelNow = e.getPreciseWheelRotation();
    }

    @Override
    public void mouseMoved(MouseEvent e) 
    {
        MOUSE.POSITION_BEFORE.set(MOUSE.POSITION_NOW);
        MOUSE.POSITION_NOW.set(e.getX(), e.getY());
    }

    @Override
    public void windowActivated(WindowEvent e) 
    {
        this.isWindowActive = true;
    }

    @Override
    public void windowClosing(WindowEvent e) 
    {
        this.isWindowActive = false;
    }

    @Override
    public void windowGainedFocus(WindowEvent e) 
    {
        this.isWindowFocused = true;
    }

    @Override
    public void windowLostFocus(WindowEvent e) 
    {
        this.isWindowFocused = false;
    }

    @Override
    public void windowStateChanged(WindowEvent e) 
    {
        this.windowState = e.getNewState();
    }

    public class Key
    {
        private final int keyEvent;
        private boolean isDown;
        private double timeOnStart;

        public Key(int keyEvent)
        {
            this.keyEvent    = keyEvent;
            this.isDown      = false;
            this.timeOnStart = 0d;
        }

        public int keyEvent() { return this.keyEvent; }

        public boolean isDown()     { return this.isDown; }
        public boolean isUp()       { return !this.isDown; }
        public double  downTime()   { return System.nanoTime() - this.timeOnStart; }
        public double  timeOnStart(){ return this.timeOnStart; }
    }

    public final class Mouse
    {
        private final Vector POSITION_BEFORE;
        private final Vector POSITION_NOW;

        public Mouse()
        {
            this.POSITION_BEFORE = Vector.zero();
            this.POSITION_NOW = Vector.zero();
        }

        public Vector position() { return this.POSITION_BEFORE; }
        public Vector speed()    { return this.POSITION_NOW.sub(this.POSITION_BEFORE); }

        public boolean isInRange(Vector from, Vector to)
        {
            return this.position().isInRange(from, to);
        }

        public boolean isInBounds(Vector position, Vector size)
        {
            return this.position().isInBounds(position, size);
        }
    }

    public class Button
    {
        protected boolean isDown;
        protected double timeOnStart;

        private Button()
        {
            this.isDown = false;
            this.timeOnStart = 0d;
        }

        public boolean isHoldInArea(Vector from, Vector to) 
        {
            return isDown() && 
                   mouse().isInRange(from, to);
        }

        public boolean isHoldInBounds(Vector position, Vector size) 
        {
            return isDown() && 
                   mouse().isInBounds(position, size);
        }

        public boolean isClickedInArea(Vector from, Vector to, double maxHoldTime) 
        {
            return isHoldInArea(from, to) &&
                   downTime() < maxHoldTime;
        }

        public boolean isClickedInBounds(Vector position, Vector size, double maxHoldTime) 
        {
            return isHoldInBounds(position, size) &&
                   downTime() < maxHoldTime;
        }

        public boolean isDown()     { return this.isDown; }
        public boolean isUp()       { return !this.isDown(); }
        public double  downTime()   { return (System.nanoTime() - this.timeOnStart()) / 1_000_000_000; }
        public double  timeOnStart(){ return this.timeOnStart; }
    }

    public final class Wheel extends Button
    {
        private double mouseWheelBefore;
        private double mouseWheelNow;

        public Wheel()
        {
            this.mouseWheelBefore = 0d;
            this.mouseWheelNow = 0d;
        }

        public double direction() { return this.speed() > 0d ? 1d : -1d; }
        public double speed() { return this.mouseWheelNow - this.mouseWheelBefore; }
    }

    public static InputListener createInputListener(Window window)
    {
        InputListener inputListener = new InputListener();
        
        window.addKeyListener(inputListener);
        window.addMouseListener(inputListener);
        window.addMouseMotionListener(inputListener);
        window.addMouseWheelListener(inputListener);
        window.addWindowListener(inputListener);
        window.addWindowStateListener(inputListener);
        window.addWindowFocusListener(inputListener);

        return inputListener;
    }
}
