package flasks.utils;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import util.Logger;
import flasks.Flasks;

public class ThreadControl implements Runnable {
  private final static ScheduledExecutorService scheduler    = Executors.newScheduledThreadPool(1);

  private ScheduledFuture<?>                    beeperHandle = null;
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
    //Logger.printOntoScreen("Thread Finalizada em: " + dateFormat.format(new Date()));
  }

  @Override
  public void run() {
    try {
      Logger.printOntoScreen("Data:" + dateFormat.format(new Date()) + " - Total de iterações: " + Flasks.getOperations());
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}