package questao01.pph;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import utilidade.Log;
import utilidade.Utils;

public class PPH_05 {
  // O nome do arquivo de input padrão(usado para testes).
  private static final String DEFAULT_INPUT_FILE_NAME = "src/questao01/pph/pph_1000.txt";

  // A matriz que vai conter os valores que validam o lemma.
  List<OrderedPair>           listS;
  OrderedPair                 parInicial;
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

      List<OrderedPair> listOriginalPair = Utils.getValuesFromInputFile(scanner, quantityOfInputValues);

      long startTime = System.currentTimeMillis();

      Log.printOntoScreen("Calculando...");
      // while (System.currentTimeMillis() - startTime < 5000) {
      // Obtém os valores que correspondem ao b = {1,.., n}
      // Atribuindo o par inicial
      parInicial = listOriginalPair.get(0);
      // Removendo da lista o par inicial
      listOriginalPair.remove(0);
      listS = new LinkedList<OrderedPair>();
      // Ordanando a lista
      utilidade.MergeSort merge = new utilidade.MergeSort();
      Log.printOntoScreen("Ordenando...");
      merge.sort(listOriginalPair);
      finalRatio = maximumRatio(listOriginalPair);
      iterations++;
      // }
      long finishTime = System.currentTimeMillis() - startTime;

      float media = (float) finishTime / iterations;
      Log.printOntoScreenF("Razão final: %f\n", finalRatio);
      Log.printOntoScreenF("Tamanho de S: %d \n", listS.size());
      Log.printOntoScreen("Conjunto S*: ");
      Log.printList(listS);

      Log.printOntoScreen("Iteraçoes realizadas: " + iterations);
      Log.printOntoScreenF("Tempo de execução: %d\n", finishTime);
      Log.printOntoScreenF("Tempo de execução Médio: %f\n", media);

    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * @param listNOfOrderedPairs
   * @return A razão máxima.
   */
  private float maximumRatio(List<OrderedPair> listNOfOrderedPairs) {
    // O R inicial é calculado pelo a0 / b0.
    float maximumRatio = parInicial.getRatio();
    Log.debugF("Razão (a0, b0): %f\n", maximumRatio);

    OrderedPair auxlPar;
    for (int i = 0; i < listNOfOrderedPairs.size(); i++) {
      auxlPar = listNOfOrderedPairs.get(i);

      Log.debugF("[%d, %d] = %f - %f\n", auxlPar.getA(), auxlPar.getB(), auxlPar.getRatio(), maximumRatio);

      if (auxlPar.getRatio() > maximumRatio) {
        // Então coloca o par(ai e o bi) na lista S.
        listS.add(auxlPar);

        // Atualiza o R(razão).
        SomaRazao(auxlPar);
        maximumRatio = calcularRazao();
        Log.debugF("Nova razão: %f\n", maximumRatio);
      }
      else
        break;
    }
    return maximumRatio;
  }

  private void SomaRazao(OrderedPair auxlPar) {
    this.somaA += auxlPar.getA();
    this.somaB += auxlPar.getB();
  }

  private float calcularRazao() {
    return (float) (this.somaA + this.parInicial.getA()) / (this.somaB + this.parInicial.getB());
  }
}
