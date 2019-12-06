
public class Simulator {

    public ComponentIface[] components;

    public Simulator(int nComponents) {
        
        // Create components
        this.components = new ComponentIface[nComponents];
        for (int i = 0; i < nComponents; i++) {
            this.components[i] = new Component(i+1, nComponents);
        }
        
        // Distribute list of component stubs
        for (ComponentIface c : components) c.initNetwork(this.components);
        
        // Init token
        this.components[0].initToken();
    }

    // csDelay = number of request events
    public void request(int pid, int csDelay) {
        if (csDelay > 0) System.out.println("P"+pid+" REQUEST "+"(CS = "+csDelay+")");
        else System.out.println("P"+pid+" REQUEST");
        components[pid-1].setCSDelay(csDelay);
        components[pid-1].broadcastRequest();
    }

    public void request(int pid) {              // Convenience wrapper (for csDelay=0)
        request(pid, 0);
    }

    public void run() {
        // Hardcoded simulation
        request(1, 2);
        // printState();
        request(2);
        // printState();
        request(3);
        // printState();
    }

    public void printState() {
        System.out.println();
        for (ComponentIface c : components) c.printStatus();
        System.out.println();
        System.out.println();
    }

    public static void main(String[] args) {
        Simulator sim = new Simulator(3);
        sim.run();
    }
}
