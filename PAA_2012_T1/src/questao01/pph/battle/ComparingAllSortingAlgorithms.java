package questao01.pph.battle;

import java.util.ArrayList;
import java.util.List;

import questao01.algoritmos.ordenacao.Sorter;
import questao01.pph.OrderedPair;
import utilidade.Log;
import utilidade.Utils;

public class ComparingAllSortingAlgorithms {

  public static void main(String[] args) {
    ComparingAllSortingAlgorithms cms = new ComparingAllSortingAlgorithms();
    cms.run();
  }

  /**
   * O método run foi criado apenas para que não fosse necessário ficar usando variáveis e métodos estáticos.
   */
  private void run() {
    // Obtém a quantidade de números que serão testados.
    int quantityOfInputValues = 10000;

    List<OrderedPair> listNOfOrderedPairs = Utils.getValuesFromInputFile(quantityOfInputValues);
    List<OrderedPair> listToSort1 = new ArrayList<OrderedPair>(listNOfOrderedPairs);
    List<OrderedPair> listToSort2 = new ArrayList<OrderedPair>(listNOfOrderedPairs);
    List<OrderedPair> listToSort3 = new ArrayList<OrderedPair>(listNOfOrderedPairs);

    Log.printOntoScreen("Calculando QuickSort net...");
    questao01.algoritmos.ordenacao.QuickSortNET qs1 = new questao01.algoritmos.ordenacao.QuickSortNET();
    performSorting(qs1, listToSort1);

    Log.printOntoScreen("Calculando MergeSort concorrente...");
    questao01.algoritmos.ordenacao.MergeSort m1 = new questao01.algoritmos.ordenacao.MergeSort();
    performSorting(m1, listToSort2);

    Log.printOntoScreen("Calculando SelectSort concorrente...");
    questao01.algoritmos.ordenacao.SelectionSort ss1 = new questao01.algoritmos.ordenacao.SelectionSort();
    performSorting(ss1, listToSort3);
  }

  private void performSorting(Sorter sorter, List<OrderedPair> listToSort) {
    long startTime = System.currentTimeMillis();

    sorter.sortAscending(listToSort);

    long finishTime = System.currentTimeMillis() - startTime;

    //Log.printList(listToSort);
    Log.printOntoScreen("Tamanho do N: " + listToSort.size());
    Log.printOntoScreen("Iterações: " + sorter.getIterations());
    Log.printOntoScreenF("Tempo de execução Total: %d\n\n", finishTime);
  }
}
