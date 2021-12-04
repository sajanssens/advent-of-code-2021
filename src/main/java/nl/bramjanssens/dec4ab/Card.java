package nl.bramjanssens.dec4ab;

import java.util.Arrays;

import static java.util.Arrays.stream;
import static java.util.function.Predicate.not;

public class Card {
    Cell[][] cells;
    int width;
    int row = 0;

    public Card(int width) {
        this.cells = new Cell[width][width];
        this.width = width;
    }

    void addRow(String data) {
        cells[row] = new Cell[width];
        String[] numsAndSpaces = data.split(" ");
        int[] nums = stream(numsAndSpaces)
                .filter(not(String::isBlank))
                .mapToInt(Integer::parseInt).toArray();

        for (int i = 0; i < nums.length; i++) {
            cells[row][i] = new Cell(nums[i]);
        }
        row++;
    }

    public boolean isFilled() {
        return row == width;
    }

    public void check(Integer call) {
        stream(cells)
                .flatMap(Arrays::stream)
                .filter(c -> c.value == call)
                .findFirst()
                .ifPresent(Cell::check);
    }

    public boolean hasBingo() {
        // check rows:
        for (int i = 0; i < width; i++) {
            Cell[] row = cells[i];
            if (stream(row).allMatch(Cell::isChecked)) return true;
        }
        // check columns:
        for (int c = 0; c < width; c++) {
            Cell[] col = new Cell[width];
            for (int r = 0; r < width; r++) {
                col[r] = cells[r][c];
            }
            if (stream(col).allMatch(Cell::isChecked)) return true;
        }
        return false;
    }

    public int score() {
        return stream(cells)
                .flatMap(Arrays::stream)
                .filter(not(Cell::isChecked))
                .mapToInt(Cell::getValue)
                .sum();
    }
}
