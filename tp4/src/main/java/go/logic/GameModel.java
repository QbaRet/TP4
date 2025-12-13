package go.logic;

import java.util.ArrayList;
import java.util.List;

public class GameModel {
    private final Board board;
    private int blackCaptures;
    private int whiteCaptures;
    private final List<GameObserver> observers = new ArrayList<>();

    public GameModel(Board board) {
        this.board = board;
    }

    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(GameObserver observer) {
        observers.remove(observer);
    }

    public Board getBoard() {
        return board;
    }

    public void setCaptures(int black, int white) {
        this.blackCaptures = black;
        this.whiteCaptures = white;
        notifyCapturesUpdated();
    }

    public int getBlackCaptures() { return blackCaptures; }
    public int getWhiteCaptures() { return whiteCaptures; }

    public void notifyBoardUpdated() {
        for (GameObserver obs : observers) {
            obs.onBoardUpdated(board);
        }
    }

    public void notifyCapturesUpdated() {
        for (GameObserver obs : observers) {
            obs.onCapturesUpdated(blackCaptures, whiteCaptures);
        }
    }
}
