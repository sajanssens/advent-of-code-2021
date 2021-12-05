package nl.bramjanssens.dec5;

import lombok.AllArgsConstructor;
import lombok.Data;

import static java.lang.Math.abs;

@Data @AllArgsConstructor
public class Vector {
    int x1, y1, x2, y2;

    public boolean isVertical() {
        return x1 == x2;
    }

    public boolean isHorizontal() {
        return y1 == y2;
    }

    public boolean isDiagonal() {
        // if (hasEqualYs() || hasEqualXs()) return false;
        return abs(x1 - x2) == abs(y1 - y2);
    }
}
