package go.server;
import java.io.DataOutputStream;
import java.net.Socket;

public class GameSession implements Runnable{
    private Socket p1Socket;
    private Socket p2Socket;
    public GameSession(Socket p1, Socket p2){
        this.p1Socket = p1;
        this.p2Socket = p2;
    }
    @Override
    public void run() {
        try{
            DataOutputStream toP1 = new DataOutputStream(p1Socket.getOutputStream());
            toP1.writeInt(1);
            //tutaj bedzie watek rozgrywki
            while(true){
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}