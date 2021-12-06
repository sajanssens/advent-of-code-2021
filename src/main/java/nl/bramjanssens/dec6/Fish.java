package nl.bramjanssens.dec6;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class Fish {
    int age;
    private boolean reset;

    public Fish(int age) {
        this.age = age;
    }

    public void decAge() {
        if (age != 0)
            age--;
        else {
            this.reset = true;
            age = 6;
        }
    }

    public boolean isReset() {
        if (reset) {
            reset = false;
            return true;
        } else {
            return false;
        }
    }
}
