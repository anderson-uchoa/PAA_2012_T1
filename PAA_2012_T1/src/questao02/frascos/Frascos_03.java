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
  //private static final String DEFAULT_INPUT_FILE_NAME = "src/questao02/frascos/bignum_08_02.txt";

  //private static final String DEFAULT_INPUT_FILE_NAME = "src/questao02/frascos/bignum_16_01.txt";

  //  private static final String DEFAULT_INPUT_FILE_NAME = "src/questao02/frascos/bignum_32_01.txt";
  //  private static final String DEFAULT_INPUT_FILE_NAME = "src/questao02/frascos/bignum_32_02.txt";
  //private static final String DEFAULT_INPUT_FILE_NAME = "src/questao02/frascos/bignum_32_03.txt";

  //  private static final String DEFAULT_INPUT_FILE_NAME = "src/questao02/frascos/bignum_64_01.txt";
  //  private static final String DEFAULT_INPUT_FILE_NAME = "src/questao02/frascos/bignum_64_02.txt";
  //private static final String DEFAULT_INPUT_FILE_NAME = "src/questao02/frascos/bignum_64_03.txt";

  //  private static final String DEFAULT_INPUT_FILE_NAME = "src/questao02/frascos/bignum_128_01.txt";
  //  private static final String DEFAULT_INPUT_FILE_NAME = "src/questao02/frascos/bignum_128_02.txt";
  //private static final String DEFAULT_INPUT_FILE_NAME = "src/questao02/frascos/bignum_128_03.txt";

  //  private static final String DEFAULT_INPUT_FILE_NAME = "src/questao02/frascos/bignum_192_01.txt";
  //  private static final String DEFAULT_INPUT_FILE_NAME = "src/questao02/frascos/bignum_192_02.txt";
  //private static final String DEFAULT_INPUT_FILE_NAME = "src/questao02/frascos/bignum_192_03.txt";

  //  private static final String DEFAULT_INPUT_FILE_NAME = "src/questao02/frascos/bignum_256_01.txt";
  //  private static final String DEFAULT_INPUT_FILE_NAME = "src/questao02/frascos/bignum_256_02.txt";
  //private static final String DEFAULT_INPUT_FILE_NAME = "src/questao02/frascos/bignum_256_03.txt";

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
      int[] quantityOfFlasks = { 32, 16, 8, 4, 2, 1 };

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

        //String stepItBroke = null;
        // Este loop vai iterar o mesmo número que foi lido do arquivo vezes a quantidade de frascos que vai ser testado. ex: 16, 8, 4, 2, 1
        for (int flasks : quantityOfFlasks) {
          startTime = System.currentTimeMillis();
          iterations = 0;

          // Encontra em que degrau da escada o frasco quebrou.
          findTheStepItBreaks(inputValue, flasks);

          finishTime = System.currentTimeMillis() - startTime;
          Log.printOntoScreenF("Tempo de execução: %d\n", finishTime);
        }

        Log.printOntoScreen("");
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

    int sizeInBitsOfInputValues = input.length;

    // Baseado na quantidade de frascos disponíveis, precisamos saber de quantos em quantos passos(bits) vamos andar.
    int eachStep = inputValue.length() / flasks;
    eachStep = (eachStep > 0) ? eachStep : 1;

    int startPos = 0;
    // O -1 é para evitar indexOutOfBound, porque um elemento com 8 bits, vai de 0 à 7.
    int endPos = (eachStep - 1);

    // A quantidade de frascos que já usamos.
    int usedFlasks = 0;
    while (endPos < sizeInBitsOfInputValues) {
      while (true) {
        // Aumenta a quantidade de passos(tentativas de quebrar um frasco) que já foram dados.
        iterations++;

        // Incrementa em 1 o valor do próximo degrau. Tenho que incrementar logo no inicio, 
        // porque não existe degrau 00000000, o primeiro é 00000001.
        // Se não conseguir incrementar é porque houve um estouro 11 + 1 = 100.
        increment(output, startPos, endPos);

        // Verifica se o degrau que jogamos foi maior ou igual ao degrau que quebra.
        int result = checkIfBreaks(output, startPos, endPos, input);
        if (result == 0) {
          // Se quebrou sai do loop de dentro.
          break;
        }
        else if (result > 0) {
          // Se jogou acima do degrau que quebra, então temos que voltar.
          decrement(output, startPos, endPos);
          break;
        }
      }

      // Aumenta em 1 a quantidade de frascos usados.
      usedFlasks++;
      startPos = endPos + 1;
      endPos += eachStep;
    }

    String stepItBroke = convertFromArray(output);
    Log.printOntoScreenF("Entrada %s, saída %s com %2d frasco(s), quebraram %d e precisou de %2d passos. ", inputValue, stepItBroke, flasks, usedFlasks,
      iterations);

    return stepItBroke;
  }

  /**
   * Verifica se o degrau que jogamos foi maior ou igual ao degrau que quebra.
   * 
   * @param output
   * @param startPos
   * @param endPos
   * @param input
   * @return True se quebrou, false se não quebrou.
   */
  private int checkIfBreaks(boolean[] output, int startPos, int endPos, boolean[] input) {
    for (int i = startPos; i <= endPos; i++) {
      if (output[i] != input[i]) {
        // output = 1 e input = 0.
        return ((output[i]) && (!input[i])) ? 1 : -1;
      }
    }

    // Eles são iguais.
    return 0;
  }

  /**
   * Incrementa em 1 o valor do próximo degrau. Tenho que incrementar logo no inicio, porque não existe degrau 00000000, o primeiro é 00000001.
   * 
   * @param input
   * @param startPos
   * @param endPos
   * @return True se foi possível incrementar, False se houve um estouro 11 + 1 = 100.
   */
  private boolean increment(boolean[] input, int startPos, int endPos) {
    // Obtém o tamanho da entrada.
    int length = (endPos - startPos) + 1;

    // O incremento para resolver a nossa questão do trabalho sempre será 1, 
    // mas para que o código ficasse genérico, fizemos o array de incremento 
    // com o mesmo tamanho do de entrada mas com todos os seus valores inicializados com 0 -> false.
    boolean[] increment = new boolean[length];
    increment[length - 1] = true;

    // Informa se houve um buffer over flow.
    boolean overFlow = false;

    // O índice J é para evitar indexOutOfBound porque nem sempre os array têm o mesmo tamanho.
    int j = endPos;
    for (int i = length - 1; i >= 0; i--) {

      if ((!input[j]) && (!increment[i])) { // input = 0, increment = 0.
        if (!overFlow) {
          input[j] = false;
        }
        else {
          input[j] = true;
          overFlow = false;
        }
        // Se esta situação acontecer, acaba.
        break;
      }
      else if ((input[j] ^ increment[i])) { // input = 0, increment = 1 or input = 1, increment = 0.
        if (!overFlow) {
          input[j] = true;
        }
        else {
          input[j] = false;
          overFlow = true;
        }
      }
      else if ((input[j]) && (increment[i])) { // input = 1, increment = 1.
        if (!overFlow) {
          input[j] = false;
        }
        else {
          input[j] = true;
        }
        overFlow = true;
      }

      j--;
    }

    // Se no final teve um overflow significa que estourou o valor máximo, 
    // então retorna false que significa que não da mais para incrementar;
    // Se não teve overflow, retorna true dizendo que a operação foi concluída com sucesso.
    return !overFlow;
  }

  /**
   * @param input
   * @param startPos
   * @param endPos
   */
  private void decrement(boolean[] input, int startPos, int endPos) {
    for (int i = startPos; i <= endPos; i++) {
      input[i] = false;
    }
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
    return convertFromArray(input, 0, input.length);
  }

  /**
   * Converte um array de boolean para uma string, onde true = 1 e false = 0.
   * 
   * @param input O array que vai ser convertido
   * @param startPos
   * @param endPos
   * @return Uma string, onde true = 1 e false = 0.
   */
  private String convertFromArray(boolean[] input, int startPos, int endPos) {
    StringBuilder output = new StringBuilder();

    for (int i = startPos; i < endPos; i++) {
      // Se o valor for true,  então o valor "1" é adicionado.
      // Se o valor for false, então o valor "0" é adicionado.
      output.append((input[i]) ? "1" : "0");
    }

    return output.toString();
  }

}