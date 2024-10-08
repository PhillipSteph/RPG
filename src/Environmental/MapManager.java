package Environmental;

import Interactions.Chest;
import Interactions.Door;
import Items.Gems;
import Items.Heart;
import Items.Item;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static GameCore.GamePanel.tileSize;

public class MapManager {
    public static int[][] map1 = new int[46][46];
    public static int[][] map2 = new int[46][46];
    public static int[][] path1 = new int[26][26];
    public static int[][] map3 = new int[46][46];

    public static int[][] house1 = new int[7][7];
    public static int[][] house2 = new int[8][8];

    public static int[][][] housearray = new int[][][]{house1, house2};
    //int arrays are being loaded into the map objects.
    //currentMap starts as the first map but will be changed to be the current map the player is in.
    //can imagine the structure as a linked list but every knod has left right up and down pointer.

    final static Random random = new Random();

    //indoormaps
    public static Map housemap1 = new Map();
    public static Map housemap2 = new Map();

    //outdoormaps
    public static Map currentMap = new Map();
    public static Map pathmap1 = new Map();
    public static Map pathmap2 = new Map();
    public static Map seaMap = new Map();
    public static Map townMap1 = new Map();

    //tilearrays
    public static BufferedImage[] tiles = new BufferedImage[32];
    public static BufferedImage[] tiles32 = new BufferedImage[1];
    //alternating tile textures
    public static BufferedImage[] altGrass = new BufferedImage[2];
    public static BufferedImage[] altWater = new BufferedImage[15];
    public static BufferedImage[] house = new BufferedImage[14];


    //itempool
    public static Item[] itempool = new Item[]{};
    public static void initallmaps(){
        getTileimages();
        inititempool();
        loadAllHouseMaps(); // all housemaps are given tile arrays, cause they're given randomly

        initmap(currentMap,map1,"World1.txt",pathmap2,pathmap1,null,null); //normal maps
        initmap(townMap1,map3,"World3.txt",null, pathmap2,null,null);
        initmap(seaMap,map2,"World2.txt",pathmap1,null,null,null);

        initmap(pathmap2,path1,"Path1.txt",townMap1,currentMap,null,null); // paths
        initmap(pathmap1,path1,"Path1.txt",currentMap,seaMap,null,null);

        initentities();
    }

    private static void inititempool() {
        itempool = new Item[]{
                new Heart("Big Heart",3),
                new Heart("Medium Heart",2),
                new Heart("Small Heart",1),
                new Gems("Big Gem",20),
                new Gems("Gem",10),
                new Gems("Small Gem",5)
        };
    }

    private static void loadAllHouseMaps() {
        loadMap("House1.txt",house1,7,7);
        loadMap("House2.txt",house2,8,8);
    }

    private static void initmap(Map map, int[][] tilearray, String file, Map left, Map right, Map up, Map down) {
        int width = tilearray.length;
        int height = tilearray[0].length;
        loadMap(file,tilearray,width,height);

        map.tileIntArray = tilearray;

        map.left = left;
        map.right = right;
        map.up = up;
        map.down = down;

        createDoors(map);
    }

    private static void initentities() {
        currentMap.chests = new Chest[]{
                new Chest(-tileSize*20,-tileSize*14,tileSize,new Heart("Big Heart",3),tiles[23]),
                new Chest(-tileSize*22,tileSize*14,tileSize,new Heart("Medium Heart",2),tiles[23]),
                new Chest(tileSize*18,-tileSize*16,tileSize,new Heart("Small Heart",3),tiles[23])
        };
    }
    public static void createDoors(Map desiredmap){
        int[][] map = desiredmap.tileIntArray;
        int totalYpixelsize = map.length*tileSize;
        int totalXpixelsize = map[0].length*tileSize;

        desiredmap.doors = new Door[20];
        int doorindex = 0;

        for (int i = 0; i < map.length; i++) {
            int y = -(totalYpixelsize/2)+(i*tileSize);
            for (int j = 0; j < map[0].length; j++) {
                int x = -(totalXpixelsize/2)+(j*tileSize);
                if(map[i][j] == 14 || map[i][j]==43){
                    System.out.println("14 at: "+i+" "+j);
                    desiredmap.doors[doorindex++] = new Door(x,y,tileSize,getrandominnermap(desiredmap));
                }
            }
        }
        for(Door door: desiredmap.doors){
            if(door == null) continue;
            System.out.println("Door at: "+door.worldX+" X "+door.worldY+" Y");
        }
    }
    public static int randomhouseindex = 0;
    public static Map getrandominnermap(Map outermap) {
        randomhouseindex++;
        if(randomhouseindex>=housearray.length){
            randomhouseindex = 0;
        }
        Map newmap = new Map();
        newmap.tileIntArray = housearray[randomhouseindex];

        newmap.down = outermap;
        generaterandomchests(newmap);
        return newmap;
    }

    private static void generaterandomchests(Map generatedmap) {
        int[][] map = generatedmap.tileIntArray;
        List<Chest> chestList = new ArrayList<>();
        int totalYpixelsize = map.length*tileSize;
        int totalXpixelsize = map[0].length*tileSize;

        for (int i = 0; i < map.length; i++) {
            int y = -(totalYpixelsize/2)+(i*tileSize);
            for (int j = 0; j < map[0].length; j++) {
                int x = -(totalXpixelsize/2)+(j*tileSize);
                // Check the criteria
                if (map[i][j] == 32 && (i < map.length-1 && map[i+1][j] == 32) && (random.nextInt(100) < 5)) {
                    System.out.println("32 at: " + i + " " + j);

                    Chest newChest = new Chest(x, y, tileSize, getrandomitem(), tiles[23]);
                    chestList.add(newChest);
                }
            }
        }

        // Convert the list to an array
        Chest[] chests = new Chest[chestList.size()];
        chests = chestList.toArray(chests);

     generatedmap.chests = chests;
    }

    private static Item getrandomitem() {
            int randomIndex = random.nextInt(itempool.length); // Generate a random index
            return itempool[randomIndex]; // Return the item at that index
    }

    public static void loadMap(String filepath, int[][] map, int width, int height){
        try{
            InputStream is = MapManager.class.getResourceAsStream(filepath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col < width && row < height){
                String line = br.readLine();
                while(col < width){
                    String numbers[] = line.split(",");
                    int num = Integer.parseInt(numbers[col]);
                    map[row][col] = num;
                    col++;
                }
                if(col == width){
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    public static void getTileimages(){
        for (int i = 8; i <= 39; i++) {
            String filename = i + ".png"; // Construct filename
            try {
                // Use getResource to read from the same directory as the class
                URL imageUrl = MapManager.class.getResource(filename);
                if (imageUrl != null) {
                    tiles[i - 8] = ImageIO.read(imageUrl); // Read the image
                    System.out.println("Successfully loaded: " + filename);
                } else {
                    System.err.println("Image not found: " + filename);
                }
            } catch (IOException e) {
                System.err.println("Error loading image: " + filename);
                System.err.println("Reason: " + e.getMessage());
                e.printStackTrace(); // Print the full stack trace for more context
            }
        }
        for (int i = 1; i <= 1; i++) {
            String filename = "32"+i + ".png"; // Construct filename
            try {
                // Use getResource to read from the same directory as the class
                URL imageUrl = MapManager.class.getResource(filename);
                if (imageUrl != null) {
                    tiles32[i - 1] = ImageIO.read(imageUrl); // Read the image
                    System.out.println("Successfully loaded: " + filename);
                } else {
                    System.err.println("Image not found: " + filename);
                }
            } catch (IOException e) {
                System.err.println("Error loading image: " + filename);
                System.err.println("Reason: " + e.getMessage());
                e.printStackTrace(); // Print the full stack trace for more context
            }
        }
        for (int i = 1; i <= 2; i++) {
            String filename = "8a"+i + ".png"; // Construct filename
            try {
                // Use getResource to read from the same directory as the class
                URL imageUrl = MapManager.class.getResource(filename);
                if (imageUrl != null) {
                    altGrass[i - 1] = ImageIO.read(imageUrl); // Read the image
                    System.out.println("Successfully loaded: " + filename);
                } else {
                    System.err.println("Image not found: " + filename);
                }
            } catch (IOException e) {
                System.err.println("Error loading image: " + filename);
                System.err.println("Reason: " + e.getMessage());
                e.printStackTrace(); // Print the full stack trace for more context
            }
        }
        for (int i = 1; i <= 15; i++) {
            String filename = "9a"+i + ".png"; // Construct filename
            try {
                // Use getResource to read from the same directory as the class
                URL imageUrl = MapManager.class.getResource(filename);
                if (imageUrl != null) {
                    altWater[i - 1] = ImageIO.read(imageUrl); // Read the image
                    System.out.println("Successfully loaded: " + filename);
                } else {
                    System.err.println("Image not found: " + filename);
                }
            } catch (IOException e) {
                System.err.println("Error loading image: " + filename);
                System.err.println("Reason: " + e.getMessage());
                e.printStackTrace(); // Print the full stack trace for more context
            }
        }
        for (int i = 1; i <= 14; i++) {
            String filename = "h"+i + ".png"; // Construct filename
            try {
                // Use getResource to read from the same directory as the class
                URL imageUrl = MapManager.class.getResource(filename);
                if (imageUrl != null) {
                    house[i - 1] = ImageIO.read(imageUrl); // Read the image
                    System.out.println("Successfully loaded: " + filename);
                } else {
                    System.err.println("Image not found: " + filename);
                }
            } catch (IOException e) {
                System.err.println("Error loading image: " + filename);
                System.err.println("Reason: " + e.getMessage());
                e.printStackTrace(); // Print the full stack trace for more context
            }
        }
    }
}
