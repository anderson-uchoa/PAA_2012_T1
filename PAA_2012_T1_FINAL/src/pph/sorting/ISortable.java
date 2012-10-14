package pph.sorting;

import java.util.List;

/**
 * @author Luciano Sampaio
 * 
 */
public interface ISortable {

  /**
   * @param arValues The array that will be ascending sorted.
   */
  <T extends Comparable<T>> void sortAscending(T[] arValues);

  /**
   * @param list The list that will be ascending sorted.
   */
  <T extends Comparable<T>> void sortAscending(List<T> list);

  /**
   * @param arValues The array that will be descending sorted.
   */
  <T extends Comparable<T>> void sortDescending(T[] arValues);

  /**
   * @param list The list that will be descending sorted.
   */
  <T extends Comparable<T>> void sortDescending(List<T> list);
}
