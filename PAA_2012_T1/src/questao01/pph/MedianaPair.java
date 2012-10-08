package questao01.pph;

/**
 * 
 * 
 * @author Rafael Oliveira Vasconcelos
 */
public class MedianaPair {
  OrderedPair orderedPair;
  int         index;

  public MedianaPair(OrderedPair orderedPair, int index) {
    this.orderedPair = orderedPair;
    this.index = index;
  }

  public OrderedPair getOrderedPair() {
    return orderedPair;
  }

  public void setOrderedPair(OrderedPair orderedPair) {
    this.orderedPair = orderedPair;
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }
}
