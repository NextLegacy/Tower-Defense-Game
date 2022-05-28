import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import engine.window.InputListener;
import engine.window.Window;
import engine.window.InputListener.Key;
import engine.window.InputListener.Mouse;

public class App 
{
    public static void main(String[] args) 
    {
        final int WIDTH = 1080, HEIGHT = 720;

        Window test = new Window(WIDTH, HEIGHT);
        test.addLayer("main");

        
        long last_time = System.nanoTime();

        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);

        while(test.isEnabled()) 
        {
            long time = System.nanoTime();
            
            for (int x = 0; x < WIDTH; x++)
            for (int y = 0; y < HEIGHT; y++)
            {
                image.setRGB(x, y, (int) (Math.random() * 0xffffff + 0xff000000));
            }

            test.render((layer) -> 
            {
                (layer.getGraphics()).drawImage(image, null, null);
                layer.getGraphics().dispose();
            });

            int deltaTime = (int) ((time - last_time) / 1000000);
            last_time = time;

            //System.out.println(deltaTime);
        }
    }
}
