package pph.sorting;

/**
 * Todas as classes que fazem ordenação neste trabalho, devem estender esta classe, pois a mesma possui alguns métodos comuns.
 * 
 * @author Luciano Sampaio
 * 
 */
public abstract class Sorter implements ISortable {

  /**
   * A quantidade de operações que foi necessária para que o resultado esperado fosse encontrado.
   */
  private long operations;

  /**
   * Troca as posições dos elementos no array.
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

  /**
   * @return A quantidade de operações que foi necessária para que o resultado esperado fosse encontrado.
   */
  public long getOperations() {
    return operations;
  }

  /**
   * Atribui uma valor à quantidade de operações que foi necessária para que o resultado esperado fosse encontrado.
   * 
   * @param iterations
   */
  public void setOperations(long iterations) {
    this.operations = iterations;
  }

  /**
   * Incrementa em 1 a quantidade de operações que foi necessária para que o resultado esperado fosse encontrado.
   */
  public void incOperations() {
    this.setOperations(getOperations() + 1);
  }

}