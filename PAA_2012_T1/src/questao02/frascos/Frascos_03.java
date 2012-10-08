/**
 * Esta o algoritmo que resolve a 2ª questão do T1 de PAA - PUC-Rio 2012.2.<br/>
 * Alunos: Luciano Sampaio, Igor Oliveira e Marcio Rosemberg.
 * 
 * Para um número de 8 bits, <br/>
 * temos: N = 11111111 = 255 (Tamanho da escada). <br/>
 * X = 00111110 = 62 (degrau em que o frasco quebra).
 */
package questao02.frascos;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import utilidade.Log;

public class Frascos_03 {

  /**
   * O nome do input padrão(usado para testes).
   */
  private static final String DEFAULT_INPUT_FILE_NAME = "src/questao02/frascos/bignum_08_01.txt";

  /**
   * A quantidade de iterações que foi necessária para que o resultado esperado fosse encontrado.
   */
  private long                iterations;

  public static void main(String[] args) {
    String inputFile;
    // Verifica se o arquivo de input foi passado como parâmetro.
    if (args.length == 1) {
      inputFile = args[0];

    }
    else {
      // Caso nenhum arquivo tenha sido informado, testa com o arquivo
      // criado para testes.
      inputFile = DEFAULT_INPUT_FILE_NAME;

      // Informa que a applicação esta em modo debug.
      Log.isDebugging = true;
    }

    Frascos_03 f = new Frascos_03();
    f.run(inputFile);
  }

  /**
   * O método run foi criado apenas para que não fosse necessário ficar usando variáveis e métodos publicos.
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

      // A quantidade de frascos que vai ser usada em cada teste.
      int[] quantityOfFlasks = { 1, 2, 4, 8, 16 };

      // Esta linha é apenas para forçar uma quebra de linha depois dos
      // números.
      scanner.nextLine();

      String inputValue;
      long startTime;
      long finishTime;
      int cont = 0;
      // Este loop vai iterar por todos os números encontrados dentro do arquivo de entrada.
      while (cont < quantityOfInputValues) {
        // Obtém o número (como uma string).
        // Exemplo: X = 00111110 = 62.
        inputValue = scanner.nextLine();

        String stepItBroke = null;
        // Este loop vai iterar o mesmo número que foi lido do arquivo vezes a quantidade de frascos que vai ser testado. ex: 1, 2, 4, 8, 16
        for (int flasks : quantityOfFlasks) {
          startTime = System.currentTimeMillis();
          iterations = 0;

          // Encontra em que degrau da escada o frasco quebrou.
          stepItBroke = findTheStepItBreaks(inputValue, flasks);

          Log.debugF("Entrada %s, saída %s com %2d frasco(s) precisou de %2d passos. ", inputValue, stepItBroke, flasks, iterations);
          finishTime = System.currentTimeMillis() - startTime;
          Log.debugF("Tempo de execução: %d\n", finishTime);
        }

        Log.debug("");
        cont++;
      }
    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * Encontra em que degrau da escada o frasco quebrou.
   * 
   * @param inputValue O degrau em que temos certeza que vai quebrar, é um dado usado apenas para comparações.
   * @param flasks A quantidade de frascos disponivel para este teste.
   * @return O degrau da escada em que o frasco quebrou.
   */
  private String findTheStepItBreaks(String inputValue, int flasks) {
    // Converte a string de entrada em um array de booleans(true ou false).
    boolean[] input = convertFromString(inputValue);
    // Um array que irá contér o degrau máximo em que o frasco é quebrado. 
    boolean[] output = new boolean[input.length];

    // Baseado na quantidade de frascos disponíveis, precisamos saber de quantos em quantos passos(bits) vamos andar.
    int eachStep = inputValue.length() / flasks;

    int startPos = 0;
    // O -1 é para evitar indexOutOfBound, porque um elemento com 8 bits, vai de 0 à 7.
    int endPos = eachStep - 1;

    // A quantidade de frascos que já usamos.
    int usedFlasks = 0;
    while (usedFlasks < flasks) {
      // Incrementa em 1 o valor do próximo passo.
      increment(output, startPos, endPos);

      while (true) {
        // Aumenta a quantidade de passos(tentativas de quebrar um frasco) que já foram dados.
        iterations++;

        increment(output, startPos, endPos);
        break;
      }

      // Aumenta em 1 a quantidade de frascos usados.
      usedFlasks++;
      startPos = endPos + 1;
      endPos += eachStep;
    }

    return convertFromArray(output);
  }

  private boolean increment(boolean[] input, int startPos, int endPos) {
    // Obtém o tamanho da entrada.
    int length = input.length;

    // O incremento para resolver a nossa questão do trabalho sempre será 1. 
    boolean[] increment = new boolean[length];
    increment[length - 1] = true;

    // O array que irá conter o resultado final deste incremento.
    boolean[] output = new boolean[length];

    // Informa se houve um buffer over flow.
    boolean overFlow = false;

    return true;
  }

  /**
   * Converte a string de entrada em um array de booleans(true ou false).
   * 
   * @param input O degrau em que temos certeza que vai quebrar, é um dado usado apenas para comparações.
   * @return Um array de boolean correspondente a string de entrada.
   */
  private boolean[] convertFromString(String input) {
    // Obtém o tamanho da entrada.
    int length = input.length();
    boolean[] output = new boolean[length];

    for (int i = 0; i < length; i++) {
      // Se o valor for "0", então o valor false é adicionado.
      // Se o valor for "1", então o valor true é adicionado.
      output[i] = (input.charAt(i) == '0') ? false : true;
    }

    return output;
  }

  /**
   * Converte um array de boolean para uma string, onde true = 1 e false = 0.
   * 
   * @param input O array que vai ser convertido
   * @return Uma string, onde true = 1 e false = 0.
   */
  private String convertFromArray(boolean[] input) {
    StringBuilder output = new StringBuilder();

    // Obtém o tamanho da entrada.
    int length = input.length;
    for (int i = 0; i < length; i++) {
      // Se o valor for true,  então o valor "1" é adicionado.
      // Se o valor for false, então o valor "0" é adicionado.
      output.append((input[i]) ? "1" : "0");
    }

    return output.toString();
  }

}
