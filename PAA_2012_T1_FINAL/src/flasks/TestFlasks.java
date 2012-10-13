package flasks;

import java.io.File;

import util.Logger;

public class TestFlasks {

  public static void main(String[] args) {
    TestFlasks testFlasks = new TestFlasks();
    testFlasks.run();
  }

  private void run() {
    // Todos os arquivos de entrada estão nestas pastas.
    String path = "test/flasks/";
    // O caminho do arquivo e o seu nome.
    String fileNameAndPath = null;
    // O nome do arquivo que vai ser processado.
    String fileName;
    File folder = new File(path);
    // Obtém a lista de arquivos neste diretório, como o próprio método "listFiles()" diz,
    // não existe uma garantia em que ordem os arquivos serão retornados.
    File[] listOfFiles = folder.listFiles();

    // Instância da classe que vai ser executada.
    Flasks flasks = new Flasks();

    for (int i = 0; i < listOfFiles.length; i++) {

      if (listOfFiles[i].isFile()) {
        fileName = listOfFiles[i].getName();
        // Somente arquivos .txt serão processados.
        if (fileName.endsWith(".txt")) {
          try {
            fileNameAndPath = path + fileName;
            flasks.run(fileNameAndPath);
          }
          catch (Exception e) {
            System.out.printf("Erro ao processar o arquivo %s com a mensagem: %s", fileNameAndPath, e.getMessage());
          }
        }
      }

    }
    // Depois que todos os arquivos foram processados, exibe uma mensagem para informar que o programa terminou com sucesso!
    Logger.printOntoScreen("Todos os arquivos foram processados.");
  }
}