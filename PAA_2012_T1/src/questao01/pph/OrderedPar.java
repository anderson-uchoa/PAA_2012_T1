package questao01.pph;

public class OrderedPar {

	private int a;
	private int b;

	public OrderedPar(int a, int b) {
		this.a = a;
		this.b = b;
	}

	public int getA() {
		return a;
	}

	public int getB() {
		return b;
	}

	public float getRatio() {
		return (float) getA() / getB();
	}
}
