import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Week3 {
    private static String lineSeparator = System.lineSeparator();
    private static List<String> requiredFields = Arrays.asList("ecl", "pid", "eyr", "hcl", "byr", "iyr", "hgt");

    public static void main(String[] args) throws IOException {
        Path source = Paths.get("target\\classes\\passports");
        Long result = getMatchesFirstRule(source);
        System.out.println(result);
    }

    private static Long getMatchesFirstRule(Path source) throws IOException {
        return Arrays.stream(Files.readString(source)
                .split(lineSeparator + lineSeparator))
                .map(entry -> Arrays.stream(entry.split("[\\n\\r\\s]+")))
                .map(stringStream -> {
                    return stringStream.collect(Collectors.toMap(x -> x.substring(0, x.indexOf(':')), y -> y.substring(y.indexOf(':') + 1)));
                })
                .filter(map -> map.keySet().containsAll(requiredFields))
                .count();
    }
}

