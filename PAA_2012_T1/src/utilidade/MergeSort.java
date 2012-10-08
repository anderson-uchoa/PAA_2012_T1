package utilidade;

import java.util.LinkedList;
import java.util.List;

public class MergeSort {
  int iterations = 0;

  public <T extends Comparable<T>> void sort(List<T> list) {
    LinkedList<T> helper = new LinkedList<T>();
    mergeSort(0, list.size() - 1, list, helper);
    //Log.printOntoScreen("Número de passos: " + this.iterations);
  }

  private <T extends Comparable<T>> void mergeSort(int low, int high, List<T> list, List<T> helper) {
    //  Check if low is smaller then high, if not then the array is sorted
    if (low < high) {
      // Get the index of the element which is in the middle
      int middle = (low + high) / 2;
      // Sort the left side of the array
      mergeSort(low, middle, list, helper);
      // Sort the right side of the array
      mergeSort(middle + 1, high, list, helper);
      // Combine them both
      merge(low, middle, high, list, helper);
    }
  }

  private <T extends Comparable<T>> void merge(int low, int middle, int high, List<T> list, List<T> helper) {

    // Copy both parts into the helper array
    helper = new LinkedList<T>();
    helper.addAll(list);

    int i = low;
    int j = middle + 1;
    int k = low;
    // Copy the smallest values from either the left or the right side back
    // to the original array
    while (i <= middle && j <= high) {
      this.iterations++;
      if (helper.get(i).compareTo(helper.get(j)) <= 0) {
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
