package engine.utils;

import java.awt.Image;
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
        Image image = getImage(name).getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);

        return imageToBufferedImage(image);
    }
    
    private static BufferedImage imageToBufferedImage(Image image)
    {
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        
        bufferedImage.getGraphics().drawImage(image, 0, 0, null);
        
        return bufferedImage;
    }
}