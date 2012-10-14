/**
 * Esta o algoritmo que resolve a 1ª questão do T1 de PAA - PUC-Rio 2012.2. <br/>
 * Alunos: Luciano Sampaio, Igor Oliveira e Marcio Rosemberg. <br/>
 */
package pph;

import java.util.ArrayList;
import java.util.List;

import pph.sorting.QuickSort;
import pph.utils.OrderedPair;
import pph.utils.PPHBase;
import util.Logger;

public class PPH_O_N_LOG_N extends PPHBase {

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

    PPH_O_N_LOG_N pph = new PPH_O_N_LOG_N();
    pph.run(inputFile);
  }

  /**
   * Este é o método que realmente faz todo o processamento. O método run foi criado apenas para que não fosse necessário ficar usando variáveis e métodos
   * estáticos.
   * 
   * @param listOriginalPair
   */
  @Override
  public void run(List<OrderedPair> listOriginalPair) {
    try {
      genericProcess(listOriginalPair, "Iniciado QuickSort - O(n log n)...");
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

    OrderedPair auxPair;
    for (int i = 0; i < listNOfOrderedPairs.size(); i++) {
      incOperations();
      auxPair = listNOfOrderedPairs.get(i);

      if (auxPair.getRatio() > maximumRatio) {
        // Então coloca o par(ai e o bi) na lista S.
        listS.add(auxPair);

        // Atualiza o R(razão).
        addSomatory(auxPair);

        // Obtém a nova razão.
        maximumRatio = getRatio();
      }
      else {
        break;
      }
    }
    return maximumRatio;
  }

  @Override
  protected void specificProcess(List<OrderedPair> listOriginalPair) {
    // Ordenando a lista.
    QuickSort sorter = new QuickSort();
    List<OrderedPair> listNOfOrderedPairs = new ArrayList<OrderedPair>(listOriginalPair);
    sorter.sortAscending(listNOfOrderedPairs);

    // Soma a quantidade de operações feitas pela ordenação + a quantidade atual do programa principal.
    setOperations(getOperations() + sorter.getOperations());

    // Calcula a razão máxima.
    finalRatio = maximumRatio(listNOfOrderedPairs);
  }
}