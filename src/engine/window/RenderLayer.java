package engine.window;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import engine.math.Vector;
import engine.utils.Fonts;
import engine.utils.Images;
import engine.utils.Sprite;

public class RenderLayer 
{
    private final String name;

    private int width;
    private int height;

    private BufferedImage image;
    private Graphics2D graphics;

    private AffineTransform defaultTransform;

    public RenderLayer(String name, int width, int height)
    {
        this.name = name;
        this.setSize(width, height);
    }

    public void setSize(int width, int height)
    {
        this.width = width;
        this.height = height;

        this.image = Images.toCompatibleImage(new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR));
        this.graphics = (Graphics2D) image.getGraphics();

        this.graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        this.graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        this.defaultTransform = this.graphics.getTransform();
    }

    public void clear()
    {
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
        graphics.setColor(new Color(0, 0, 0, 0));

        graphics.fillRect(0, 0, width, height);

        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
        graphics.setColor(Color.black);
    }

    public boolean is(String name) { return this.name == name; }
    public boolean isNot(String name) { return this.name != name; }

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
        this.graphics.setFont(Fonts.DEFAULT_FONT);
        this.graphics.setTransform(this.defaultTransform);
    }

    public FontMetrics getMetrics(Font font)
    {
        return this.graphics.getFontMetrics(font);
    }

    public void setColor(Color color)
    {
        this.graphics.setColor(color);
    }

    public void setColor(int rgba)
    {
        int r = (rgba >> 24) & 0xFF;
        int g = (rgba >> 16) & 0xFF;
        int b = (rgba >> 8) & 0xFF;
        int a = rgba & 0xFF;
        
        this.graphics.setColor(new Color(r, g, b, a));
    }

    public void renderSprite(Sprite sprite)
    {
        this.graphics().drawImage(sprite.image(), sprite.transform(), null);
    }

    public void renderSpriteCentered(Sprite sprite)
    {
        this.graphics().drawImage(sprite.image(), sprite.centeredTransform(), null);
    }

    public void drawStringCentered(String text, Vector position)
    {  
        FontMetrics metrics = graphics.getFontMetrics();
        
        drawString(text, new Vector(position.x - metrics.stringWidth(text) / 2, position.y + metrics.getAscent() / 4));
    }

    public void drawString(String text, Vector position)
    {  
        graphics.drawString(text, (int) position.x, (int) position.y);
    }

    public void drawLine(Vector from, Vector to)
    {
        graphics.drawLine((int)from.x, (int)from.y, (int)to.x, (int)to.y);
    }

    public void drawRect(Vector position, Vector size)
    {
        this.graphics().drawRect((int)(position.x), (int)(position.y), (int)(size.x), (int)(size.y));
    }

    public void drawRect(Vector position, Vector size, double thickness)
    {
        position = position.sub(thickness);
        size = size.add(thickness * 2).sub(0, 1);

        for (int i = 0; i < thickness; i++)
        {
            this.graphics().drawRect((int)position.x + i, (int)position.y + i, (int)size.x - i * 2, (int)size.y - i * 2);
        }
    }

    public void drawRectCentered(Vector position, Vector size)
    {
        this.graphics().drawRect((int)(position.x - size.x / 2), (int)(position.y - size.y / 2), (int)size.x, (int)size.y);
    }

    public void fillRect(Vector position, Vector size)
    {
        this.graphics().fillRect((int)position.x, (int)position.y, (int)size.x, (int)size.y);
    }

    public void fillRectCentered(Vector position, Vector size)
    {
        this.graphics().fillRect((int)(position.x - size.x / 2), (int)(position.y - size.y / 2), (int)size.x, (int)size.y);
    }
}
