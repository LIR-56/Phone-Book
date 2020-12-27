package phonebook;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.function.BiFunction;

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
        LinearSearch(directory, findings);

        System.out.println("");
        bubbleSort(directory, findings);
    }

    private static void bubbleSort(ArrayList<Pair<String, String>> directory, ArrayList<String> findings) {
        System.out.println("Start searching (bubble sort + jump search)...");

        var startTime = System.currentTimeMillis();
        bubbleSort(directory, (A, B) -> A.second.compareTo(B.second));
        var endSortingTime = System.currentTimeMillis();


        var startSearchingTime = System.currentTimeMillis();
        var foundAmount = linearSearch(directory, findings);
        var endTime = System.currentTimeMillis();
        var resultTime = endTime - startTime;
        System.out.printf("Found %d / %d entries. Time taken: %s\n",
                foundAmount, findings.size(), timeInMsToString(resultTime));
        System.out.printf("Sorting time: %s\n", timeInMsToString(endSortingTime - startTime));
        System.out.printf("Searching time: %s\n", timeInMsToString(endTime - startSearchingTime));
    }

    private static void LinearSearch(ArrayList<Pair<String, String>> directory, ArrayList<String> findings) {
        var startTime = System.currentTimeMillis();
        System.out.println("Start searching (linear search)...");
        var foundAmount = linearSearch(directory, findings);

        var endTime = System.currentTimeMillis();
        var resultTime = endTime - startTime;

        System.out.printf("Found %d / %d entries. Time taken: %s\n",
                foundAmount, findings.size(), timeInMsToString(resultTime));
    }

    private static String timeInMsToString(long time) {
        var minutes = time / (60 * 1000);
        var sec = (time % (60 * 1000)) / 1000;
        var ms = time % 1000;
        return String.format("%d min. %d sec. %d ms", minutes, sec, ms);
    }

    private static int jumpSearch(ArrayList<Pair<String, String>> directory, ArrayList<String> findings) {
        return 0;
    }

    private static int linearSearch(ArrayList<Pair<String, String>> directory, ArrayList<String> findings) {
        var result = 0;
        for (var toFind : findings) {
            for (var dictEntry : directory) {
                if (dictEntry.second.equals(toFind)) {
                    result++;
                    break;
                }
            }
        }
        return result;
    }

    private static Pair<String, String> parseDirectoryLine(String nextLine) {
        var splitter = nextLine.indexOf(" ");
        return Pair.of(nextLine.substring(0, splitter), nextLine.substring(splitter + 1));
    }

    private static <T> void bubbleSort(ArrayList<T> arrayList, BiFunction<T, T, Integer> compare) {
        for (int j = 1; j < arrayList.size() - 1; j++) {
            T tmp = null;
            for (int i = 0; i < arrayList.size() - 1 - j; j++) {
                if (compare.apply(arrayList.get(i), arrayList.get(i + 1)) > 0) {
                    tmp = arrayList.get(i);
                    arrayList.set(i, arrayList.get(i + 1));
                    arrayList.set(i + 1, tmp);
                }
            }
            if (tmp == null) {
                return;
            }
        }
    }
}
