/**
 * Esta o algoritmo que resolve a 1ª questão do T1 de PAA - PUC-Rio 2012.2. <br/>
 * Alunos: Luciano Sampaio, Igor Oliveira e Marcio Rosemberg. <br/>
 */
package pph;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import pph.utils.OrderedPair;
import pph.utils.PPHBase;
import util.Logger;

public class PPH_O_de_N extends PPHBase {

  // O nome do arquivo de input padrão(usado para testes).
  private static final String DEFAULT_INPUT_FILE_NAME = "test/pph/pph_10000.txt";

  public static void main(String[] args) {
    String inputFile;
    // Verifica se o arquivo de input foi passado como parâmetro.
    if (args.length == 1) {
      inputFile = args[0];

    }
    else {
      // Caso nenhum arquivo tenha sido informado, testa com o arquivo criado para testes.
      inputFile = DEFAULT_INPUT_FILE_NAME;

      // Informa que a applicação esta em modo debug.
      Logger.isDebugging = false;
    }

    PPH_O_de_N pph = new PPH_O_de_N();
    pph.run(inputFile);
  }

  /**
   * Este é o método que realmente faz todo o processamento. O método run foi criado apenas para que não fosse necessário ficar usando variáveis e métodos
   * estáticos.
   * 
   * @param listNOfOrderedPairs
   */
  @Override
  public void run(List<OrderedPair> listNOfOrderedPairs) {
    try {
      Logger.printOntoScreen("Iniciado em O(N)...");

      // Momento em que o algoritmo iniciou sua execução.
      long startTime = System.currentTimeMillis();

      // A razão que deve ser calculada e apresentada no final.
      float finalRatio = 0;

      // Este é o par(a0, b0).
      initialPair = listNOfOrderedPairs.get(0);
      // Remove o par(a0, b0) da lista N de pares ordenados
      listNOfOrderedPairs.remove(0);

      // Quantidade de iterações feitas dentro de 5 segundos.
      long iterations = 0;

      while (System.currentTimeMillis() - startTime < 5000) {
        // Em cada iteração, é um novo processamento, então a quantidade de operações é setada para 0.
        setOperations(0);

        // Zerando as variáveis iniciais.
        listS = new LinkedList<OrderedPair>();

        // Seta o somatório de A e B para 0.
        resetSomatory();

        // Calcula a razão máxima.
        finalRatio = maximumRatio(listNOfOrderedPairs);

        // Incrementa a quantidade de iterações feitas dentro de 5 segundos.
        iterations++;
      }
      // Como informa na questão o par ordenado (a0, b0) sempre estará em S*.
      listS.add(0, initialPair);

      // Momento em que o algoritmo terminou sua execução.
      long finishTime = System.currentTimeMillis() - startTime;

      float media = (float) finishTime / iterations;

      //Log.printList(listS);
      Logger.printOntoScreen("Tamanho do N: " + (listNOfOrderedPairs.size()));
      Logger.printOntoScreenF("Conjunto S* com %d elementos: \n", listS.size());
      Logger.printOntoScreenF("Razão final: %f\n", finalRatio);
      Logger.printOntoScreen("Operações: " + getOperations());
      Logger.printOntoScreen("Iterações realizadas em 5 segundos: " + iterations);
      Logger.printOntoScreenF("Tempo de execução Médio: %f\n", media);
      Logger.printOntoScreenF("Tempo de execução Total: %d\n\n", finishTime);
    }
    catch (Exception e) {
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

    // Um par ordenado usado para auxiliar.
    OrderedPair auxPair;

    for (int i = 0; i < listNOfOrderedPairs.size(); i++) {
      incOperations();
      auxPair = listNOfOrderedPairs.get(i);

      if (auxPair.getRatio() > maximumRatio) {
        // Então coloca o par(ai e o bi) na lista S.
        listS.add(auxPair);

        // Atualiza o somatório.
        addSomatory(auxPair);

        // Obtém a nova razão.
        maximumRatio = getRatio();

        if (!isLemmaValid(listS, maximumRatio)) {
          // Se existir algum par(ai / bi) que não seja maior do que a
          // razão atual, este par deve ser removido do listS e uma
          // nova razão deve ser calculada.
          maximumRatio = getRatio();
        }
      }
    }

    return maximumRatio;
  }

  /**
   * @param listS
   * @param maximumRatio
   * @return Retorna true se existiu algum par ordenado na lista S que não era verdade em relação ao Lemma.
   */
  private boolean isLemmaValid(List<OrderedPair> listS, float maximumRatio) {
    boolean valid = true;

    List<OrderedPair> listAux = new LinkedList<OrderedPair>();

    OrderedPair auxPair;
    for (Iterator<OrderedPair> iterator = listS.iterator(); iterator.hasNext();) {
      auxPair = iterator.next();
      incOperations();

      // Se o ratio for menor, então o par ordenado deve ser removido.
      if (auxPair.getRatio() < maximumRatio) {
        valid = false;

        // Tenho que remover o par ordenado na posição i.
        subtractSomatory(auxPair);

        // Adiciona o item para ser removido depois que o loop terminar. 
        // Remover de uma só vez é mais rápido.
        listAux.add(auxPair);
      }
    }

    // Remove os itens do conjunto S*.
    listS.removeAll(listAux);

    return valid;
  }
}