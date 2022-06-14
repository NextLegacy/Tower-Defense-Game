package game.menu;

import java.awt.Color;

public class MapMetadata
{
    public static final MapMetadata[] maps = new MapMetadata[]
    {
        new MapMetadata("meadow", "Wiese", "backgrounds/meadow_bg", Difficulty.EASY),
        new MapMetadata("ice", "Eis", "backgrounds/ice_bg", Difficulty.EASY),
        new MapMetadata("mountain", "Berg", "backgrounds/mountain_bg", Difficulty.MEDIUM),
        new MapMetadata("islands", "Inseln", "backgrounds/islands_bg", Difficulty.MEDIUM),
        new MapMetadata("volcano", "Vulkan", "backgrounds/volcano_bg", Difficulty.HARD),
        new MapMetadata("space", "Weltraum", "backgrounds/space_bg", Difficulty.HARD)
    };
    
    public String name;
    public String displayName;
    public String previewImageName;
    public Difficulty difficulty;
    
    public MapMetadata(String name, String displayName, String previewImageName, Difficulty difficulty)
    {
        this.name = name;
        this.displayName = displayName;
        this.previewImageName = previewImageName;
        this.difficulty = difficulty;
    }
    
    public static enum Difficulty
    {
        EASY("einfach", new Color(23, 211, 23)),
        MEDIUM("mittel", new Color(211, 211, 23)),
        HARD("schwierig", new Color(211, 23, 23));
        
        public String displayName;
        public Color color;
        private Difficulty(String displayName, Color color)
        {
            this.displayName = displayName;
            this.color = color;
        }
    }
}
