package questao01.algoritmos.ordenacao;

/**
 * This class sorts an array using the Selection Sort algorithm.
 * 
 * @author Luciano Sampaio
 * 
 */
public class SelectionSort extends Sorter implements ISortable {

  /**
   * @param arValues The array that will be sorted.
   */
  @Override
  public <T extends Comparable<T>> void sortAscending(T[] arValues) {
    int positionSmallestValue;

    for (int i = 0; i < arValues.length; i++) {
      // It gets the value of the array in the i position.
      positionSmallestValue = i;

      for (int j = (i + 1); j < arValues.length; j++) {
        if ((arValues[positionSmallestValue].compareTo(arValues[j])) > 0) {
          // It gets the new smallest value.
          positionSmallestValue = j;
        }
      }
      // Put the smallest value at the position i.
      swap(arValues, i, positionSmallestValue);
    }
  }

}