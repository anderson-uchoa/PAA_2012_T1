package utilidade;

import java.util.List;
import java.util.Random;

import questao01.pph.MedianaPair;
import questao01.pph.OrderedPair;

public class RandomizedSelect {

  public MedianaPair sort(List<OrderedPair> list, int left, int right, int i) {
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
    int index = Random(left, right);
    swap(list, right, index);
    return 0;
  }

  private int Random(int left, int right) {
    Random rdn = new Random();
    int index = rdn.nextInt();
    while (index < left || index > right) {
      index = rdn.nextInt();
    }
    return index;
  }

  /** Swaps the values at the specified array indexes */
  private void swap(List<OrderedPair> list, int idxa, int idxb) {
    OrderedPair temp = list.get(idxa);
    list.set(idxa, list.get(idxb));
    list.set(idxb, temp);

  }

}
