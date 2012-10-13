package questao01.pph;

public class OrderedPair implements Comparable<OrderedPair> {

  private int     a;
  private int     b;
  private float   ratio;
  private boolean isChanged;

  public OrderedPair(int a, int b) {
    this.a = a;
    this.b = b;
    isChanged = true;
  }

  public int getA() {
    return a;
  }

  public int getB() {
    return b;
  }

  public float getRatio() {
    if (isChanged) {
      isChanged = false;
      ratio = (float) getA() / getB();
    }
    return ratio;
  }

  @Override
  public String toString() {
    return String.format("[(%3d,%3d) - %f]", getA(), getB(), getRatio());
  }

  @Override
  public int compareTo(OrderedPair obj) {
    float divisaoThis = this.getRatio();
    float divisaoObj = obj.getRatio();
    if (divisaoThis < divisaoObj) {
      return -1;
    }
    else if (divisaoThis == divisaoObj) {
      return 0;
    }
    else
      return 1;
  }
}
