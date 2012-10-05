package utilidade;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import questao01.pph.OrderedPar;

public class Utils {

	/**
	 * @param quantityOfInputValues
	 *            Quantidade de elementos que serão criados.
	 * @return A lista de pares ordenados criada aleatóriamente.
	 */
	public static List<OrderedPar> getValuesFromInputFile(int quantityOfInputValues) {
		Random rnd = new Random();

		List<OrderedPar> listNOfOrderedPairs = new LinkedList<OrderedPar>();
		int a;
		int b;

		for (int i = 0; i < quantityOfInputValues; i++) {
			a = rnd.nextInt(500) + 1;
			b = rnd.nextInt(1000) + 1;

			listNOfOrderedPairs.add(new OrderedPar(a, b));
		}

		return listNOfOrderedPairs;
	}

	/**
	 * Obtém os valores que correspondem ao A ou ao B(de uma forma generica) =
	 * {1,.., n}
	 * 
	 * @param quantityOfInputValues
	 *            A quantidade de números contidos neste arquivo.
	 * @param scanner
	 *            Objeto que lê do arquivo.
	 * @return Obtém os valores que correspondem ao A ou ao B.
	 */
	public static List<OrderedPar> getValuesFromInputFile(Scanner scanner, int quantityOfInputValues) {
		// Cria a lista de pares ordenados com a quantidade de elementos que ele
		// vai conter.
		List<OrderedPar> listNOfOrderedPairs = new LinkedList<OrderedPar>();

		List<Integer> listA = getListFromInputFile(scanner, quantityOfInputValues);
		List<Integer> listB = getListFromInputFile(scanner, quantityOfInputValues);

		for (int i = 0; i < quantityOfInputValues; i++) {
			listNOfOrderedPairs.add(new OrderedPar(listA.get(i), listB.get(i)));
		}

		return listNOfOrderedPairs;
	}

	/**
	 * Obtém os valores que correspondem ao A ou ao B(de uma forma generica) =
	 * {1,.., n}
	 * 
	 * @param quantityOfInputValues
	 *            A quantidade de números contidos neste arquivo.
	 * @param scanner
	 *            Objeto que lê do arquivo.
	 * @return Obtém os valores que correspondem ao A ou ao B.
	 */
	public static List<Integer> getListFromInputFile(Scanner scanner, int quantityOfInputValues) {
		List<Integer> listTemp = new LinkedList<Integer>();
		int inputValue;

		for (int i = 0; i < quantityOfInputValues; i++) {
			inputValue = scanner.nextInt();

			listTemp.add(inputValue);

			Log.debugF("%4d", inputValue);
		}
		Log.debug("");

		return listTemp;
	}

	/**
	 * Calcula a razão.
	 * 
	 * @param a
	 * @param b
	 * @return O resultado da divisão de a por b.
	 */
	public static float calcRatio(long a, long b) {
		return (float) a / b;
	}
}
