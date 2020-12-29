package phonebook;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import static phonebook.LinearSearch.linearSearch;
import static phonebook.Main.timeInMsToString;

public class BubbleSortAndJumpSearch {

    private static ArrayList<Pair<String, String>> sortedArray;

    public static void bubbleSortAndJumpSearch(ArrayList<Pair<String, String>> directory,
                                                ArrayList<String> findings,
                                                long timeToInterruptSorting) {
        System.out.println("Start searching (bubble sort + jump search)...");

        boolean isSortInterrupted = false;
        var startTime = System.currentTimeMillis();
        var sortThread = new Thread(() ->
                sortedArray = bubbleSort(directory, (A, B) -> A.second.compareTo(B.second)));
        sortThread.start();
        while (!sortThread.isInterrupted() && sortThread.isAlive()) {
            if (System.currentTimeMillis() - startTime > timeToInterruptSorting) {
                sortThread.interrupt();
                isSortInterrupted = true;
            }
        }

        var endSortingTime = System.currentTimeMillis();

        var startSearchingTime = System.currentTimeMillis();
        int foundAmount;
        if (isSortInterrupted) {
            foundAmount = linearSearch(directory, findings);
        } else {
            foundAmount = jumpSearch(sortedArray, findings);
        }
        var endTime = System.currentTimeMillis();
        var resultTime = endTime - startTime;
        System.out.printf("Found %d / %d entries. Time taken: %s\n",
                foundAmount, findings.size(), timeInMsToString(resultTime));
        System.out.printf("Sorting time: %s", timeInMsToString(endSortingTime - startTime));
        if (isSortInterrupted) {
            System.out.println(" - STOPPED, moved to linear search");
        } else {
            System.out.println();
        }
        System.out.printf("Searching time: %s\n", timeInMsToString(endTime - startSearchingTime));
    }

    private static <T> ArrayList<T> bubbleSort(ArrayList<T> arrayListOriginal, BiFunction<T, T, Integer> compare) {
        var arrayList = new ArrayList<>(arrayListOriginal);
        for (int j = 0; j < arrayList.size() - 1; j++) {
            T tmp = null;
            for (int i = 0; i < arrayList.size() - 1 - j; i++) {
                if (Thread.interrupted()) {
                    return null;
                }
                if (compare.apply(arrayList.get(i), arrayList.get(i + 1)) > 0) {
                    tmp = arrayList.get(i);
                    arrayList.set(i, arrayList.get(i + 1));
                    arrayList.set(i + 1, tmp);
                }
            }
            if (tmp == null) {
                return arrayList;
            }
        }
        return arrayList;
    }

    private static int jumpSearch(ArrayList<Pair<String, String>> directory, ArrayList<String> findings) {
        var jumpLength = (int) Math.sqrt(directory.size());
        int found = 0;
        for (var toFind : findings) {
            int leftPos = -1;
            int rightPos = 0;
            while (rightPos < directory.size() - 1) {
                leftPos++;
                rightPos = Math.min(leftPos + jumpLength, directory.size() - 1);
                if (directory.get(rightPos).second.compareTo(toFind) >= 0) {
                    if (backwardSearch(directory.subList(leftPos, rightPos), toFind)) {
                        found++;
                        break;
                    }
                }
                leftPos = rightPos;
            }
        }
        return found;
    }

    private static boolean backwardSearch(List<Pair<String, String>> directory, String toFind) {
        for (int i = directory.size() - 1; i >= 0; i--) {
            if (directory.get(i).second.compareTo(toFind) == 0) {
                return true;
            }
            if (directory.get(i).second.compareTo(toFind) < 0) {
                System.out.println("Can't find string" + toFind);
                return false;
            }
        }
        return false;
    }
}
