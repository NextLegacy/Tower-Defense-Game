package engine.utils;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

public class Fonts 
{
    public static Font DEFAULT_FONT = Fonts.get("arial", 12);

    public static Font get(String name, int size)
    {
        return new Font(name, Font.PLAIN, size);
    }

    public static void RegisterFont(String fontName)
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
