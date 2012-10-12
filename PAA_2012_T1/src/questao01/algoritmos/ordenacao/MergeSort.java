package questao01.algoritmos.ordenacao;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

/**
 * This class sorts an array using the Merge Sort algorithm.
 * 
 * @author Luciano Sampaio
 * 
 */
public class MergeSort extends Sorter implements ISortable {

  @Override
  public <T extends Comparable<T>> void sortAscending(List<T> list) {
    //    @SuppressWarnings("unchecked")
    //    T[] arValues = list.toArray((T[]) new OrderedPair[list.size()]);

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
    mergeSort(arValues, 0, arValues.length - 1);
  }

  /**
   * Split the array until it gets to one value.
   * 
   * @param <T>
   * @param arValues
   * @param left
   * @param right
   */
  private <T extends Comparable<T>> void mergeSort(T[] arValues, int left, int right) {

    if (left < right) {
      // Get the middle of the array.
      int middle = (left + right) / 2;

      // Divide.
      mergeSort(arValues, left, middle);
      mergeSort(arValues, middle + 1, right);

      // Conquer.
      merge(arValues, left, middle, right);
    }
  }

  /**
   * Sort and Merge the elements of the array.
   * 
   * @param <T>
   * @param arValues
   * @param left
   * @param middle
   * @param right
   */
  private <T extends Comparable<T>> void merge(T[] arValues, int left, int middle, int right) {
    //
    int nrTimes = (right - left) + 1;
    //
    int leftMoves = left;
    T leftValue = null;
    //
    int middleMoves = middle + 1;
    T middleValue = null;

    Vector<T> arTemp = new Vector<T>(nrTimes);

    for (int i = 0; i < nrTimes; i++) {

      if (leftMoves <= middle) {
        leftValue = arValues[leftMoves];
      }

      if (middleMoves <= right) {
        middleValue = arValues[middleMoves];
      }

      if ((leftValue != null) && (middleValue == null)) {
        arTemp.add(i, leftValue);
        leftMoves++;
        leftValue = null;
      }
      else if ((leftValue == null) && (middleValue != null)) {
        arTemp.add(i, middleValue);
        middleMoves++;
        middleValue = null;
      }
      else if (leftValue.compareTo(middleValue) > 0) {
        arTemp.add(i, leftValue);
        leftMoves++;
        leftValue = null;
      }
      else {
        arTemp.add(i, middleValue);
        middleMoves++;
        middleValue = null;
      }

    }

    // Transfer the sorted elements to the original array.
    for (int i = 0; i < nrTimes; i++) {
      arValues[left + i] = arTemp.get(i);
    }
  }

}
