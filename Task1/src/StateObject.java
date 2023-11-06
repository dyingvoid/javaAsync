public class StateObject {
    private volatile int x;
    private volatile int count;
    private static Object lock = new Object();

    public StateObject(int number){
        this.x = number;
        this.count = number;
    }

    public void incrementX() {
        synchronized (lock){
            x++;
        }
    }

    public void incrementCount() {
        synchronized (lock){
            count++;
        }
    }

    public int getX(){
        synchronized (lock){
            return x;
        }
    }

    public int getCount() {
        synchronized (lock){
            return count;
        }
    }

    public boolean xLessThan(int value){
        return x < value;
    }
}
