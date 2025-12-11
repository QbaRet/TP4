package go.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import go.logic.Protocol;
import go.ui.ConsoleView;
import go.ui.GameView;

public class GoClient {
    private Socket socket; 
    private DataInputStream fromServer;
    private DataOutputStream toServer;
    private final GameView gameView = new ConsoleView();
    public static void main(String[] args){
        new GoClient().connect();
    }
    private void connect(){
        try {
            gameView.showMessage("Łączenie z serwerem");
            socket = new Socket("localhost", Protocol.Port);
            fromServer = new DataInputStream(socket.getInputStream());
            toServer = new DataOutputStream(socket.getOutputStream());

            int playerId = fromServer.readInt();
            gameView.showMessage("Połączono jako gracz " + playerId);
            if(playerId == Protocol.Player1){
                gameView.showMessage("Czekanie na drugiego gracza");
                fromServer.readInt();
                gameView.showMessage("Drugi gracz dołączył. Rozpoczynam grę");
            }
            else{
                gameView.showMessage("Rozpoczynam grę");
            }
            playGame(playerId);
        } catch (Exception e) {
            gameView.showMessage("Błąd połączenia z serwerem");
        }
    } 
    private void playGame(int playerId) throws IOException{
        boolean myTurn = (playerId == Protocol.Player1);
//        Scanner scanner = new Scanner(System.in);
        while(true){
            if(myTurn){
                gameView.showMessage("Twój ruch. Wpisz współrzędne 'x y' lub 'pass', 'surrender', 'quit':");
                String input = gameView.getInput();
                
                if(input.equalsIgnoreCase("pass")){
                    toServer.writeInt(Protocol.PASS);
                    toServer.flush();
                    gameView.showMessage("Pasujesz turę.");
                    myTurn = false;
                } 
                else if(input.equalsIgnoreCase("surrender")){
                    toServer.writeInt(Protocol.SURRENDER);
                    toServer.flush();
                    gameView.showMessage("Poddajesz się. Koniec gry.");
                    break;
                }
                else if(input.equalsIgnoreCase("quit")){
                    toServer.writeInt(Protocol.QUIT);
                    toServer.flush();
                    gameView.showMessage("Wychodzisz z gry.");
                    break;
                }
                else {
                    try {
                        String[] parts = input.split("\\s+");
                        if(parts.length == 2){
                            int x = Integer.parseInt(parts[0]);
                            int y = Integer.parseInt(parts[1]);
                            toServer.writeInt(Protocol.MOVE);
                            toServer.writeInt(x);
                            toServer.writeInt(y);
                            toServer.flush();
                            gameView.showMessage("Ruch wysłany na pozycję (" + x + "," + y + ")");
                            myTurn = false;
                        } else {
                            gameView.showMessage("Nieprawidłowy format. Wpisz 'x y' lub komendę.");
                        }
                    } catch (NumberFormatException e) {
                        gameView.showMessage("Nieprawidłowe współrzędne. Spróbuj ponownie.");
                    }
                }

            } else {
                gameView.showMessage("Czekanie na ruch przeciwnika");
            
                int messageType = fromServer.readInt();

                if (messageType == Protocol.MOVE) {
                    int x = fromServer.readInt();
                    int y = fromServer.readInt();
                    gameView.showMessage("Przeciwnik postawił kamień na: " + x + ", " + y);
                    
                    myTurn = true;
                }
                else if (messageType == Protocol.INVALID_MOVE) {
                    int x = fromServer.readInt();
                    int y = fromServer.readInt();
                    gameView.showMessage("Ruch ("+x+","+y+") jest nielegalny! Spróbuj ponownie.");
                    myTurn = true;
                }
                else if (messageType == Protocol.PASS) {
                    gameView.showMessage("Przeciwnik spasował.");
                    myTurn = true;
                }
                else if (messageType == Protocol.SURRENDER) {
                    gameView.showMessage("Przeciwnik się poddał! Wygrałeś.");
                    break;
                }
                else if (messageType == Protocol.QUIT) {
                    gameView.showMessage("Przeciwnik wyszedł z gry.");
                    break;
                }
            }
        }
    }
}   