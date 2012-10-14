package pph.sorting;

import java.util.Arrays;
import java.util.List;

public class QuickSort extends Sorter {

  @Override
  public <T extends Comparable<T>> void sortAscending(List<T> list) {
    // Desta forma ficou genérico.
    @SuppressWarnings("unchecked")
    T[] arValues = (T[]) java.lang.reflect.Array.newInstance(list.get(0).getClass(), list.size());
    for (int i = 0; i < list.size(); i++) {
      arValues[i] = list.get(i);
    }

    sortAscending(arValues);

    list.clear();
    list.addAll(Arrays.asList(arValues));
  }

  /**
   * @param arValues The array that will be sorted.
   */
  @Override
  public <T extends Comparable<T>> void sortAscending(T[] arValues) {
    quickSort(arValues, 0, arValues.length - 1);
  }

  /**
   * @param <T>
   * @param arValues
   * @param low
   * @param high
   */
  private <T extends Comparable<T>> void quickSort(T[] arValues, int low, int high) {
    int i = low, j = high;
    // Get the pivot element from the middle of the list
    T pivot = arValues[low + (high - low) / 2];

    // Divide into two lists
    while (i <= j) {
      incOperations();
      // If the current value from the left list is smaller then the pivot
      // element then get the next element from the left list
      while (arValues[i].compareTo(pivot) > 0) {
        incOperations();
        i++;
      }
      // If the current value from the right list is larger then the pivot
      // element then get the next element from the right list
      while (arValues[j].compareTo(pivot) < 0) {
        incOperations();
        j--;
      }

      // If we have found a values in the left list which is larger then
      // the pivot element and if we have found a value in the right list
      // which is smaller then the pivot element then we exchange the
      // values.
      // As we are done we can increase i and j
      if (i <= j) {
        swap(arValues, i, j);
        i++;
        j--;
      }
    }
    // Recursion
    if (low < j)
      quickSort(arValues, low, j);
    if (i < high)
      quickSort(arValues, i, high);
  }

  @Override
  public <T extends Comparable<T>> void sortDescending(T[] arValues) {
  }

  @Override
  public <T extends Comparable<T>> void sortDescending(List<T> list) {
  }

}