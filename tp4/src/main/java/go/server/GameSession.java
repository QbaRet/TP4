package go.server;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import go.logic.Board;
import go.logic.GameMechanics;
import go.logic.Protocol;
import go.logic.Stone;

public class GameSession implements Runnable{
    private Socket p1Socket;
    private Socket p2Socket;
    private Board board;
    private GameMechanics mechanics;
    
    public GameSession(Socket p1, Socket p2){
        this.p1Socket = p1;
        this.p2Socket = p2;
        this.board = new Board(19);
        this.mechanics = new GameMechanics();
    }
    @Override
    public void run() {
        try{
            DataInputStream input1 = new DataInputStream(p1Socket.getInputStream());
            DataOutputStream output1 = new DataOutputStream(p1Socket.getOutputStream());
            
            DataInputStream input2 = new DataInputStream(p2Socket.getInputStream());
            DataOutputStream output2 = new DataOutputStream(p2Socket.getOutputStream());
            output1.writeInt(1); 
            output1.flush();
            System.out.println("Gra rozpoczęta.");
            while(true){
                int messageType=input1.readInt();
                if(messageType==Protocol.MOVE){
                    int x=input1.readInt();
                    int y=input1.readInt();
                    
                    if(mechanics.IsMovePossible(board, (x - 1), (y - 1), Stone.BLACK)){
                        System.out.println("Gracz 1 wykonał ruch na pozycję ("+x+","+y+")");
                        output2.writeInt(Protocol.BOARD_STATE);
                        Protocol.sendBoard(board, output2);
                        output2.writeInt(Protocol.CAPTURES);
                        output2.writeInt(mechanics.blackCaptures);
                        output2.writeInt(mechanics.whiteCaptures);
                        output2.writeInt(Protocol.MOVE);
                        output2.writeInt(x);
                        output2.writeInt(y);
                        output2.flush();
                    } else {
                        System.out.println("Gracz 1 wykonał nielegalny ruch na pozycję ("+x+","+y+")");
                        output1.writeInt(Protocol.INVALID_MOVE);
                        output1.writeInt(x);
                        output1.writeInt(y);
                        output1.flush();
                        continue; 
                    }
                }
                else if(messageType==Protocol.PASS){
                    System.out.println("Gracz 1 pasuje.");
                    output2.writeInt(Protocol.PASS);
                    output2.flush();
                }
                else if(messageType==Protocol.SURRENDER){
                    System.out.println("Gracz 1 się poddał. Gracz 2 wygrywa.");
                    output2.writeInt(Protocol.SURRENDER);
                    output2.flush();
                    break;
                }
                else if(messageType==Protocol.QUIT){
                    System.out.println("Gracz 1 wyszedł z gry.");
                    output2.writeInt(Protocol.QUIT);
                    output2.flush();
                    break;
                }
                
                messageType=input2.readInt();
                if(messageType==Protocol.MOVE){
                    int x=input2.readInt();
                    int y=input2.readInt();
                    
                    if(mechanics.IsMovePossible(board, (x - 1), (y - 1), Stone.WHITE)){
                        System.out.println("Gracz 2 wykonał ruch na pozycję ("+x+","+y+")");
                        output1.writeInt(Protocol.BOARD_STATE);
                        Protocol.sendBoard(board, output1);
                        output1.writeInt(Protocol.CAPTURES);
                        output1.writeInt(mechanics.blackCaptures);
                        output1.writeInt(mechanics.whiteCaptures);
                        output1.writeInt(Protocol.MOVE);
                        output1.writeInt(x);    
                        output1.writeInt(y);
                        output1.flush();
                    } else {
                        System.out.println("Gracz 2 wykonał nielegalny ruch na pozycję ("+x+","+y+")");
                        output2.writeInt(Protocol.INVALID_MOVE);
                        output2.writeInt(x);
                        output2.writeInt(y);
                        output2.flush();
                    }
                }
                else if(messageType==Protocol.PASS){
                    System.out.println("Gracz 2 pasuje.");
                    output1.writeInt(Protocol.PASS);
                    output1.flush();
                }
                else if(messageType==Protocol.SURRENDER){
                    System.out.println("Gracz 2 się poddał. Gracz 1 wygrywa.");
                    output1.writeInt(Protocol.SURRENDER);
                    output1.flush();
                    break;
                }
                else if(messageType==Protocol.QUIT){
                    System.out.println("Gracz 2 wyszedł z gry.");
                    output1.writeInt(Protocol.QUIT);
                    output1.flush();
                    break;
                }
            }

        } catch (Exception e) {
            System.out.println("Któryś z graczy rozłączył się.");
        }
    }
}