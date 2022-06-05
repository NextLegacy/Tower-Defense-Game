package engine.window;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import engine.math.Vector;
import engine.utils.Fonts;
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

        this.image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        this.graphics = image.createGraphics();

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

    public void renderSprite(Sprite sprite)
    {
        this.graphics().drawImage(sprite.image(), sprite.transform(), null);
    }

    public void drawString(String text, Vector position)
    {  
        FontMetrics metrics = graphics.getFontMetrics();

        int x = (int)position.x - metrics.stringWidth(text) / 2;
        int y = (int)position.y + metrics.getAscent() / 2;

        graphics.drawString(text, x, y);
    }

    public void drawLine(Vector from, Vector to)
    {
        graphics.drawLine((int)from.x, (int)from.y, (int)to.x, (int)to.y);
    }

    public void fillRect(Vector position, Vector size)
    {
        this.graphics().fillRect((int)position.x, (int)position.y, (int)size.x, (int)size.y);
    }
}
