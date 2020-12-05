import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day3 {

    public static void main(String[] args) throws IOException {
        Path source = Paths.get("target\\classes\\lopes");
        int firstTreeCount = skiDown(source, 1, 1);
        int secondTreeCount = skiDown(source, 3, 1);
        int thirdTreeCount = skiDown(source, 5, 1);
        int fourthTreeCount = skiDown(source, 7, 1);
        int fifthTreeCount = skiDown(source, 1, 2);
        System.out.println((long)firstTreeCount * secondTreeCount * thirdTreeCount * fourthTreeCount * fifthTreeCount);
    }

    private static int skiDown(Path source, int right, int down) throws IOException {
        int treeCount = 0;
        int row = 0;
        char tree = 35;
        int position = 0;

        List<List<Integer>> slope = Files
                .lines(source)
                .map(line -> Stream
                        .iterate(line, x -> x + x)
                        .limit(11)
                        .flatMapToInt(String::chars)
                        .boxed()
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());

        for (int i = 0; i <= slope.size(); i++) {
            position = position + right;
            row = row + down;

            if (row >= slope.size()) {
                break;
            }

            if (slope.get(row).get(position) == tree) {
                treeCount++;
            }
        }
        return treeCount;
    }
}