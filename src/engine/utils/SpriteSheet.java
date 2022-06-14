package engine.utils;

import java.awt.image.BufferedImage;

import engine.math.Vector;

public class SpriteSheet 
{
    public final BufferedImage spriteSheet;
    public final Vector size;
    public final Vector tileSize;
    public final Sprite[][] sprites;

    public SpriteSheet(BufferedImage image, Vector tileSize)
    {
        this.spriteSheet = image;
        this.tileSize = tileSize;

        this.size = new Vector(spriteSheet.getWidth() / tileSize.x, spriteSheet.getHeight() / tileSize.y);
        
        this.sprites = new Sprite[(int)size.x][(int)size.y];

        setupSprites();
    }
    
    public SpriteSheet(String imageName, Vector tileSize) { this(Images.getImage(imageName), tileSize); }
    
    private void setupSprites()
    {
        for (int x = 0; x < spriteSheet.getWidth() / tileSize.x; x++)
        {
            for (int y = 0; y < spriteSheet.getHeight() / tileSize.y; y++)
            {
                sprites[x][y] = new Sprite(spriteSheet.getSubimage((int)(x * tileSize.x), (int)(y * tileSize.y), (int)tileSize.x, (int)tileSize.y));
            }
        }
    }

    public Sprite getSprite(int x, int y)
    {
        if (x < 0 || x >= size.x || y < 0 || y >= size.y)
        {
            return Sprite.ohno();
        }

        return sprites[x][y].deriveSprite();
    }
}
