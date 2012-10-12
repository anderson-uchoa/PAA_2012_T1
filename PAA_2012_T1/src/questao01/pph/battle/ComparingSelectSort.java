package questao01.pph.battle;

import java.util.ArrayList;
import java.util.List;

import questao01.pph.OrderedPair;
import questao01.pph.ordenacao.Sorter;
import utilidade.Log;
import utilidade.SelectionSort;
import utilidade.Utils;

public class ComparingSelectSort {

  public static void main(String[] args) {
    ComparingSelectSort cms = new ComparingSelectSort();
    cms.run();
  }

  /**
   * O método run foi criado apenas para que não fosse necessário ficar usando variáveis e métodos estáticos.
   */
  private void run() {
    // Obtém a quantidade de números que serão testados.
    int quantityOfInputValues = 10000;

    List<OrderedPair> listNOfOrderedPairs = Utils.getValuesFromInputFile(quantityOfInputValues);
    //    Log.printList(listNOfOrderedPairs);
    List<OrderedPair> listToSort1 = new ArrayList<OrderedPair>(listNOfOrderedPairs);
    List<OrderedPair> listToSort2 = new ArrayList<OrderedPair>(listNOfOrderedPairs);
    OrderedPair[] listToSort3 = new OrderedPair[listNOfOrderedPairs.size()];

    Log.printOntoScreen("Calculando SelectSort atual...");
    SelectionSort ss1 = new SelectionSort();
    performSorting(ss1, listToSort1);

    Log.printOntoScreen("Calculando SelectSort concorrente...");
    questao01.pph.ordenacao.SelectionSort ss2 = new questao01.pph.ordenacao.SelectionSort();
    performSorting(ss2, listToSort2);

    Log.printOntoScreen("Calculando SelectSort com array...");
    questao01.pph.ordenacao.SelectionSort ss3 = new questao01.pph.ordenacao.SelectionSort();
    listNOfOrderedPairs.toArray(listToSort3);
    performSorting(ss3, listToSort3);
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
