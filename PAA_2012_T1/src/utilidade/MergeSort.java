package utilidade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import questao01.pph.ordenacao.Sorter;

public class MergeSort extends Sorter {
  int iterations = 0;

  public <T extends Comparable<T>> void sort(List<T> list) {
    sortAscending(list);
  }

  @Override
  public <T extends Comparable<T>> void sortAscending(T[] arValues) {
    sortAscending(Arrays.asList(arValues));
  }

  @Override
  public <T extends Comparable<T>> void sortAscending(List<T> list) {
    mergeSort(0, list.size() - 1, list);
  }

  private <T extends Comparable<T>> void mergeSort(int low, int high, List<T> list) {
    //  Check if low is smaller then high, if not then the array is sorted
    if (low < high) {
      // Get the index of the element which is in the middle
      int middle = (low + high) / 2;
      // Sort the left side of the array
      mergeSort(low, middle, list);
      // Sort the right side of the array
      mergeSort(middle + 1, high, list);
      // Combine them both
      merge(low, middle, high, list);
    }
  }

  private <T extends Comparable<T>> void merge(int low, int middle, int high, List<T> list) {
    // Copy both parts into the helper array
    List<T> helper = new ArrayList<T>(list.size());
    helper.addAll(list);

    int i = low;
    int j = middle + 1;
    int k = low;
    // Copy the smallest values from either the left or the right side back
    // to the original array
    while (i <= middle && j <= high) {
      this.iterations++;
      if (helper.get(i).compareTo(helper.get(j)) > 0) {
        list.set(k, helper.get(i));
        i++;
      }
      else {
        list.set(k, helper.get(j));
        j++;
      }
      k++;
    }
    // Copy the rest of the left side of the array into the target array
    while (i <= middle) {
      list.set(k, helper.get(i));
      k++;
      i++;
    }
  }
}
