package questao02.frascos;

import java.util.Scanner;

public class Teste_Binario {

  static void decimalToBinary(int op1, int aOp[]) {

    int result, i = 0;

    do {

      result = op1 % 2;

      op1 /= 2;

      aOp[i] = result;

      i++;

    } while (op1 > 0);

  }

  static int binaryToDecimal(int array[]) {

    int sum = 0, i;

    //for(i = 0; i < 8; i++){

    //   if( array[i]) sum += pow(2,i);

    //}

    return sum;

  }

  static void showBinary(int array[], int n) {

    int i;

    for (i = n - 1; i >= 0; i--) {

      System.out.print(array[i]);

    }

    System.out.println("\n");

  }

  public static void main(String[] args) {

    int op1 = -1, op2 = -1, sum;

    int aOp1[] = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };

    int aOp2[] = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };

    int aSum[] = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };

    System.out.println("Enter two operands (0 to 255): ");

    //System.out.println( op1, op2);

    while (op1 < 0 || op1 > 255 || op2 < 0 || op2 > 255) {

      System.out.println("Enter two operands (0 to 31): ");
      Scanner input = new Scanner(System.in);
      op1 = input.nextInt();
      op2 = input.nextInt();
      //scanf("%d %d", &op1, &op2);

    }

    decimalToBinary(op1, aOp1);

    decimalToBinary(op2, aOp2);

    System.out.printf("Binary Equivalent of %d is ", op1);

    showBinary(aOp1, 8);

    System.out.printf("Binary Equivalent of %d is ", op2);

    showBinary(aOp2, 8);

    //if (!addBinary(aOp1, aOp2, aSum)) {
    if (addBinary(aOp1, aOp2, aSum) < 1) {

      System.out.printf("Sum of the two number is : ");

      showBinary(aSum, 8);

      sum = binaryToDecimal(aSum);

      System.out.printf("Binary eqivalent is: %d", sum);

    }
    else {

      System.out.printf("Overflow");

    }

    //return 0;

  }

  static int addBinary(int a1[], int a2[], int result[]) {

    int i, c = 0;

    for (i = 0; i < 8; i++) {

      result[i] = ((a1[i] ^ a2[i]) ^ c); //a xor b xor c

      c = ((a1[i] & a2[i]) | (a1[i] & c)) | (a2[i] & c); //ab+bc+ca

    }

    result[i - 1] = c;

    return c;

  }

}
