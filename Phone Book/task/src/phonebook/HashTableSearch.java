package phonebook;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

import static phonebook.Main.timeInMsToString;

public class HashTableSearch {
    public static void hashTableSearch(ArrayList<Pair<String, String>> directory,
                                       ArrayList<String> toFind) {
        System.out.println("Start searching (hash table)...");

        var startTime = System.currentTimeMillis();
        var directoryHashTable = directory.stream()
                .collect(Collectors.toMap(x -> x.second, x -> x.first));
        var endCreatingHashTableTime = System.currentTimeMillis();
        var foundAmount = searchInHashTable(directoryHashTable, toFind);

        var endTime = System.currentTimeMillis();
        var resultTime = endTime - startTime;

        System.out.printf("Found %d / %d entries. Time taken: %s\n",
                foundAmount, toFind.size(), timeInMsToString(resultTime));
        System.out.printf("Creating time: %s\n",
                timeInMsToString(endCreatingHashTableTime - startTime));
        System.out.printf("Searching time: %s\n",
                timeInMsToString(endTime - endCreatingHashTableTime));
    }

    private static int searchInHashTable(Map<String, String> directoryHashTable,
                                         ArrayList<String> toFind) {
        int found = 0;
        for (var s : toFind) {
            if (directoryHashTable.containsKey(s)) {
                found++;
            }
        }
        return found;
    }
}
