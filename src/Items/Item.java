package Items;

import java.awt.image.BufferedImage;

public abstract class Item {
    public String name;
    public BufferedImage image;
    public boolean use(){
        return false;
    }
}
