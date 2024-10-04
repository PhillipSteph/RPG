package Entities;

import Items.Item;

import java.awt.image.BufferedImage;

public class Entity {
    public int worldX;
    public int worldY;
    public int size;
    public int facing;
    public int health;
    public int maxhealth;
    public Item[] inventory = new Item[10];
    public BufferedImage image;
    public Entity(int worldX, int worldY, int size, int maxhealth){
        this.worldX = worldX;
        this.worldY = worldY;
        this.size = size;
        this.facing = 2;
        this.maxhealth = maxhealth;
        this.health = maxhealth;
    }
    public void relocate(int newX, int newY){
        this.worldX = newX;
        this.worldY = newY;
    }
    public void move(int dx, int dy){
        this.worldX += dx;
        this.worldY += dy;
    }
    public void damage(int damage){
        this.health -= damage;
        if(this.health < 0){
            this.health = 0;
        }
    }
    public void addHealth(int health){
        this.health+=health;
        if(this.health >= maxhealth){
            this.health = maxhealth;
        }
    }
    public boolean isDead(){
        if(health<=0) return true;
        return false;
    }
    public void addItem(Item item){
        for(int i = 0; i<inventory.length;i++){
            if(inventory[i] == null){
                inventory[i] = item;
                return;
            }
        }
    }
    public void useItem(int index){
        if(index>=inventory.length-1){
            return;
        }
        inventory[index].use();
    }
}
