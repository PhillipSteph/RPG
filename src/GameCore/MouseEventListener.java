package GameCore;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseEventListener implements MouseListener {
    public boolean leftClick,rightClick,middleClick, thumb1, thumb2;

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int Code = e.getButton();
        if(Code == 1){
            leftClick = true;
        }
        if(Code == 2){
            middleClick = true;
        }
        if(Code == 3){
            rightClick = true;
        }
        if(Code == 4){
            thumb1 = true;
        }
        if(Code == 5){
            thumb2 = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int Code = e.getButton();
        if(Code == 1){
            leftClick = false;
        }
        if(Code == 2){
            middleClick = false;
        }
        if(Code == 3){
            rightClick = false;
        }
        if(Code == 4){
            thumb1 = false;
        }
        if(Code == 5){
            thumb2 = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}