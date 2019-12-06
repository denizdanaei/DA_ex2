
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

    public void request(int pid) {
        request(pid, 0);
    }
    public void request(int pid, int csDelay) {
        System.out.println("P"+pid+" REQUEST");
        components[pid-1].setCSDelay(csDelay);      // Warning: fails if multiple pending request for the same proc
        components[pid-1].broadcastRequest();
    }

    public void run() {
        // Hardcoded simulation
        request(1, 2);
        printState();
        request(2);
        printState();
        request(3);
        printState();
    }

    public void printState() {
        for (ComponentIface c : components) {
            c.printStatus();
        }
        System.out.println();
        System.out.println();
    }

    public static void main(String[] args) {
        Simulator sim = new Simulator(3);
        sim.run();
    }
}
