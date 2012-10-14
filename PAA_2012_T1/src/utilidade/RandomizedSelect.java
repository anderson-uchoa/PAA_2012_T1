package utilidade;

import java.util.List;

import questao01.pph.MedianaPair;
import questao01.pph.OrderedPair;
import questao01.pph.ordenacao.Sorter;

public class RandomizedSelect extends Sorter {

  /**
   * Método que retorna a Mediana, ou seja, retorna o par ordenando e sua posição na lista
   * 
   * @param list
   * @return
   */
  public MedianaPair findMediana(List<OrderedPair> list) {
    return sort(list, 0, list.size() - 1, list.size() / 2);
  }

  /**
   * Método que recursivamente re-organiza a lista de forma que do pivot à esquerda todos os elementos são menores e à direita todos são maiores. Não há
   * ordenação no lado esquerdo e direito.
   * 
   * @param list
   * @param left
   * @param right
   * @param pivot
   * @return
   */
  public <T extends Comparable<T>> MedianaPair sort(List<T> list, int left, int right, int pivot) {
    // Incrementado as iterações para facilitar a mensuração da complexidade final
    this.incIterations();
    if (left == right)
      return new MedianaPair(list.get(left), left);

    // Seleciona um pivot aleatório entre o índice inferior e superio da lista
    int pivotNewIndex = RandomizedPartition(list, left, right);
    int pivotDist = pivotNewIndex - left + 1;
    if (pivot == pivotDist)
      return new MedianaPair(list.get(pivotNewIndex), pivotNewIndex);
    else if (pivot < pivotDist)// Procurando apenas no lado esquerdo da lista
      return sort(list, left, pivotNewIndex - 1, pivot);
    else {// Procurando apenas no lado direito da lista
      return sort(list, pivotNewIndex + 1, right, pivot - pivotDist);
    }
  }

  /**
   * Método retorna uma posição aleatória entre o limite inferior e superior
   * 
   * @param list
   * @param left
   * @param right
   * @return
   */
  private <T extends Comparable<T>> int RandomizedPartition(List<T> list, int left, int right) {
    // Escolhendo aleatoreamente um pivot entre "left" e "right"
    int index = new java.util.Random().nextInt(right + 1 - left) + left;
    //swap
    T aux = list.get(index);
    list.set(index, list.get(right));
    list.set(right, aux);

    //Particionando  a lista
    return partition(list, left, right);
  }

  /**
   * Método que efetivamente separa a lista em 2. O menores que o pivot ficam à esquerda e todos maiores ficam à direita
   * 
   * @param list
   * @param left
   * @param right
   * @return
   */
  private <T extends Comparable<T>> int partition(List<T> list, int left, int right) {
    T temp;
    // escolhendo o pivot
    T pivot = list.get(right);
    int i = left - 1;
    for (int j = left; j <= right - 1; j++) {
      // Incrementado as iterações para facilitar a mensuração da complexidade final
      this.incIterations();
      if (list.get(j).compareTo(pivot) <= 0) {// Se menor precisa trocar de posição
        i = i + 1;
        // Swap entre os elementos da posição i e j
        temp = list.get(j);
        list.set(j, list.get(i));
        list.set(i, temp);
      }
    }
    //Swap entre os elementos da posição i+1  e right (Final da lista) 
    temp = list.get(right);
    list.set(right, list.get(i + 1));
    list.set(i + 1, temp);
    // Retorna a posição do pivot. À esquerda os menores e à direita os maiores mas sem uma ordenação em cada lado
    return i + 1;
  }

  @Override
  public <T extends Comparable<T>> void sortAscending(T[] arValues) {
    // TODO Auto-generated method stub
  }

  @Override
  public <T extends Comparable<T>> void sortAscending(List<T> list) {
    sort(list, 0, list.size() - 1, list.size() / 2);
  }
}
