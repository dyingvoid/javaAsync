public class Lucky {
    static volatile StateObject x = new StateObject(0);
    static volatile StateObject count = new StateObject(0);

    static class LuckyThread extends Thread {
        @Override
        public void run() {
            while (x.lessThan(999999)) {
                    x.increment();
                    if ((x.get() % 10) + (x.get() / 10) % 10 +
                            (x.get() / 100) % 10 == (x.get() / 1000)
                            % 10 + (x.get() / 10000) % 10 + (x.get() / 100000) % 10) {
                        //System.out.println(x);
                        count.increment();
                    }
                    System.out.println(this.getName());
                    System.out.println(x.get());
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
        System.out.println("Total: " + count.get() + " " + x.get());
    }
}