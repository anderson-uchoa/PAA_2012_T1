/**
 * Esta o algoritmo que resolve a 2ª questão do T1 de PAA - PUC-Rio 2012.2.
 * Alunos: Luciano Sampaio, Igor Oliveira e Marcio Rosemberg.
 * 
 * Para um número de 8 bits, temos:
 * N = 11111111 = 255 (Tamanho da escada).
 * X = 00111110 = 62  (degrau em que o frasco quebra).
 * 
 * Links: 
 * 	http://www.dattalo.com/technical/theory/sqrt.html
 * 	http://projectus.freehost7.com/ASIC-implementation-of-square-root-processor/?square-root-extraction-form-binary-number
 */
package questao02.frascos;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import utilidade.Util;

public class Frascos_03 {

	/**
	 * O nome do input padrão(usado para testes).
	 */
	// private static final String DEFAULT_INPUT_FILE_NAME =
	// "src/questao02/frascos/bignum_32_01.txt";
	private static final String DEFAULT_INPUT_FILE_NAME = "src/questao02/frascos/input_teste.txt";

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

		Frascos_03 f = new Frascos_03();
		f.run(inputFile);
	}

	/**
	 * O método run foi criado apenas para que não fosse necessário ficar usando
	 * variáveis e métodos publicos.
	 * 
	 * @param inputFile
	 */
	private void run(String inputFile) {
		try {
			// Abre o arquivo para que o dados possam ser lidos.
			Scanner scanner = new Scanner(new File(inputFile));

			// Obtém o tamanho em bits dos números contidos neste arquivo.
			short sizeInBitsOfInputValues = scanner.nextShort();

			// Obtém a quantidade de números contidos neste arquivo.
			int quantityOfInputValues = scanner.nextInt();

			// Esta linha é apenas para forçar uma quebra de linha depois dos
			// números.
			scanner.nextLine();

			int cont = 0;
			String inputValue;
			while (cont < quantityOfInputValues) {
				// Obtém o número (como uma string).
				// Exemplo: 00111110 = 62.
				inputValue = scanner.nextLine();

				// Obtém o valor de N (Tamanho da escada).
				// int ladderSize = getLadderSize();

				Util.debug(inputValue);

				cont++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}