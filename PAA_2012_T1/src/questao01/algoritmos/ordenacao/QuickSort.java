package questao01.algoritmos.ordenacao;

import java.util.List;

/**
 * This class sorts an array using the Quick Sort algorithm. <br/>
 * http://www.algolist.net/Algorithms/Sorting/Quicksort
 * 
 * @author Luciano Sampaio
 * 
 */
public class QuickSort extends Sorter implements ISortable {

  @Override
  public <T extends Comparable<T>> void sortAscending(List<T> list) {
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
   * @param left
   * @param right
   */
  private <T extends Comparable<T>> void quickSort(T[] arValues, int left, int right) {
    // It can only continue if the left and right haven't crossed them yet.
    if (left >= right) {
      return;
    }

    int pivotPosition = partition(arValues, left, right);
    quickSort(arValues, left, pivotPosition - 1);
    quickSort(arValues, pivotPosition + 1, right);
  }

  /**
   * @param <T>
   * @param arValues
   * @param left
   * @param right
   * @return The index of the pivot.
   */
  private <T extends Comparable<T>> int partition(T[] arValues, int left, int right) {
    int indexPivot = (left + right) / 2;
    T pivot = arValues[indexPivot];

    //
    swap(arValues, indexPivot, right);
    for (int i = indexPivot = left; i < right; i++) {
      if (arValues[i].compareTo(pivot) <= 0) {
        swap(arValues, indexPivot++, i);
      }
    }

    swap(arValues, indexPivot, right);
    return indexPivot;
  }

}