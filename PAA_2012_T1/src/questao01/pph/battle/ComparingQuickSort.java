package questao01.pph.battle;

import java.util.ArrayList;
import java.util.List;

import questao01.pph.OrderedPair;
import questao01.pph.ordenacao.Sorter;
import utilidade.Log;
import utilidade.Utils;

public class ComparingQuickSort {

  public static void main(String[] args) {
    ComparingQuickSort cms = new ComparingQuickSort();
    cms.run();
  }

  /**
   * O método run foi criado apenas para que não fosse necessário ficar usando variáveis e métodos estáticos.
   */
  private void run() {
    // Obtém a quantidade de números que serão testados.
    int quantityOfInputValues = 1000000;

    List<OrderedPair> listNOfOrderedPairs = Utils.getValuesFromInputFile(quantityOfInputValues);
    //    Log.printList(listNOfOrderedPairs);
    List<OrderedPair> listToSort1 = new ArrayList<OrderedPair>(listNOfOrderedPairs);
    List<OrderedPair> listToSort2 = new ArrayList<OrderedPair>(listNOfOrderedPairs);

    Log.printOntoScreen("Calculando QuickSort meu...");
    questao01.pph.ordenacao.QuickSort qs1 = new questao01.pph.ordenacao.QuickSort();
    performSorting(qs1, listToSort1);

    Log.printOntoScreen("Calculando QuickSort net...");
    questao01.pph.ordenacao.QuickSortNET qs2 = new questao01.pph.ordenacao.QuickSortNET();
    performSorting(qs2, listToSort2);

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
