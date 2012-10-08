package utilidade;

import java.util.List;

import questao01.pph.MedianaPair;
import questao01.pph.OrderedPair;

public class SelectionSort {
  public OrderedPair select(List<OrderedPair> list, int left, int right, int k) {
    if (left == right) // If the list contains only one element
      return list.get(left); // Return that element
    // select pivotIndex between left and right
    int median = medianOfMedians(list, left, right);
    int pivotNewIndex = partition(list, left, right, median);
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

  public MedianaPair selectIterativo(List<OrderedPair> list, int left, int right, int k) {
    // select pivotIndex between left and right
    //long startTime = System.currentTimeMillis();
    //System.out.println("selectIterativo - Início: ");
    while (left != right) {
      int median = medianOfMedians(list, left, right);
      int pivotNewIndex = partition(list, left, right, median);
      int pivotDist = pivotNewIndex - left + 1;
      // The pivot is in its final sorted position,
      // so pivotDist reflects its 1-based position if list were sorted
      if (pivotDist == k)
        //return list.get(pivotNewIndex);
        return new MedianaPair(list.get(pivotNewIndex), pivotNewIndex);
      else if (k < pivotDist)
        right = pivotNewIndex - 1;
      else {
        k = k - pivotDist;
        left = pivotNewIndex + 1;
      }
    }
    //long endTime = System.currentTimeMillis();
    //System.out.println("Fim selectIterativo: " + (endTime - startTime));
    //return list.get(left);
    return new MedianaPair(list.get(left), left);
  }

  private int partition(List<OrderedPair> list, int left, int right, int pivotIndex) {
    //long startTime = System.currentTimeMillis();
    // System.out.println("partition Pivot inteiro - Início: ");
    OrderedPair swp;
    OrderedPair x = list.get(pivotIndex);
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

    //long endTime = System.currentTimeMillis();
    // System.out.println("Fim Partition: " + (endTime - startTime));
    return i + 1;
  }

  public MedianaPair partition(List<OrderedPair> list, int left, int right, OrderedPair orderedPairpivot) {
    //long startTime = System.currentTimeMillis();
    //System.out.println("partition Pivot OrderedPair - Início: " + startTime);
    OrderedPair swp;
    int i = left - 1;
    for (int j = left; j < right - 1; j++) {
      if (list.get(j).compareTo(orderedPairpivot) < 1) {
        i += 1;
        swp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, swp);
      }
    }
    swp = list.get(i + 1);
    list.set(i + 1, list.get(right - 1));
    list.set(right - 1, swp);
    //    for (int j = 0; j < i + 1; j++) {
    //      if (list.get(j).compareTo(orderedPairpivot) > 0) {
    //        System.out.println("ops...");
    //      }
    //    }
    //long endTime = System.currentTimeMillis();
    //System.out.println("Fim Partition OrderedPair: " + (endTime - startTime));
    return new MedianaPair(swp, i + 1);
  }

  // returns the index of the median of medians.
  // requires a variant of select, "selectIdx" which returns the index of the
  // selected item rather than the value
  private int medianOfMedians(List<OrderedPair> list, int left, int right) {
    //long startTime = System.currentTimeMillis();
    //System.out.println("medianOfMediansr - Início: ");
    int numMedians = (right - left) / 5;
    int swap;
    for (int i = 0; i < numMedians; i++) {
      // get the median of the five-element subgroup
      int subLeft = left + i * 5;
      int subRight = subLeft + 5;
      //System.out.println("medianOfMedians - selectIdx - Inicio " + left);
      //System.out.println("medianOfMedians - selectIdx - Fim: " + right);
      int medianIdx = selectIdx(list, subLeft, subRight, 2);
      // alternatively, use a faster method that works on lists of size 5
      // move the median to a contiguous block at the beginning of the
      // list
      swap = i;
      i = medianIdx;
      medianIdx = swap;
    }
    // select the median from the contiguous block
    //long endTime = System.currentTimeMillis();
    //System.out.println("medianOfMedians - selectIdx Retorno - Inicio " + left);
    //System.out.println("medianOfMedians - selectIdx Retorno - Fim: " + right);
    return selectIdx(list, left, left + numMedians, numMedians / 2);
  }

  private int selectIdx(List<OrderedPair> list, int left, int right, int pivot) {
    //long startTime = System.currentTimeMillis();
    //System.out.println("selectIdx - Início: ");
    if (right - left < 6)
      InsertionSort(list, left, right);
    // else {
    //  MergeSort merge = new MergeSort();
    //   merge.sort(list);
    //}
    int idx;
    if ((left + right) % 2 == 0)
      idx = (left + right + 1) / 2;
    else {
      idx = (((left + right + 1) / 2) + ((left + right + 1) / 2) + 1) / 2;
    }
    //long endTime = System.currentTimeMillis();
    // System.out.println("Fim selectIdx: " + (endTime - startTime));
    return idx;
  }

  private void InsertionSort(List<OrderedPair> list, int left, int right) {
    //long startTime = System.currentTimeMillis();
    // System.out.println("InsertionSort - Início: ");
    //System.out.println("InsertionSort - left: " + left);
    //System.out.println("InsertionSort - right: " + right);
    //    if (right - left != 5) {
    //      System.out.println("InsertionSort - Intervalo Errado!!!!!!!!!!!!!!!: " + (right - left));
    //    }
    //List<OrderedPair> subList = list.subList(left, right);
    //int cont = left + 1;
    //for (int j = left + 1; j < right; j++) {
    for (int j = 0; j < list.size(); j++) {
      //OrderedPair orderedPairkey = list.get(j);
      OrderedPair orderedPairkey = list.get(j);
      int i = j - 1;
      //int i = cont - 1;
      //System.out.println("I: " + i);
      //System.out.println("Count: " + cont);
      while (i >= 0 && list.get(j).compareTo(orderedPairkey) > 0) {
        list.set(i + 1, list.get(i));
        i = i - 1;
      }

      list.set(i + 1, orderedPairkey);
      //cont++;
    }
    //long endTime = System.currentTimeMillis();
    //System.out.println("Fim InsertionSort: " + (endTime - startTime));
  }
}
