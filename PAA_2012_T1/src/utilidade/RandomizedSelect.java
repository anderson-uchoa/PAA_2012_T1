package utilidade;

import java.util.List;

import questao01.pph.MedianaPair;
import questao01.pph.OrderedPair;

public class RandomizedSelect {

  public MedianaPair sort(List<OrderedPair> list, int left, int right, int i) {
    if (left == i)
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

  public MedianaPair sortIterativo(List<OrderedPair> list, int left, int right, int i) {
    //System.out.println("Sort");
    while (left != right) {
      if (left == i)
        return new MedianaPair(list.get(left), left);

      int pivotNewIndex = RandomizedPartition(list, left, right);
      int pivotDist = pivotNewIndex - left + 1;
      if (i == pivotDist)
        return new MedianaPair(list.get(pivotNewIndex), pivotNewIndex);
      else if (i < pivotDist)
        right = pivotNewIndex - 1;
      else {
        i = i - pivotDist;
        left = pivotNewIndex + 1;
      }
    }
    return new MedianaPair(list.get(left), left);
  }

  private int RandomizedPartition(List<OrderedPair> list, int left, int right) {
    //System.out.println("RandomizedPartition");
    // Escolhendo aleatoreamente um pivot entre "left" e "right"
    int index = Random(left, right);

    //swap
    OrderedPair aux = list.get(index);
    list.set(index, list.get(right - 1));
    list.set(right - 1, aux);

    //swap(list, right, index);
    return partition(list, left, right);
  }

  private int partition(List<OrderedPair> list, int left, int right) {
    //System.out.println("partition");
    int i = left, j = right - 1;
    OrderedPair tmp;
    OrderedPair pivot = list.get(j);
    while (i < j) {
      while (list.get(i).compareTo(pivot) < 0 && i < j)
        i++;
      while (list.get(j).compareTo(pivot) > 1 && i < j)
        j--;

      tmp = list.get(i);
      list.set(i, list.get(j));
      list.set(j, tmp);
      i++;
      j--;
    }
    ;
    return i;
  }

  private int Random(int left, int right) {

    //System.out.println("Random");
    if (left == right || (right - left == 1))
      return left;

    java.util.Random rdn = new java.util.Random();
    int index = randomIndex(rdn.nextInt(right), left, right);

    //    System.out.println("left  : " + left);
    //    System.out.println("right :" + right);
    //    System.out.println("index : " + index);
    return index;
  }

  /**
   * Swaps the values at the specified array indexes
   * 
   * @param list
   * @param idxa
   * @param idxb
   */
  private void swap(List<OrderedPair> list, int idxa, int idxb) {
    if (idxa == list.size())
      idxa--;
    if (idxb == list.size())
      idxb--;

    OrderedPair temp = list.get(idxa);
    list.set(idxa, list.get(idxb));
    list.set(idxb, temp);
  }

  private int randomIndex(int randomIndex, int left, int right) {
    int result = -1;
    while (result <= left || result >= right) {
      result = new java.util.Random().nextInt(right);
      if ((right + result) % 2 == 0)
        result = (result + right + 1) / 2;
      else
        result = (((result + right + 1) / 2) + ((result + right + 1) / 2) + 1) / 2;
    }
    return result;
  }
}
