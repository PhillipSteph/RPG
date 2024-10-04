package GameCore;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyEventListener implements KeyListener {
    public boolean shift,space,enter,escape,left,right,up,down, inventoryOpen, inventorykeyAlreadyPressed, use;
    boolean locked;
    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if(keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W){
            up = true;
            
        }
        if (keyCode == KeyEvent.VK_I || keyCode == KeyEvent.VK_E) {
            if (!inventorykeyAlreadyPressed) {

                inventoryOpen = !inventoryOpen;

                inventorykeyAlreadyPressed = true;
            }
        }
        if(keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S){
            down = true;
        }
        if(keyCode == KeyEvent.VK_U){
            use = true;
        }
        if(keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A){
            left = true;
            
        }
        if(keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D){
            right = true;
            
        }
        if(keyCode == KeyEvent.VK_SHIFT){
            shift = true;
            
        }
        if(keyCode == KeyEvent.VK_SPACE){
            space = true;
            
        }
        if(keyCode == KeyEvent.VK_ENTER){
            enter = true;
            
        }
        if(keyCode == KeyEvent.VK_ESCAPE){
            escape = true;
            
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if(keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W){
            up = false;
            
        }
        if(keyCode == KeyEvent.VK_U){
            use = false;
        }
        if(keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S){
            down = false;
            
        }
        if (keyCode == KeyEvent.VK_I || keyCode == KeyEvent.VK_E) {
            // Reset the key press flag when the key is released
            inventorykeyAlreadyPressed = false;
        }
        if(keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A){
            left = false;
            
        }
        if(keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D){
            right = false;
            
        }
        if(keyCode == KeyEvent.VK_SHIFT){
            shift = false;
            
        }
        if(keyCode == KeyEvent.VK_SPACE){
            space = false;
        }
        if(keyCode == KeyEvent.VK_ENTER){
            enter = false;
        }
        if(keyCode == KeyEvent.VK_ESCAPE){
            escape = false;
            
        }
    }
}
