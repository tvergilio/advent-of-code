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
        Long result = getMatches(source);
        System.out.println(result);
    }

    private static Long getMatches (Path source) throws IOException {
        return Arrays.stream(Files.readString(source)
                .split(lineSeparator + lineSeparator))
                .map(entry -> Arrays.stream(entry.split("[\\n\\r\\s]+")))
                .map(stringStream -> {
                    return stringStream.collect(Collectors.toMap(x -> x.substring(0, x.indexOf(':')), y -> y.substring(y.indexOf(':') + 1)));
                })
                .filter(map -> map.keySet().containsAll(requiredFields))
                .filter(map -> validateBirthYear(map.get("byr")))
                .filter(map -> validateIssueYear(map.get("iyr")))
                .filter(map -> validateExpirationYear(map.get("eyr")))
                .filter(map -> validateHeight(map.get("hgt")))
                .filter(map -> validateHairColour(map.get("hcl")))
                .filter(map -> validateEyeColour(map.get("ecl")))
                .filter(map -> validatePassport(map.get("pid")))
                .count();
    }

    private static boolean validateYear(String input, int length, int atLeast, int atMost) {
        return input.length() >= length
                && Integer.parseInt(input) >= atLeast
                && Integer.parseInt(input) <= atMost;
    }

    private static boolean validateBirthYear(String birthYear) {
        return validateYear(birthYear, 4, 1920, 2002);
    }

    private static boolean validateIssueYear(String issueYear) {
        return validateYear(issueYear, 4, 2010, 2020);
    }

    private static boolean validateExpirationYear(String expirationYear) {
        return validateYear(expirationYear, 4, 2020, 2030);
    }

    private static boolean validateHeight(String height) {
        if (height.endsWith("cm")) {
            int heightInCm = Integer.parseInt(height.substring(0, height.indexOf("cm")));
            return heightInCm >= 150 && heightInCm <= 193;
        } else if (height.endsWith("in")) {
            int heightInCm = Integer.parseInt(height.substring(0, height.indexOf("in")));
            return heightInCm >= 59 && heightInCm <= 76;
        }
        return false;
    }

    private static boolean validateHairColour(String hairColour) {
        return hairColour.matches("^#[0-9a-fA-F]{6}");
    }

    private static boolean validateEyeColour(String eyeColour) {
        List<String> validColours = Arrays.asList("amb", "blu", "brn", "gry", "grn", "hzl", "oth");
        return validColours.contains(eyeColour);
    }

    private static boolean validatePassport(String passportNumber) {
        return passportNumber.matches("[0-9]{9}");
    }
}

