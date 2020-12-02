import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PasswordValidator {
    public static void main(String[] args) throws IOException {
        Path source = Paths.get("target\\classes\\passwords");
        Long result = getMatchesSecondRule(source);
        System.out.println(result);
    }

    private static Long getMatchesSecondRule(Path source) throws IOException {
        return Files.lines(source)
                .filter(line -> {
                    String word = line.substring(line.lastIndexOf(' ') + 1);
                    String letter = line.substring(line.indexOf(' ') + 1, line.indexOf(':'));
                    Integer position1 = Integer.valueOf(line.substring(0, line.indexOf('-'))) - 1;
                    Integer position2 = Integer.valueOf(line.substring(line.indexOf('-') + 1, line.indexOf(' '))) - 1;
                    boolean firstMatch = String.valueOf(word.charAt(position1)).equals(letter);
                    boolean secondMatch = String.valueOf(word.charAt(position2)).equals(letter);
                    return firstMatch ^ secondMatch;
                })
                .count();
    }

    private static Long getMatchesFirstRule(Path source) throws IOException {
        return Files.lines(source)
                    .filter(line -> {
                        String word = line.substring(line.lastIndexOf(' '));
                        String letter = line.substring(line.indexOf(' ') + 1, line.indexOf(':'));
                        Integer from = Integer.valueOf(line.substring(0, line.indexOf('-')));
                        Integer to = Integer.valueOf(line.substring(line.indexOf('-') + 1, line.indexOf(' ')));
                        Integer occurrences = word.length() - word.replaceAll(letter,"").length();
                        return from <= occurrences && occurrences <= to;
                    })
                    .count();
    }
}

