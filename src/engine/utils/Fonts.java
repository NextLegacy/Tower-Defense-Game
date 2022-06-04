package engine.utils;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

public class Fonts 
{
    public static final Font DEFAULT_FONT = Fonts.get("arial", 12, Font.PLAIN);

    public static Font get(String name, int size, int style)
    {
        return new Font(name, style, size);
    }

    public static void registerFont(String fontName)
    {
        Font font = null;
        try 
        {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("rsc/fonts/" + fontName + ".ttf"));
        } catch (FontFormatException | IOException e) 
        {
            e.printStackTrace();
        }

        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        graphicsEnvironment.registerFont(font);
    }    
}
