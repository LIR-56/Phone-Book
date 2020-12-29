package phonebook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import static phonebook.Main.timeInMsToString;

public class QuickSortAndBinarySearch {

    public static void quickSortAndBinarySearch(ArrayList<Pair<String, String>> originalDirectory,
                                                ArrayList<String> findings) {
        var directory = new ArrayList<>(originalDirectory);
        System.out.println("Start searching (quick sort + binary search)...");
        var startTime = System.currentTimeMillis();
        quickSort(directory, 0, directory.size() - 1);
        var sortEndTime = System.currentTimeMillis();

        var startSearchingTime = System.currentTimeMillis();
        var foundAmount = binarySearch(directory, findings);
        var endTime = System.currentTimeMillis();

        var resultTime = endTime - startTime;
        System.out.printf("Found %d / %d entries. Time taken: %s\n",
                foundAmount, findings.size(), timeInMsToString(resultTime));
        System.out.printf("Sorting time: %s\n", timeInMsToString(sortEndTime - startTime));
        System.out.printf("Searching time: %s\n", timeInMsToString(endTime - startSearchingTime));
    }

    private static void quickSort(ArrayList<Pair<String, String>> directory, int left, int right) {
        if (left < right) {
            int pivotIndex = partition(directory, left, right); // the pivot is already on its place
            quickSort(directory, left, pivotIndex - 1);  // sort the left subarray
            quickSort(directory, pivotIndex + 1, right); // sort the right subarray
        }

    }

    private static int partition(ArrayList<Pair<String, String>> array, int left, int right) {
        var pivot = array.get(right);  // choose the rightmost element as the pivot
        int partitionIndex = left; // the first element greater than the pivot

        /* move large values into the right side of the array */
        for (int i = left; i < right; i++) {
            if (array.get(i).second.compareTo(pivot.second) <= 0) { // may be used '<' as well
                swap(array, i, partitionIndex);
                partitionIndex++;
            }
        }

        swap(array, partitionIndex, right); // put the pivot on a suitable position

        return partitionIndex;
    }

    private static void swap(ArrayList<Pair<String, String>> array, int i, int j) {
        var temp = array.get(i);
        array.set(i, array.get(j));
        array.set(j, temp);
    }

    private static int binarySearch(ArrayList<Pair<String, String>> directory, ArrayList<String> findings) {
        return massiveBinarySearch(directory, findings, 0, directory.size() - 1);
    }

    private static int massiveBinarySearch(ArrayList<Pair<String, String>> array,
                                           Collection<String> stringsToFind, int left,
                                           int right) {
        if (left > right) {
            return 0;
        }

        var result = 0;
        int mid = left + (right - left) / 2;


        result += stringsToFind.stream()
                .filter(x -> x.equals(array.get(mid).second))
                .count();

        var leftPartToFind = stringsToFind.stream()
                .filter(x -> x.compareTo(array.get(mid).second) < 0)
                .collect(Collectors.toList());
        if (!leftPartToFind.isEmpty()) {
            result += (massiveBinarySearch(array, leftPartToFind, left, mid - 1));
        }

        var rightPartToFind = stringsToFind.stream()
                .filter(x -> x.compareTo(array.get(mid).second) > 0)
                .collect(Collectors.toList());
        if (!rightPartToFind.isEmpty()) {
            result += (massiveBinarySearch(array, rightPartToFind, mid + 1, right));
        }

        return result;
    }

}
