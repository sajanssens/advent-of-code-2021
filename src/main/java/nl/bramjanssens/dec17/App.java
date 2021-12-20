package nl.bramjanssens.dec17;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

import static java.util.Comparator.comparingInt;
import static nl.bramjanssens.dec17.Point.zero;
import static nl.bramjanssens.util.Util.between;
import static nl.bramjanssens.util.Util.sum;

public class App {

    public static void main(String[] args) {
        new App().run();
    }

    private void run() {
        int xmin = 79, xmax = 137, ymin = -117, ymax = -176;
        Rectangle targetArea = new Rectangle(new Point(xmin, ymin), new Point(xmax, ymax));

        Set<Point> candidates = new HashSet<>();

        // find candidates
        for (int x = 6; x <= xmax; x++) {
            for (int y = -1000; y <= 1000; y++) {
                Point currPos = zero();
                Point velo = new Point(x, y);

                while (currPos.below(xmax, ymax)) {
                    currPos = currPos.plus(velo);
                    if (currPos.within(targetArea)) {
                        candidates.add(new Point(x, y));
                    }
                    velo.brake();
                }
            }
        }

        // find the top y
        int maxYvelo = candidates.stream().max(comparingInt(o -> o.y)).get().y;
        int highestY = sum(maxYvelo);
        System.out.println(highestY);

        // part two:
        int size = candidates.size();
        System.out.println(size);
    }
}

@Data
@AllArgsConstructor
class Point {
    public Integer x, y;

    static Point zero() {
        return new Point(0, 0);
    }

    Point plus(Point p) {
        return new Point(x + p.x, y + p.y);
    }

    void brake() {
        if (this.x > 0) this.x--;
        this.y--;
    }

    public boolean below(int xmax, int ymax) {
        return x <= xmax && y >= ymax;
    }

    public boolean within(Rectangle r) {
        return between(x, r.topLeft.x, r.lowRight.x) && between(y, r.lowRight.y, r.topLeft.y);
    }
}

@Data
@AllArgsConstructor
class Rectangle {
    public Point topLeft, lowRight;
}
