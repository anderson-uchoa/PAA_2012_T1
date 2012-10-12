package questao01.algoritmos.ordenacao;

/**
 * @author Luciano Sampaio
 * 
 */
public abstract class Sorter implements ISortable {

  private long iterations;

  /**
   * Swap the position of the elements in the array.
   * 
   * @param arValues
   * @param one
   * @param two
   */
  protected <T> void swap(T[] arValues, int one, int two) {
    T temp = arValues[one];
    arValues[one] = arValues[two];
    arValues[two] = temp;
  }

  public long getIterations() {
    return iterations;
  }

  public void incIterations() {
    this.iterations++;
  }
}