package utilidade;

import java.util.List;

import questao01.pph.MedianaPair;
import questao01.pph.OrderedPair;

public class SelectionSortExtend {

  private int         somaA = 0;
  private int         somaB = 0;
  private OrderedPair parInicial;

  public MedianaPair select(List<OrderedPair> list, int left, int right, int k) {
    parInicial = list.get(0);

    if (left == right) // If the list contains only one element
      return new MedianaPair(list.get(left), left);//list.get(left); // Return that element
    // select pivotIndex between left and right
    int median = medianOfMedians(list, left, right);
    int pivotNewIndex = partition(list, left, right, median);
    int pivotDist = pivotNewIndex - left + 1;
    // The pivot is in its final sorted position,
    // so pivotDist reflects its 1-based position if list were sorted
    if (pivotDist == k)
      return new MedianaPair(list.get(left), left);//list.get(pivotNewIndex);
    else if (k < pivotDist)
      return select(list, left, pivotNewIndex - 1, k);
    else
      return select(list, pivotNewIndex + 1, right, k - pivotDist);
  }

  public MedianaPair selectIterativo(List<OrderedPair> list, int left, int right, int k) {
    parInicial = list.get(0);

    // select pivotIndex between left and right
    while (left != right && right > 0) {
      int median = medianOfMedians(list, left, right);
      int pivotNewIndex = partition(list, left, right, median);
      int pivotDist = pivotNewIndex - left + 1;
      // The pivot is in its final sorted position,
      // so pivotDist reflects its 1-based position if list were sorted
      if (pivotDist == k)
        return new MedianaPair(list.get(pivotNewIndex), pivotNewIndex);
      else if (k < pivotDist)
        right = pivotNewIndex - 1;
      else {
        k = k - pivotDist;
        left = pivotNewIndex + 1;
      }
    }
    return new MedianaPair(list.get(left), left);
  }

  private int partition(List<OrderedPair> list, int left, int right, int pivotIndex) {
    int i = left, j = right - 1;
    OrderedPair tmp;
    OrderedPair pivot = list.get(pivotIndex);
    while (i < j) {
      while (list.get(i).compareTo(pivot) < 0 && i < j)
        i++;
      while (list.get(j).compareTo(pivot) > 1 && i < j)
        j--;

      //if (i <= j) {
      tmp = list.get(i);
      list.set(i, list.get(j));
      list.set(j, tmp);
      i++;
      j--;
    }
    ;
    return i;
  }

  // returns the index of the median of medians. Based on the ratio of N
  private int medianOfMedians(List<OrderedPair> list, int left, int right) {
    float maximumRatio = parInicial.getRatio();
    int numMedians = (right - left) / 5;
    int idx = left;

    if (idx == 0)
      idx++;

    for (int i = left; i < right; i++) {
      OrderedPair auxlPar = list.get(i);
      if (auxlPar.getRatio() > maximumRatio) {
        SomaRazao(auxlPar);
        maximumRatio = calcularRazao();
      }
    }

    return selectIdx(list, left, left + numMedians, numMedians / 2);
  }

  private void SomaRazao(OrderedPair auxlPar) {
    this.somaA += auxlPar.getA();
    this.somaB += auxlPar.getB();
  }

  private float calcularRazao() {
    return (float) (this.somaA + this.parInicial.getA()) / (this.somaB + this.parInicial.getB());
  }

  private int selectIdx(List<OrderedPair> list, int left, int right, int pivot) {
    if (right - left < 6)
      InsertionSort(list, left, right);

    int idx;
    if ((left + right) % 2 == 0)
      idx = (left + right + 1) / 2;
    else {
      idx = (((left + right + 1) / 2) + ((left + right + 1) / 2) + 1) / 2;
    }
    return idx;
  }

  public void InsertionSort(List<OrderedPair> list, int left, int right) {
    for (int j = left + 1; j < list.size(); j++) {
      OrderedPair orderedPairkey = list.get(j);
      int i = j - 1;
      while (i >= 0 && list.get(i).compareTo(orderedPairkey) > 0) {
        list.set(i + 1, list.get(i));
        i = i - 1;
      }
      list.set(i + 1, orderedPairkey);
    }
  }

  /** Swaps the values at the specified array indexes */
  private void swap(List<OrderedPair> array, int a, int b) {
    OrderedPair temp = array.get(a);
    array.set(a, array.get(b));
    array.set(b, temp);

  }

}
