package go.logic;

// klasa Board tworzy początkowy stan planszy oraz wykonuje zmiany podczas rozgrywki, przechowując jednocześnie obecny stan planszy

public class Board {
    private final int size;
    public final Stone[][] fields;

    public Board(int size) {
        this.size = size;
        this.fields = new Stone[size][size];

        for (int i = 0; i<size; i++) {
            for (int j = 0; j < size; j++) {
                fields[i][j] = Stone.EMPTY;
            }
        }
    }

    public int getSize() {
        return size;
    }


    // metoda zmieniająca wskazane pole na planszy na wartość przekazaną jako argument
    public void setField(int wspX, int wspY, Stone stone) {
        if (!isFieldOnBoard(wspX, wspY)) {
            throw new IllegalArgumentException("Podane pole nie nalezy do planszy!");
        }
        fields[wspX][wspY] = stone;
    }


    //funkcja zwracająca wartość pola wskazanego w argumencie metody
    public Stone getField(int wspX, int wspY) {
        if (!isFieldOnBoard(wspX, wspY)) {
            throw new IllegalArgumentException("Podane pole nie nalezy do planszy!");
        }
        return fields[wspX][wspY];
    }


    //funkcja pomocnicza określająca, czy wskazane pole nalezy do planszy
    public boolean isFieldOnBoard(int x, int y) {
        return x >= 0 && x < size && y >= 0 && y < size;
    }
}
