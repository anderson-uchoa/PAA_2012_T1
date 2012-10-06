package questao02.frascos;

public class Frascos_02 {
  static String steste = "";

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    // TODO code application logic here
    String degrau = "00110011";
    int passos;
    passos = quebra(degrau, 2);
    System.out.println("Quebrou em " + steste + " em " + passos + " passos");
    passos = quebra("00110011", 4);
    System.out.println("Quebrou em " + steste + " em " + passos + " passos");
  }

  private static int quebra(String degrau, int frascos) {
    int passos = 0;
    steste = "";
    for (int i = 0; i < degrau.length(); i++) {
      steste += "0";
    }
    int divisor = degrau.length() / frascos;
    int cont = 0, inicio = 0, fim = divisor - 1;
    while (cont < frascos) {
      steste = incrementa(steste, inicio, fim);
      while (true) {
        passos++;
        System.out.println("Steste: " + steste);
        if (!quebrou(degrau, steste, inicio, fim)) {
          steste = incrementa(steste, inicio, fim);
        }
        else {
          break;
        }
      }
      cont++;
      inicio = fim + 1;
      fim += divisor;
    }
    return passos;
  }

  private static String incrementa(String steste, int inicio, int fim) {
    boolean overflow = false;
    StringBuilder teste = new StringBuilder(steste);
    for (int i = fim; i >= inicio; i--) {
      if (teste.charAt(i) == '0') {
        if (!overflow) {
          teste.setCharAt(i, '1');
          break;
        }
        else { //overflow
          overflow = false;
          teste.setCharAt(i, '1');
          break;
        }
      }
      else {
        teste.setCharAt(i, '0');
        overflow = true;
      }
    }
    steste = teste.toString();
    return steste;
  }

  private static boolean quebrou(String degrau, String steste, int inicio, int fim) {
    String comp1 = degrau.substring(inicio, fim + 1);
    String comp2 = steste.substring(inicio, fim + 1);
    if (comp1.equals(comp2)) {
      return true;
    }
    return false;
  }
}
