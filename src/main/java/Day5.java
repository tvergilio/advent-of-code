import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Day5 {

    public static void main(String[] args) throws IOException {
        Path source = Paths.get("target\\classes\\tickets");
        int result = Seat.getHighestSeat(source);
        long mySeat = Seat.getYourSeat(source);
        System.out.println(result);
        System.out.println(mySeat);
    }
}

