import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class PoolExample {

    public static void main(String[] args) throws InterruptedException {

        // создаем пул для выполнения наших задач
        //   максимальное количество созданных задач - 3
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                // не изменяйте эти параметры
                3, 3, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<>(3));

        // сколько задач выполнилось
        AtomicInteger count = new AtomicInteger(0);

        // сколько задач выполняется
        AtomicInteger inProgress = new AtomicInteger(0);

        // отправляем задачи на выполнение
        for (int i = 0; i < 10; i++) {
            //List<Future<?>> futures = new ArrayList<>();
            final int number = i * 3;

            for(int j = number; j < number + executor.getMaximumPoolSize(); j++){
                final int taskNumber = j;

                System.out.println("creating #" + taskNumber);
                Future<Object> task = executor.submit(() -> {
                    int working = inProgress.incrementAndGet();
                    System.out.println("start #" + taskNumber + ", in progress: " + working);
                    try {
                        // тут какая-то полезная работа
                        Thread.sleep(Math.round(1000 + Math.random() * 2000));
                    } catch (InterruptedException e) {
                        // ignore
                    }
                    working = inProgress.decrementAndGet();
                    System.out.println("end #" + taskNumber + ", in progress: " + working + ", done tasks: " + count.incrementAndGet());
                    return null;
                });
                //futures.add(task);
                //AwaitAnyTaskCompletion(futures, executor.getMaximumPoolSize());
                AwaitAnyTaskCompletion(executor.getQueue());
            }
        }
        executor.shutdown();
    }

    private static void AwaitAnyTaskCompletion(BlockingQueue<?> queue){
        while(queue.remainingCapacity() == 0);
    }

    private static void AwaitAnyTaskCompletion(List<Future<?>> futures, int maxCount) {
        if(futures.size() < maxCount)
            return;

        while(true){
            for(int i = 0; i < futures.size(); i++){
                Future<?> future = futures.get(i);
                if(future.isDone()){
                    futures.remove(i);
                    return;
                }
            }
        }
    }
}