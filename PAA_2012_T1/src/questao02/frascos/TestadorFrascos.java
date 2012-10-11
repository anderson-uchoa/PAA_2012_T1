package questao02.frascos;

import java.io.File;

import utilidade.Log;

public class TestadorFrascos {

  public static void main(String[] args) {
    String path = "test/frascos/";

    String files;
    String fileNameAndPath = null;
    File folder = new File(path);
    File[] listOfFiles = folder.listFiles();

    // Instância da classe que vai ser executada.
    Frascos_03 f = new Frascos_03();

    for (int i = 0; i < listOfFiles.length; i++) {

      if (listOfFiles[i].isFile()) {
        files = listOfFiles[i].getName();
        if (files.endsWith(".txt")) {
          try {
            fileNameAndPath = path + files;
            //System.out.println(path + files);
            f.run(fileNameAndPath);
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
