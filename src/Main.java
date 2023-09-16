public class Main {

    //公共锁对象
    public static final Object lock = new Object();
    //线程数
    public static final int count = 3;
    //计数起点
    public static volatile int start = 0;
    //计数终点
    public static final int end = 100;

    public static void main(String[] args) {
        for (int i = 0; i < count; i++) {
            new Thread(new Print(i)).start();
        }

    }

    //静态内部类 线程
    static class Print implements Runnable {
        private final int index;

        Print(int index) {
            this.index = index;
        }

        @Override
        public void run() {
            while (start < end) {
                synchronized (lock) {
                    while (start % count != index) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (start <= end) {
                        System.out.println("Thread " + index + "打印结果" + start);
                    }
                    start++;
                    //唤醒所有线程
                    lock.notifyAll();
                }
            }
        }

    }
}
