package nl.bramjanssens.dec4ab;

import lombok.Data;

@Data
public class Cell {
    int value = 0;
    boolean checked = false;

    public Cell(int value) {
        this.value = value;
    }

    public void check() {
        this.checked = true;
    }
}
