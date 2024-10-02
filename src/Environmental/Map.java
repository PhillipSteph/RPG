package Environmental;

import Entities.Entity;
import Interactions.Chest;

public class Map{
    public int[][] currentmap;
    public Entity[] entities;
    public Chest[] chests;
    public Map left;
    public Map right;
    public Map up;
    public Map down;
}
