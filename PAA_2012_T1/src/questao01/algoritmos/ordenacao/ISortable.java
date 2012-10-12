package questao01.algoritmos.ordenacao;

/**
 * @author Luciano Sampaio
 * 
 */
public interface ISortable {

  /**
   * @param arValues The array that will be sorted.
   */
  <T extends Comparable<T>> void sortAscending(T[] arValues);
}
