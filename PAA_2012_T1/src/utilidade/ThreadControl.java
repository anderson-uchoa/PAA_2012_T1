package utilidade;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import questao02.frascos.Frascos_05;

public class ThreadControl implements Runnable {
  private final static ScheduledExecutorService scheduler    = Executors.newScheduledThreadPool(1);

  private ScheduledFuture                       beeperHandle = null;
  private long                                  initialDelay, period;
  DateFormat                                    dateFormat;

  /**
   * Método que configura o comportamento do Scheduled da thread
   * 
   * @param initialDelay - Tempo em segundos que a thread demorará para começar a testar
   * @param period - período entre as sucessivas execuções em segundos
   */
  public ThreadControl(long initialDelay, long period) {
    this.initialDelay = initialDelay;
    this.period = period;
    dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
  }

  public void start() {
    beeperHandle = scheduler.scheduleAtFixedRate(this, this.initialDelay, this.period, SECONDS);
  }

  public void cancel() {
    beeperHandle.cancel(true);
    //System.out.println("Thread Finalizada em: " + dateFormat.format(new Date()));
  }

  @Override
  public void run() {
    try {

      Log.printOntoScreen("\nData:" + dateFormat.format(new Date()) + " - Realizando operações....." + " Total de iterações:" + Frascos_05.getIterations()
        + "\n");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}