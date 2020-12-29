package phonebook;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import static phonebook.BubbleSortAndJumpSearch.bubbleSortAndJumpSearch;
import static phonebook.LinearSearch.getLinearSearchResult;
import static phonebook.QuickSortAndBinarySearch.quickSortAndBinarySearch;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        String directoryFile = "/tmp/phonebook/directory.txt";
        String findFile = "/tmp/phonebook/find.txt";

        var sc = new java.util.Scanner(new File(directoryFile));
        //59339418 Gabrila Araminta
        var directory = new ArrayList<Pair<String, String>>();
        while (sc.hasNext()) {
            directory.add(parseDirectoryLine(sc.nextLine()));
        }
        var findings = new ArrayList<String>();
        sc = new java.util.Scanner(new File(findFile));
        while (sc.hasNext()) {
            findings.add(sc.nextLine());
        }
        var longTime = getLinearSearchResult(directory, findings);

        System.out.println();
        bubbleSortAndJumpSearch(directory, findings, longTime * 10);

        System.out.println();
        quickSortAndBinarySearch(directory, findings);
    }

    static String timeInMsToString(long time) {
        var minutes = time / (60 * 1000);
        var sec = (time % (60 * 1000)) / 1000;
        var ms = time % 1000;
        return String.format("%d min. %d sec. %d ms.", minutes, sec, ms);
    }

    private static Pair<String, String> parseDirectoryLine(String nextLine) {
        var splitter = nextLine.indexOf(" ");
        return Pair.of(nextLine.substring(0, splitter), nextLine.substring(splitter + 1));
    }



}
