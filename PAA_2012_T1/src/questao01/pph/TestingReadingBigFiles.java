package questao01.pph;

public class TestingReadingBigFiles {

  /**
   * @param args
   */
  public static void main(String[] args) {
    String fileNameAndPath = "test/pph/pph_10.txt";

    try {
      BigFile file = new BigFile(fileNameAndPath);
      for (String currentValue : file)
        System.out.println(currentValue);

      file.Close();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    //    try {
    //      ReadBigFile rbf = new ReadBigFile(fileNameAndPath);
    //      for (Integer currentValue : rbf) {
    //        System.out.println(currentValue);
    //      }
    //    }
    //    catch (Exception e) {
    //      e.printStackTrace();
    //    }

  }
}
