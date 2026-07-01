package dz3;

public class Main {
    public static void main(String[] args) {
        MyCustomPool pool = new MyCustomPool(10);
        for (int i = 0; i < 11; i++) {
            final int task = i;
            pool.execute(() -> System.out.println("Выполняется задача - " + task));
        }

        pool.shutdown();

        try {
            pool.execute(() -> System.out.println("Эта задача не должна выполниться"));
        } catch (IllegalStateException e) {
            System.err.println(">>> Перехвачено ожидаемое исключение: " + e.getMessage());
        }

    }
}
