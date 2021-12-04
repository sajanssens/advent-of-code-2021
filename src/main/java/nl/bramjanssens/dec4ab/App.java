package nl.bramjanssens.dec4ab;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.stream;
import static java.util.function.Predicate.not;
import static nl.bramjanssens.util.Util.getLines;

public class App {

    public static final int WIDTH = 5;

    public static void main(String[] args) throws URISyntaxException, IOException {
        List<String> lines = getLines("dec4/input.txt");

        String[] numbers = lines.get(0).split(",");
        List<Integer> callList = stream(numbers).map(Integer::parseInt).toList();
        List<Card> cards = readCards(lines);

        Card loser = null;
        for (Integer call : callList) {
            for (Card card : cards) {
                card.check(call);
            }

            // part 1
            // Optional<Card> winner = findWinner(cards);
            // if (winner.isPresent()) {
            //     int score = winner.get().score();
            //     System.out.println(call);
            //     System.out.println(score);
            //     System.out.println(score * call);
            //     break;
            // }

            // part 2
            List<Card> losers = findLosers(cards);
            if (losers.size() == 1) {
                loser = losers.get(0);
            }

            if (loser != null && loser.hasBingo()) {
                int score = loser.score();
                System.out.println(call);
                System.out.println(score);
                System.out.println(score * call);
                break;
            }
        }
    }

    private static List<Card> readCards(List<String> lines) {
        List<Card> cards = new ArrayList<>();
        Card currentCard = new Card(WIDTH);
        for (int i = 2; i < lines.size(); i++) {
            String line = lines.get(i);

            if (line.isBlank()) continue;

            currentCard.addRow(line);
            if (currentCard.isFilled()) {
                cards.add(currentCard);
                currentCard = new Card(WIDTH);
            }
        }
        return cards;
    }

    private static Optional<Card> findWinner(List<Card> cards) {
        return cards.stream()
                .filter(Card::hasBingo)
                .findFirst();
    }

    private static List<Card> findLosers(List<Card> cards) {
        return cards.stream()
                .filter(not(Card::hasBingo))
                .toList();
    }
}
