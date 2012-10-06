/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package questao01.pph;
import java.util.Random;

/**
 *
 * @author mrosemberg
 */

public class Pph_N2 {
static final int N = 100;
static int pares [][] = new int[N][2];
static int S [][] = new int[N][2];
static int index = 0; //indicador de fim do array
static int a, b, passos;
static void preenchePares(){ //sem repetições
    boolean flag;
    Random rnd = new Random();
    pares [0][0] = rnd.nextInt(500);
    pares [0][1] = rnd.nextInt(1000)+1;
    rnd = new Random();    
   for (int i=1; i<pares.length; i++){
   //     flag = true;
   //     while(flag){
           a = rnd.nextInt(500)+1;
           b = rnd.nextInt(1000);
   //        for(int j=0; j<i; j++){
   //           if (pares[i][0]!=a && pares[i][1]!=b) 
   //              flag = false; 
   //              break;
   //        }          
   //     }
        pares [i][0] = a;
        pares [i][1] = b; 
    }
}
static float razao(int an, int bn){
    if (bn==0){
        return (float) 10000;        
    }
    else{
       return (float) an/bn; 
    }
}
 static void razaoMaxima(){      
    passos = 0;
    boolean flag = true;
    float R;     
    float aux;
    float Rant = (float) pares[0][0]/pares[0][1];        
    float Rmax = Rant;
    for (int k=0; k<N; k++){                 
     R = Rant;
     index = 0;     
     if (flag){
     //   System.out.println(Rmax);
     }   
     for (int j=0; j<S.length; j++){
         S[j][0] = 0;
         S[j][1] = 0;
     }
     S[0][0] = pares[0][0]; //coloca a0 e b0 no S
     S[0][1] = pares[0][1];              
     a=S[0][0];
     b=S[0][1];
     for (int i=1; i<pares.length; i++){    
         passos++;
         aux = razao(pares[i][0],pares[i][1]);
         //System.out.printf("aux: %.4f R: %.4f\n",aux,R);
         if (aux>R){
             index++;
             S[index][0]=pares[i][0]; //adiciona o par ordenado em S
             S[index][1]=pares[i][1];
             a+=S[index][0];
             b+=S[index][1];             
             R = (float) a/b;             
         } //if
     } //for i      
     Rant=R;
     if (Rmax<R){
         Rmax=R;
         flag = true;
     }
     else{
     //    flag=false;
      //return;
     }
    } 
     //for (int i=0; i<=index; i++){        
     //     System.out.println("(S"+i+",S"+i+"): ("+S[i][0]+","+S[i][1]+")");            
     //} //for    
 }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here        
        preenchePares();
       // System.out.println("(A0,B0): ("+pares[0][0]+","+pares[0][1]+")"); 
        //for (int i=1; i<pares.length; i++){
        //    System.out.println("(A"+i+",B"+i+"): ("+pares[i][0]+","+pares[i][1]+")");
        //} // for
        long tempo = System.currentTimeMillis();
        long iteracoes = 0;
        while (System.currentTimeMillis()-tempo<5000){           
            razaoMaxima();
            iteracoes++;
        }              
        tempo = System.currentTimeMillis()-tempo;
        System.out.println("Conjunto S");        
        for (int i=0; i<=index; i++){        
          System.out.println("(S"+i+",S"+i+"): ("+S[i][0]+","+S[i][1]+")");            
       } //for
       System.out.println("N: "+N);
       System.out.println("Passos: "+passos);
       System.out.println("Razão Máxima: "+a+"/"+b);
       float media = (float) tempo/iteracoes;
       System.out.println("Interaçoes realizadas: "+iteracoes);
       System.out.printf("Tempo de execução em microsegundos: %.12f\n",media);
        
    } // main
} // pph
