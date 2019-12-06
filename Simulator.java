import java.rmi.registry.LocateRegistry;

public class Simulator {

    public ComponentIface[] components;

    public Simulator(int nComponents) {

        try {
            // Get component stubs
            this.components = new ComponentIface[nComponents];
            for (int i = 0; i < nComponents; i++) {
                this.components[i] = (ComponentIface) LocateRegistry.getRegistry().lookup(Integer.toString(i+1));
            }
            
            // Distribute list of component stubs
            for (ComponentIface c : components) c.initNetwork(this.components);

            // Initialize token
            this.components[0].initToken();

        } catch (Exception e) {
            System.out.println("Simulator exception "+e.toString());
        }
    }

    // csDelay = number of request events
    public void request(int pid, int csDelay) {
        if (csDelay > 0) System.out.println("P"+pid+" REQUEST "+"(CS = "+csDelay+")");
        else System.out.println("P"+pid+" REQUEST");
        try {
            components[pid-1].setCSDelay(csDelay);
            components[pid-1].broadcastRequest();

        } catch (Exception e) {
            System.out.println("Simulator exception @request "+e.toString());
        }
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
        try {
            for (ComponentIface c : components) c.printStatus();
        } catch (Exception e) {
            System.out.println("Simulator exception @printState "+e.toString());
        }
        System.out.println();
        System.out.println();
    }

    public static void main(String[] args) {
        Simulator sim = new Simulator(3);
        sim.run();
    }
}
