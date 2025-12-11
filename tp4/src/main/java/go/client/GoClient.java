package go.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
            playGame(playerId);
        } catch (Exception e) {
            System.out.println("Błąd połączenia z serwerem");
        }
    } 
    private void playGame(int playerId) throws IOException{
        boolean myTurn = (playerId == Protocol.Player1);
        Scanner scanner = new Scanner(System.in);
        while(true){
            if(myTurn){
                System.out.println("Twoch ruch. Wpisz współrzędne x y lub 'pass', 'surrender', 'quit':");

                //Tutaj bedzie trzeba dodac obsluge pass, surrender, quit
                if(scanner.hasNextInt()){
                    int x = scanner.nextInt();
                    int y = scanner.nextInt();
                    toServer.writeInt(Protocol.MOVE);
                    toServer.writeInt(x);
                    toServer.writeInt(y);
                    toServer.flush();
                    System.out.println("Ruch wysłany na pozycję (" + x + "," + y + ")");
                    myTurn = false;
                } else {
                    scanner.next(); 
                }

            } else {
                System.out.println("Czekanie na ruch przeciwnika");
            
                int messageType = fromServer.readInt();

                if (messageType == Protocol.MOVE) {
                    int x = fromServer.readInt();
                    int y = fromServer.readInt();
                    System.out.println("Przeciwnik postawił kamień na: " + x + ", " + y);
                    
                    myTurn = true;
                }
                else if (messageType == Protocol.INVALID_MOVE) {
                    int x = fromServer.readInt();
                    int y = fromServer.readInt();
                    System.out.println("Ruch ("+x+","+y+") jest nielegalny! Spróbuj ponownie.");
                    myTurn = true;
                }
                else if (messageType == Protocol.PASS) {
                    System.out.println("Przeciwnik spasował.");
                    myTurn = true;
                }
                else if (messageType == Protocol.SURRENDER) {
                    System.out.println("Przeciwnik się poddał! Wygrałeś.");
                    break;
                }
                else if (messageType == Protocol.QUIT) {
                    System.out.println("Przeciwnik wyszedł z gry.");
                    break;
                }
            }
        }
    }
}   