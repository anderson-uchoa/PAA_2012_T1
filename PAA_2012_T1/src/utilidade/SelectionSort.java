package utilidade;

import java.util.List;

import questao01.algoritmos.ordenacao.ISortable;
import questao01.pph.MedianaPair;

public class SelectionSort implements ISortable {

  @Override
  public <T extends Comparable<T>> void sortAscending(T[] arValues) {
  }

  @Override
  public <T extends Comparable<T>> void sortAscending(List<T> list) {
    selectIterativo(list, 0, list.size(), list.size() / 2);
  }

  public <T extends Comparable<T>> MedianaPair<T> select(List<T> list, int left, int right, int k) {
    if (left == right) // If the list contains only one element
      return new MedianaPair<T>(list.get(left), left);//list.get(left); // Return that element
    // select pivotIndex between left and right
    int median = medianOfMedians(list, left, right);
    int pivotNewIndex = partition(list, left, right, median);
    int pivotDist = pivotNewIndex - left + 1;
    // The pivot is in its final sorted position,
    // so pivotDist reflects its 1-based position if list were sorted
    if (pivotDist == k)
      return new MedianaPair<T>(list.get(left), left);//list.get(pivotNewIndex);
    else if (k < pivotDist)
      return select(list, left, pivotNewIndex - 1, k);
    else
      return select(list, pivotNewIndex + 1, right, k - pivotDist);
  }

  public <T extends Comparable<T>> MedianaPair<T> selectIterativo(List<T> list, int left, int right, int k) {
    // select pivotIndex between left and right
    while (left != right) {
      int median = medianOfMedians(list, left, right);
      int pivotNewIndex = partition(list, left, right, median);
      int pivotDist = pivotNewIndex - left + 1;
      // The pivot is in its final sorted position,
      // so pivotDist reflects its 1-based position if list were sorted
      if (pivotDist == k)
        return new MedianaPair<T>(list.get(pivotNewIndex), pivotNewIndex);
      else if (k < pivotDist)
        right = pivotNewIndex - 1;
      else {
        k = k - pivotDist;
        left = pivotNewIndex + 1;
      }
    }
    return new MedianaPair<T>(list.get(left), left);
  }

  private <T extends Comparable<T>> int partition(List<T> list, int left, int right, int pivotIndex) {
    int i = left, j = right - 1;
    T tmp;
    T pivot = list.get(pivotIndex);
    while (i < j) {
      while (list.get(i).compareTo(pivot) < 0 && i < j)
        i++;
      while (list.get(j).compareTo(pivot) > 1 && i < j)
        j--;

      //if (i <= j) {
      tmp = list.get(i);
      list.set(i, list.get(j));
      list.set(j, tmp);
      i++;
      j--;
    }
    ;
    return i;
  }

  // returns the index of the median of medians.
  // requires a variant of select, "selectIdx" which returns the index of the
  // selected item rather than the value
  private <T extends Comparable<T>> int medianOfMedians(List<T> list, int left, int right) {
    int numMedians = (right - left) / 5;
    int swap;
    for (int i = 0; i < numMedians; i++) {
      // get the median of the five-element subgroup
      int subLeft = left + i * 5;
      int subRight = subLeft + 5;
      int medianIdx = selectIdx(list, subLeft, subRight, 2);
      // alternatively, use a faster method that works on lists of size 5
      // move the median to a contiguous block at the beginning of the
      // list
      swap = i;
      i = medianIdx;
      medianIdx = swap;
    }
    // select the median from the contiguous block
    return selectIdx(list, left, left + numMedians, numMedians / 2);
  }

  private <T extends Comparable<T>> int selectIdx(List<T> list, int left, int right, int pivot) {
    if (right - left < 6)
      InsertionSort(list, left, right);

    int idx;
    if ((left + right) % 2 == 0)
      idx = (left + right + 1) / 2;
    else {
      idx = (((left + right + 1) / 2) + ((left + right + 1) / 2) + 1) / 2;
    }
    return idx;
  }

  public <T extends Comparable<T>> void InsertionSort(List<T> list, int left, int right) {
    for (int j = left + 1; j < list.size(); j++) {
      T orderedPairkey = list.get(j);
      int i = j - 1;
      while (i >= 0 && list.get(i).compareTo(orderedPairkey) > 0) {
        list.set(i + 1, list.get(i));
        i = i - 1;
      }
      list.set(i + 1, orderedPairkey);
    }
  }
}
