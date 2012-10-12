package questao01.pph.battle;

import java.util.ArrayList;
import java.util.List;

import questao01.pph.OrderedPair;
import questao01.pph.ordenacao.Sorter;
import utilidade.Log;
import utilidade.MergeSort;
import utilidade.Utils;

public class ComparingMergeSort {

  public static void main(String[] args) {
    ComparingMergeSort cms = new ComparingMergeSort();
    cms.run();
  }

  /**
   * O método run foi criado apenas para que não fosse necessário ficar usando variáveis e métodos estáticos.
   */
  private void run() {
    // Obtém a quantidade de números que serão testados.
    int quantityOfInputValues = 10;

    List<OrderedPair> listNOfOrderedPairs = Utils.getValuesFromInputFile(quantityOfInputValues);
    //    Log.printList(listNOfOrderedPairs);
    List<OrderedPair> listToSort1 = new ArrayList<OrderedPair>(listNOfOrderedPairs);
    List<OrderedPair> listToSort2 = new ArrayList<OrderedPair>(listNOfOrderedPairs);
    OrderedPair[] listToSort3 = new OrderedPair[listNOfOrderedPairs.size()];

    Log.printOntoScreen("Calculando MergeSort atual...");
    MergeSort m1 = new MergeSort();
    performSorting(m1, listToSort1);

    Log.printOntoScreen("Calculando MergeSort concorrente...");
    questao01.pph.ordenacao.MergeSort m2 = new questao01.pph.ordenacao.MergeSort();
    performSorting(m2, listToSort2);

    Log.printOntoScreen("Calculando MergeSort com array...");
    questao01.pph.ordenacao.MergeSort m3 = new questao01.pph.ordenacao.MergeSort();
    listNOfOrderedPairs.toArray(listToSort3);
    performSorting(m3, listToSort3);
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

  private void performSorting(Sorter sorter, OrderedPair[] listToSort) {
    long startTime = System.currentTimeMillis();

    sorter.sortAscending(listToSort);

    long finishTime = System.currentTimeMillis() - startTime;

    Log.printOntoScreen("Tamanho do N: " + listToSort.length);
    Log.printOntoScreen("Iterações: " + sorter.getIterations());
    Log.printOntoScreenF("Tempo de execução Total: %d\n\n", finishTime);
  }

}
