package Items;

import GameCore.GamePanel;

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
    public boolean use(){
        if(player.health==100){
            GamePanel.log.setMessage("Health is full!");
            return false;
        }
        player.addHealth(life);
        this.life = 0;
        return true;
    }
}
