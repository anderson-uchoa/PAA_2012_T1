package utilidade;

import java.util.List;

import questao01.pph.MedianaPair;
import questao01.pph.OrderedPair;

public class SelectionSort {
  public MedianaPair select(List<OrderedPair> list, int left, int right, int k) {
    if (left == right) // If the list contains only one element
      return new MedianaPair(list.get(left), left);//list.get(left); // Return that element
    // select pivotIndex between left and right
    int median = medianOfMedians(list, left, right);
    int pivotNewIndex = partition(list, left, right, median);
    int pivotDist = pivotNewIndex - left + 1;
    // The pivot is in its final sorted position,
    // so pivotDist reflects its 1-based position if list were sorted
    if (pivotDist == k)
      return new MedianaPair(list.get(left), left);//list.get(pivotNewIndex);
    else if (k < pivotDist)
      return select(list, left, pivotNewIndex - 1, k);
    else
      return select(list, pivotNewIndex + 1, right, k - pivotDist);
  }

  public MedianaPair selectIterativo(List<OrderedPair> list, int left, int right, int k) {
    // select pivotIndex between left and right
    while (left != right) {
      int median = medianOfMedians(list, left, right);
      int pivotNewIndex = Testepartition(list, left, right, median);
      int pivotDist = pivotNewIndex - left + 1;
      // The pivot is in its final sorted position,
      // so pivotDist reflects its 1-based position if list were sorted
      if (pivotDist == k)
        return new MedianaPair(list.get(pivotNewIndex), pivotNewIndex);
      else if (k < pivotDist)
        right = pivotNewIndex - 1;
      else {
        k = k - pivotDist;
        left = pivotNewIndex + 1;
      }
    }
    return new MedianaPair(list.get(left), left);
  }

  int Testepartition(List<OrderedPair> list, int left, int right, int pivotIndex) {
    int i = left, j = right - 1;
    OrderedPair tmp;
    //int pivot = arr[(left + right) / 2];
    OrderedPair pivot = list.get(pivotIndex);
    while (i <= j) {
      while (list.get(i).compareTo(pivot) < 0)
        i++;
      while (list.get(j).compareTo(pivot) > 1)
        j--;
      if (i <= j) {
        tmp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, tmp);
        i++;
        j--;
      }
    }
    ;

    //    OrderedPair x = list.get(i);
    //    for (int k = 1; k <= i + 1; k++) {
    //      if (list.get(k - 1).compareTo(x) > 0) {
    //        System.out.println("há um par maior que o pivot à esquerda....");
    //      }
    //    }
    //
    //    //Testando se há um par maior que o pivot a direita
    //    for (int k = i + 1; k < list.size(); k++) {
    //      OrderedPair p = list.get(j);
    //      if (p.compareTo(x) < 0) {
    //        System.out.println("há um par maior que o pivot a direita....");
    //      }
    //    }

    return i;
  }

  public int partition(List<OrderedPair> list, int left, int right, int pivotIndex) {
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

    //swap list[pivotIndex] and list[right]  // Move pivot to end
    // Versao Wikipedia - Não funciona
    //    swp = list.get(pivotIndex);
    //    list.set(pivotIndex, list.get(right - 1));
    //    list.set(right - 1, swp);
    //
    //    int storeIndex = left;
    //    for (int i = left; i <= right - 1; i++) {
    //      if (list.get(i).compareTo(x) < 1) {
    //        //swap list[storeIndex] and list[i]
    //        swp = list.get(storeIndex);
    //        list.set(pivotIndex, list.get(i));
    //        list.set(i, swp);
    //        storeIndex++;
    //      }
    //    }
    //    //swap list[right] and list[storeIndex]  // Move pivot to its final place
    //    swp = list.get(right - 1);
    //    list.set(right - 1, list.get(storeIndex));
    //    list.set(storeIndex, swp);
    //return storeIndex;

    // Testando se há um par maior que o pivot à esquerda
    x = list.get(i + 1);
    for (int j = 1; j <= i + 1; j++) {
      if (list.get(j - 1).compareTo(x) > 0) {
        System.out.println("há um par maior que o pivot à esquerda....");
      }
    }

    //Testando se há um par maior que o pivot a direita
    for (int j = i + 1; j < list.size(); j++) {
      OrderedPair p = list.get(j);
      if (p.compareTo(x) < 0) {
        System.out.println("há um par maior que o pivot a direita....");
      }
    }

    return i + 1;
  }

  //  public MedianaPair partition(List<OrderedPair> list, int left, int right, OrderedPair orderedPairpivot) {
  //    OrderedPair swp;
  //    int i = left - 1;
  //    for (int j = left; j < right - 1; j++) {
  //      if (list.get(j).compareTo(orderedPairpivot) < 1) {
  //        i += 1;
  //        swp = list.get(i);
  //        list.set(i, list.get(j));
  //        list.set(j, swp);
  //      }
  //    }
  //    swp = list.get(i + 1);
  //    list.set(i + 1, list.get(right - 1));
  //    list.set(right - 1, swp);
  //    return new MedianaPair(swp, i + 1);
  //  }

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
    if (right - left < 6)
      InsertionSort(list, left, right);
    //    else {
    //      MergeSort merge = new MergeSort();
    //      merge.sort(list);
    //    }
    int idx;
    if ((left + right) % 2 == 0)
      idx = (left + right + 1) / 2;
    else {
      idx = (((left + right + 1) / 2) + ((left + right + 1) / 2) + 1) / 2;
    }
    return idx;
  }

  public void InsertionSort(List<OrderedPair> list, int left, int right) {
    for (int j = left + 1; j < list.size(); j++) {
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
