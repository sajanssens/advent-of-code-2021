package nl.bramjanssens.dec10;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static java.util.Collections.reverse;
import static java.util.Collections.sort;
import static nl.bramjanssens.util.Util.getLines;

public class App {

    private static final String openTags = "({[<";
    private static final String closeTags = ")}]>";

    public static void main(String[] args) throws URISyntaxException, IOException {
        List<String> lines = getLines("dec10/input.txt");
        List<String> illegalTokens = new ArrayList<>();
        List<String> incompleteLines = new ArrayList<>();

        // Part 1
        for (String line : lines) {
            boolean incompleteLine = true;
            Stack<String> lineStack = new Stack<>();
            String[] tags = line.split("");
            for (String tag : tags) {
                if (isOpenTag(tag)) {
                    lineStack.push(tag);
                } else if (isCloseTag(tag)) {
                    if (!flip(tag).equals(lineStack.pop())) {
                        illegalTokens.add(tag);
                        incompleteLine = false;
                        break;
                    }
                }
            }
            if (incompleteLine) {
                incompleteLines.add(line);
            }
        }
        System.out.println(illegalTokens.stream().mapToInt(App::score).sum());

        // Part 2
        List<Long> scores = new ArrayList<>();
        for (String line : incompleteLines) {
            Stack<String> lineStack = new Stack<>();
            String[] tags = line.split("");
            for (String tag : tags) {
                if (isOpenTag(tag)) {
                    lineStack.push(tag);
                } else {
                    lineStack.pop();
                }
            }
            List<String> completionString = new ArrayList<>(lineStack.stream().map(App::flip).toList());
            reverse(completionString);
            long score = completionString.stream().mapToLong(App::score2).reduce(0, (sum, next) -> sum * 5 + next);
            scores.add(score);
        }
        sort(scores);
        long middle = scores.get(((scores.size() + 1) / 2) - 1);
        System.out.println(middle);
    }

    private static String flip(String tag) {
        return switch (tag) {
            case "}" -> "{";
            case ")" -> "(";
            case "]" -> "[";
            case ">" -> "<";
            case "{" -> "}";
            case "(" -> ")";
            case "[" -> "]";
            case "<" -> ">";
            default -> "";
        };
    }

    private static int score(String tag) {
        return switch (tag) {
            case "}" -> 1197;
            case ")" -> 3;
            case "]" -> 57;
            case ">" -> 25137;
            default -> 0;
        };
    }

    private static int score2(String tag) {
        return switch (tag) {
            case ")" -> 1;
            case "]" -> 2;
            case "}" -> 3;
            case ">" -> 4;
            default -> 0;
        };
    }

    private static boolean isOpenTag(String tag) {
        return openTags.contains(tag);
    }

    private static boolean isCloseTag(String tag) {
        return closeTags.contains(tag);
    }
}
