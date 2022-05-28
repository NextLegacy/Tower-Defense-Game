package engine.window;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class RenderLayer 
{
    public final String name;

    public int width;
    public int height;

    public BufferedImage image;

    public RenderLayer(String name, int width, int height)
    {
        this.name = name;
        this.setSize(width, height);
    }

    public void setSize(int width, int height)
    {
        this.width = width;
        this.height = height;

        this.image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
    }

    public Graphics2D getGraphics()
    {
        return ((Graphics2D) this.image.getGraphics());
    }
}
