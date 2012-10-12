package questao01.pph.ordenacao;

import java.util.List;

/**
 * @author Luciano Sampaio
 * 
 */
public interface ISortable {

  /**
   * @param arValues The array that will be sorted.
   */
  <T extends Comparable<T>> void sortAscending(T[] arValues);

  /**
   * @param list The list that will be sorted.
   */
  <T extends Comparable<T>> void sortAscending(List<T> list);
}
