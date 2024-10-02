package Environmental;

import java.awt.*;

public class Tile {
    public Color color;
    public int size;
    public int worldX;
    public int worldY;
    public boolean collision;
    public Image image;
    public Tile(Color color, int size, int worldX, int worldY, boolean collision, Image image){
        this.color = color;
        this.size = size;
        this.worldX = worldX;
        this.worldY = worldY;
        this.collision = collision;
        this.image = image;
    }
}
