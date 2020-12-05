import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.IntSummaryStatistics;
import java.util.stream.IntStream;

public class Seat {
    int row;
    int column;

    public Seat(String rowCode, String columnCode) {
        this.row = getRowNumberFromCode(rowCode);
        this.column = getColumnNumberFromCode(columnCode);
    }

    public static long getYourSeat(Path source) throws IOException {
        IntSummaryStatistics summaryStatistics = getAllSeats(source).summaryStatistics();
        long max = summaryStatistics.getMax();
        long min = summaryStatistics.getMin();
        return max*(max+1)/2 - (min-1)*min/2 - summaryStatistics.getSum();
    }

    public static int getHighestSeat(Path source) throws IOException {
        return getAllSeats(source)
                .max()
                .getAsInt();
    }

    public static IntStream getAllSeats(Path source) throws IOException {
        return Files.lines(source)
                .map(line -> new Seat(line.substring(0, 7), line.substring(7)))
                .map(Seat::calculateSeatId)
                .mapToInt(x -> x);
    }

    private int calculateSeatId() {
        return row * 8 + column;
    }

    private int getRowNumberFromCode(String rowCode) {
        return getNumberFromCode(rowCode, 70);
    }

    private int getColumnNumberFromCode(String columnCode) {
        return getNumberFromCode(columnCode, 76);
    }

    private int getNumberFromCode(String code, final int asciiZeroCode) {
        return code
                .chars()
                .map(n -> n == asciiZeroCode ? 0 : 1)
                .reduce((x, y) -> x * 2 + y)
                .getAsInt();
    }

}


