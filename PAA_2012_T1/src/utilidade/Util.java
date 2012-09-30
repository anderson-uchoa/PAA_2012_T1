package utilidade;

/**
 * Esta o algoritmo que resolve a 2ª questão do T1 de PAA - PUC-Rio 2012.2.
 * Alunos: Luciano Sampaio, Igor Oliveira e Marcio Rosemberg. <br/>
 * Esta classe contém alguns métodos utils para ajudar no desenvolvimento.
 */
public class Util {

	public static boolean isDebugging;

	/**
	 * Imprime uma mensagem na tela.
	 * 
	 * @param message
	 *            A mensagem que será impressa na tela.
	 */
	public static void printOntoScreen(Object message) {
		printOntoScreen(message, true);
	}

	/**
	 * Imprime uma mensagem na tela.
	 * 
	 * @param message
	 *            A mensagem que será impressa na tela.
	 * @param breakLine
	 *            Informa se depois de imprimir a mensagem uma quebra de linha
	 *            deve ser usada.
	 */
	public static void printOntoScreen(Object message, boolean breakLine) {
		System.out.print(message);

		if (breakLine) {
			System.out.println();
		}
	}

	/**
	 * Só imprime a mensagem na tela.
	 * 
	 * @param message
	 *            A mensagem que será impressa na tela.
	 * @param args
	 *            Argumentos que serão usados no printf.
	 */
	public static void printOntoScreenF(String message, Object... args) {
		System.out.printf(message, args);
	}

	/**
	 * Só imprime a mensagem na tela se a aplicação estiver em modo debug.
	 * 
	 * @param message
	 *            A mensagem que será impressa na tela.
	 */
	public static void debug(Object message) {
		debug(message, true);
	}

	/**
	 * Só imprime a mensagem na tela se a aplicação estiver em modo debug.
	 * 
	 * @param message
	 *            A mensagem que será impressa na tela.
	 * @param breakLine
	 *            Informa se depois de imprimir a mensagem uma quebra de linha
	 *            deve ser usada.
	 */
	public static void debug(Object message, boolean breakLine) {
		if (isDebugging) {
			printOntoScreen(message, breakLine);
		}
	}

	/**
	 * Só imprime a mensagem na tela se a aplicação estiver em modo debug.
	 * 
	 * @param message
	 *            A mensagem que será impressa na tela.
	 * @param args
	 *            Argumentos que serão usados no printf.
	 */
	public static void debugF(String message, Object... args) {
		if (isDebugging) {
			printOntoScreenF(message, args);
		}
	}

	/**
	 * @param arrayS
	 */
	public static void printMatriz(int[][] matrizToPrint) {
		for (int i = 0; i < matrizToPrint.length; i++) {
			// Quando isto for verdade é porque já é hora de parar de imprimir.
			if ((matrizToPrint[i][0] == 0) && (matrizToPrint[i][1] == 0)) {
				break;
			}

			printOntoScreenF("[%3d,%3d]\n", matrizToPrint[i][0], matrizToPrint[i][1]);
		}
	}
}