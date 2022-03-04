public class ProducerConsumer_Process_Synchronization {
    private final int SIZE = 5;

    public static void main(String[] args) {

    }
}

class Buffer {
    private int[] buffer;
    private int write;//producer
    private int read;//consumer
    private int count;//number of items in buffer
    //private boolean available;//available to read/write, AKA empty or full

    public Buffer(int size) {
        buffer = new int[size];
        write = 0;
        read = 0;
        count = 0;
    }

    public synchronized int put(int value) {
        int size = buffer.length;
        while (count == size) {
            try {
                wait();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
        buffer[write] = value;
        write = (write + 1) % size;
        count++;
        System.out.println("Produced " + value + "," + count + " items in Buffer.");
        notifyAll();
        return count;
    }//end put

    public synchronized int withdraw() {
        int size = buffer.length;
        while (count == 0) {
            try {
                wait();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }

        int next_consumed = buffer[read];
        read = (read + 1) % size;
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

    public int getWrite() {
        return write;
    }

    public int getRead() {
        return read;
    }

    public int counter() {
        return count;
    }
}//end Buffer
