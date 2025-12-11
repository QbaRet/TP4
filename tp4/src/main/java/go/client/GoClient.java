package go.client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

import go.logic.Protocol;

public class GoClient {
    private Socket socket; 
    private DataInputStream fromServer;
    private DataOutputStream toServer;
    public static void main(String[] args){
        new GoClient().connect();
    }
    private void connect(){
        try {
            System.out.println("Łączenie z serwerem");
            socket = new Socket("localhost", Protocol.Port);
            fromServer = new DataInputStream(socket.getInputStream());
            toServer = new DataOutputStream(socket.getOutputStream());
            int playerId = fromServer.readInt();
            System.out.println("Połączono jako gracz " + playerId);
            if(playerId == Protocol.Player1){
                System.out.println("Czekanie na drugiego gracza");
                fromServer.readInt();
                System.out.println("Drugi gracz dołączył. Rozpoczynam grę");
            }
            else{
                System.out.println("Rozpoczynam grę");
            }
            new Scanner(System.in).nextLine();
        } catch (Exception e) {
            System.out.println("Błąd połączenia z serwerem");
        }
    } 
}