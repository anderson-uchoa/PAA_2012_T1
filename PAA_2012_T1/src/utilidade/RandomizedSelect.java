package utilidade;

import java.util.List;

import questao01.pph.MedianaPair;
import questao01.pph.OrderedPair;

public class RandomizedSelect {

  public MedianaPair sort(List<OrderedPair> list, int left, int right, int i) {
    System.out.println("Sort");
    if (left == i)
      return new MedianaPair(list.get(left), left);

    int q = RandomizedPartition(list, left, right);
    int k = q - left + 1;
    if (i == k)
      return new MedianaPair(list.get(q), q);
    else if (i < k)
      return sort(list, left, q - 1, i);

    else
      return sort(list, q + 1, right, i - k);
  }

  private int RandomizedPartition(List<OrderedPair> list, int left, int right) {
    System.out.println("RandomizedPartition");
    int index = Random(left, right);

    OrderedPair aux = list.get(index);
    list.set(right - 1, list.get(index));
    list.set(index, aux);
    //swap(list, right, index);
    return partition(list, left, right);
  }

  private int partition(List<OrderedPair> list, int left, int right) {
    System.out.println("partition");
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
    System.out.println("Random");
    if (left == right)
      return left;

    int idx;
    if ((right - left) % 2 == 0)
      idx = (left + right + 1) / 2;
    else {
      idx = (((left + right + 1) / 2) + ((left + right + 1) / 2) + 1) / 2;
    }

    System.out.println("left  : " + left);
    System.out.println("right :" + right);
    System.out.println("index : " + idx);
    return idx + 1;

    //    Random rdn = new Random();
    //    int index = rdn.nextInt(right);
    //    while ((index <= left) && (index <= right)) {
    //      int dif = right - left;
    //      index += dif > 0 ? dif : left;
    //      //index = rdn.nextInt(right);
    //    }
    //    System.out.println("left  : " + left);
    //    System.out.println("right :" + right);
    //    System.out.println("index : " + index);
    //    return index;
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
}
