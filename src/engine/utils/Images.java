package engine.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
//import java.nio.file.NoSuchFileException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class Images
{
    
    private static HashMap<String, BufferedImage> imageMap = new HashMap<String, BufferedImage>();
    
    private static final String IMAGE_PATH = "./resources/images/";
    private static final String IMAGE_EXTENSION = ".png";
    
    public static BufferedImage getImage(String name) //throws NoSuchFileException
    {
        if(imageMap.containsKey(name)) return imageMap.get(name);
        
        try
        {
            BufferedImage image = ImageIO.read(new File(IMAGE_PATH + name + IMAGE_EXTENSION));
            imageMap.put(name, image);
            return image;
        }
        catch(IOException e)
        {
            //throw new NoSuchFileException(name);
            return null;
        }
    }
    
}