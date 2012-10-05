/**
 * Esta o algoritmo que resolve a 1ª questão do T1 de PAA - PUC-Rio 2012.2.
 * Alunos: Luciano Sampaio, Igor Oliveira e Marcio Rosemberg.
 */
package questao01.pph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

import utilidade.Util;

public class PPH_03 {

	// O nome do arquivo de input padrão(usado para testes).
	private static final String DEFAULT_INPUT_FILE_NAME = "src/questao01/pph/pph_1000.txt";

	// A matriz que vai conter os valores que validam o lemma.
	int[][] arrayS;

	// Para evitar colocar numeros literais no código.
	short ColumnA = 0;
	short ColumnB = 1;

	public static void main(String[] args) {
		String inputFile;
		// Verifica se o arquivo de input foi passado como parâmetro.
		if (args.length == 1) {
			inputFile = args[0];

		} else {
			// Caso nenhum arquivo tenha sido informado, testa com o arquivo
			// criado para testes.
			inputFile = DEFAULT_INPUT_FILE_NAME;

			// Informa que a applicação esta em modo debug.
			Util.isDebugging = false;
		}

		PPH_03 pph = new PPH_03();
		pph.run(inputFile);
	}

	/**
	 * O método run foi criado apenas para que não fosse necessário ficar usando
	 * variáveis e métodos estáticos.
	 * 
	 * @param inputFile
	 */
	private void run(String inputFile) {
		try {
			// Abre o arquivo para que o dados possam ser lidos.
			Scanner scanner = new Scanner(new File(inputFile));

			// Obtém a quantidade de números contidos neste arquivo + 1(o a0 e
			// b0 não entram) * 2(porque é a mesma quantidade para o A e para o
			// B).
			int quantityOfInputValues = scanner.nextInt() + 1;
			//int quantityOfInputValues = 100;

			// A razão que deve ser calculada e apresentada no final.
			float finalRatio = 0;

			// Esta linha é apenas para forçar uma quebra de linha depois dos
			// números.
			scanner.nextLine();

			int[][] arrayOrderedPairs = null;
			// Obtém os valores que correspondem ao a = {1,.., n}
			//arrayOrderedPairs = getValuesFromInputFile(quantityOfInputValues);
			 arrayOrderedPairs = getValuesFromInputFile(arrayOrderedPairs,
			 ColumnA, quantityOfInputValues, scanner);

			// Obtém os valores que correspondem ao b = {1,.., n}
			 arrayOrderedPairs = getValuesFromInputFile(arrayOrderedPairs,
			 ColumnB, quantityOfInputValues, scanner);

			// Inicia a matriz S com o tamanho de elementos de pares ordenados
			// e 2 colunas.
			arrayS = new int[arrayOrderedPairs.length][2];

			int aZero = arrayOrderedPairs[0][ColumnA];
			int bZero = arrayOrderedPairs[0][ColumnB];

			long startTime = System.currentTimeMillis();
			long iterations = 0;
			Util.printOntoScreen("Calculando...");
			// while (System.currentTimeMillis() - startTime < 5000) {
			// Calcula a razão máxima.
			bubbleSort(arrayOrderedPairs);
			finalRatio = maximumRation(arrayOrderedPairs, arrayS);
			iterations++;
			// }
			long finishTime = System.currentTimeMillis() - startTime;

			float media = (float) finishTime / iterations;
			Util.printOntoScreenF("Razão final: %f\n", finalRatio);
			// Util.printOntoScreen("Conjunto S*: ");
			// Util.printMatriz(arrayS);

			Util.printOntoScreen("Interaçoes realizadas: " + iterations);
			Util.printOntoScreenF("Tempo de execução: %f\n", media);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param quantityOfInputValues
	 * @return
	 */
	private int[][] getValuesFromInputFile(int quantityOfInputValues) {
		Random rnd = new Random();
		int[][] arrayOrderedPairs = new int[quantityOfInputValues][2];

		for (int i = 0; i < arrayOrderedPairs.length; i++) {
			arrayOrderedPairs[i][ColumnA] = rnd.nextInt(500) + 1;
			arrayOrderedPairs[i][ColumnB] = rnd.nextInt(1000) + 1;
		}

		return arrayOrderedPairs;
	}

	/**
	 * Obtém os valores que correspondem ao A ou ao B(de uma forma generica) =
	 * {1,.., n}
	 * 
	 * @param arrayOrderedPairs
	 * @param index
	 * @param quantityOfInputValues
	 *            A quantidade de números contidos neste arquivo.
	 * @param scanner
	 *            Objeto que lê do arquivo.
	 * @return Obtém os valores que correspondem ao A ou ao B.
	 */
	private int[][] getValuesFromInputFile(int[][] arrayOrderedPairs,
			int index, int quantityOfInputValues, Scanner scanner) {
		// Cria a matriz com a quantidade de elementos que ele vai conter.
		// 2 Linhas(uma para A e uma para B) e quantityOfInputValues de colunas.
		if (arrayOrderedPairs == null) {
			arrayOrderedPairs = new int[quantityOfInputValues][2];
		}

		int cont = 0;
		int inputValue;
		while (cont < quantityOfInputValues) {
			inputValue = scanner.nextInt();

			arrayOrderedPairs[cont][index] = inputValue;

			Util.debug(inputValue + " ", false);

			// Incrementa o contatdor.
			cont++;
		}
		Util.debug("");

		return arrayOrderedPairs;
	}

	/**
	 
	 */
	private float maximumRation(int[][] arrayOrderedPairs, int[][] arrayS) {
		float aux = 0;

		int a = arrayOrderedPairs[0][0];
		int b = arrayOrderedPairs[0][1];
		int indexS = 0;
		float R = (float) a / b;
		System.out.println("Razão AoB0: " +  R);
		for (int i = 1; i < arrayOrderedPairs.length; i++) {
			aux = (float) arrayOrderedPairs[i][0] / arrayOrderedPairs[i][1];
			System.out.println(" aux: " +  aux+ " = " +   arrayOrderedPairs[i][0] + " / " +  arrayOrderedPairs[i][1]);
			if (aux > R) {
				a += arrayOrderedPairs[i][0];
				b += arrayOrderedPairs[i][1];
				R = (float) a / b;
				arrayS[indexS][0] = arrayOrderedPairs[i][0];
				arrayS[indexS][1] = arrayOrderedPairs[i][1];
				indexS++;
			} else
				break;
			System.out.println(" aux: " +  aux+ " R: " +  R);
			// if
		} // for i
		return R;
    }
	
	

	/**
	 * Calcula a razão baseado nos valores que existem no conjunto S*;
	 * 
	 * @param arrayS
	 * @param cont
	 * @return Atualiza a razão baseada em A0 + somatório Ai até BN dividido por
	 *         B0 + somatório Bi até BN.
	 */
	private float updateRatio(int[][] arrayS, int cont) {
		long a = 0;
		long b = 0;

		for (int i = 0; i < cont; i++) {
			Util.debugF("Somátorio de: [%d, %d]\n", arrayS[i][ColumnA],
					arrayS[i][ColumnB]);
			a += arrayS[i][ColumnA];
			b += arrayS[i][ColumnB];
		}
		return calcRatio(a, b);
	}

	/**
	 * Calcula a razão.
	 * 
	 * @param a
	 * @param b
	 * @return O resultado da divisão de a por b.
	 */
	private float calcRatio(long a, long b) {
		return (float) a / b;
	}

	private void bubbleSort(int[][] array) {

		for (int i = 0; i < array.length; i++) {
			for (int j = 1; j < array.length - 1; j++) {
				float razao = (float) array[j][0] / array[j][1];
				float razaoProximoItem = (float) array[j + 1][0]
						/ array[j + 1][1];
				if (razaoProximoItem > razao) {
					int p[] = array[j];
					array[j] = array[j + 1];
					array[j + 1] = p;
				}
			}
		}
    }

}