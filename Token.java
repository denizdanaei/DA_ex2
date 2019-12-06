import java.util.Queue;
import java.util.LinkedList;

public class Token {

    private int[] LN;
    private Queue<Integer> queue;

    public Token(int numComponents) {
        this.LN = new int[numComponents];
        this.queue = new LinkedList<Integer>();
    }

    public void updateLN(int pid) {
        LN[pid-1]++;
    }

    public void updateQueue(int[] RN) {
        for (int i = 0; i < LN.length; i++) {
            if (RN[i] > LN[i] && !queue.contains(i+1)) {
                queue.add(i+1);
            }
        }
    }

    public void popFromQueue() {
        queue.remove();
    }

    public int peekQueue() {
        return queue.peek();
    }

    public boolean isEmpty() {
        if (queue.isEmpty()) return true;
        else return false;
    }
}
