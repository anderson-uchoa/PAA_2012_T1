package pph.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import util.Logger;

/**
 * Esta classe contém alguns métodos utils e comuns para ajudar no desenvolvimento de questão pph.<br/>
 * Alunos: Luciano Sampaio, Igor Oliveira e Marcio Rosemberg. <br/>
 */
public class Utils {

  /**
   * Obtém os valores que correspondem ao A ou ao B(de uma forma generica) = {1,.., n}
   * 
   * @param inputFile O arquivo que será usando como fonte de dados.
   * @return Obtém os valores que correspondem ao A ou ao B.
   * @throws Exception
   */
  public static List<OrderedPair> getListFromInputFile(String inputFile) throws Exception {
    Logger.printOntoScreen("Obtendo valores do arquivo de entrada...");

    // Tentar abrir o arquivo.
    ReadBigFile rbf = new ReadBigFile(inputFile);

    // Obtém o objeto que vai iterar por todas as linhas do arquivo.
    Iterator<Integer> iterator = rbf.iterator();

    // Obtém a quantidade de números contidos neste arquivo.
    // O valor encontrado é o mesmo para os elementos de A e de B, ou seja, se o valor for 10
    // temos 10 elementos para o conjunto A e 10 para o conjunto B.
    // O + 1 é porque temos que ler mais dois(1 em para cada conjunto) números que são um para A0 e um para o B0.
    int quantityOfInputValues = (iterator.hasNext()) ? iterator.next() + 1 : 0;

    // Cria uma lista temporária que vai conter os elementos lidos do arquivo.
    List<OrderedPair> listTemp = new ArrayList<OrderedPair>(quantityOfInputValues);

    // Este loop, adiciona todos os elementos de A.
    int count = 0;
    while ((iterator.hasNext()) && (count < quantityOfInputValues)) {
      int currentValue = iterator.next();
      count++;

      listTemp.add(new OrderedPair(currentValue));
    }

    // Este loop, adiciona todos os elementos de B.
    count = 0;
    while ((iterator.hasNext()) && (count < quantityOfInputValues)) {
      int currentValue = iterator.next();

      listTemp.get(count).setB(currentValue);

      count++;
    }

    // Libera o arquivo.
    rbf.Close();

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