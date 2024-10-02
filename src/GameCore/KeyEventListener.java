package GameCore;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyEventListener implements KeyListener {
    public boolean shift,space,enter,escape,left,right,up,down, inventoryOpen, inventorykeyAlreadyPressed;
    boolean locked;
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if(keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W){
            up = true;
            locked = true;
        }
        if (keyCode == KeyEvent.VK_I || keyCode == KeyEvent.VK_E) {
            if (!inventorykeyAlreadyPressed) {

                inventoryOpen = !inventoryOpen;

                inventorykeyAlreadyPressed = true;
            }
        }
        if(keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S){
            down = true;
            locked = true;
        }
        if(keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A){
            left = true;
            locked = true;
        }
        if(keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D){
            right = true;
            locked = true;
        }
        if(keyCode == KeyEvent.VK_SHIFT){
            shift = true;
            locked = true;
        }
        if(keyCode == KeyEvent.VK_SPACE){
            space = true;
            locked = true;
        }
        if(keyCode == KeyEvent.VK_ENTER){
            enter = true;
            locked = true;
        }
        if(keyCode == KeyEvent.VK_ESCAPE){
            escape = true;
            locked = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if(keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W){
            up = false;
            locked = false;
        }
        if(keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S){
            down = false;
            locked = false;
        }
        if (keyCode == KeyEvent.VK_I || keyCode == KeyEvent.VK_E) {
            // Reset the key press flag when the key is released
            inventorykeyAlreadyPressed = false;
        }
        if(keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A){
            left = false;
            locked = false;
        }
        if(keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D){
            right = false;
            locked = false;
        }
        if(keyCode == KeyEvent.VK_SHIFT){
            shift = false;
            locked = false;
        }
        if(keyCode == KeyEvent.VK_SPACE){
            space = false;
            locked = false;
            GamePanel.player.damage(10);
        }
        if(keyCode == KeyEvent.VK_ENTER){
            enter = false;
            locked = false;
            GamePanel.player.addHealth(10);

        }
        if(keyCode == KeyEvent.VK_ESCAPE){
            escape = false;
            locked = false;
        }
    }
}
