package utilidade;

import java.util.List;

import questao01.pph.MedianaPair;
import questao01.pph.OrderedPair;
import questao01.pph.ordenacao.Sorter;

public class RandomizedSelectPivot extends Sorter {

  // Para evitar colocar numeros literais no código.
  private int         somaA = 0;
  private int         somaB = 0;
  // Par Inicial
  private OrderedPair parInicial;

  /**
   * Método que retorna a Mediana, ou seja, retorna o par ordenando e sua posição na lista
   * 
   * @param list
   * @param parInicial
   * @return
   */
  public MedianaPair findMediana(List<OrderedPair> list) {
    // Armazena o par inicial e o remove da lista
    this.parInicial = list.get(0);
    //list.remove(0);
    return sort(list, 1, list.size() - 1, list.size() / 2);
  }

  /**
   * Método que recursivamente re-organiza a lista de forma que do pivot à esquerda todos os elementos são menores e à direita todos são maiores. Não há
   * ordenação no lado esquerdo e direito.
   * 
   * @param list
   * @param parInicial
   * @param left
   * @param right
   * @param pivot
   * @return
   */
  public <T extends Comparable<T>> MedianaPair sort(List<T> list, int left, int right, int pivot) {
    // Incrementado as iterações para facilitar a mensuração da complexidade final
    while (left != right) {
      this.incIterations();

      // Seleciona um pivot com base no somatório das razões entre o índice inferior e superio da lista
      int pivotNewIndex = PivotPartition(list, left, right);
      int pivotDist = pivotNewIndex - left + 1;
      if (pivot == pivotDist)
        return new MedianaPair(list.get(pivotNewIndex), pivotNewIndex);
      else if (pivot < pivotDist)// Procurando apenas no lado esquerdo da lista
        //return sort(list, left, pivotNewIndex - 1, pivot);
        right = pivotNewIndex - 1;
      else {// Procurando apenas no lado direito da lista
        //return sort(list, pivotNewIndex + 1, right, pivot - pivotDist);
        pivot = pivot - pivotDist;
        left = pivotNewIndex + 1;
      }
    }
    return new MedianaPair(list.get(left), left);
  }

  /**
   * Método retorna uma posição aleatória entre o limite inferior e superior
   * 
   * @param list
   * @param left
   * @param right
   * @return
   */
  private <T extends Comparable<T>> int PivotPartition(List<T> list, int left, int right) {
    // Escolhendo aleatoreamente um pivot entre "left" e "right"
    int index = calcularPivot(list, left, right);
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

  private <T extends Comparable<T>> int calcularPivot(List<T> list, int left, int right) {

    // Calculando a razão máxima para o intervalo
    float maximumRatio = parInicial.getRatio();
    for (int i = left; i < right; i++) {
      OrderedPair auxlPar = (OrderedPair) list.get(i);
      if (auxlPar.getRatio() > maximumRatio) {
        SomaRazao(auxlPar);
        maximumRatio = calcularRazao();
      }
    }
    // Há casos que a razão é maior que o intervalo trabalhado. Então tira-se a média
    if (maximumRatio > right)
      return (right - left) / 2;

    return (int) maximumRatio;
  }

  private void SomaRazao(OrderedPair auxlPar) {
    this.somaA += auxlPar.getA();
    this.somaB += auxlPar.getB();
  }

  private float calcularRazao() {
    return (float) (this.parInicial.getA()) / (this.parInicial.getB());
  }

  @Override
  public <T extends Comparable<T>> void sortAscending(T[] arValues) {
    // TODO Auto-generated method stub
  }

  @Override
  public <T extends Comparable<T>> void sortAscending(List<T> list) {
    // Armazena o par inicial e o remove da lista
    this.parInicial = (OrderedPair) list.get(0);
    //list.remove(0);
    sort(list, 0, list.size() - 1, list.size() / 2);
  }
}
