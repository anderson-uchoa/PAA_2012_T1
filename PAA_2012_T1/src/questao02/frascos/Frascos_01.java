/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package questao02.frascos;

/**
 * 
 * @author mrosemberg
 */

public class Frascos_01 {
  static int degrau;
  static int n;
  static int passos;

  static int escadaFrasco(int deg, int tamanho, int fr) {
    degrau = deg;
    n = tamanho;
    passos = 0;
    return (quebra(0, tamanho, fr));
  }

  static boolean quebrou(int i) {
    return (i >= degrau);
  }

  static int quebra(int inicio, int fim, int frascos) {
    if (frascos == 1) {
      for (int i = inicio + 1; i <= fim; i++) {
        passos++;
        System.out.printf("Frascos: %d, Inicio %d, Fim %d, i%d, Passos %d\n", frascos, inicio, fim, i, passos);
        if (quebrou(i)) {
          return i;
        }
      }
    }
    else {
      int div = calculaDiv(inicio, fim, frascos);
      for (int i = inicio + div; i <= fim; i += div) {
        passos++;
        System.out.printf("Frascos: %d, Inicio %d, Fim %d, Div %d, i%d, Passos %d\n", frascos, inicio, fim, div, i, passos);
        if (quebrou(i)) {
          return quebra(i - div, i, frascos - 1);
        }
      }
    }
    return -1;
  }

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    // TODO code application logic here
    System.out.println("Quebrou em: " + escadaFrasco(51, 100, 2) + " em " + passos + " passos");
    // System.out.println("Quebrou em: "+escadaFrasco(51, 125, 3)+" em "+passos+" passos");
    System.out.println("Quebrou em: " + escadaFrasco(51, 256, 4) + " em " + passos + " passos");

  }

  private static int calculaDiv(int inicio, int fim, int frascos) {
    int base = fim - inicio;
    double expo = (double) 1 / frascos;
    double aux = base / Math.pow(base, expo);
    System.out.println("Div: " + aux);
    return (int) aux;
  }
}
