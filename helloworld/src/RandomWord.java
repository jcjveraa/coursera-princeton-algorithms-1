import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        int i = 0;
        var champion = "";
        while (!StdIn.isEmpty()) {
            String contestant = StdIn.readString();
            i++;
            var p = 1.0 / i;
            var selected = StdRandom.bernoulli(p);
            if (selected) champion = contestant;
        }

        System.out.println(champion);
    }
}
