package Environmental;

import Entities.Entity;
import Interactions.Chest;
import Interactions.Door;

public class Map{
    public int[][] tileIntArray;
    public Entity[] entities;
    public Chest[] chests;
    public Door[] doors;
    public Map left;
    public Map right;
    public Map up;
    public Map down;
}
