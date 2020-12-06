import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Day6 {
    private static String lineSeparator = System.lineSeparator();

    private static boolean isAsciiLetter(int value) {
        return value > 64;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Result: " + firstCheck(getInput()));
        System.out.println("Result: " + secondCheck(getInput()));
    }

    private static Stream<String> getInput() throws IOException {
        Path source = Paths.get("target\\classes\\answers");
        return Arrays.stream(Files.readString(source)
                .split(lineSeparator + lineSeparator));
    }

    private static long firstCheck(Stream<String> input) {
        return input
                .map(String::chars)
                .flatMapToLong(i -> LongStream.of(i.filter(Day6::isAsciiLetter)
                .distinct()
                .count()))
                .sum();
    }

    private static long secondCheck(Stream<String> input) throws IOException {
        List<List<String>> list = input
                .map(chunk -> Arrays.stream(chunk.split(lineSeparator)).collect(Collectors.toList()))
                .collect(Collectors.toList());

        int matches = 0;

        for (List<String> chunk : list) {
            List<String> distinct = getDistinct(chunk);
            for (String character : distinct) {
                boolean allHaveIt = chunk.stream().allMatch(word -> word.contains(character));
                if (allHaveIt) {
                    matches ++;
                }
            }
        }
        return matches;
    }

    private static List<String> getDistinct(List<String> input) {
        return input.stream()
                    .flatMapToInt(String::chars)
                    .filter(Day6::isAsciiLetter)
                    .distinct()
                    .mapToObj(i -> Character.toString((char) i))
                    .collect(Collectors.toList());
    }
}
