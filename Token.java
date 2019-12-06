import java.util.Queue;
import java.util.LinkedList;

public class Token {
    private int[] LN;
    public Queue<Integer> queue;

    public Token(int numComponents) {
        this.LN = new int[numComponents];
        this.queue = new LinkedList<Integer>();
    }

    public void updateLN(int pid) {
        LN[pid-1]++;
    }

    public void updateQueue(int[] RN) {
        System.out.print("Queue ");
        for (int i = 0; i < LN.length; i++) {
            if (RN[i] > LN[i] && !queue.contains(i+1)) queue.add(i+1);         // Can the difference ever be more than 1?
        }
        System.out.println(queue.toString());
    }

    public void popRequest() {
        queue.remove();
    }
}
