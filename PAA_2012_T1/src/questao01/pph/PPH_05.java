package questao01.pph;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import utilidade.Log;
import utilidade.Utils;

public class PPH_05 {
  // O nome do arquivo de input padrão(usado para testes).
  private static final String DEFAULT_INPUT_FILE_NAME = "src/questao01/pph/pph_10.txt";

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

    PPH_05 pph = new PPH_05();
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
      int iterations = 0;
      // Esta linha é apenas para forçar uma quebra de linha depois dos
      // números.
      scanner.nextLine();

      List<OrderedPair> listOrderedPairs = null;
      List<OrderedPair> listOriginalPair = Utils.getValuesFromInputFile(scanner, quantityOfInputValues);

      long startTime = System.currentTimeMillis();

      Log.printOntoScreen("Calculando...");
      // while (System.currentTimeMillis() - startTime < 5000) {
      listOrderedPairs = new LinkedList<OrderedPair>();
      // Obtém os valores que correspondem ao b = {1,.., n}
      listOrderedPairs.addAll(listOriginalPair);
      // Atribuindo o par inicial
      OrderedPair parInicial = listOrderedPairs.get(0);
      // Removendo da lista o par inicial
      listOrderedPairs.remove(0);
      listS = new LinkedList<OrderedPair>();
      // Ordanando a lista
      utilidade.Mergesort merge = new utilidade.Mergesort();
      merge.sort(listOrderedPairs);
      finalRatio = maximumRation(listOrderedPairs, listS, parInicial);
      iterations++;
      // }
      long finishTime = System.currentTimeMillis() - startTime;

      float media = (float) finishTime / iterations;
      Log.printOntoScreenF("Razão final: %f\n", finalRatio);
      Log.printOntoScreenF("Tamanho de S: %d \n", listS.size());
      Log.printOntoScreen("Conjunto S*: ");
      Log.printList(listS);

      Log.printOntoScreen("Iteraçoes realizadas: " + iterations);
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
    float maximumRation = inicialPar.getRatio();
    for (int i = 0; i <= listOrderedPairs.size() - 1; i++) {
      aux = listOrderedPairs.get(i).getRatio();
      if (aux > maximumRation) {
        this.somaA += listOrderedPairs.get(i).getA();
        this.somaB += listOrderedPairs.get(i).getB();
        maximumRation = (float) this.somaA / this.somaB;
        listS.add(listOrderedPairs.get(i));
      }
    }
    return maximumRation;
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
