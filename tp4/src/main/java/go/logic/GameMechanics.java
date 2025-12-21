package go.logic;

//Klasa przechowująca zasady rozgrywki w grę Go

public class GameMechanics {
    public int blackCaptures = 0;
    public int whiteCaptures = 0;


    //Metoda sprawdzająca, czy mozliwe jest mozliwy do wykonania
    public boolean IsMovePossible(Board board, int x, int y, Stone color) {
        if (!board.isFieldOnBoard(x, y)) return false; // jezeli wskazane pole nie nalezy do planszy - zwracamy false
        if (board.getField(x, y) != Stone.EMPTY ) return false; //jezeli aktualnie lezy jakis kamien na wskazanym polu - zwracamy false
        board.setField(x, y, color); // Sprawdzamy, czy po wstawieniu kamienia występuje bicie lub czy wstawiony kamien ma jeszcze
        //wolne oddechy

        CheckCaptures(board, x, y, color); // sprawdzamy, czy nie wystapilo bicie

        if (AreNeighboursFieldsFree(board, x, y)) {// jezeli jest co najmniej jedno miejsce sąsiednie niezajęte - ruch jest mozliwy
            return true;
        }

        board.setField(x, y, Stone.EMPTY); // w innym wypadku musimy cofnac nieprawidlowe wstawienie kamienia i zwrocic false
        return false;
    }

    private boolean AreNeighboursFieldsFree(Board board, int x, int y) {

        for (Direction d : Direction.values()) { // przy uzyciu wartosci enum Direction sprawdzamy, czy jakiekolwiek
            // sasiednie pole jest wolne
            int newX = x + d.getDx();
            int newY = y + d.getDy();

            if (board.isFieldOnBoard(newX, newY)) {
                if (board.getField(newX, newY) == Stone.EMPTY) {
                    return true;
                }
            }
        }
        return false;
    }

    private void CheckCaptures(Board board, int x, int y, Stone color) {
        for (Direction d : Direction.values()) { // sprawdzamy kazde sasiednie pole
            int newX = x + d.getDx();
            int newY = y + d.getDy();

            if (board.isFieldOnBoard(newX, newY) && board.getField(newX, newY) == color.opponent()) { // sprawdzamy czy rozwazane pole
                //nalezy do planszy oraz czy na tym polu ;ezy kamien przeciwnika
                if (!AreNeighboursFieldsFree(board, newX, newY)) {
                    board.setField(newX, newY, Stone.EMPTY);
                    if (color == Stone.BLACK) {
                        blackCaptures++;
                    } else {
                        whiteCaptures++;
                    }
                }
            }
        }
    }
}
