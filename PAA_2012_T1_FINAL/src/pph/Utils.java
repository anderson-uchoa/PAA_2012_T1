package pph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import util.Logger;

/**
 * Esta classe contém alguns métodos utils e comuns para ajudar no desenvolvimento de questão pph.<br/>
 * Alunos: Luciano Sampaio, Igor Oliveira e Marcio Rosemberg. <br/>
 */
public class Utils {

  /**
   * Obtém os valores que correspondem ao A ou ao B(de uma forma generica) = {1,.., n}
   * 
   * @param quantityOfInputValues A quantidade de números contidos neste arquivo.
   * @param scanner Objeto que lê do arquivo.
   * @return Obtém os valores que correspondem ao A ou ao B.
   */
  public static List<OrderedPair> getListFromInputFile(Scanner scanner, int quantityOfInputValues) {
    Logger.printOntoScreen("Obtendo valores do arquivo de entrada...");

    //List<OrderedPair> listNOfOrderedPairs = new LinkedList<OrderedPair>();
    // Cria a lista de pares ordenados com a quantidade de elementos que ele vai conter.
    List<OrderedPair> listTemp = new ArrayList<OrderedPair>(quantityOfInputValues);

    List<Integer> listA = getValuesFromInputFile(scanner, quantityOfInputValues);
    List<Integer> listB = getValuesFromInputFile(scanner, quantityOfInputValues);

    Iterator<Integer> iteratorA = listA.iterator();
    Iterator<Integer> iteratorB = listB.iterator();

    // As listas de A e B têm a mesma quantidade de elementos.
    while (iteratorA.hasNext()) {
      listTemp.add(new OrderedPair(iteratorA.next(), iteratorB.next()));
    }

    return listTemp;
  }

  /**
   * Obtém os valores que correspondem ao A ou ao B(de uma forma generica) = {1,.., n}
   * 
   * @param quantityOfInputValues A quantidade de números contidos neste arquivo.
   * @param scanner Objeto que lê do arquivo.
   * @return Obtém os valores que correspondem ao A ou ao B.
   */
  private static List<Integer> getValuesFromInputFile(Scanner scanner, int quantityOfInputValues) {
    List<Integer> listTemp = new ArrayList<Integer>(quantityOfInputValues);
    int inputValue;

    for (int i = 0; i < quantityOfInputValues; i++) {
      inputValue = scanner.nextInt();

      listTemp.add(inputValue);

      Logger.debugF("%4d", inputValue);
    }
    Logger.debug("");

    return listTemp;
  }

  /**
   * São gerados números aleatórios baseado na quantidade de elementos que é solicitada.
   * 
   * @param quantityOfInputValues Quantidade de elementos que serão criados.
   * @return A lista de pares ordenados criada aleatóriamente.
   */
  public static List<OrderedPair> getRandomValues(int quantityOfInputValues) {
    Logger.printOntoScreen("Obtendo valores randomicamente...");
    Random rnd = new Random();

    List<OrderedPair> listTemp = new ArrayList<OrderedPair>(quantityOfInputValues);
    int a;
    int b;

    for (int i = 0; i < quantityOfInputValues; i++) {
      a = rnd.nextInt(500) + 1;
      b = rnd.nextInt(1000) + 1;

      listTemp.add(new OrderedPair(a, b));
    }

    return listTemp;
  }

  /**
   * Calcula a razão.
   * 
   * @param a
   * @param b
   * @return O resultado da divisão de a por b.
   */
  public static float calcRatio(long a, long b) {
    return (float) a / b;
  }
}