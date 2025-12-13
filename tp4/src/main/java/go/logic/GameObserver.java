package go.logic;

public interface GameObserver {
    void onBoardUpdated(Board board);
    void onCapturesUpdated(int blackCaptures, int whiteCaptures);
}
