package game.menu;

import java.awt.Color;

public class MapMetadata
{
    public static final MapMetadata[] maps = new MapMetadata[]
    {
        new MapMetadata("test_map", "Test", "backgrounds/test_bg", Difficulty.EASY),
        new MapMetadata("test_map", "Test", "backgrounds/test_bg", Difficulty.EASY),
        new MapMetadata("test_map", "Test", "backgrounds/test_bg", Difficulty.MEDIUM),
        new MapMetadata("test_map", "Test", "backgrounds/test_bg", Difficulty.MEDIUM),
        new MapMetadata("test_map", "Test", "backgrounds/test_bg", Difficulty.MEDIUM),
        new MapMetadata("test_map", "Test", "backgrounds/test_bg", Difficulty.HARD),
        new MapMetadata("test_map", "Test", "backgrounds/test_bg", Difficulty.HARD),
        new MapMetadata("test_map", "Test", "backgrounds/test_bg", Difficulty.HARD)
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
        EASY("easy", new Color(23, 211, 23)),
        MEDIUM("medium", new Color(211, 211, 23)),
        HARD("hard", new Color(211, 23, 23));
        
        public String displayName;
        public Color color;
        private Difficulty(String displayName, Color color)
        {
            this.displayName = displayName;
            this.color = color;
        }
    }
}
