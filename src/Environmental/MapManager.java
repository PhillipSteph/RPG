package Environmental;

import Interactions.Chest;
import Interactions.Door;
import Items.Gems;
import Items.Heart;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.io.IOException;
import java.net.URL;

import static GameCore.GamePanel.tileSize;

public class MapManager {
    public static int[][] map1 = new int[46][46];
    public static int[][] map2 = new int[46][46];
    public static int[][] path1 = new int[26][26];
    public static int[][] map3 = new int[46][46];
    public static int[][] house1 = new int[7][7];
    public static int[][] house2 = new int[8][8];

    //int arrays are being loaded into the map objects.
    //currentMap starts as the first map but will be changed to be the current map the player is in.
    //can imagine the structure as a linked list but every knod has left right up and down pointer.

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

    public static void initmaps(){
        loadMap("World1.txt",map1,46,46); //loading txt into map1
        loadMap("Path1.txt",path1, 26,26);
        loadMap("World2.txt",map2,46,46);
        loadMap("World3.txt",map3,46,46);

        loadMap("House1.txt",house1,7,7);
        loadMap("House2.txt",house2,8,8);

        // indoormaps
        housemap1.tileIntArray = house1;
        housemap1.down = currentMap;

        housemap2.tileIntArray = house2;
        housemap2.down = currentMap;
    //outdoormaps
        currentMap.tileIntArray = map1;
        currentMap.left = pathmap2;
        currentMap.right = pathmap1;

        pathmap2.tileIntArray = path1;
        pathmap2.right = currentMap;
        pathmap2.left = townMap1;

        townMap1.tileIntArray = map3;
        townMap1.right = pathmap2;

        pathmap1.tileIntArray = path1;
        pathmap1.left = currentMap;
        pathmap1.right = seaMap;

        seaMap.tileIntArray = map2;
        seaMap.left = pathmap1;

        initentities();
    }

    private static void initentities() {
        currentMap.chests = new Chest[]{
                new Chest(-tileSize*20,-tileSize*14,tileSize,new Heart("ThreeLife4U",3),tiles[23]),
                new Chest(-tileSize*22,tileSize*14,tileSize,new Heart("TwoLife4U",2),tiles[23]),
                new Chest(tileSize*18,-tileSize*16,tileSize,new Heart("ThreeLife4U",3),tiles[23])
        };
        currentMap.doors = new Door[]{
                new Door(10*tileSize,-5*tileSize,tileSize,housemap1),
                new Door(19*tileSize,-3*tileSize,tileSize,housemap2)
        };
        housemap1.chests = new Chest[]{
                new Chest(tileSize,-tileSize*3,tileSize,new Heart("ThreeLife4U",3),tiles[23]),
                new Chest(0,-tileSize*3,tileSize,new Gems("Big Gem",10),tiles[23]),

        };
        housemap2.chests = new Chest[]{
                new Chest(tileSize*2,-tileSize*3,tileSize,new Gems("Small Gem",5),tiles[23]),
        };
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
    }

}
