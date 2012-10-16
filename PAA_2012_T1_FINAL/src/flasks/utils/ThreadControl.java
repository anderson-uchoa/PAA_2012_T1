package flasks.utils;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import util.Logger;
import flasks.Flasks;

public class ThreadControl implements Runnable {
  private ScheduledThreadPoolExecutor scheduler = null;
  private long                        initialDelay;
  private DateFormat                  dateFormat;
  private Flasks                      flasks;

  /**
   * Método que configura o comportamento do Scheduled da thread
   * 
   * @param initialDelay - Tempo em segundos que a thread demorará para começar a testar
   * @param flasks
   */
  public ThreadControl(long initialDelay, Flasks flasks) {
    this.initialDelay = initialDelay;
    this.flasks = flasks;

    dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
  }

  public void start() {
    scheduler = new ScheduledThreadPoolExecutor(1);
    scheduler.setContinueExistingPeriodicTasksAfterShutdownPolicy(false);

    // This will execute the WorkerThread only once after 10 Seconds
    scheduler.schedule(this, this.initialDelay, SECONDS);
  }

  public void cancel() {
    //Interrupt the threads and shutdown the scheduler.
    scheduler.shutdownNow();
  }

  @Override
  public void run() {
    flasks.setKeepGoing(false);
    Logger.printOntoScreenF("Data: %s - Total de iterações: %s \n", dateFormat.format(new Date()), Flasks.getStrOperations());
  }
}