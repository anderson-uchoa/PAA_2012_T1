package utilidade;

import java.util.LinkedList;
import java.util.List;

import questao01.pph.MedianaPair;
import questao01.pph.OrderedPair;

public class SelectionSort {
  public OrderedPair select(List<OrderedPair> list, int left, int right, int k) {
    if (left == right) // If the list contains only one element
      return list.get(left); // Return that element
    // select pivotIndex between left and right
    // int pivotNewIndex = partition(list, left, right, pivotIndex);
    int median = medianOfMedians(list, left, right);
    int pivotNewIndex = partition2(list, left, right, median);
    int pivotDist = pivotNewIndex - left + 1;
    // The pivot is in its final sorted position,
    // so pivotDist reflects its 1-based position if list were sorted
    if (pivotDist == k)
      return list.get(pivotNewIndex);
    else if (k < pivotDist)
      return select(list, left, pivotNewIndex - 1, k);
    else
      return select(list, pivotNewIndex + 1, right, k - pivotDist);
  }

  private int partition(List<OrderedPair> list, int left, int right, int pivotIndex) {
    //float pivotValue = list.get(pivotIndex).getRatio();
    OrderedPair swp, orderedPairpivot;
    orderedPairpivot = list.get(pivotIndex);
    // swap list[pivotIndex] and list[right] // Move pivot to end
    int storeIndex = left;
    for (int i = left; i < right - 1; i++) {
      if (list.get(i).getRatio() <= orderedPairpivot.getRatio()) {
        // Swapping
        swp = list.get(storeIndex);
        list.set(storeIndex, list.get(i));
        list.set(i, swp);
        storeIndex++;
      }
      // Move pivot to its final place
      swp = list.get(right - 1);
      list.set(right - 1, list.get(storeIndex));
      list.set(storeIndex, swp);
    }
    return storeIndex;
  }

  private int partition2(List<OrderedPair> list, int left, int right, int pivotIndex) {
    OrderedPair swp;
    OrderedPair x = list.get(right - 1);
    int i = left - 1;
    for (int j = left; j < right - 1; j++) {
      if (list.get(j).compareTo(x) < 1) {
        i += 1;
        swp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, swp);
      }
    }
    swp = list.get(i + 1);
    list.set(i + 1, list.get(right - 1));
    list.set(right - 1, swp);
    return i + 1;
  }

  public MedianaPair partition(List<OrderedPair> list, int left, int right, OrderedPair orderedPairpivot) {
    OrderedPair swp = null;
    // swap list[pivotIndex] and list[right] // Move pivot to end
    int storeIndex = left;
    for (int i = left; i < right - 1; i++) {
      if (list.get(i).getRatio() <= orderedPairpivot.getRatio()) {
        // Swapping
        swp = list.get(storeIndex);
        list.set(storeIndex, list.get(i));
        list.set(i, swp);
        storeIndex++;
      }
      // Move pivot to its final place
      swp = list.get(right - 1);
      list.set(right - 1, list.get(storeIndex));
      list.set(storeIndex, swp);
    }
    return new MedianaPair(swp, storeIndex);
  }

  // returns the index of the median of medians.
  // requires a variant of select, "selectIdx" which returns the index of the
  // selected item rather than the value
  private int medianOfMedians(List<OrderedPair> list, int left, int right) {
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

  private int selectIdx(List<OrderedPair> list, int left, int right, int pivot) {
    // Ordenando
    List<OrderedPair> nova = new LinkedList<OrderedPair>();
    //    for (int i = left; i < right; i++) {
    //      nova.add(list.get(i));
    //    }
    InsertionSort(list, left, right);
    int idx, size;
    size = nova.size();
    //if (size > 1) {
    //if (size % 2 == 0)
    if ((left + right) % 2 == 0)
      idx = (left + right + 1) / 2;
    else {
      idx = (((left + right + 1) / 2) + ((left + right + 1) / 2) + 1) / 2;
    }
    //return list.indexOf(nova.get(idx));
    // return idx;
    //}
    //return 0;
    return idx;
  }

  private void InsertionSort(List<OrderedPair> list, int left, int right) {
    //for (int j = 1; j < list.size(); j++) {
    for (int j = left + 1; j < right; j++) {
      OrderedPair orderedPairkey = list.get(j);
      int i = j - 1;
      while (i >= 0 && list.get(i).compareTo(orderedPairkey) > 0) {
        list.set(i + 1, list.get(i));
        i = i - 1;
      }
      list.set(i + 1, orderedPairkey);
    }
  }

}
