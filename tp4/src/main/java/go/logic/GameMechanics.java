package go.logic;

public class GameMechanics {
    public Boolean IsMovePossible(Board board, int x, int y, Stone color) {
        if (board.getField(x, y) != Stone.EMPTY) return false;

        board.setField(x, y, color);

        // Nalezy sprawdzic, czy kamien ma jeszcze oddechy
        // Nalezy sprawdzic czy po polozeniu kamienia nie zabieramy kamienia przeciwnika

        return true;
    }
}
