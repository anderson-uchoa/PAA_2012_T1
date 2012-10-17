package utilidade;

import java.util.List;

import questao01.pph.MedianaPair;
import questao01.pph.ordenacao.Sorter;

public class MediamOfMediams extends Sorter {

  @Override
  public <T extends Comparable<T>> void sortAscending(T[] arValues) {
  }

  @Override
  public <T extends Comparable<T>> void sortAscending(List<T> list) {
    //selectIterativo(list, 0, list.size() - 1, list.size() / 2);
    int idx = medianOfMedians(list, 0, list.size() - 1);
    select(list, 0, list.size() - 1, idx);
  }

  public <T extends Comparable<T>> MedianaPair<T> select(List<T> list, int left, int right, int k) {
    if (left == right) // If the list contains only one element
      return new MedianaPair<T>(list.get(left), left);//list.get(left); // Return that element
    // select pivotIndex between left and right
    //int median = medianOfMedians(list, left, right);
    int pivotNewIndex = partition(list, left, right, k);
    int pivotDist = pivotNewIndex - left + 1;
    // The pivot is in its final sorted position,
    // so pivotDist reflects its 1-based position if list were sorted
    if (pivotDist == k)
      return new MedianaPair<T>(list.get(pivotNewIndex), pivotNewIndex);
    else if (k < pivotDist)
      return select(list, left, pivotNewIndex - 1, k);
    else
      return select(list, pivotNewIndex + 1, right, k - pivotDist);
  }

  public <T extends Comparable<T>> MedianaPair<T> findMediana(List<T> list, int left, int right, int k) {
    //return selectIterativo(list, left, right - 1, k);
    int idx = medianOfMedians(list, 0, list.size() - 1);
    return selectIterativo(list, 0, list.size() - 1, idx);
  }

  public <T extends Comparable<T>> MedianaPair<T> selectIterativo(List<T> list, int left, int right, int k) {
    // select pivotIndex between left and right
    while (left != right) {
      //int median = medianOfMedians(list, left, right);
      int pivotNewIndex = partition(list, left, right, k);
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

  public <T extends Comparable<T>> int partition(List<T> list, int left, int right, int pivotIndex) {
    //    int i = left, j = right; // right - 1;
    //    T tmp;
    //    T pivot = list.get(pivotIndex);
    //    while (i < j) {
    //      while (list.get(i).compareTo(pivot) < 0 && i < j)
    //        i++;
    //      while (list.get(j).compareTo(pivot) > 1 && i < j)
    //        j--;
    //
    //      //if (i <= j) {
    //      tmp = list.get(i);
    //      list.set(i, list.get(j));
    //      list.set(j, tmp);
    //      i++;
    //      j--;
    //    }
    //    ;
    //    return i;

    T temp;
    // escolhendo o pivot
    T pivot = list.get(pivotIndex);
    int i = left - 1;
    for (int j = left; j <= right - 1; j++) {
      // Incrementado as iterações para facilitar a mensuração da complexidade final
      this.incIterations();
      if (list.get(j).compareTo(pivot) <= 0) {// Se menor precisa trocar de posição
        i = i + 1;
        // Swap entre os elementos da posição i e j
        temp = list.get(j);
        list.set(j, list.get(i));
        list.set(i, temp);
      }
    }
    //Swap entre os elementos da posição i+1  e right (Final da lista) 
    temp = list.get(right);
    list.set(right, list.get(i + 1));
    list.set(i + 1, temp);
    // Retorna a posição do pivot. À esquerda os menores e à direita os maiores mas sem uma ordenação em cada lado
    return i + 1;
  }

  // returns the index of the median of medians.
  // requires a variant of select, "selectIdx" which returns the index of the
  // selected item rather than the value
  private <T extends Comparable<T>> int medianOfMedians(List<T> list, int left, int right) {
    int numMedians = (right - left) / 5;
    T temp;
    int subLeft = left;
    int subRight = -1;
    int i;
    for (i = 0; i <= numMedians; i++) {
      // get the median of the five-element subgroup
      subLeft = subRight + 1;
      subRight = subLeft + 4;
      // Ordenando a porção trabalhada
      InsertionSort(list, subLeft, subRight);
      int medianIdx = selectIdx(list, subLeft, subRight, 2);

      // alternatively, use a faster method that works on lists of size 5
      // move the median to a contiguous block at the beginning of the list
      //swap i and medianIdx
      temp = list.get(0);
      list.set(0, list.get(medianIdx));
      list.set(medianIdx, temp);
    }
    // select the median from the contiguous block
    return selectIdx(list, left, left + numMedians, numMedians / 2);
  }

  private <T extends Comparable<T>> int selectIdx(List<T> list, int left, int right, int pivot) {
    while (left != right) {
      //      if (left == right) // If the list contains only one element
      //        return left;// Return that element
      //
      //      // select pivotIndex between left and right
      //      int pivotNewIndex = partition(list, left, right, pivot);
      //      int pivotDist = pivotNewIndex - left + 1;
      //      // The pivot is in its final sorted position,
      //      // so pivotDist reflects its 1-based position if list were sorted
      //      if (pivotDist == pivot)
      //        return pivotNewIndex;
      //      else if (pivot < pivotDist)
      //        return selectIdx(list, left, pivotNewIndex - 1, pivot);
      //      else
      //        return selectIdx(list, pivotNewIndex + 1, right, pivot - pivotDist);
      //    }
      int pivotNewIndex = partition(list, left, right, pivot);
      int pivotDist = pivotNewIndex - left + 1;
      // The pivot is in its final sorted position,
      // so pivotDist reflects its 1-based position if list were sorted
      if (pivotDist == pivot)
        return pivotNewIndex;
      else if (pivot < pivotDist)
        right = pivotNewIndex - 1;
      else {
        pivot = pivot - pivotDist;
        left = pivotNewIndex + 1;
      }
    }
    return left;
  }

  public <T extends Comparable<T>> void InsertionSort(List<T> list, int left, int right) {
    for (int j = left + 1; j < right; j++) {
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
