package Interactions;

import Entities.Entity;
import Items.Item;

import java.awt.image.BufferedImage;

public class Chest extends Entity {
    public Item item;
    public Chest(int worldX, int worldY, int size, Item item, BufferedImage image) {
        super(worldX, worldY, size, 100000);
        this.item = item;
        this.image = image;
    }
}
