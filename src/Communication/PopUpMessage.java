package Communication;

public class PopUpMessage implements Runnable{

    Thread popUp;
    public int indexruntime = 0;
    public String message;
    @Override
    public void run() {
        while(true){
            indexruntime++;
            if(indexruntime>1200){
                indexruntime = 121;
            }
            try {
                Thread.sleep(1000/30);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void setMessage(String msg){
        this.message = msg;
        this.indexruntime = 0;
    }
    public String getMessage(){
        if(indexruntime<121){
            return this.message;
        }
        return "";
    }
    public void start(){
        popUp = new Thread(this);
        popUp.start();
    }
}
