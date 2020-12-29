package phonebook;

import java.util.ArrayList;

import static phonebook.Main.timeInMsToString;

public class LinearSearch {

    public static long getLinearSearchResult(ArrayList<Pair<String, String>> directory, ArrayList<String> findings) {
        var startTime = System.currentTimeMillis();
        System.out.println("Start searching (linear search)...");
        var foundAmount = linearSearch(directory, findings);

        var endTime = System.currentTimeMillis();
        var resultTime = endTime - startTime;

        System.out.printf("Found %d / %d entries. Time taken: %s\n",
                foundAmount, findings.size(), timeInMsToString(resultTime));
        return resultTime;
    }

    static int linearSearch(ArrayList<Pair<String, String>> directory, ArrayList<String> findings) {
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

}
