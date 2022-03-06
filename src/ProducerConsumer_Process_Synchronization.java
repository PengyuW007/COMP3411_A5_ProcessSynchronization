import java.util.Random;

public class ProducerConsumer_Process_Synchronization {
    private static final int SIZE = 5;

    public static void main(String[] args) {
        Buffer buffer = new Buffer(SIZE);

        Random random = new Random();
        int missions = random.nextInt(20) + 1;
        Producer producer = new Producer(buffer, missions);
        Consumer consumer = new Consumer(buffer, missions);
        producer.start();
        consumer.start();
    }
}

class Producer extends Thread {
    private Buffer buffer;
    private int produced;

    public Producer(Buffer buffer, int produced) {
        this.buffer = buffer;
        this.produced = produced;
        if (produced > 2) {
            System.out.println(this.produced + " items were produced.");
        } else {
            System.out.println(this.produced + " item was produced");
        }
    }

    public void run() {
        for (int i = 0; i < produced; i++) {
            buffer.put(i);
            try {
                Random random = new Random();
                int sleep = random.nextInt(100);
                //int sleep =100;
                sleep(sleep);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }

    }
}//end Producer

class Consumer extends Thread {
    private Buffer buffer;
    private int consumed;

    public Consumer(Buffer buffer, int consumed) {
        this.buffer = buffer;
        this.consumed = consumed;
        if (consumed > 2) {
            System.out.println(this.consumed + " items need to be consumed.");
        } else {
            System.out.println(this.consumed + " item needs to be consumed");
        }
    }

    public void run() {
        for (int i = 0; i < consumed; i++) {
            buffer.remove();
            try {
                Random random = new Random();
                int sleep = random.nextInt(1000);
                //int sleep = 1000;
                sleep(sleep);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }
}//end Consumer

class Buffer {
    private int[] buffer;
    private int in;//producer
    private int out;//consumer
    private int count;//number of items in buffer

    public Buffer(int size) {
        buffer = new int[size];
        in = 0;
        out = 0;
        count = 0;
    }

    public synchronized int put(int value) {
        int size = buffer.length;
        while (count == size) {
            System.out.println("The buffer is full, stop producing!");
            try {
                wait();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
        buffer[in] = value;
        in = (in + 1) % size;
        count++;
        System.out.println("Produced " + value + "," + count + " items in Buffer.");
        notifyAll();
        return count;
    }//end put

    public synchronized int remove() {
        int size = buffer.length;
        while (count == 0) {
            System.out.println("Buffer is empty, stop consuming!");
            try {
                wait();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }

        int next_consumed = buffer[out];
        out = (out + 1) % size;
        count--;
        System.out.println("Consumed " + next_consumed + "," + count + " items in Buffer.");
        notifyAll();
        return next_consumed;
    }//end withdraw

    /*
    Getters
     */
    public int[] getBuffer() {
        return buffer;
    }

    public int getIn() {
        return in;
    }

    public int getOut() {
        return out;
    }

    public int counter() {
        return count;
    }
}//end Buffer
