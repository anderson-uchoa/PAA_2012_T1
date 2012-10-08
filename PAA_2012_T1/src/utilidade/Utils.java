package utilidade;

import java.util.ArrayList;
import java.util.LinkedList;
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
    Random rnd = new Random();

    List<OrderedPair> listNOfOrderedPairs = new ArrayList<OrderedPair>();
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
    // Cria a lista de pares ordenados com a quantidade de elementos que ele
    // vai conter.
    //List<OrderedPair> listNOfOrderedPairs = new LinkedList<OrderedPair>();
    List<OrderedPair> listNOfOrderedPairs = new ArrayList<OrderedPair>(quantityOfInputValues);

    List<Integer> listA = getListFromInputFile(scanner, quantityOfInputValues);
    List<Integer> listB = getListFromInputFile(scanner, quantityOfInputValues);

    for (int i = 0; i < quantityOfInputValues; i++) {
      listNOfOrderedPairs.add(new OrderedPair(listA.get(i), listB.get(i)));
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

  public static <T extends Comparable<T>> void merge(int inicio, int fim, List<T> list) {
    if (inicio < fim) {
      int meio = (inicio + fim) / 2;
      merge(inicio, meio, list);
      merge(meio + 1, fim, list);
      mesclarCormen(inicio, meio, fim, list);
    }
  }

  // Ordena dois trechos ordenados e adjacente de vetores e ordena-os
  // conjuntamente

  private static <T extends Comparable<T>> void mesclar(int inicio, int meio, int fim, List<T> list) {

    int tamanho = fim - inicio + 1;

    /*
     * Inicialização de um vetor temporario para auxiliar na ordenação O vetor temporário é uma cópia do trecho que será ordenado
     */

    List temp = new LinkedList<OrderedPair>();

    temp.addAll(list);

    /*
     * Laço para ordenação do vetor, utilizando o vetor temporário, usando índices i e j para cada trecho de vetor da mesclagem
     */

    int i = 0;
    int j = meio - inicio + 1;

    // A depender das condições, recebe um elemento de um trecho ou outro
    for (int posicao = 0; posicao < tamanho; posicao++) {
      int pos = inicio + posicao;
      if (j <= tamanho - 1) {
        if (i <= meio - inicio) {
          if (((T) temp.get(i)).compareTo((T) temp.get(j)) < 0) {
            list.set(pos, (T) temp.get(i++));
          }
          else {
            list.set(pos, (T) temp.get(j++));
          }
        }
        else {
          list.set(pos, (T) temp.get(j++));
        }
      }
      else {

        list.set(pos, (T) temp.get(i++));
      }
    }
  }

  private static <T extends Comparable<T>> void mesclarCormen(int inicio, int meio, int fim, List<T> list) {

    int n1 = meio - inicio + 1;
    int n2 = fim - meio;
    int i, j;
    List<T> listaEsquerda = new LinkedList<T>();
    List<T> listaDireita = new LinkedList<T>();
    for (i = 0; i < n1; i++) {
      listaEsquerda.add(list.get(inicio + i - 1));
    }
    for (j = 0; j < n2; j++) {
      listaDireita.add(list.get(meio + j));
    }
    //0listaEsquerda.add(n1+1, T);
    //listaDireita.add(n2+1, T);

    i = 0;
    j = 0;
    for (int k = inicio; k < fim; k++) {
      if (listaEsquerda.get(i).compareTo(listaDireita.get(j)) <= 0) {
        list.set(k, listaEsquerda.get(i));
        i++;
      }
      else {
        list.set(k, listaDireita.get(j));
        j++;
      }
    }
  }
}
