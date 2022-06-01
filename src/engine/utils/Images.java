package engine.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class Images
{
    
    private static HashMap<String, BufferedImage> imageMap = new HashMap<String, BufferedImage>();
    
    private static final String IMAGE_TYPE = "images";
    private static final String IMAGE_EXTENSION = ".png";
    private static final String DEFAULT_SPRITE_NAME = "ohno";

    public static BufferedImage getImage(String name)
    {
        if(imageMap.containsKey(name)) return imageMap.get(name);
        
        try
        {
            BufferedImage image = ImageIO.read(ResourceManager.getFile(IMAGE_TYPE, name + IMAGE_EXTENSION));
            imageMap.put(name, image);
            return image;
        }
        catch(IOException e)
        {
            return getImage(DEFAULT_SPRITE_NAME);
        }
    }

    public static BufferedImage getImage(String name, int width, int height)
    {
        // TODO: implement
        return null;
    }
    
}