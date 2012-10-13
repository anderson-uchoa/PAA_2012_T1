package utilidade;

import java.util.List;

import questao01.pph.MedianaPair;
import questao01.pph.OrderedPair;
import questao01.pph.ordenacao.ISortable;
import questao01.pph.ordenacao.Sorter;

public class RandomizedSelect extends Sorter implements ISortable {

  //  public MedianaPair sortAscending(List<OrderedPair> list, int left, int right, int i) {
  //    return sort(list, left, list.size() - 1, i);
  //  }

  public MedianaPair findMediana(List<OrderedPair> list) {
    return sort(list, 0, list.size() - 1, list.size() / 2);
  }

  public <T extends Comparable<T>> MedianaPair sort(List<T> list, int left, int right, int i) {
    this.incIterations();
    if (left == right)
      return new MedianaPair(list.get(left), left);

    int pivotNewIndex = RandomizedPartition(list, left, right);
    int pivotDist = pivotNewIndex - left + 1;
    if (i == pivotDist)
      return new MedianaPair(list.get(pivotNewIndex), pivotNewIndex);
    else if (i < pivotDist)
      return sort(list, left, pivotNewIndex - 1, i);
    else {
      return sort(list, pivotNewIndex + 1, right, i - pivotDist);
    }
  }

  //  public MedianaPair sortIterativo(List<OrderedPair> list, int left, int right, int i) {
  //    //System.out.println("Sort");
  //    while (left != right) {
  //      if (left == i)
  //        return new MedianaPair(list.get(left), left);
  //
  //      int pivotNewIndex = RandomizedPartition(list, left, right);
  //      int pivotDist = pivotNewIndex - left + 1;
  //      if (i == pivotDist)
  //        return new MedianaPair(list.get(pivotNewIndex), pivotNewIndex);
  //      else if (i < pivotDist)
  //        right = pivotNewIndex - 1;
  //      else {
  //        i = i - pivotDist;
  //        left = pivotNewIndex + 1;
  //      }
  //    }
  //    return new MedianaPair(list.get(left), left);
  //  }

  private <T extends Comparable<T>> int RandomizedPartition(List<T> list, int left, int right) {
    //System.out.println("RandomizedPartition");
    // Escolhendo aleatoreamente um pivot entre "left" e "right"
    int index = left + (right - left) / 2; //new Random().nextInt(right + 1 - left) + left;// Random(left, right);

    //swap
    T aux = list.get(index);
    list.set(index, list.get(right));
    list.set(right, aux);

    //Partition
    return partition(list, left, right);
  }

  private <T extends Comparable<T>> int partition(List<T> list, int left, int right) {
    T temp;
    T x = list.get(right);
    int i = left - 1;
    for (int j = left; j <= right - 1; j++) {
      this.incIterations();
      if (list.get(j).compareTo(x) <= 0) {
        i = i + 1;
        // swap(A[i], A[j])
        temp = list.get(j);
        list.set(j, list.get(i));
        list.set(i, temp);
      }
    }
    //swap(A[i+1], A[r])
    temp = list.get(right);
    list.set(right, list.get(i + 1));
    list.set(i + 1, temp);

    return i + 1;

    // p - inicio
    //r - fim
    //System.out.println("partition");
    //    int i = left, j = right; //- 1;
    //    OrderedPair tmp;
    //    OrderedPair pivot = list.get(j);
    //    while (i < j) {
    //      while (list.get(i).compareTo(pivot) < 0)
    //        //&& i < j)
    //        i++;
    //      while (list.get(j).compareTo(pivot) > 1)
    //        //&& i < j)
    //        j--;
    //
    //      if (i < j) {
    //        tmp = list.get(i);
    //        list.set(i, list.get(j));
    //        list.set(j, tmp);
    //        i++;
    //        j--;
    //      }
    //    }
    //    return i - 1;
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
