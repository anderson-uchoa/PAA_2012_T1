package questao02.frascos;

import java.io.File;

import utilidade.Log;

public class TestadorFrascos {

  public static void main(String[] args) {
    TestadorFrascos tf = new TestadorFrascos();
    tf.run();
  }

  private void run() {
    // Todos os arquivos de entrada estão nestas pastas.
    String path = "test/frascos/";

    String files;
    String fileNameAndPath = null;
    File folder = new File(path);
    // Obtém a lista de arquivos neste diretório.
    File[] listOfFiles = folder.listFiles();

    // Instância da classe que vai ser executada.
    Frascos_05 f = new Frascos_05();

    for (int i = 0; i < listOfFiles.length; i++) {

      if (listOfFiles[i].isFile()) {
        files = listOfFiles[i].getName();
        if (files.endsWith(".txt")) {
          try {
            fileNameAndPath = path + files;
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