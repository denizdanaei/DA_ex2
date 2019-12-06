import java.util.Arrays;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

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
            try {
                c.onRequest(id, RN[id-1]);          // Broadcast to everyone
            } catch (Exception e) {
                System.out.println("Exception @broadcast "+e.toString());
            }
        }
    }

    public void onRequest(int pid, int seq) {
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
        try {
            componentList[dst-1].onTokenReceive(token);
            if (dst != id) token = null;
        } catch (Exception e) {
            System.out.println("Exception @sendToken "+e.toString());
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

    // RMI stuff --------------------------------------------------------------
    public static void main(String[] args) {

        // Parse input arguments (PID)
        if (args.length != 2) System.exit(1);
        int pid = Integer.parseInt(args[0]);
        int nComponents = Integer.parseInt(args[1]);
        
        // Create process
        Component c = new Component(pid, nComponents);

        // Export to RMI registry and set PID as a name
        try {
            ComponentIface stub = (ComponentIface) UnicastRemoteObject.exportObject(c, 0);
            LocateRegistry.getRegistry().rebind(args[0], stub);
            System.out.println("Component ready");
        } catch (Exception e) {
            System.err.println("Component exception: " + e.toString());
            e.printStackTrace();
            System.exit(1);
        }
    }
}