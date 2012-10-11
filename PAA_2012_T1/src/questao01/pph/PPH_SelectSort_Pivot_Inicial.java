package questao01.pph;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import utilidade.Log;
import utilidade.SelectionSortExtend;
import utilidade.Utils;

public class PPH_SelectSort_Pivot_Inicial {
  //O nome do arquivo de input padrão(usado para testes).
  private static final String DEFAULT_INPUT_FILE_NAME = "src/questao01/pph/pph_100000.txt";

  // A matriz que vai conter os valores que validam o lemma.
  List<OrderedPair>           listS;

  // Para evitar colocar numeros literais no código.
  private int                 somaA                   = 0;
  private int                 somaB                   = 0;
  OrderedPair                 parInicial;

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
    PPH_SelectSort_Pivot_Inicial pph = new PPH_SelectSort_Pivot_Inicial();
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

      Log.printOntoScreen("Lendo arquivo...");
      List<OrderedPair> listOriginalPair = Utils.getValuesFromInputFile(scanner, quantityOfInputValues);
      Log.printOntoScreen("Arquivo completo...");
      long startTime = System.currentTimeMillis();

      Log.printOntoScreen("Calculando...");
      //while (System.currentTimeMillis() - startTime < 5000) {
      // Obtém os valores que correspondem ao b = {1,.., n}
      // Atribuindo o par inicial
      parInicial = listOriginalPair.get(0);
      // Removendo da lista o par inicial
      listOriginalPair.remove(0);

      listS = new LinkedList<OrderedPair>();

      // Ordanando a lista
      SelectionSortExtend selectSort = new SelectionSortExtend();
      int size = quantityOfInputValues - 1;
      MedianaPair mediana = selectSort.selectIterativo(listOriginalPair, 0, size, 0);

      finalRatio = maximumRatio(listOriginalPair, size, mediana);
      listS.add(0, parInicial);

      long finishTime = System.currentTimeMillis();
      Log.printOntoScreenF("Tempo de execução: %d\n", finishTime - startTime);
      iterations++;
      // }
      float media = (float) finishTime / iterations;
      Log.printOntoScreenF("Razão final: %f\n", finalRatio);
      Log.printOntoScreenF("Tempo de execução: %d\n", finishTime - startTime);
      Log.printOntoScreenF("Tamanho de S: %d \n", listS.size());
      Log.printOntoScreen("Conjunto S*: ");
      Log.printList(listS);
      //Log.printOntoScreen("Conjunto N: ");
      //Log.printList(listOriginalPair);

      Log.printOntoScreen("Iteraçoes realizadas: " + iterations);
      Log.printOntoScreenF("Tempo de execução: %f\n", media);

    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * @param listNOfOrderedPairs
   * @param mediana
   * @param count
   * @return A razão máxima.
   */
  private float maximumRatio(List<OrderedPair> listNOfOrderedPairs, int count, MedianaPair mediana) {
    //long startTime = System.currentTimeMillis();
    float maximumRatio = parInicial.getRatio();
    Log.debugF("Razão (a0, b0): %f\n", maximumRatio);

    long iterations = 0;
    // Zerando as variáveis iniciais.
    //listS = new LinkedList<OrderedPair>();

    OrderedPair auxlPar;
    for (int i = count - 1; i >= mediana.getIndex(); i--) {
      iterations++;
      auxlPar = listNOfOrderedPairs.get(i);

      Log.debugF("[%d, %d] = %f - %f\n", auxlPar.getA(), auxlPar.getB(), auxlPar.getRatio(), maximumRatio);

      if (auxlPar.getRatio() > maximumRatio) {
        // Então coloca o par(ai e o bi) na lista S.
        listS.add(auxlPar);
        //  Atualiza o R(razão).
        SomaRazao(auxlPar);
        maximumRatio = calcularRazao();
        Log.debugF("Nova razão: %f\n", maximumRatio);
        int idx = 0;
        //float menor = listNOfOrderedPairs.get(0).getRatio();
        //        for (i = 0; i < listNOfOrderedPairs.size(); i++) {
        //          if (listNOfOrderedPairs.get(i).getRatio() >= maximumRatio) {
        //            if (menor <= listNOfOrderedPairs.get(i).getRatio()) {
        //              menor = listNOfOrderedPairs.get(i).getRatio();
        //              idx = i;
        //            }
        //          }
        //        }
        //        SelectionSort selectSort = new SelectionSort();
        //        selectSort.SelectFixedPivot(listNOfOrderedPairs, 0, listNOfOrderedPairs.size(), idx);
      }
    }
    Log.printOntoScreenF("Número de passos: %d\n", iterations);
    return maximumRatio;
  }

  private void SomaRazao(OrderedPair auxlPar) {
    this.somaA += auxlPar.getA();
    this.somaB += auxlPar.getB();
  }

  private float calcularRazao() {
    return (float) (this.somaA + this.parInicial.getA()) / (this.somaB + this.parInicial.getB());
  }

  private int maximumRation(List<OrderedPair> listOrderedPairs) {
    float aux = 0;

    this.somaA = parInicial.getA();
    this.somaB = parInicial.getB();
    // int indexS = 0;
    float R = parInicial.getRatio();
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
      // if
    } // for i
    int i, idx = 0;
    float menor = listOrderedPairs.get(0).getRatio();
    for (i = 0; i < listOrderedPairs.size(); i++) {
      if (listOrderedPairs.get(i).getRatio() >= R) {
        if (menor <= listOrderedPairs.get(i).getRatio()) {
          menor = listOrderedPairs.get(i).getRatio();
          idx = i;
        }
      }
    }
    System.out.println(listOrderedPairs.get(idx).getRatio());
    return idx;
  }
}
