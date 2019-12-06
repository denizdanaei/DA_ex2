import java.util.Arrays;

public class Component {
    
    public final int id;
    private int[] RN;
    private Token token;
    private boolean criticalSection;

    public Component(int id, int nComponents) {
        this.id = id;
        this.RN = new int[nComponents];
        this.token = null;
        this.criticalSection = false;
    }

    public int requestToken() {
        RN[id-1]++;
        return RN[id-1];
        // Broadcasting implemented in simulator
    }

    public void onRequest(int pid, int seq) {
        System.out.println("P"+this.id+" received request: (P"+pid+", "+seq+")");
        this.RN[pid-1] = Math.max(1, seq);      // Update local RN
    }


    // Helper functions for simulation ----------------------------------------
    public void initToken() {
        this.token = new Token();
    }

    public void printStatus() {
        System.out.println("P"+id);
        System.out.println(Arrays.toString(RN));
        if (token == null) System.out.println("no token");
        else System.out.println("has token");
        if (criticalSection ) System.out.println("critical section");
        else System.out.println("not critical section\n");
    }
}