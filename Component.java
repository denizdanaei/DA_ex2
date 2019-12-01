import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

public class Component extends UnicastRemoteObject implements Component_RMI {
    
    
    /**
     * Component with id 0 is initialized with the token.
     */
    public Component() throws RemoteException {
    
    }
    
    /**
     * Binding the remote object (stub) in the local registry
     */
    private void bind() {
        try{
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(naming + id, this);
            System.err.println("Process " + id + " ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }

   
}
