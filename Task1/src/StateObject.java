public class StateObject {
    private volatile int x;
    private volatile int count;

    public StateObject(int number){
        this.x = number;
        this.count = number;
    }

    public synchronized void incrementX() { x++; };

    public synchronized void incrementCount() { count++; }

    public synchronized int getX(){ return x; }

    public synchronized int getCount() {
        return count;
    }
}
