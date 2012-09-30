/**
 * Esta o algoritmo que resolve a 1ª questão do T1 de PAA - PUC-Rio 2012.2.
 * Alunos: Luciano Sampaio, Igor Oliveira e Marcio Rosemberg.
 */
package questao01.pph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import utilidade.Util;

public class PPH_02 {

	// O nome do input padrão(usado para testes).
	private static final String DEFAULT_INPUT_FILE_NAME = "src/questao01/pph/pph_100.txt";

	// A matriz que vai conter os valores que validam o lemma.
	int[][] arrayS;

	// A razão que deve ser calculada e apresentada no final.
	double ratio;

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

		PPH_02 pph = new PPH_02();
		pph.run(inputFile);
	}

	/**
	 * O método run foi criado apenas para que não fosse necessário ficar usando
	 * variáveis e métodos publicos.
	 */
	private void run(String inputFile) {
		try {
			// Abre o arquivo para que o dados possam ser lidos.
			Scanner scanner = new Scanner(new File(inputFile));

			// Obtém a quantidade de números contidos neste arquivo + 1(o a0 e
			// b0 não entram) * 2(porque é a mesma quantidade para o A e para o
			// B).
			int quantityOfInputValues = scanner.nextInt();

			// Esta linha é apenas para forçar uma quebra de linha depois dos
			// números.
			scanner.nextLine();

			// O primeiro número da lista é o a0.
			int aZero = scanner.nextInt();
			Util.debug(aZero);

			int[][] arrayOrderedPairs = null;
			// Obtém os valores que correspondem ao a = {1,.., n}
			arrayOrderedPairs = getValuesFromInputFile(arrayOrderedPairs, ColumnA, quantityOfInputValues, scanner);

			// O primeiro número da lista é o b0.
			int bZero = scanner.nextInt();
			Util.debug(bZero);

			// Obtém os valores que correspondem ao b = {1,.., n}
			arrayOrderedPairs = getValuesFromInputFile(arrayOrderedPairs, ColumnB, quantityOfInputValues, scanner);

			// Inicia a matriz S* com o tamanho de elementos de pares ordenados
			// e 2 colunas.
			arrayS = new int[arrayOrderedPairs.length][2];

			long startTime = System.currentTimeMillis();
			long iterations = 0;
			while (System.currentTimeMillis() - startTime < 5000) {
				// Calcula a razão.
				maximumRation(aZero, bZero, arrayOrderedPairs);
				iterations++;
			}
			long finishTime = System.currentTimeMillis() - startTime;

			double media = (double) finishTime / iterations;
			Util.printOntoScreenF("Razão final: %f\n", ratio);
			Util.printOntoScreen("Conjunto S*: ");
			Util.printMatriz(arrayS);

			Util.printOntoScreen("Interaçoes realizadas: " + iterations);
			Util.printOntoScreenF("Tempo de execução: %f\n", media);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Obtém os valores que correspondem ao a ou ao b(de uma forma generica) =
	 * {1,.., n}
	 * 
	 * @param quantityOfInputValues
	 *            A quantidade de números contidos neste arquivo.
	 * @param scanner
	 *            Objeto que lê do arquivo.
	 * @return
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

			Util.debug(inputValue + " ", false);

			// Incrementa o contatdor.
			cont++;
		}
		Util.debug("");

		return arrayOrderedPairs;
	}

	/**
	 * @param aZero
	 * @param bZero
	 * @param arrayOrderedPairs
	 */
	private void maximumRation(int aZero, int bZero, int[][] arrayOrderedPairs) {
		// O a0 e o b0 já são automáticamente inseridos no array S*.
		arrayS[0][ColumnA] = aZero;
		arrayS[0][ColumnB] = bZero;
		int cont = 1;

		// O R inicial é calculado pelo a0 / b0.
		ratio = updateRatio(arrayS, cont);
		Util.debugF("[%d, %d] = %f ", aZero, bZero, ratio);
		Util.debug("");

		double auxRatio;
		for (int i = 0; i < arrayOrderedPairs.length; i++) {
			auxRatio = calcRatio(arrayOrderedPairs[i][ColumnA], arrayOrderedPairs[i][ColumnB]);
			Util.debugF("[%d, %d] = %f - %f", arrayOrderedPairs[i][ColumnA], arrayOrderedPairs[i][ColumnB], auxRatio,
					ratio);
			Util.debug("");

			if (auxRatio > ratio) {
				// Então coloca o ai e o bi no array S*.
				arrayS[cont][ColumnA] = arrayOrderedPairs[i][ColumnA];
				arrayS[cont][ColumnB] = arrayOrderedPairs[i][ColumnB];

				// Incrementa o contatdor.
				cont++;

				// Atualiza o R(razão).
				ratio = updateRatio(arrayS, cont);
			}
		}
	}

	/**
	 * Calcula a razão baseado nos valores que existem no conjunto S*;
	 * 
	 * @param arrayS
	 * @param cont
	 * @return
	 */
	private double updateRatio(int[][] arrayS, int cont) {
		long a = 0;
		long b = 0;

		for (int i = 0; i < cont; i++) {
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
	 * @return
	 */
	private double calcRatio(long a, long b) {
		return (double) a / b;
	}
}