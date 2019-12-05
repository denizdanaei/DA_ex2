
public class Simulator {
    public static void main(String[] args) {

        // Create components
        Component[] components = {
            new Component(1, 3),
            new Component(2, 3),
            new Component(3, 3)
        };

        // Initialize token
        components[0].initToken();

        // Print state
        for (Component c : components) {
            c.printStatus();
        }
    }
}