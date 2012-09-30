package questao02.frascos;

public class Frascos_01 {
	static int degrau;
	static int n;
	static int passos;

	static int escadaFrasco(int deg, int tamanho, int fr) {
		degrau = deg;
		n = tamanho;
		passos = 0;
		return (quebra(0, tamanho, fr));
	}

	static boolean quebrou(int i) {
		return (i >= degrau);
	}

	static int quebra(int inicio, int fim, int frascos) {
		if (frascos == 1) {
			for (int i = inicio; i <= fim; i++) {
				passos++;
				System.out.printf("Frascos: %d, Inicio: %d, Fim: %d, i: %d, Passos: %d\n", frascos, inicio, fim, i,
						passos);
				if (quebrou(i)) {
					return i;
				}
			}
		} else {
			int div = calculaDiv(inicio, fim, frascos);
			int i = 0;
			for (i = inicio + div; i <= fim; i += div) {
				passos++;
				System.out.printf("Frascos: %d, Inicio: %d, Fim: %d, Div: %d, i: %d, Passos: %d\n", frascos, inicio,
						fim, div, i, passos);
				if (quebrou(i)) {
					return quebra(i - div, i, frascos - 1);
				}
			}
			return quebra(i - div, fim, frascos - 1);
		}
		return -1;
	}

	public static void main(String[] args) {
		System.out.println("Quebrou em: " + escadaFrasco(99, 100, 2) + " em " + passos + " passos\n");
		System.out.println("Quebrou em: " + escadaFrasco(124, 125, 3) + " em " + passos + " passos\n");
		System.out.println("Quebrou em: " + escadaFrasco(255, 256, 4) + " em " + passos + " passos\n");

		System.out.println("Quebrou em: " + escadaFrasco(17, 3125, 5) + " em " + passos + " passos\n");
		System.out.println("Quebrou em: " + escadaFrasco(3010, 3125, 5) + " em " + passos + " passos\n");

		System.out.println("Quebrou em: " + escadaFrasco(13, 46656, 6) + " em " + passos + " passos\n");
		System.out.println("Quebrou em: " + escadaFrasco(46654, 46656, 6) + " em " + passos + " passos\n");
	}

	private static int calculaDiv(int inicio, int fim, int frascos) {
		int base = fim - inicio;
		double expo = (double) 1 / frascos;
		double aux = (double) base / Math.pow(base, expo);
		System.out.println("Div: " + aux);
		return (int) aux;
	}
}
