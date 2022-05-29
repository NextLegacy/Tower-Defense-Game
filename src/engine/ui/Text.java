package engine.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import engine.math.Vector;
import engine.utils.Fonts;
public class Text
{
    public String text;
    public Font font;
    public Color color;
    public Vector position;
    
    public boolean centered;

    public Text()
    {
        this.text = "";
        this.font = Fonts.DEFAULT_FONT;
        this.color = Color.BLACK;
        this.position = Vector.zero();
        this.centered = false;
    }

    public void render(Graphics2D g2d)
    {
        g2d.setColor(color);
        g2d.setFont(font);

        if (centered)
            g2d.drawString(text, (float) (position.x - (g2d.getFontMetrics().stringWidth(text) / 2)), (float) position.y);
        else
            g2d.drawString(this.text, (float)this.position.x, (float)this.position.y);
    }
}
