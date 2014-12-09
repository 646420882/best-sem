import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by vbzer_000 on 14-9-18.
 */
public class SchedulerTest {
    private static final ExecutorService scheduler =
            Executors.newFixedThreadPool(1);

    public static void main(String args[]) {
        SchedulerTest test = new SchedulerTest();
        test.beepForAnHour();
    }


    public void beepForAnHour() {
        final Semaphore semaphore = new Semaphore(1);

        final Runnable beeper = new Runnable() {
            public void run() {
                try {
                    semaphore.acquire();
                    System.out.println("beep1 " + Calendar.getInstance().getTime());
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            }
        };


        for (final AtomicInteger i = new AtomicInteger(0); i.get() <  100; i.getAndIncrement()) {
            final int value = i.get();
            final Runnable beeper2 = new Runnable() {
                public void run() {
                    try {
                        semaphore.acquire();
                        System.out.println("beep " + value + " "+ Calendar.getInstance().getTime());
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        semaphore.release();
                    }
                }
            };
            final Future<?> beeperHandle =
                    scheduler.submit(beeper2);
        }
//        final ScheduledFuture<?> beeperHandle2 =
//                scheduler.scheduleAtFixedRate(beeper2, 10, 10, TimeUnit.SECONDS);
//
//        scheduler.schedule(new Runnable() {
//            public void run() {
//                beeperHandle.cancel(true);
//            }
//        }, 60 * 60, TimeUnit.SECONDS);

//        scheduler.schedule(new Runnable() {
//            public void run() {
//                beeperHandle2.cancel(true);
//            }
//        }, 60 * 60, TimeUnit.SECONDS);
    }
}
