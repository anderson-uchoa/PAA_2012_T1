package flasks.utils;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import util.Logger;
import flasks.Flasks;

public class ThreadControl implements Runnable {
  private ScheduledThreadPoolExecutor scheduler    = null;
  private ScheduledFuture<?>          beeperHandle = null;
  private long                        initialDelay, period;
  DateFormat                          dateFormat;

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
    scheduler = new ScheduledThreadPoolExecutor(1);
    scheduler.setContinueExistingPeriodicTasksAfterShutdownPolicy(false);

    beeperHandle = scheduler.scheduleAtFixedRate(this, this.initialDelay, this.period, SECONDS);
  }

  public void cancel() {
    beeperHandle.cancel(true);

    scheduler.shutdownNow();
    scheduler = null;
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