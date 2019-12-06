import java.util.Arrays;

public class Component implements ComponentIface {
    
    private final int id;
    private int[] RN;
    private Token token;
    private boolean criticalSection;
    private int csDelay;
    private ComponentIface[] componentList;

    public Component(int id, int nComponents) {
        this.id = id;
        this.RN = new int[nComponents];
        this.token = null;
        this.criticalSection = false;
        this.csDelay = 0;
    }

    public int getId() {
        return this.id;
    }

    public boolean hasToken() {
        if (this.token != null) return true;
        else return false;
    }

    public void broadcastRequest() {
        assert this.criticalSection == false;   // Safety check
        RN[id-1]++;
        for (ComponentIface c : componentList) {
            c.onRequest(id, RN[id-1]);          // Broadcast to everyone
        }
    }

    public void onRequest(int pid, int seq) {
        // System.out.println("P"+this.id+" received request: (P"+pid+", "+seq+")");
        RN[pid-1] = Math.max(RN[pid-1], seq);      // Update RN
        if (hasToken()) {
            token.updateQueue(RN);
            if (criticalSection && --csDelay == 0) releaseToken();
            if (!criticalSection && !token.isEmpty()) sendToken();
        }
    }

    public void onTokenReceive(Token t) {
        System.out.println("P"+id+" received token");
        this.token = t;
        this.criticalSection = true;
        if (csDelay == 0) releaseToken();
    }

    public void releaseToken() {
        criticalSection = false;
        token.updateLN(id);
        token.popFromQueue();
    }

    public void sendToken() {
        assert token.isEmpty() == false;
        int dst = token.peekQueue();
        componentList[dst-1].onTokenReceive(token);
        if (dst != id) token = null;
    }

    // Helper functions for simulation ----------------------------------------
    public void initNetwork(ComponentIface[] components) {
        this.componentList = components;
    }

    public void initToken() {
        this.token = new Token(RN.length);
    }

    public void setCSDelay(int delay) {
        this.csDelay = delay;
    }

    public void printStatus() {
        StringBuilder str = new StringBuilder();
        str.append("P"+id+" "+Arrays.toString(RN));
        if (token != null) str.append("\ttoken");
        if (criticalSection) str.append("\tcritical");
        System.out.print(str.toString().indent(2));
    }
}