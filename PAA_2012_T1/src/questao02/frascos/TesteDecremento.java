package questao02.frascos;

import utilidade.Utils;

public class TesteDecremento {

  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

    boolean[] decremento = new boolean[] { false, true, false, true };
    Utils.decrement(decremento, 0, 4); // saida:  false, true, false, false
    decremento = new boolean[] { false, true, false, false };
    Utils.decrement(decremento, 0, 4);// saida:  false, false, true, true
    decremento = new boolean[] { false, false, true, true, false, true, false, true };
    Utils.decrement(decremento, 0, 8); // saida:  false, false, true, true, false, true,false, false
    decremento = new boolean[] { true, true, true, true, false, true, true, false };
    Utils.decrement(decremento, 0, 8); // saida:  true, true, true, true, false, true, false, true
  }
}
