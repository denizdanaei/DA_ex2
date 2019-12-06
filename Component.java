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
        RN[pid-1] = Math.max(RN[pid-1], seq);      // Update local RN
    }


    // Helper functions for simulation ----------------------------------------
    public void initToken() {
        this.token = new Token();
    }

    public void printStatus() {
        StringBuilder str = new StringBuilder();
        str.append("P"+id+" "+Arrays.toString(RN));
        if (token != null) str.append("\thas token\t");
        if (criticalSection) str.append("critical section");
        System.out.print(str.toString().indent(2));
    }
}