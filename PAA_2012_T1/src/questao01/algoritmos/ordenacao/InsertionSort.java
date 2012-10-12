package questao01.algoritmos.ordenacao;

/**
 * This class sorts an array using the Insertion Sort algorithm.
 * 
 * @author Luciano Sampaio
 * 
 */
public class InsertionSort extends Sorter implements ISortable {

  /**
   * @param arValues The array that will be sorted.
   */
  @Override
  public <T extends Comparable<T>> void sortAscending(T[] arValues) {
    T currentValue;
    int currentPosition;

    for (int i = 1; i < arValues.length; i++) {
      // It gets the value of the array in the i position.
      currentValue = arValues[i];
      currentPosition = i;

      // While currentPosition > 0 we still have numbers to compare.
      // and while currentValue smaller than the prior position we still need to
      // swap them.
      while ((currentPosition > 0) && (currentValue.compareTo(arValues[currentPosition - 1]) < 0)) {
        arValues[currentPosition] = arValues[currentPosition - 1];

        // It decreases the value to compare with another(prior) one.
        currentPosition--;
      }
      // The currentValue goes to the right position.
      arValues[currentPosition] = currentValue;
    }
  }

}