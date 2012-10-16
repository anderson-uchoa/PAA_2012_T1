package flasks;

import java.io.File;

import util.Logger;

public class TestFlasks {

  public static void main(String[] args) {
    // Todos os arquivos de entrada estão nestas pastas.
    String path = "test/flasks/";
    // O nome do arquivo que vai ser processado.
    String fileName;
    // O caminho do arquivo e o seu nome.
    String fileNameAndPath = null;
    File folder = new File(path);
    // Obtém a lista de arquivos neste diretório, como o próprio método "listFiles()" diz,
    // não existe uma garantia em que ordem os arquivos serão retornados.
    File[] listOfFiles = folder.listFiles();

    // Instância da classe que vai ser executada.
    Flasks flasks = new Flasks();

    // Momento em que o algoritmo iniciou sua execução.
    long startTime;
    // Momento em que o algoritmo terminou sua execução.
    long finishTime;

    for (int i = 0; i < listOfFiles.length; i++) {

      if (listOfFiles[i].isFile()) {
        fileName = listOfFiles[i].getName();
        // Somente arquivos .txt e .dat serão processados.
        if (fileName.endsWith(".dat") || fileName.endsWith(".txt")) {
          try {
            fileNameAndPath = path + fileName;

            // Imprime o nome do arquivo.
            Logger.printOntoScreen(fileNameAndPath);

            // Este loop vai iterar o mesmo número que foi lido do arquivo, 
            // vezes a quantidade de frascos que vai ser testado. 
            // ex: 256, 192, 128, 64, 32, 16, 8, 4, 2, 1
            for (int qtyflasks : Flasks.quantityOfFlasks) {
              startTime = System.currentTimeMillis();

              Logger.printOntoScreenF("Frascos: %d\n", qtyflasks);
              flasks.run(fileNameAndPath, qtyflasks);

              if (!flasks.isKeepGoing()) {
                break;
              }

              finishTime = System.currentTimeMillis() - startTime;

              Logger.printOntoScreenF("Tempo de execução(ms): %s\n\n", Flasks.formatString(finishTime));
            }
          }
          catch (Exception e) {
            Logger.printOntoScreenF("Erro ao processar o arquivo %s com a mensagem: %s", fileNameAndPath, e.getMessage());
          }
        }
      }

    }
    // Depois que todos os arquivos foram processados, exibe uma mensagem para informar que o programa terminou com sucesso!
    Logger.printOntoScreen("Todos os arquivos foram processados.");
  }
}