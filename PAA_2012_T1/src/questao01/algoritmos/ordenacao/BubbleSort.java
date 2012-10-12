package questao01.algoritmos.ordenacao;

/**
 * This class sorts an array using the Bubble Sort algorithm.
 * 
 * @author Luciano Sampaio
 * 
 */
public class BubbleSort extends Sorter implements ISortable {

  /**
   * @param arValues The array that will be sorted.
   */
  @Override
  public <T extends Comparable<T>> void sortAscending(T[] arValues) {
    // It tells if the array is ordered.
    boolean isOrdered = false;

    /*
     * Because we are checking if value of the position i is bigger than the value of the position i + 1, so we can't get to the last position of the array,
     * that's why we do (arValues.length - 1).
     */
    int lastPosition = (arValues.length - 1);

    // It stays in the loop until every value is in the right place.
    while (!isOrdered) {
      isOrdered = true;

      for (int i = 0; i < lastPosition; i++) {
        // If the value of the position i is bigger than the position i + 1, so
        // the values have to be changed.
        if (arValues[i].compareTo(arValues[i + 1]) > 0) {
          swap(arValues, i, i + 1);
          isOrdered = false;
        }
      }
    }
  }

}