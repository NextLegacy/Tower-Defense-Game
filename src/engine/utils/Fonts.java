package engine.utils;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

public class Fonts 
{
    public static Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 12);

    public static void RegisterFont(String fontName)
    {
        Font font = null;
        try 
        {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts\\custom_font.ttf")).deriveFont(12f);
        } catch (FontFormatException | IOException e) 
        {
            e.printStackTrace();
        }

        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        graphicsEnvironment.registerFont(font);
    }    
}
