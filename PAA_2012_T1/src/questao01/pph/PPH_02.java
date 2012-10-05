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

public class PPH_02 {

	// O nome do arquivo de input padrão(usado para testes).
	private static final String DEFAULT_INPUT_FILE_NAME = "src/questao01/pph/pph_10.txt";

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
			Util.isDebugging = true;
		}

		PPH_02 pph = new PPH_02();
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
			// int quantityOfInputValues = 100000;

			// A razão que deve ser calculada e apresentada no final.
			float finalRatio = 0;

			// Esta linha é apenas para forçar uma quebra de linha depois dos
			// números.
			scanner.nextLine();

			int[][] arrayOrderedPairs = null;
			// Obtém os valores que correspondem ao a = {1,.., n}
			// arrayOrderedPairs =
			// getValuesFromInputFile(quantityOfInputValues);
			arrayOrderedPairs = getValuesFromInputFile(arrayOrderedPairs, ColumnA, quantityOfInputValues, scanner);

			// Obtém os valores que correspondem ao b = {1,.., n}
			arrayOrderedPairs = getValuesFromInputFile(arrayOrderedPairs, ColumnB, quantityOfInputValues, scanner);

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
			finalRatio = maximumRatio(calcRatio(aZero, bZero), arrayOrderedPairs);
			iterations++;
			// }
			long finishTime = System.currentTimeMillis() - startTime;

			float media = (float) finishTime / iterations;
			Util.printOntoScreenF("Razão final: %f\n", finalRatio);
			// Util.printOntoScreen("Conjunto S*: ");
			// Util.printMatriz(arrayS);

			Util.printOntoScreen("Interaçoes realizadas: " + iterations);
			Util.printOntoScreenF("Tempo de execução: %f\n", media);

		} catch (FileNotFoundException e) {
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
	private int[][] getValuesFromInputFile(int[][] arrayOrderedPairs, int index, int quantityOfInputValues,
			Scanner scanner) {
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

			Util.debugF("%4d", inputValue);

			// Incrementa o contatdor.
			cont++;
		}
		Util.debug("");

		return arrayOrderedPairs;
	}

	/**
	 * @param previousRatio
	 * @param arrayOrderedPairs
	 * @return A razão máxima.
	 */
	private float maximumRatio(float previousRatio, int[][] arrayOrderedPairs) {
		// O a0 e o b0 já são automáticamente inseridos no array S*.
		arrayS[0][ColumnA] = arrayOrderedPairs[0][ColumnA];
		arrayS[0][ColumnB] = arrayOrderedPairs[0][ColumnB];
		int cont = 1;

		// O R inicial é calculado pelo a0 / b0.
		float currentRatio = previousRatio;

		float auxRatio;
		for (int i = 1; i < arrayOrderedPairs.length; i++) {
			auxRatio = calcRatio(arrayOrderedPairs[i][ColumnA], arrayOrderedPairs[i][ColumnB]);
			Util.debugF("[%d, %d] = %f - %f", arrayOrderedPairs[i][ColumnA], arrayOrderedPairs[i][ColumnB], auxRatio,
					currentRatio);
			Util.debug("");

			if (auxRatio > currentRatio) {
				// Então coloca o ai e o bi no array S*.
				arrayS[cont][ColumnA] = arrayOrderedPairs[i][ColumnA];
				arrayS[cont][ColumnB] = arrayOrderedPairs[i][ColumnB];

				// Incrementa o contatdor.
				cont++;

				// Atualiza o R(razão).
				currentRatio = updateRatio(arrayS, cont);
			}
		}

		Util.printOntoScreenF("Razão atual %f - Razão anterior %f\n", currentRatio, previousRatio);
		if (currentRatio <= previousRatio) {
			return previousRatio;
		} else {
			return maximumRatio(currentRatio, arrayOrderedPairs);
		}
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
			// Util.debugF("Somátorio de: [%d, %d]\n", arrayS[i][ColumnA],
			// arrayS[i][ColumnB]);
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
}