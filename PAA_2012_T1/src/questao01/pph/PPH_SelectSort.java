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
  private static final String DEFAULT_INPUT_FILE_NAME = "src/questao01/pph/pph_1000000.txt";

  // A matriz que vai conter os valores que validam o lemma.
  LinkedList<OrderedPair>     listS;

  // Para evitar colocar numeros literais no código.
  int                         somaA                   = 0;
  int                         somaB                   = 0;
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
      // Ordanando a lista
      utilidade.SelectionSort selectSort = new SelectionSort();
      int size = listOrderedPairs.size();
      OrderedPair teste = selectSort.select(listOrderedPairs, 0, size, size / 2);
      //      for (int i = 0; i < listOrderedPairs.indexOf(teste); i++) {
      //        if (listOrderedPairs.get(i).compareTo(teste) > 0) {
      //          System.out.println("opsss1!!!!!");
      //        }
      //      }
      //MedianaPair mediana = selectSort.partition(listOrderedPairs, 0, listOrderedPairs.size(), teste);
      //mediana.setOrderedPair(teste);
      //finalRatio = maximumRation(listOrderedPairs, listS, parInicial);
      int index = listOrderedPairs.indexOf(teste);
      finalRatio = maximumRatio(listOrderedPairs, new MedianaPair(teste, index));
      iterations++;
      // }
      long finishTime = System.currentTimeMillis() - startTime;

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
   * @return A razão máxima.
   */
  private float maximumRatio(List<OrderedPair> listNOfOrderedPairs, MedianaPair mediana) {

    float maximumRatio = parInicial.getRatio();
    Log.debugF("Razão (a0, b0): %f\n", maximumRatio);

    long iterations = 0;
    for (int k = mediana.index; k < listNOfOrderedPairs.size(); k++) {
      // Zerando as variáveis iniciais.
      listS = new LinkedList<OrderedPair>();

      OrderedPair auxlPar;
      for (int i = 0; i < listNOfOrderedPairs.size(); i++) {
        iterations++;
        auxlPar = listNOfOrderedPairs.get(i);

        Log.debugF("[%d, %d] = %f - %f\n", auxlPar.getA(), auxlPar.getB(), auxlPar.getRatio(), maximumRatio);

        if (auxlPar.getRatio() > maximumRatio) {
          // Então coloca o par(ai e o bi) na lista S.
          listS.add(auxlPar);

          // Atualiza o R(razão).
          maximumRatio = updateRatio(listS);
          Log.debugF("Nova razão: %f\n", maximumRatio);

          if (isLemmaNotValid(listS, maximumRatio)) {
            // Se existir algum par(ai / bi) que não seja maior do que a razão atual, este par deve ser removido do listS e uma nova razão deve ser calculada.
            maximumRatio = updateRatio(listS);
          }
        }
      }
    }
    Log.printOntoScreenF("Número de passos: %d\n", iterations);
    return maximumRatio;
    //float aux = 0;
    //this.somaA = parInicial.getA();
    //this.somaB = parInicial.getB();
    //float maximumRation = parInicial.getRatio();
    //for (int i = mediana.index; i <= listNOfOrderedPairs.size() - 1; i++) {
    //  aux = listNOfOrderedPairs.get(i).getRatio();
    //  if (aux > maximumRation) {
    //   this.somaA += listNOfOrderedPairs.get(i).getA();
    //   this.somaB += listNOfOrderedPairs.get(i).getB();
    //   maximumRation = (float) this.somaA / this.somaB;
    //  listS.add(listNOfOrderedPairs.get(i));
    // }
    // }
    //return maximumRation;

  }

  /**
   * Calcula a razão baseado nos valores que existem no conjunto S*;
   * 
   * @param listS
   * @return Atualiza a razão baseada em A0 + somatório Ai até BN dividido por B0 + somatório Bi até BN.
   */
  private float updateRatio(List<OrderedPair> listS) {
    long a = parInicial.getA();
    long b = parInicial.getB();

    OrderedPair auxlPar;
    for (int i = 0; i < listS.size(); i++) {
      auxlPar = listS.get(i);
      // Util.debugF("Somátorio de: [%d, %d]\n", auxlPar.getA(),
      // auxlPar.getB());
      a += auxlPar.getA();
      b += auxlPar.getB();
    }
    return Utils.calcRatio(a, b);
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
      }
      else {
        count++;
      }
    }

    return invalid;
  }
}