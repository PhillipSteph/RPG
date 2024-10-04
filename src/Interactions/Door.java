package Interactions;

import Entities.Entity;
import Environmental.Map;

public class Door extends Entity {
    public Map innermap;
    public Map outermap;
    public Door(int worldX, int worldY, int size, Map innermap, Map outermap) {
        super(worldX, worldY, size, 100000);
        this.innermap = innermap;
        this.outermap = outermap;
    }
}
