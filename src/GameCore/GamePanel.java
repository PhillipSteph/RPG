package GameCore;

import Entities.Entity;
import Environmental.Map;
import Environmental.MapManager;
import Environmental.Tile;
import Interactions.Chest;
import Items.Item;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static Environmental.MapManager.currentMap;

public class GamePanel extends JPanel implements Runnable {
    Thread gp;
    long frames = 0;
    int screenWidth = 1920;
    int screenHeight = 1080;
    final public static int tileSize = 120;
    
    int playerX = (screenWidth/2)-(tileSize/2);
    int playerY = (screenHeight/2)-(tileSize/2);

    KeyEventListener keyboard = new KeyEventListener();
    MouseEventListener mouse = new MouseEventListener();
    public static Entity player = new Entity(0, 0, tileSize, 100);
    Entity malte = new Entity(0,0,tileSize*2, 50);

    private Font pixelFont;
    private Font pixelFontBig;

    Tile[][] currenttiles;
    public GamePanel(){
        this.addKeyListener(keyboard);
        this.addMouseListener(mouse);
        this.setFocusable(true);
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        try {
            // Load the font from resources (inside your project package)
            InputStream fontStream = getClass().getResourceAsStream("/pixelfont.otf");

            if (fontStream != null) {
                pixelFont = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(24f); // Font size 24
                pixelFontBig = pixelFont.deriveFont(48f);

            } else {
                throw new IOException("Font file not found");
            }

        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            pixelFont = new Font("Monospaced", Font.PLAIN, 24);  // Fallback font
            pixelFontBig = pixelFont.deriveFont(48f);

        }
    }

    @Override
    public void run() {
        MapManager.initmaps();
        initTileArray(MapManager.currentMap.currentmap);
        while(true){
            update();
            repaint();
            try {
                Thread.sleep(1000/120);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void initTileArray(int[][] map) {
        int totalYpixelsize = map.length*tileSize;
        int totalXpixelsize = map[0].length*tileSize;
        Tile[][] tiles = new Tile[map.length][map[0].length];
        for (int i = 0; i < tiles.length; i++) {
            int y = -(totalYpixelsize/2)+(i*tileSize);
            for (int j = 0; j < tiles[0].length; j++) {
                int x = -(totalXpixelsize/2)+(j*tileSize);
                tiles[i][j] = getTilefromID(map[i][j], y, x);
            }
        }
        currenttiles = tiles;
        replaceCornerWater(currenttiles);
    }

    private void replaceCornerWater(Tile[][] tiles) {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
               if(tiles[i][j].color == Color.BLUE){
                   //if it is a Water tile
                   boolean leftgrass = false;
                   boolean topgrass = false;
                   boolean rightgrass = false;
                   boolean bottomgrass = false;

                   if(i>=1){
                       topgrass = (tiles[i-1][j].color == Color.GREEN);
                   }
                   if(j>=1){
                       leftgrass = (tiles[i][j-1].color == Color.GREEN);
                   }
                   if(i<tiles.length-1){
                       bottomgrass = (tiles[i+1][j].color == Color.GREEN);
                   }
                   if(j<tiles[0].length-1){
                       rightgrass = (tiles[i][j+1].color == Color.GREEN);
                   }
                    if(leftgrass && topgrass && bottomgrass && rightgrass){
                        tiles[i][j].image = MapManager.altWater[14];
                    }else if(leftgrass && topgrass && bottomgrass){
                        tiles[i][j].image = MapManager.altWater[9];
                    }else if(leftgrass && topgrass && rightgrass){
                        tiles[i][j].image = MapManager.altWater[11];
                    }else if(leftgrass && bottomgrass && rightgrass){
                        tiles[i][j].image = MapManager.altWater[8];
                    }else if(bottomgrass && topgrass && rightgrass){
                        tiles[i][j].image = MapManager.altWater[10];
                    }else if(leftgrass && topgrass){
                        tiles[i][j].image = MapManager.altWater[1];
                    }else if(rightgrass && topgrass){
                        tiles[i][j].image = MapManager.altWater[3];
                    }else if(leftgrass && bottomgrass){
                        tiles[i][j].image = MapManager.altWater[7];
                    }else if(rightgrass && bottomgrass){
                        tiles[i][j].image = MapManager.altWater[5];
                    }else if(bottomgrass && topgrass){
                        tiles[i][j].image = MapManager.altWater[13];
                    }else if(leftgrass && rightgrass){
                        tiles[i][j].image = MapManager.altWater[12];
                    }else if(leftgrass){
                        tiles[i][j].image = MapManager.altWater[0];
                    }else if(topgrass){
                        tiles[i][j].image = MapManager.altWater[2];
                    }else if(rightgrass){
                        tiles[i][j].image = MapManager.altWater[4];
                    }else if(bottomgrass){
                        tiles[i][j].image = MapManager.altWater[6];
                    }
               }
            }
        }
    }

    private Tile getTilefromID(int id, int y, int x) {
        switch(id){
            case 8:
                Tile returnTile =  new Tile(Color.GREEN, tileSize, x, y, false, MapManager.tiles[0]);
                if((Math.random()*100)%10<=1){
                    returnTile.image = MapManager.altGrass[0];
                }else if((Math.random()*100)%20<=1){
                    returnTile.image = MapManager.altGrass[1];
                }
                return returnTile;
            case 9:
                return new Tile(Color.BLUE, tileSize, x, y, true, MapManager.tiles[1]);
            case 10:
                return new Tile(Color.DARK_GRAY, tileSize, x, y, true, MapManager.tiles[2]);
            case 11:
                return new Tile(Color.LIGHT_GRAY, tileSize, x, y, false, MapManager.tiles[3]);
            case 12:
                Tile returnTile3 =  new Tile(Color.GREEN, tileSize, x, y, true, MapManager.tiles[4]);
                if((Math.random()*100)%10<=4){
                    returnTile3.image = MapManager.tiles[20];
                }else if((Math.random()*100)%20<=4){
                    returnTile3.image = MapManager.tiles[21];
                }
                return returnTile3;
            case 13:
                return new Tile(Color.ORANGE, tileSize, x, y, true, MapManager.tiles[5]);
            case 14:
                Color darkOrange = new Color(211, 135, 29);
                return new Tile(darkOrange, tileSize, x, y, true, MapManager.tiles[6]);
            case 15:
                return new Tile(Color.RED, tileSize, x, y, true, MapManager.tiles[7]);
            case 16:
                return new Tile(Color.RED, tileSize, x, y, true, MapManager.tiles[8]);
            case 17:
                return new Tile(Color.RED, tileSize, x, y, true, MapManager.tiles[9]);
            case 18:
                Tile returnTile2 =  new Tile(Color.GREEN, tileSize, x, y, false, MapManager.tiles[10]);
                if((Math.random()*100)%10<=4) {
                    returnTile2.image = MapManager.tiles[19];
                }
                return returnTile2;
            case 19:

                return new Tile(Color.GREEN, tileSize, x, y, true, MapManager.tiles[23]);
        }
        return new Tile(Color.BLACK,tileSize, x, y, true, MapManager.tiles[0]);
    }
    public void start(){
        gp = new Thread(this);
        gp.start();
    }
    public void update(){
        checkInputs();
        if(player.worldX >= currenttiles[currenttiles.length-1][currenttiles.length-1].worldX && player.facing == 1 ){
            changemapto(currentMap.right, 1);
        }
        if(player.worldX <= currenttiles[0][0].worldX && player.facing == 0 ){
            changemapto(currentMap.left, 0);
        }
        frames++;
    }

    private void changemapto(Map map, int direction) {
        if(map == null) return;
        initTileArray(map.currentmap);
        switch(direction){
            case 1:
                player.relocate(currenttiles[0][0].worldX,player.worldY);
                currentMap = currentMap.right;
                break;
            case 0:
                player.relocate(currenttiles[currenttiles.length-1][currenttiles.length-1].worldX,player.worldY);
                currentMap = currentMap.left;
                break;
        }
    }

    private void checkInputs() {
        boolean[] input = new boolean[]{keyboard.down, keyboard.up, keyboard.left,keyboard.right};
        if(keyboard.down){
            if(nocollision(0,5)) player.move(0,5);
        }
        else if(keyboard.up){
            if(nocollision(0,-5)) player.move(0,-5);
        }
        else if(keyboard.left){
            if(nocollision(-5,0)) player.move(-5,0);
        }
        else if(keyboard.right){
            if(nocollision(5,0)) player.move(5,0);
        }
        if(keyboard.shift){
            tryToInteract();
        }
    }

    private void tryToInteract() {
        for(Chest entity : currentMap.chests){
            if(entity == null) continue;
            int entityX = entity.worldX;
            int entityY = entity.worldY;
            int playerX = player.worldX;
            int playerY = player.worldY;
            if(player.facing == 3 && playerY>entityY && playerY<entityY+tileSize*1.2 && playerX > entityX-tileSize/2 && playerX < entityX+tileSize/2){
                usechest(entity);
            }
        }
    }

    private void usechest(Chest entity) {
        player.addItem(entity.item);
        for(int i = 0;i<currentMap.chests.length;i++){
            if(entity.equals(currentMap.chests[i])){
                currentMap.chests[i] = null;
            }
        }
    }

    private boolean nocollision(int deltaX, int deltaY) {
        if(deltaX<0){
            player.facing = 0; // left
        }
        if(deltaX>0){
            player.facing = 1; //right
        }
        if (deltaY > 0) {
            player.facing = 2; //down
        }
        if(deltaY<0){
            player.facing = 3; //up
        }
        // Get the player's current position
        int playerX = player.worldX; // Assuming you have a method to get the player's x-coordinate
        int playerY = player.worldY; // Assuming you have a method to get the player's y-coordinate

        // Calculate the new position of the player
        int newPlayerX = playerX + deltaX;
        int newPlayerY = playerY + deltaY;

        // Determine the bounds of the player in the new position
        int playerLeft = newPlayerX + tileSize/4;
        int playerRight = newPlayerX + tileSize - tileSize/4;
        int playerTop = newPlayerY + tileSize/2;
        int playerBottom = newPlayerY + tileSize;

        // Assuming tiles is a 2D array of Tile objects
        for (int i = 0; i < currenttiles.length; i++) {
            for (int j = 0; j < currenttiles[i].length; j++) {
                Tile tile = currenttiles[i][j]; // Access the tile at the current position

                if (tile.collision) {
                    // Calculate the tile's boundaries
                    int tileLeft = tile.worldX;
                    int tileRight = tile.worldX + tileSize;
                    int tileTop = tile.worldY;
                    int tileBottom = tile.worldY + tileSize;

                    // Check for collision
                    if (playerRight > tileLeft && playerLeft < tileRight &&
                            playerBottom > tileTop && playerTop < tileBottom) {
                        return false; // Collision detected
                    }
                }
            }
        }

        return true; // No collision
    }


    public void paintComponent(Graphics g){
        g.setFont(pixelFont);

        Color darkforestGreen = new Color(30,70,35);

        g.setColor(darkforestGreen);
        g.fillRect(0,0,screenWidth,screenHeight);

        g.setColor(Color.white);
        drawTiles(g, currenttiles);
        drawPlayer(g);
        drawEntities(g);
        drawUI(g);
    }
    boolean firstinventoryopened = false;
    private void drawUI(Graphics g) {
        g.setFont(pixelFont);

        g.drawString(frames+" Frames", 50, 50);
        g.drawString(frames/120+" seconds",50,70);
        g.drawString(player.worldX+" X | "+player.worldY+" Y |",50,90);
        if(keyboard.inventoryOpen){
            g.setColor(new Color(81, 4, 4, 189));
            g.fillRect(screenWidth-500,100,400,screenHeight-200);
            g.setColor(Color.WHITE);

            g.setFont(pixelFontBig);
            g.drawString("Inventory",screenWidth-460,160);

            g.setFont(pixelFont);
            int i = 0;
            for(Item item : player.inventory){
                if(item == null) continue;
                g.drawImage(item.image,screenWidth-450,200+i*50,25,25,null);
                g.drawString(item.name,screenWidth-400,225+i*50);

                i++;
            }
            firstinventoryopened = true;
        }else if(!firstinventoryopened){
            g.setColor(Color.WHITE);
            g.drawString("Press I to open Inventory",screenWidth-500,160);
        }
        for(int i=0;i<player.health;i+=10){
            g.drawImage(MapManager.tiles[22],50+i*4, screenHeight-50-tileSize/2,tileSize/2,tileSize/2,null);
        }
    }

    private void drawTiles(Graphics g, Tile tiles[][]) {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                drawTile(g,tiles[i][j]);
            }
        }
        for(Chest entity : currentMap.chests){
            drawEntity(g,entity);
        }
    }

    private void drawEntity(Graphics g, Chest entity) {
        if(entityIsNotInRange(entity)){
            return;
        }
        if(entity.image !=null){
            g.drawImage(entity.image,playerX+(entity.worldX-player.worldX), playerY+(entity.worldY-player.worldY),tileSize,tileSize,null);
        }
    }

    private boolean entityIsNotInRange(Entity entity) {
        if(     entity == null
                ||  playerX+(entity.worldX-player.worldX) <= -entity.size
                ||  playerX+(entity.worldX-player.worldX) >= screenWidth
                ||  playerY+(entity.worldY-player.worldY) <= -entity.size
                ||  playerY+(entity.worldY-player.worldY) >= screenHeight
        ) return true;
        else return false;
    }

    private void drawTile(Graphics g, Tile tile) {
        if(tileisnotinrange(tile)){
            return;
        }
        g.setColor(tile.color);
        g.fillRect(playerX+(tile.worldX-player.worldX), playerY+(tile.worldY-player.worldY),tile.size,tile.size);
        if(tile.image !=null){
            g.drawImage(tile.image,playerX+(tile.worldX-player.worldX), playerY+(tile.worldY-player.worldY),tileSize,tileSize,null);
        }
    }

    private boolean tileisnotinrange(Tile tile) {
        if(     tile == null
                ||  playerX+(tile.worldX-player.worldX) <= -tile.size
                ||  playerX+(tile.worldX-player.worldX) >= screenWidth
                ||  playerY+(tile.worldY-player.worldY) <= -tile.size
                ||  playerY+(tile.worldY-player.worldY) >= screenHeight
        ) return true;
        else return false;
    }

    private void drawPlayer(Graphics g) {
        switch(player.facing){
            case 0:
                //facing left
                if(frames%120<60){
                    g.drawImage(MapManager.tiles[11],(screenWidth/2)-(player.size/2), (screenHeight/2)-(player.size/2),tileSize,tileSize,null);
                }else{
                    g.drawImage(MapManager.tiles[15],(screenWidth/2)-(player.size/2), (screenHeight/2)-(player.size/2),tileSize,tileSize,null);
                }
            break;
            case 1:
                if(frames%120<60){
                    g.drawImage(MapManager.tiles[12],(screenWidth/2)-(player.size/2), (screenHeight/2)-(player.size/2),tileSize,tileSize,null);
                }else{
                    g.drawImage(MapManager.tiles[16],(screenWidth/2)-(player.size/2), (screenHeight/2)-(player.size/2),tileSize,tileSize,null);
                }
            break;
            case 2: //facing down
                if(frames%120<60){
                    g.drawImage(MapManager.tiles[13],(screenWidth/2)-(player.size/2), (screenHeight/2)-(player.size/2),tileSize,tileSize,null);
                }else{
                    g.drawImage(MapManager.tiles[17],(screenWidth/2)-(player.size/2), (screenHeight/2)-(player.size/2),tileSize,tileSize,null);
                }
            break;
            case 3: //facing up
                if(frames%120<60){
                    g.drawImage(MapManager.tiles[14],(screenWidth/2)-(player.size/2), (screenHeight/2)-(player.size/2),tileSize,tileSize,null);
                }else{
                    g.drawImage(MapManager.tiles[18],(screenWidth/2)-(player.size/2), (screenHeight/2)-(player.size/2),tileSize,tileSize,null);
                }
            break;
            default:
                g.drawImage(MapManager.tiles[13],(screenWidth/2)-(player.size/2), (screenHeight/2)-(player.size/2),tileSize,tileSize,null);
            break;
        }
    }
    public void drawEntities(Graphics g){
//        g.drawImage(MapManager.tiles32[0],(screenWidth/2)-(player.size/2) + tileSize*3, (screenHeight/2)-(player.size/2),tileSize*2,tileSize*2,null);
    }
}
