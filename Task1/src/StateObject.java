public class StateObject {
    private volatile int value;

    public StateObject(int number){
        value = number;
    }

    public synchronized void increment() {
            value++;
    }

    public synchronized int get(){
            return value;
    }

    public synchronized boolean lessThan(int other){
            return value < other;
    }
}
