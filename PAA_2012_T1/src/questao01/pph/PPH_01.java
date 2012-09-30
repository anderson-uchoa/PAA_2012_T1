package questao01.pph;

import java.util.Random;

public class PPH_01 {
	static final int N = 1000;
	static int pares[][] = new int[N][2];
	static int S[][] = new int[N][2];
	static int index = 0; // indicador de fim do array
	static int a, b;

	static void preenchePares() {
		Random rnd = new Random();
		pares[0][0] = rnd.nextInt(500);
		pares[0][1] = rnd.nextInt(1000) + 1;
		for (int i = 1; i < pares.length; i++) {
			pares[i][0] = rnd.nextInt(500) + 1;
			pares[i][1] = rnd.nextInt(1000) + 1;
		}
	}

	static void razaoMaxima() {
		a = pares[0][0];
		b = pares[0][1];
		S[0][0] = a; // coloca a0 e b0 no S
		S[0][1] = b;
		double R = (double) a / b;
		double aux = 0;
		for (int i = 1; i < pares.length; i++) {
			aux = (double) pares[i][0] / pares[i][1];
			// System.out.printf("aux: %.4f R: %.4f\n",aux,R);
			if (aux > R) {
				index++;
				S[index][0] = pares[i][0]; // adiciona o par ordenado em S
				S[index][1] = pares[i][1];
				a = 0;
				b = 0;
				for (int j = 0; j <= index; j++) { // recalcula R
					a += S[j][0];
					b += S[j][1];
				} // for j
				R = (double) a / b;
			} // if
		} // for i
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		// TODO code application logic here
		preenchePares();
		// System.out.println("(A0,B0): ("+pares[0][0]+","+pares[0][1]+")");
		// for (int i=1; i<pares.length; i++){
		// System.out.println("(A"+i+",B"+i+"): ("+pares[i][0]+","+pares[i][1]+")");
		// } // for
		long tempo = System.currentTimeMillis();
		long iteracoes = 0;
		while (System.currentTimeMillis() - tempo < 5000) {
			index = 0;
			razaoMaxima();
			iteracoes++;
		}
		tempo = System.currentTimeMillis() - tempo;
		System.out.println("Conjunto S");
		// int a=0, b=0;
		// for (int i=0; i<=index; i++){
		// System.out.println("(S"+i+",S"+i+"): ("+S[i][0]+","+S[i][1]+")");
		// a+=S[i][0];
		// b+=S[i][1];
		// } //for
		System.out.println("N: " + N);
		System.out.println("Razão Máxima: " + a + "/" + b);
		double media = (double) tempo / iteracoes;
		System.out.println("Interaçoes realizadas: " + iteracoes);
		System.out.printf("Tempo de execução: %.12f\n", media);

	} // main
} // pph
