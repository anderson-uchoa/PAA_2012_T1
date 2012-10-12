package questao01.algoritmos.ordenacao;

import java.util.List;

/**
 * This class sorts an array using the Bucket Sort algorithm.
 * 
 * @author Luciano Sampaio
 * 
 */
public class BucketSort extends Sorter implements ISortable {

  @Override
  public <T extends Comparable<T>> void sortAscending(List<T> list) {
  }

  /**
   * @param arValues The array that will be sorted.
   */
  @Override
  public <T extends Comparable<T>> void sortAscending(T[] arValues) {
    if (arValues instanceof Integer[]) {
      sortAscending((Integer[]) arValues);
    }
    else {
      System.out.println("The Bucket Sort Algorithm can only sort Integer values.");
    }
  }

  /**
   * @param arValues The array that will be sorted.
   */
  public void sortAscending(Integer[] arValues) {
    int nrValuesInserted = 0;

    // Initialize the array of occurrences with 0 as the default value.
    int[] arOccurrences = new int[arValues.length + 1];
    // for (int i = 0; i < arOccurrences.length; i++) {
    // arOccurrences[i] = 0;
    // }

    // Iterate through the array and count how many times each value has
    // appeared and increment by 1 every time.
    for (int i = 0; i < arValues.length; i++) {
      arOccurrences[arValues[i]] += 1;
    }

    for (int j = 0; j < arOccurrences.length; j++) {
      int nrOccurrences = arOccurrences[j];

      for (int k = 0; k < nrOccurrences; k++) {
        arValues[nrValuesInserted++] = j;
      }
    }
  }

}