import java.util.Arrays;

public class Component implements ComponentIface {
    
    public final int id;
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
            c.onRequest(id, RN[id-1]);      // Bcast to everyone (including yourself)
        }
    }

    public void onRequest(int pid, int seq) {
        // System.out.println("P"+this.id+" received request: (P"+pid+", "+seq+")");
        
        RN[pid-1] = Math.max(RN[pid-1], seq);      // Update RN
        if (hasToken()) {
            token.updateQueue(RN);
            if (criticalSection && --csDelay == 0) releaseToken();
            if (!criticalSection) sendToken();
        }
    }

    public void onTokenReceive(Token t) {
        this.token = t;
        this.criticalSection = true;
        System.out.println("\tP"+id+" received token");
    }

    public void releaseToken() {
        criticalSection = false;
        token.updateLN(id);
        // token.updateQueue(this.RN);
        // also when do you pop yourself off the queue?
    }

    public void sendToken() {
        // token.updateQueue(this.RN);
        if (!token.queue.isEmpty()) {
            int dst = token.queue.peek();
            componentList[dst-1].onTokenReceive(token);
            if (dst != id) token = null;
        } else {
            System.out.println("Queue empty");
        }
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