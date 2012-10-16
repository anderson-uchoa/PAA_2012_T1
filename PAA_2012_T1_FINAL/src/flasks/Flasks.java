/**
 * Este é o algoritmo que resolve a 2ª questão do T1 de PAA - PUC-Rio 2012.2.<br/>
 * Alunos: Luciano Sampaio, Igor Oliveira e Marcio Rosemberg.
 * 
 * Para um número de 8 bits, <br/>
 * temos: N = 11111111 = 255 (Tamanho da escada). <br/>
 * X = 00111110 = 62 (degrau em que o frasco quebra).
 */
package flasks;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import util.Logger;
import flasks.utils.FlasksBase;

public class Flasks extends FlasksBase implements Runnable {

  // Tempo em que a thread vai esperar para verificar se o tempo de processamento já foi excedido.
  private int                 TIME_OUT_PROCESS        = 120;

  // O nome do input padrão(usado para testes).
  private static final String DEFAULT_INPUT_FILE_NAME = "test/flasks/bignum_32_01.dat";
  // private static final String DEFAULT_INPUT_FILE_NAME = "test/flasks/bignum_64_01.dat";
  // private static final String DEFAULT_INPUT_FILE_NAME = "test/flasks/bignum_128_01.dat";
  // private static final String DEFAULT_INPUT_FILE_NAME = "test/flasks/bignum_192_01.dat";
  // private static final String DEFAULT_INPUT_FILE_NAME = "test/flasks/bignum_256_01.dat";

  // A quantidade de frascos que vai ser usada em cada teste.
  // Como 256 é a maior instância que será testada, então para instâncias com menos bits, 256 irá simular
  // a quantidade infinita como é pedido em um item da questão dos frascos.
  public static int[]         quantityOfFlasks        = { 256, 192, 128, 64, 32, 16, 8, 4, 2, 1 };

  // Informa se o algoritmo deve continuar processando o arquivo com a quantidade atual de frascos.
  private boolean             keepGoing;

  // Momento em que o algoritmo iniciou sua execução.
  private long                startTime;

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
      Logger.isDebugging = false;
    }

    Flasks flasks = new Flasks();
    for (int qtyflasks : quantityOfFlasks) {
      flasks.run(inputFile, qtyflasks);
    }
  }

  /**
   * O método run foi criado apenas para que não fosse necessário ficar usando variáveis e métodos estáticos.
   * 
   * @param inputFile O arquivo que contém as instâncias de dados que serão usadas pelo programa.
   * @param qtyflasks A quantidade de frascos que será usada para processar o arquivo.
   */
  public void run(String inputFile, int qtyflasks) {
    try {
      // Informa se o algoritmo deve continuar processando o arquivo com a quantidade atual de frascos.
      setKeepGoing(true);
      // Abre o arquivo para que os dados possam ser lidos.
      Scanner scanner = new Scanner(new File(inputFile));

      // Obtém o tamanho em bits dos números contidos neste arquivo.
      // Como não estamos usando, o código esta comentado.
      //short sizeInBitsOfInputValues = scanner.nextShort();
      scanner.nextShort();

      // Obtém a quantidade de números contidos neste arquivo.
      int quantityOfInputValues = scanner.nextInt();

      // Esta linha é apenas para forçar uma quebra de linha depois dos números.
      scanner.nextLine();

      // O conteúdo lido da instância(linha) atual.
      String inputValue;
      // Um contador para saber quando todas as instâncias já foram processados.
      int cont = 0;

      // De 2 em 2 minutos, ele dar um aviso de ainda esta vivo.
      //ThreadControl control = new ThreadControl(10, this);
      ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(1);
      scheduler.setContinueExistingPeriodicTasksAfterShutdownPolicy(false);
      // Inicia a thread que vai ficar rodando de 10 em 10 minutos.
      scheduler.scheduleAtFixedRate(this, TIME_OUT_PROCESS, TIME_OUT_PROCESS, SECONDS);

      // A quantidade de operações que foi necessária para que o resultado esperado fosse encontrado.
      setOperations(0);

      // Este loop vai iterar por todos as instâncias encontrados dentro do arquivo de entrada.
      while ((cont < quantityOfInputValues) && (isKeepGoing())) {
        startTime = System.currentTimeMillis();

        // Obtém o número (como uma string).
        // Exemplo: 00111110.
        inputValue = scanner.nextLine();

        // Encontra em que degrau da escada o frasco quebrou.
        findTheStepItBreaks(inputValue, qtyflasks);

        cont++;
      }

      // Fecha o scanner e libera o acesso ao arquivo de entrada.
      scanner.close();

      // Cancela a thread que avisa que o programa ainda esta vivo.
      scheduler.shutdownNow();
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
    // Obtém o tamanho em bits dos números contidos neste arquivo.
    int sizeInBitsOfInputValues = input.length;

    // Baseado na quantidade de frascos disponíveis, precisamos saber de quantos em quantos passos(bits) vamos andar.
    int eachStep = inputValue.length() / flasks;
    eachStep = (eachStep > 0) ? eachStep : 1;

    // Posição inicial na qual vamos começar o processamento.
    int startPos = 0;
    // O -1 é para evitar indexOutOfBound, porque um elemento com 8 bits, vai de 0 à 7.
    int endPos = (eachStep - 1);

    // A quantidade de operações que foi necessária para que o resultado esperado fosse encontrado.
    // setOperations(0);

    while ((endPos < sizeInBitsOfInputValues) && (isKeepGoing())) {
      // Este while(true) é o mesmo que: "Enquanto não quebrar um frasco, aumente 1 andar e tente de novo".
      while (isKeepGoing()) {
        // Só vai exibir esta informação quando estiver em modo debug.
        Logger.debug("isKeepGoing: " + isKeepGoing());
        incOperations();

        // Incrementa em 1 o valor do próximo degrau. Tenho que incrementar logo no inicio, 
        // porque não existe degrau 00000000, o primeiro é 00000001.
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

      // Atualiza a posição inicial e final do array que esta sendo processada.
      startPos = endPos + 1;
      endPos += eachStep;
    }

    // Converte o array com o resultado final(andar em que o frasco quebra) em uma string.
    return convertFromArray(output);
  }

  /**
   * Converte a string de entrada em um array de booleans(true ou false).
   * 
   * @param input A string que será convertida para array.
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
   * Incrementa em 1 o valor "binário" passado como parâmetro.
   * 
   * @param input O array que terá o valor atual incrementado em 1.
   * @param startPos A posição inicial que irá ser considerada dentro do array.
   * @param endPos A posição final que irá ser considerada dentro do array.
   */
  private void increment(boolean[] input, int startPos, int endPos) {
    // Informa se houve um buffer over flow, o valor inicial é falso.
    boolean overFlow = false;
    int i = endPos;

    // Este método sempre incrementa o valor atual mais 1, se o valor no primeiro elemento for 0, 
    // adicionamos mais 1 e acaba, NÃO TEM MAIS NADA PARA FAZER.
    // Se for 1, ai temos um over flow e precisamos de mais testes.
    if (!input[i]) { // input = 0
      input[i] = true;
    }
    else if (input[i]) { // input = 1
      input[i] = false;
      overFlow = true;
      // A operação é feita da direita para a esquerda.
      // Decrementa a posição do índice que está sendo testado.
      i--;
    }

    while (overFlow) {
      if (!input[i]) { // input = 0
        if (overFlow) {
          input[i] = true;
        }
        // Se esta situação acontecer, acaba.
        overFlow = false;
      }
      else if (input[i]) { // input = 1
        if (overFlow) {
          input[i] = false;
          overFlow = true;
        }
      }
      // A operação é feita da direita para a esquerda.
      // Decrementa a posição do índice que está sendo testado.
      i--;
    }
  }

  /**
   * Verifica se o degrau que jogamos foi maior ou igual ao degrau que quebra.
   * 
   * @param output O array que contém os degraus que estamos processando.
   * @param startPos A posição inicial que irá ser considerada dentro do array.
   * @param endPos A posição final que irá ser considerada dentro do array.
   * @param input O array que contém o degrau que temos certeza que vai quebrar, é um dado usado apenas para comparações.
   * @return True se quebrou, false se não quebrou.
   */
  private int checkIfBreaks(boolean[] output, int startPos, int endPos, boolean[] input) {
    for (int i = startPos; i <= endPos; i++) {
      if (output[i] != input[i]) {
        // Se output = 1 e input = 0 é porque jogamos em um degrau mais alto.
        // Se output = 0 e input = 1 é porque ainda estamos em um degrau mais baixo.
        return ((output[i]) && (!input[i])) ? 1 : -1;
      }
    }

    // Se chegar aqui é porque eles são iguais.
    return 0;
  }

  /**
   * @param input O array que contém os degraus que estamos processando.
   * @param startPos A posição inicial que irá ser considerada dentro do array.
   * @param endPos A posição final que irá ser considerada dentro do array.
   */
  private void decrement(boolean[] input, int startPos, int endPos) {
    for (int i = startPos; i <= endPos; i++) {
      input[i] = false;
    }
  }

  /**
   * Converte um array de boolean para uma string, onde true = 1 e false = 0.
   * 
   * @param input O array que vai ser convertido
   * @return Uma string, onde true = 1 e false = 0.
   */
  private String convertFromArray(boolean[] input) {
    int startPos = 0;
    int endPos = input.length;

    StringBuilder output = new StringBuilder();

    for (int i = startPos; i < endPos; i++) {
      // Se o valor for true,  então o valor "1" é adicionado.
      // Se o valor for false, então o valor "0" é adicionado.
      output.append((input[i]) ? "1" : "0");
    }

    return output.toString();
  }

  public void setTimeOut(int timeOutProcess) {
    this.TIME_OUT_PROCESS = timeOutProcess;
  }

  public synchronized boolean isKeepGoing() {
    return keepGoing;
  }

  public void setKeepGoing(boolean _keepGoing) {
    keepGoing = _keepGoing;
  }

  @Override
  public void run() {
    // Momento em que o algoritmo terminou sua execução.
    long finishTime = System.currentTimeMillis() - startTime;

    if (finishTime >= TIME_OUT_PROCESS) {
      // Imprime a quantidade de operação já processadas.
      DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
      Logger.printOntoScreenF("Data: %s - Total de iterações: %s \n", dateFormat.format(new Date()), getStrOperations());

      // Informa que o programa deve parar de processar a quantidade de frascos que esta processando neste momento.
      setKeepGoing(false);
    }
  }
}