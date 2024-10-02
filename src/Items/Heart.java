package Items;

import java.awt.image.BufferedImage;

import static Environmental.MapManager.tiles;
import static GameCore.GamePanel.player;

public class Heart extends Item{
    public int life;
    public Heart(String name, int stage){
        this.name = name;
        switch(stage){
            case 1:
                this.life = 10;
                break;
            case 2:
                this.life = 20;
                break;
            case 3:
                this.life = 30;
                break;
            default:
                break;
        }
        this.image = tiles[22];
    }
    @Override
    public void use(){
        player.addHealth(life);
        this.life = 0;
    }
}
