package engine.window;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import engine.utils.Lambda.Action1;

public class Window extends Frame
{
    private ArrayList<RenderLayer> layers;
    private BufferStrategy strategy;
    
    public Window(final int WIDTH, final int HEIGHT)
    {
        layers = new ArrayList<>();

        this.addWindowListener(new WindowListener());

        this.setSize(WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    } 

    public void addLayer(String name)
    {
        RenderLayer layer = new RenderLayer(name, this.getWidth(), this.getHeight());

        this.layers.add(layer);
    }

    public RenderLayer getLayer(String name)
    {
        return layers.stream()
            .filter((layer) -> name.equals(layer.name()))
            .findAny()
            .orElse(null);
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

                for (RenderLayer layer : this.layers)
                {
                    renderProcess.invoke(layer);
                    
                    graphics.drawImage(layer.image(), 0, 0, null);
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
        public void windowClosing(WindowEvent e) 
        {
            dispose();
        }
    }
}
