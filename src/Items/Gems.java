package Items;

import static Environmental.MapManager.tiles;
import static GameCore.GamePanel.player;
public class Gems extends Item{
    int money;
    public Gems(String name, int amount){
        this.name = name;
        this.money = amount;
        this.image = tiles[31];
    }

    @Override
    public void use(){
        player.addMoney(money);
        this.money = 0;
    }
}
