package questao01.pph;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import utilidade.Log;
import utilidade.RandomizedSelectPivot;
import utilidade.Utils;

public class PPH_RandomizedSelectPivot {

  //O nome do arquivo de input padrão(usado para testes).
  private static final String DEFAULT_INPUT_FILE_NAME = "test/pph/pph_10.txt";

  // A matriz que vai conter os valores que validam o lemma.
  List<OrderedPair>           listS;

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
    PPH_RandomizedSelectPivot pph = new PPH_RandomizedSelectPivot();
    pph.run(inputFile);
  }

  /**
   * O método run foi criado apenas para que não fosse necessário ficar usando variáveis e métodos estáticos.
   * 
   * @param inputFile
   */
  public void run(String inputFile) {
    try {
      Log.printOntoScreen("Iniciado Randomized Select com Pivot - Complexidade Máxima O(n2)...");
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

      List<OrderedPair> listOriginalPair = Utils.getValuesFromInputFile(scanner, quantityOfInputValues);
      long startTime = System.currentTimeMillis();
      Log.printOntoScreen("Calculando...");
      // Atribuindo o par inicial
      parInicial = listOriginalPair.get(0);
      // Removendo da lista o par inicial
      //listOriginalPair.remove(0);

      while (System.currentTimeMillis() - startTime < 5000) {
        // Obtém os valores que correspondem ao b = {1,.., n}
        listS = new LinkedList<OrderedPair>();

        // Inicia a matriz S com o tamanho de elementos de pares ordenados e 2 colunas.
        somaA = parInicial.getA();
        somaB = parInicial.getB();

        // Ordanando a lista
        RandomizedSelectPivot randomizedSelect = new RandomizedSelectPivot();
        int size = quantityOfInputValues - 1;
        MedianaPair<OrderedPair> mediana = randomizedSelect.findMediana(listOriginalPair);

        finalRatio = maximumRatio(listOriginalPair, size, mediana);
        listS.add(0, parInicial);

        iterations++;
      }
      long finishTime = System.currentTimeMillis() - startTime;

      float media = (float) finishTime / iterations;
      Log.printList(listS);

      Log.printOntoScreenF("Conjunto S* com %d elementos: \n", listS.size());
      Log.printOntoScreen("Tamanho do N: " + (quantityOfInputValues - 1));
      Log.printOntoScreenF("Razão final: %f\n", finalRatio);
      Log.printOntoScreen("Iteraçoes realizadas: " + iterations);
      Log.printOntoScreenF("Tempo de execução Médio: %f\n", media);
      Log.printOntoScreenF("Tempo de execução Total: %d\n\n", finishTime);
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
    float maximumRatio = parInicial.getRatio();
    Log.debugF("Razão (a0, b0): %f\n", maximumRatio);

    // Zerando as variáveis iniciais.
    OrderedPair auxlPar;
    //for (int i = count - 1; i >= mediana.getIndex(); i--) {
    for (int i = mediana.getIndex(); i < listNOfOrderedPairs.size(); i++) {
      auxlPar = listNOfOrderedPairs.get(i);

      Log.debugF("[%d, %d] = %f - %f\n", auxlPar.getA(), auxlPar.getB(), auxlPar.getRatio(), maximumRatio);

      if (auxlPar.getRatio() > maximumRatio) {
        // Então coloca o par(ai e o bi) na lista S.
        listS.add(auxlPar);

        // Atualiza o R(razão).
        maximumRatio = updateRatio(listS);
        //Log.debugF("Nova razão: %f\n", maximumRatio);

        if (isLemmaNotValid(listS, maximumRatio)) {
          // Se existir algum par(ai / bi) que não seja maior do que a
          // razão atual, este par deve ser removido do listS e uma
          // nova razão deve ser calculada.
          //          maximumRatio = updateRatio(listS);
          maximumRatio = Utils.calcRatio(somaA, somaB);
        }
      }
    }
    Log.debug("Fim !!!");
    return maximumRatio;
  }

  /**
   * Calcula a razão baseado nos valores que existem no conjunto S*;
   * 
   * @param listS
   * @return Atualiza a razão baseada em A0 + somatório Ai até BN dividido por B0 + somatório Bi até BN.
   */
  private float updateRatio(List<OrderedPair> listS) {
    //incIterations();

    OrderedPair auxPar = listS.get(listS.size() - 1);
    somaA += auxPar.getA();
    somaB += auxPar.getB();
    //}
    return Utils.calcRatio(somaA, somaB);
  }

  /**
   * @param listS
   * @param maximumRatio
   * @return Retorna true se existiu algum par ordenado na lista S que não era verdade em relação ao Lemma.
   */
  private boolean isLemmaNotValid(List<OrderedPair> listS, float maximumRatio) {
    boolean invalid = false;

    List<OrderedPair> listAux = new LinkedList<OrderedPair>();

    OrderedPair auxPar;
    for (Iterator<OrderedPair> iterator = listS.iterator(); iterator.hasNext();) {
      auxPar = iterator.next();
      //incIterations();

      // Se o ratio for menor, então o par ordenado deve ser removido.
      if (auxPar.getRatio() < maximumRatio) {
        //Log.debugF("Lemma não é verdade em :[%d, %d] = %f - %f\n", auxPar.getA(), auxPar.getB(), auxPar.getRatio(), maximumRatio);
        invalid = true;

        // Tenho que remover o par ordenado na posição i.
        somaA -= auxPar.getA();
        somaB -= auxPar.getB();
        // Adiciona o item para ser removido.
        listAux.add(auxPar);
      }
    }

    // Remove os itens do conjunto S*.
    listS.removeAll(listAux);

    return invalid;
  }
}