import java.util.concurrent.CountDownLatch;

public class Main {
    public static void main(String[] args) {

        startTwoThread();

        try {
            Thread.sleep(5000);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        //start two threads at the same time with simple lock
        startTwoThreadSimple();
    }

    private static void startTwoThread() {
        var latch = new CountDownLatch(1);

        var thread1 = new Thread(() -> {
            try {
                latch.await();
                System.out.println("Thread 1 is processing");
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        var thread2 = new Thread(() -> {
            try {
                latch.await();
                System.out.println("Thread 2 is processing");
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        thread1.start();
        thread2.start();

        System.out.println("Start two threads with countdown after 5seconds");
        try {
            Thread.sleep(5000);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        latch.countDown();
    }

    private static void startTwoThreadSimple() {
        var lock = new Object();

        var thread1 = new Thread(() -> {
            synchronized(lock) {
                try {
                    lock.wait();
                    System.out.println("Thread 1 is processing");
                }
                catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        var thread2 = new Thread(() -> {
            synchronized(lock) {
                try {
                    lock.wait();
                    System.out.println("Thread 2 is processing");
                }
                catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        thread1.start();
        thread2.start();

        System.out.println("Start two threads with lock after 5seconds");
        try {
            Thread.sleep(5000);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        synchronized(lock) {
            lock.notifyAll();
        }
    }
}