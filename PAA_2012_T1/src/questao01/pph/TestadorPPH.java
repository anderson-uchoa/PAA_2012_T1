package questao01.pph;

import java.io.File;

import utilidade.Log;

public class TestadorPPH {

  public static void main(String[] args) {
    String path = "test/pph/";

    String files;
    String fileNameAndPath = null;
    File folder = new File(path);
    File[] listOfFiles = folder.listFiles();

    // Instância da classe que vai ser executada.
    PPH_05 p5 = new PPH_05();
    PPH_07 p7 = new PPH_07();
    PPH_SelectSort pSelectSort = new PPH_SelectSort();
    PPH_SelectionSortExtend pSelectSortExtend = new PPH_SelectionSortExtend();

    for (int i = 0; i < listOfFiles.length; i++) {

      if (listOfFiles[i].isFile()) {
        files = listOfFiles[i].getName();
        if (files.endsWith(".txt")) {
          try {
            fileNameAndPath = path + files;
            System.out.println(path + files);
            p7.run(fileNameAndPath);
            p5.run(fileNameAndPath);
            pSelectSort.run(fileNameAndPath);
            pSelectSortExtend.run(fileNameAndPath);
          }
          catch (Exception e) {
            System.out.printf("Erro ao processar o arquivo %s com a mensagem: %s", fileNameAndPath, e.getMessage());
          }
        }
      }
    }
    Log.printOntoScreen("Todos os arquivos foram processados.");
  }

}
