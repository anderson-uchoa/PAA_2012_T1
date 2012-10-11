/**
 * Esta o algoritmo que resolve a 1ª questão do T1 de PAA - PUC-Rio 2012.2. <br/>
 * Alunos: Luciano Sampaio, Igor Oliveira e Marcio Rosemberg.
 */
package questao01.pph;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import utilidade.Log;
import utilidade.Utils;

public class PPH_03 {

  // O nome do arquivo de input padrão(usado para testes).
  private static final String DEFAULT_INPUT_FILE_NAME = "test/pph/pph_10.txt";

  // A matriz que vai conter os valores que validam o lemma.
  LinkedList<OrderedPair>     listS;

  // Para evitar colocar numeros literais no código.
  int                         somaA                   = 0;
  int                         somaB                   = 0;

  public static void main(String[] args) {
    String inputFile;
    // Verifica se o arquivo de input foi passado como parâmetro.
    if (args.length == 1) {
      inputFile = args[0];

    }
    else {
      // Caso nenhum arquivo tenha sido informado, testa com o arquivo
      // criado para testes.
      inputFile = DEFAULT_INPUT_FILE_NAME;

      // Informa que a applicação esta em modo debug.
      Log.isDebugging = false;
    }

    PPH_03 pph = new PPH_03();
    pph.run(inputFile);
  }

  /**
   * O método run foi criado apenas para que não fosse necessário ficar usando variáveis e métodos estáticos.
   * 
   * @param inputFile
   */
  private void run(String inputFile) {
    try {
      // Abre o arquivo para que o dados possam ser lidos.
      Scanner scanner = new Scanner(new File(inputFile));

      // Obtém a quantidade de números contidos neste arquivo + 1(o a0 e
      // b0 não entram) * 2(porque é a mesma quantidade para o A e para o
      // B).
      int quantityOfInputValues = scanner.nextInt() + 1;
      // int quantityOfInputValues = 100;

      // A razão que deve ser calculada e apresentada no final.
      float finalRatio = 0;

      // Esta linha é apenas para forçar uma quebra de linha depois dos
      // números.
      scanner.nextLine();

      List<OrderedPair> listOriginalPair = Utils.getValuesFromInputFile(scanner, quantityOfInputValues);
      OrderedPair parInicial = new OrderedPair(listOriginalPair.get(0).getA(), listOriginalPair.get(0).getB());

      long startTime = System.currentTimeMillis();
      long iterations = 0;
      Log.printOntoScreen("Calculando...");
      // Removendo da lista o par inicial
      listOriginalPair.remove(0);
      while (System.currentTimeMillis() - startTime < 5000) {
        // Obtém os valores que correspondem ao b = {1,.., n}
        // Inicia a Lista S com o tamanho de elementos de pares
        // ordenados
        // e 2 colunas.

        listS = new LinkedList<OrderedPair>();
        // Ordanando a lista
        bubbleSort(listOriginalPair);
        finalRatio = maximumRation(listOriginalPair, listS, parInicial);

        iterations++;
      }
      listS.add(parInicial);
      long finishTime = System.currentTimeMillis() - startTime;

      float media = (float) finishTime / iterations;
      Log.printOntoScreenF("Razão final: %f\n", finalRatio);
      Log.printOntoScreenF("Tamanho de S: %d \n", listS.size());
      Log.printOntoScreen("Conjunto S*: ");
      Log.printList(listS);

      Log.printOntoScreen("Interaçoes realizadas: " + iterations);
      Log.printOntoScreenF("Tempo de execução: %f\n", media);

    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private float maximumRation(List<OrderedPair> listOrderedPairs, List<OrderedPair> listS, OrderedPair inicialPar) {
    float aux = 0;

    this.somaA = inicialPar.getA();
    this.somaB = inicialPar.getB();
    // int indexS = 0;
    float R = (float) inicialPar.getA() / inicialPar.getB();
    // System.out.println("Razão AoB0: " + R);
    for (int i = 0; i < listOrderedPairs.size(); i++) {
      aux = (float) listOrderedPairs.get(i).getA() / listOrderedPairs.get(i).getB();
      // System.out.println(" aux: " + aux+ " = " +
      // arrayOrderedPairs[i][0] + " / " + arrayOrderedPairs[i][1]);
      if (aux > R) {
        this.somaA += listOrderedPairs.get(i).getA();
        this.somaB += listOrderedPairs.get(i).getB();
        R = (float) this.somaA / this.somaB;
        listS.add(listOrderedPairs.get(i));
      }
      else
        break;
      // System.out.println(" aux: " + aux+ " R: " + R);
      // if
    } // for i
    return R;
  }

  private void bubbleSort(List<OrderedPair> list) {

    for (int i = 0; i < list.size(); i++) {
      for (int j = 0; j < list.size() - 1; j++) {
        float razao = (float) list.get(j).getA() / list.get(j).getB();
        float razaoProximoItem = (float) list.get(j + 1).getA() / list.get(j + 1).getB();

        if (razaoProximoItem > razao) {
          OrderedPair parAux = list.get(j);
          list.set(j, list.get(j + 1));
          list.set(j + 1, parAux);
        }
      }
    }
  }

}