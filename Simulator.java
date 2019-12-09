import java.rmi.registry.LocateRegistry;

public class Simulator {

    public ComponentIface[] components;

    public Simulator(int nComponents) {
        // Get Component stubs, distribute list of all Components to everyone, init token
        try {
            this.components = new ComponentIface[nComponents];
            for (int i = 0; i < nComponents; i++) {
                this.components[i] = (ComponentIface) LocateRegistry.getRegistry().lookup(Integer.toString(i+1));
            }
            for (ComponentIface c : components) c.initNetwork(this.components);
            this.components[0].initToken();

        } catch (Exception e) {
            System.out.println("Simulator exception "+e.toString());
        }
    }

    // csDelay = number of request events
    public void request(int pid, int csDelay) {
        System.out.println();
        System.out.println("P"+pid+" REQUEST");
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
        // request(1, 2);
        // // printState();
        // request(2);
        // // printState();
        // request(3);
        // // // printState();

        // Another example
        request(2, 2);
        printState();
        request(1, 2);
        printState();
        request(3);
        request(4);
        request(5);
        printState();
    }

    public void printState() {
        // System.out.println();
        // try {
        //     for (ComponentIface c : components) c.printStatus();
        // } catch (Exception e) {
        //     System.out.println("Simulator exception @printState "+e.toString());
        // }
        // System.out.println();
        // System.out.println();
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Simulator <num components>");
            System.exit(1);
        }
        Simulator sim = new Simulator(Integer.parseInt(args[0]));
        sim.run();
    }
}
