package engine.window;

import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import engine.math.Vector;
import engine.utils.Lambda.Action1;

public class Window extends Frame
{
    private BufferStrategy strategy;

    private final ArrayList<RenderLayer> layers;
    
    private final InputListener inputListener;

    public Window(final int width, final int height)
    {
        layers = new ArrayList<>();

        this.inputListener = InputListener.createInputListener(this);

        this.setResizable(false);
        //this.setUndecorated(true);
        
        this.setIgnoreRepaint(true);
        this.setFocusable(true);
        this.requestFocus();

        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    } 

    public void addLayer(String name)
    {
        RenderLayer layer = new RenderLayer(name, this.getWidth(), this.getHeight());

        this.layers.add(layer);
    }

    public Vector v_size(double r_width, double r_height) { return new Vector(width(r_width), height(r_height)); }

    public Vector v_size() { return new Vector(this.getWidth(), this.getHeight()); }

    public double width(double ratio) { return width() * ratio; }
    public double height(double ratio) { return height() * ratio; }

    public double width() { return this.getWidth(); }
    public double height() { return this.getHeight(); }


    public RenderLayer getLayer(String name)
    {
        return layers.stream()  //LINQ (:
            .filter((layer) -> name.equals(layer.name()))
            .findAny()
            .orElse(null);
    }

    public InputListener getInputListener()
    {
        return inputListener;
    }

    public Window render(Action1<RenderLayer> renderProcess)
    {
        this.strategy = this.getBufferStrategy();

        if (this.strategy == null)
        {
            this.createBufferStrategy(2);
            return render(renderProcess);
        }

        do 
        {
            do 
            {
                Graphics2D graphics = (Graphics2D) strategy.getDrawGraphics();

                //RENDER START

                graphics.fillRect(0, 0, this.getWidth(), this.getHeight());

                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
                graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                
                for (RenderLayer renderLayer : this.layers)
                {   
                    renderProcess.invoke(renderLayer);
                    
                    graphics.drawImage(renderLayer.image(), 0, 0, null);
                }

                //RENDER END

                graphics.dispose();
    
            } while (strategy.contentsRestored());
    
            strategy.show();

        } while (strategy.contentsLost());

        return this;
    }
}
