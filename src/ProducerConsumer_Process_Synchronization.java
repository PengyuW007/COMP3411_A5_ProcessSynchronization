public class ProducerConsumer_Process_Synchronization {
    private final int SIZE = 5;

    public static void main(String[] args) {

    }
}

class Buffer{
    private int[]buffer;
    private int write;//producer
    private int read;//consumer

    public Buffer(int size){
        buffer = new int[size];
        write = 0;
        read = 0;
    }


    /*
    Getters
     */
    public int[] getBuffer() {
        return buffer;
    }

    public int getWrite(){
        return write;
    }

    public int getRead() {
        return read;
    }
}
