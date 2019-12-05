import java.util.Arrays;

public class Component {
    
    public int id;
    private int[] RN;
    private Token token;
    private boolean criticalSection;

    public Component(int id, int nComponents) {
        this.id = id;
        this.RN = new int[nComponents];
        this.token = null;
        this.criticalSection = false;
    }

    public int[] requestToken() {
        RN[id-1]++;
        return RN;
        // Broadcasting implemented in simulator
    }

    public void onRequest(int[] seq) {
        System.out.println("P"+id+" received request: "+Arrays.toString(seq));
    }


    // Helper functions for simulation
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