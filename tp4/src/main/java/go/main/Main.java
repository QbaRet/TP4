package go.main;

import go.logic.Board;
import go.logic.Stone;
import go.ui.ConsoleView;
import go.ui.GameView;

public class Main {
    public static void main(String[] args) {
        Board board = new Board(19);
        GameView gameView = new ConsoleView();

        //test wyswietlania planszy
        board.setField(2,2, Stone.BLACK);
        board.setField(15,3, Stone.BLACK);

        board.setField(1,2, Stone.WHITE);
        board.setField(2,1, Stone.WHITE);

        gameView.showBoard(board);
    }
}
