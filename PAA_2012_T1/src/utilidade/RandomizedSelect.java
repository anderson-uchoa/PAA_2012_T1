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
    Random rdn = new Random();

    return 0;
  }

}
