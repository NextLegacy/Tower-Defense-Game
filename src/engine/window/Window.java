package engine.window;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.HashMap;

import engine.utils.Lambda.Action1;

public class Window extends Frame
{
    private HashMap<String, RenderLayer> layers;
    private BufferStrategy strategy;
    
    public Window(int width, int height)
    {
        layers = new HashMap<>();

        this.addWindowListener(new WindowListener());

        setSize(width, height);
        setLocationRelativeTo(null);
        setVisible(true);
    } 

    public void addLayer(String name)
    {
        RenderLayer layer = new RenderLayer(name, this.getWidth(), this.getHeight());

        this.layers.put(name, layer);
    }

    public RenderLayer getLayer(String name)
    {
        return layers.get(name);
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
                Graphics graphics = strategy.getDrawGraphics();

                //RENDER START

                for (RenderLayer layer : this.layers.values())
                {
                    renderProcess.invoke(layer);
                    
                    graphics.drawImage(layer.image, 0, 0, null);
                }

                //RENDER END

                graphics.dispose();
    
            } while (strategy.contentsRestored());
    
            strategy.show();

        } while (strategy.contentsLost());

        return this;
    }
    
    private class WindowListener extends WindowAdapter
    {
        @Override
        public void windowClosing(WindowEvent e) {
            dispose();
        }
    }
}
