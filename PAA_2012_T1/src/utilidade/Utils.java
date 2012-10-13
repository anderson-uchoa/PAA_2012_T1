package utilidade;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import questao01.pph.OrderedPair;

public class Utils {

  /**
   * @param quantityOfInputValues Quantidade de elementos que serão criados.
   * @return A lista de pares ordenados criada aleatóriamente.
   */
  public static List<OrderedPair> getValuesFromInputFile(int quantityOfInputValues) {
    Log.printOntoScreen("Obtendo valores randomicamente...");
    Random rnd = new Random();

    List<OrderedPair> listNOfOrderedPairs = new ArrayList<OrderedPair>(quantityOfInputValues);
    int a;
    int b;

    for (int i = 0; i < quantityOfInputValues; i++) {
      a = rnd.nextInt(500) + 1;
      b = rnd.nextInt(1000) + 1;

      listNOfOrderedPairs.add(new OrderedPair(a, b));
    }

    return listNOfOrderedPairs;
  }

  /**
   * Obtém os valores que correspondem ao A ou ao B(de uma forma generica) = {1,.., n}
   * 
   * @param quantityOfInputValues A quantidade de números contidos neste arquivo.
   * @param scanner Objeto que lê do arquivo.
   * @return Obtém os valores que correspondem ao A ou ao B.
   */
  public static List<OrderedPair> getValuesFromInputFile(Scanner scanner, int quantityOfInputValues) {
    Log.printOntoScreen("Obtendo valores do arquivo de entrada...");
    // Cria a lista de pares ordenados com a quantidade de elementos que ele vai conter.
    //List<OrderedPair> listNOfOrderedPairs = new LinkedList<OrderedPair>();
    List<OrderedPair> listNOfOrderedPairs = new ArrayList<OrderedPair>(quantityOfInputValues);

    List<Integer> listA = getListFromInputFile(scanner, quantityOfInputValues);
    List<Integer> listB = getListFromInputFile(scanner, quantityOfInputValues);

    Iterator<Integer> iteratorA = listA.iterator();
    Iterator<Integer> iteratorB = listB.iterator();

    // As listas de A e B têm a mesma quantidade de elementos.
    while (iteratorA.hasNext()) {
      listNOfOrderedPairs.add(new OrderedPair(iteratorA.next(), iteratorB.next()));
    }

    return listNOfOrderedPairs;
  }

  /**
   * Obtém os valores que correspondem ao A ou ao B(de uma forma generica) = {1,.., n}
   * 
   * @param quantityOfInputValues A quantidade de números contidos neste arquivo.
   * @param scanner Objeto que lê do arquivo.
   * @return Obtém os valores que correspondem ao A ou ao B.
   */
  public static List<Integer> getListFromInputFile(Scanner scanner, int quantityOfInputValues) {
    List<Integer> listTemp = new ArrayList<Integer>(quantityOfInputValues);
    int inputValue;

    for (int i = 0; i < quantityOfInputValues; i++) {
      inputValue = scanner.nextInt();

      listTemp.add(inputValue);

      Log.debugF("%4d", inputValue);
    }
    Log.debug("");

    return listTemp;
  }

  public static List<OrderedPair> getListBetterCase(int quantityOfInputValues) {

    List<OrderedPair> listNOfOrderedPairs = new ArrayList<OrderedPair>();
    listNOfOrderedPairs.add(new OrderedPair(1000000, 1));

    Random rnd = new Random();
    int a;
    int b;

    for (int i = 0; i < quantityOfInputValues; i++) {
      a = rnd.nextInt(500) + 1;
      b = rnd.nextInt(7000) + 1;

      listNOfOrderedPairs.add(new OrderedPair(a, b));
    }
    return listNOfOrderedPairs;
  }

  public static List<OrderedPair> getListWorstCase(int quantityOfInputValues) {
    List<OrderedPair> listNOfOrderedPairs = new ArrayList<OrderedPair>();

    Random rnd = new Random();
    int a = 1;
    int b = 1000;
    float razao = (float) a / b;
    listNOfOrderedPairs.add(new OrderedPair(a, b));
    for (int i = 0; i < quantityOfInputValues; i++) {
      a += 100;
      //b += 1;
      float aux = (float) a / b;
      //boolean teste = aux < razao;
      //      while (teste) {
      //        a *= 2;
      //        aux = (float) a / b;
      //        //b = rnd.nextInt(500);
      //        teste = aux < razao;
      //      }
      listNOfOrderedPairs.add(new OrderedPair(a, b));
      razao += (float) a / b;
    }
    //Log.printList(listNOfOrderedPairs);
    return listNOfOrderedPairs;
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

  public static void decrement(boolean[] input, int startPos, int endPos) {
    // Obtém o tamanho da entrada.
    int length = (endPos - startPos);// + 1;

    // O incremento para resolver a nossa questão do trabalho sempre será 1, 
    // mas para que o código ficasse genérico, fizemos o array de incremento 
    // com o mesmo tamanho do de entrada mas com todos os seus valores inicializados com 0 -> false.
    boolean[] decrement = new boolean[length];
    decrement[length - 1] = true;

    // Informa se houve um buffer over flow.
    boolean overFlow = false;
    // O índice J é para evitar indexOutOfBound porque nem sempre os array têm o mesmo tamanho.
    //int j = endPos;
    for (int i = length - 1; i >= 0; i--) {
      if (input[i] && decrement[i] && !overFlow) { // se for 1 e 1 e não over flow - fica 0
        input[i] = false;
      }
      else if (!input[i] && decrement[i]) { // se for 0 e 1 e Over flow - fica 1 e toma emprestado da posicao anterior
        if (!overFlow) {
          input[i] = true;
          if (input[i - 1] == false) {
            overFlow = true;
            //input[i - 1] = true;
          }
        }
        else {// se over flow  e 0 - basta colocar 0
          if (input[i] == false) {
            input[i] = true;
          }
          else { // se tem 1, deixa com 0 e acaba o over flow
            input[i] = false;
            overFlow = false;
          }

          //          if (input[i - 1] == true) {
          //            input[i - 1] = false;
          //            overFlow = false;
          //          }
        }
        //input[i - 1] = true;
        //overFlow = true;
      }
      else if (!input[i] && !decrement[i] && overFlow) {
        input[i] = true;
      }
      else if (input[i] && !decrement[i] && overFlow) {
        input[i] = false;
        overFlow = false;
      }
      //      else if (input[i] && !decrement[i]) {
      //        input[i] = true; // n precisa apenas para testar
      //      }
      //      else if (!input[i] && !decrement[i] && overFlow) {
      //        input[i] = true;
      //        overFlow = false;
      //      }
    }
  }
}
