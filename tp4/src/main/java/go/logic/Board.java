package go.logic;

public class Board {
    private final int size;
    private final Stone[][] fields;

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

    public void setField(int x, int y, Stone stone) {
        fields[x][y] = stone;
    }

    public Stone getField(int x, int y) {
        if (x < 0 || x > size || y < 0 || y > size) {
            throw new IllegalArgumentException("Podane pole nie nalezy do planszy!");
        }
        return fields[x][y];
    }
}
