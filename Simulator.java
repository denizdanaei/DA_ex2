
public class Simulator {

    public Component[] components;

    public Simulator(int nComponents) {
        this.components = new Component[nComponents];
        for (int i = 0; i < nComponents; i++) {
            this.components[i] = new Component(i+1, nComponents);
        }
        this.components[0].initToken();
    }

    public void request(int pid) {
        System.out.println("P"+pid+" REQUEST");
        // Get sequence number and broadcast request
        int sequence = components[pid-1].requestToken();
        for (Component c : components) {
            if (c.id != pid) c.onRequest(pid, sequence);
        }
        System.out.println();
    }

    public void run() {
        // Hardcoded simulation
        request(1);
        request(2);
        request(3);
        System.out.println();
        printState();
    }

    public void printState() {
        for (Component c : components) {
            c.printStatus();
        }
    }

    public static void main(String[] args) {
        Simulator sim = new Simulator(3);
        sim.run();
    }
}
