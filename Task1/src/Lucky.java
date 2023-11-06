public class Lucky {
    static volatile StateObject x = new StateObject(0);

    static Object lock = new Object();

    static class LuckyThread extends Thread {
        @Override
        public void run() {
            while (x.xLessThan(999999)) {
                    x.incrementX();
                    if ((x.getX() % 10) + (x.getX() / 10) % 10 +
                            (x.getX() / 100) % 10 == (x.getX() / 1000)
                            % 10 + (x.getX() / 10000) % 10 + (x.getX() / 100000) % 10) {
                        //System.out.println(x);
                        x.incrementCount();
                    }
                    System.out.println(this.getName());
                    System.out.println(x.getX());
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new LuckyThread();
        Thread t2 = new LuckyThread();
        Thread t3 = new LuckyThread();
        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();
        System.out.println("Total: " + x.getCount());
    }
}