package pph.utils;

import java.util.List;

import util.Base;

public abstract class PPHBase extends Base {

  private float               ratio;
  private boolean             isChanged;

  // A lista S* que vai conter os valores que validam o lemma.
  protected List<OrderedPair> listS;

  // Este é o par(a0, b0).
  protected OrderedPair       initialPair;

  // Guardamos o somatório de A e B para evitar reprocessamento desta informação.
  protected long              somatoryA;
  protected long              somatoryB;

  /**
   * O método run foi criado apenas para que não fosse necessário ficar usando variáveis e métodos estáticos.
   * 
   * @param inputFile
   */
  public void run(String inputFile) {
    try {
      // Carrega a lista de pares ordenados na memória.
      List<OrderedPair> listNOfOrderedPairs = Utils.getListFromInputFile(inputFile);

      // Este é o método que realmente faz todo o processamento.
      run(listNOfOrderedPairs);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public abstract void run(List<OrderedPair> listNOfOrderedPairs);

  public void resetSomatory() {
    somatoryA = initialPair.getA();
    somatoryB = initialPair.getB();
    isChanged = true;
  }

  /**
   * @param auxPair
   */
  public void addSomatory(OrderedPair auxPair) {
    somatoryA += auxPair.getA();
    somatoryB += auxPair.getB();
    isChanged = true;
  }

  /**
   * @param auxPair
   */
  public void subtractSomatory(OrderedPair auxPair) {
    somatoryA -= auxPair.getA();
    somatoryB -= auxPair.getB();
    isChanged = true;
  }

  /**
   * Calcula a razão.
   * 
   * @return O resultado da divisão do somatoryA por somatoryB.
   */
  public float getRatio() {
    // Se a razão já foi calculada e nenhum dos valores a e b foi alterado, 
    // então para ganhar alguns milisegundos nós não recalculamos a razão mas retornamos 
    // a que já tinha sido calculada anteriormente.
    if (isChanged) {
      isChanged = false;
      ratio = (float) somatoryA / somatoryB;
    }

    return ratio;
  }
}