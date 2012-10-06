/**
 * Esta o algoritmo que resolve a 1ª questão do T1 de PAA - PUC-Rio 2012.2. Alunos: Luciano Sampaio, Igor Oliveira e Marcio Rosemberg.
 * http://en.wikipedia.org/wiki/Selection_algorithm#Properties_of_pivot
 */
package questao01.pph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import utilidade.Log;
import utilidade.Utils;

public class PPH_06 {

  // O nome do arquivo de input padrão(usado para testes).
  private static final String DEFAULT_INPUT_FILE_NAME = "src/questao01/pph/pph_10.txt";

  // A matriz que vai conter os valores que validam o lemma.
  List<OrderedPair>           listS;

  // Este é o par(a0, b0).
  OrderedPair                 initialPair;

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

    PPH_06 pph = new PPH_06();
    pph.run(inputFile);
  }

  /**
   * O método run foi criado apenas para que não fosse necessário ficar usando variáveis e métodos estáticos.
   * 
   * @param inputFile
   */
  private void run(String inputFile) {
    try {
      Log.printOntoScreen("Iniciado...");
      // Abre o arquivo para que o dados possam ser lidos.
      Scanner scanner = new Scanner(new File(inputFile));

      // Obtém a quantidade de números contidos neste arquivo + 1(o a0 e
      // b0 não entram) * 2(porque é a mesma quantidade para o A e para o
      // B).
      // int quantityOfInputValues = scanner.nextInt() + 1;
      int quantityOfInputValues = 100;

      // A razão que deve ser calculada e apresentada no final.
      float finalRatio = 0;

      // Esta linha é apenas para forçar uma quebra de linha depois dos
      // números.
      scanner.nextLine();

      // Obtém os valores que correspondem ao a = {1,.., n}
      List<OrderedPair> listNOfOrderedPairs = Utils.getValuesFromInputFile(quantityOfInputValues);
      Log.printOntoScreen("Obtendo valores do arquivo de entrada...");
      // List<OrderedPair> listNOfOrderedPairs = Utils
      // .getValuesFromInputFile(scanner, quantityOfInputValues);

      // Este é o par(a0, b0).
      initialPair = listNOfOrderedPairs.get(0);
      // Remove o par(a0, b0) da lista N de pares ordenados
      listNOfOrderedPairs.remove(0);

      long startTime = System.currentTimeMillis();
      long iterations = 0;
      Log.printOntoScreen("Calculando...");
      //while (System.currentTimeMillis() - startTime < 5000) {
      // Inicia a matriz S com o tamanho de elementos de pares
      // ordenados
      // e 2 colunas.
      listS = new LinkedList<OrderedPair>();

      // Calcula a razão máxima.
      finalRatio = maximumRatio(listNOfOrderedPairs);
      iterations++;
      //}
      long finishTime = System.currentTimeMillis() - startTime;

      float media = (float) finishTime / iterations;
      Log.printOntoScreenF("Razão final: %f\n", finalRatio);
      Log.printOntoScreenF("Conjunto S* com %d elementos: \n", listS.size());
      Log.printList(listS);

      Log.printOntoScreen("Tamanho do N: " + (quantityOfInputValues - 1));
      Log.printOntoScreen("Iteraçoes realizadas: " + iterations);
      Log.printOntoScreenF("Tempo de execução: %f\n", media);

    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * @param listNOfOrderedPairs
   * @return A razão máxima.
   */
  private float maximumRatio(List<OrderedPair> listNOfOrderedPairs) {
    // O R inicial é calculado pelo a0 / b0.
    float maximumRatio = initialPair.getRatio();
    Log.debugF("Razão (a0, b0): %f\n", maximumRatio);

    long iterations = 0;
    for (int k = 0; k < listNOfOrderedPairs.size(); k++) {
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
  }

  /**
   * Calcula a razão baseado nos valores que existem no conjunto S*;
   * 
   * @param listS
   * @return Atualiza a razão baseada em A0 + somatório Ai até BN dividido por B0 + somatório Bi até BN.
   */
  private float updateRatio(List<OrderedPair> listS) {
    long a = initialPair.getA();
    long b = initialPair.getB();

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