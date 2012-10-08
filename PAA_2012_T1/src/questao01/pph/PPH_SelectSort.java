package questao01.pph;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import utilidade.Log;
import utilidade.SelectionSort;
import utilidade.Utils;

public class PPH_SelectSort {
  // O nome do arquivo de input padrão(usado para testes).
  private static final String DEFAULT_INPUT_FILE_NAME = "src/questao01/pph/pph_1000.txt";

  // A matriz que vai conter os valores que validam o lemma.
  LinkedList<OrderedPair>     listS;

  // Para evitar colocar numeros literais no código.
  private int                 somaA                   = 0;
  private int                 somaB                   = 0;
  OrderedPair                 parInicial;

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
      Log.isDebugging = false;
    }

    PPH_SelectSort pph = new PPH_SelectSort();
    pph.run(inputFile);
  }

  /**
   * O método run foi criado apenas para que não fosse necessário ficar usando variáveis e métodos estáticos.
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
      // int quantityOfInputValues = 100;

      // A razão que deve ser calculada e apresentada no final.
      float finalRatio = 0;
      int iterations = 0;
      // Esta linha é apenas para forçar uma quebra de linha depois dos
      // números.
      scanner.nextLine();

      List<OrderedPair> listOrderedPairs = null;
      Log.printOntoScreen("Lendo arquivo...");
      List<OrderedPair> listOriginalPair = Utils.getValuesFromInputFile(scanner, quantityOfInputValues);
      Log.printOntoScreen("Arquivo completo...");
      long startTime = System.currentTimeMillis();

      Log.printOntoScreen("Calculando...");
      //while (System.currentTimeMillis() - startTime < 5000) {
      listOrderedPairs = new LinkedList<OrderedPair>();
      // Obtém os valores que correspondem ao b = {1,.., n}
      listOrderedPairs.addAll(listOriginalPair);
      // Atribuindo o par inicial
      parInicial = listOrderedPairs.get(0);
      // Removendo da lista o par inicial
      listOrderedPairs.remove(0);

      listS = new LinkedList<OrderedPair>();

      startTime = System.currentTimeMillis();
      // Ordanando a lista
      SelectionSort selectSort = new SelectionSort();
      //int size = listOrderedPairs.size();
      int size = quantityOfInputValues - 1;
      MedianaPair mediana = selectSort.selectIterativo(listOrderedPairs, 0, size, size / 2);
      long finishTime = System.currentTimeMillis();
      Log.printOntoScreenF("Tempo de execução: %d\n", finishTime - startTime);

      startTime = System.currentTimeMillis();
      finalRatio = maximumRatio(listOrderedPairs, size, mediana);
      finishTime = System.currentTimeMillis();
      Log.printOntoScreenF("Tempo de execução: %d\n", finishTime - startTime);
      iterations++;
      // }
      finishTime = System.currentTimeMillis() - startTime;

      float media = (float) finishTime / iterations;
      Log.printOntoScreenF("Razão final: %f\n", finalRatio);
      Log.printOntoScreenF("Tamanho de S: %d \n", listS.size());
      Log.printOntoScreen("Conjunto S*: ");
      Log.printList(listS);

      Log.printOntoScreen("Iteraçoes realizadas: " + iterations);
      Log.printOntoScreenF("Tempo de execução: %f\n", media);

    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * @param listNOfOrderedPairs
   * @param mediana
   * @param count
   * @return A razão máxima.
   */
  private float maximumRatio(List<OrderedPair> listNOfOrderedPairs, int count, MedianaPair mediana) {
    //long startTime = System.currentTimeMillis();
    float maximumRatio = parInicial.getRatio();
    Log.debugF("Razão (a0, b0): %f\n", maximumRatio);

    long iterations = 0;
    // Zerando as variáveis iniciais.
    listS = new LinkedList<OrderedPair>();

    OrderedPair auxlPar;
    for (int i = mediana.index; i < count; i++) {
      iterations++;
      auxlPar = listNOfOrderedPairs.get(i);

      Log.debugF("[%d, %d] = %f - %f\n", auxlPar.getA(), auxlPar.getB(), auxlPar.getRatio(), maximumRatio);

      if (auxlPar.getRatio() > maximumRatio) {
        // Então coloca o par(ai e o bi) na lista S.
        listS.add(auxlPar);
        SomaRazao(auxlPar);
        // Atualiza o R(razão).
        maximumRatio = calcularRazao();
        Log.debugF("Nova razão: %f\n", maximumRatio);

        if (isLemmaNotValid(listS, maximumRatio)) {
          // Se existir algum par(ai / bi) que não seja maior do que a razão atual, este par deve ser removido do listS e uma nova razão deve ser calculada.
          maximumRatio = calcularRazao();
        }
      }
    }
    //long endTime = System.currentTimeMillis();
    //System.out.println("maximumRatio - Fim: " + (endTime - startTime));
    Log.printOntoScreenF("Número de passos: %d\n", iterations);
    return maximumRatio;
  }

  private void SomaRazao(OrderedPair auxlPar) {
    this.somaA += auxlPar.getA();
    this.somaB += auxlPar.getB();
  }

  private void SubtracaoRazao(OrderedPair auxlPar) {
    this.somaA -= auxlPar.getA();
    this.somaB -= auxlPar.getB();
  }

  private float calcularRazao() {
    return (float) (this.somaA + this.parInicial.getA()) / (this.somaB + this.parInicial.getB());
  }

  /**
   * @param listS
   * @param maximumRatio
   * @return Retorna true se existiu algum par ordenado na lista S que não era verdade em relação ao Lemma.
   */
  private boolean isLemmaNotValid(List<OrderedPair> listS, float maximumRatio) {
    boolean invalid = false;

    OrderedPair auxlPar;
    int count = 0;
    while (count < listS.size()) {
      auxlPar = listS.get(count);

      // Se o ratio for menor, então o par ordenado deve ser removido.
      if (auxlPar.getRatio() < maximumRatio) {
        Log.debugF("Lemma não é verdade em :[%d, %d] = %f - %f\n", auxlPar.getA(), auxlPar.getB(), auxlPar.getRatio(), maximumRatio);
        invalid = true;

        // Tenho que remover o par ordenado na posição i.
        listS.remove(count);
        SubtracaoRazao(auxlPar);
      }
      else {
        count++;
      }
    }

    return invalid;
  }
}