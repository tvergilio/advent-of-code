import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Day1 {
    public static void main(String[] args) throws IOException {
        Path source = Paths.get("target\\classes\\input");
        List<Integer> numbers = Files.lines(source).map(Integer::valueOf).collect(Collectors.toList());
        int answer = findProductOfThreeNumbers(numbers);
        System.out.println(answer);
    }

    private static int findProductOfTwoNumbers(List<Integer> numbers) throws IOException {
        return numbers.stream()
                .filter(x -> numbers.stream().anyMatch(y -> x + y == 2020))
                .reduce((x, y) -> x * y)
                .get();
    }

    private static int findProductOfThreeNumbers(List<Integer> numbers) throws IOException {
        return numbers.stream()
                .filter(x -> numbers.stream()
                        .anyMatch(y -> numbers.stream()
                                .anyMatch(z -> x + y + z == 2020)))
                .reduce((a, b) -> a * b)
                .get();
    }
}
