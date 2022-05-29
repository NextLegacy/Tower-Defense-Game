package engine.window;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import engine.utils.Fonts;

public class RenderLayer 
{
    private final String name;

    private int width;
    private int height;

    private BufferedImage image;
    private Graphics2D graphics;

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
        this.graphics = image.createGraphics();
    }

    public String name() { return this.name; }
    public int width() { return this.width; }
    public int height() { return this.height; }
    public BufferedImage image() { return this.image; }

    public Graphics2D graphics()
    {
        return this.graphics;
    }

    public void resetGraphics()
    {
        this.graphics.setColor(Color.BLACK);
        this.graphics().setFont(Fonts.DEFAULT_FONT);
    }
}
