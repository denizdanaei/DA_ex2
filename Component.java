import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

public class Component extends UnicastRemoteObject implements Component_RMI, Runnable {
    
    private static final long serialVersionUID = 1L;
    public int id, n;
    public boolean isTokenHere;
    private int[] RN; //or public?
    /**
     * Component with id 0 is initialized with the token.
     * id = a number smaller than n
     * n = number of components that
     * 
     **/
    public Component(int id, int n) throws RemoteException {
        super();
        this.id=id;
        this.n=n;
        this.RN=new int[n];
        if(id==0){this.isTokenHere=true;}
        else{this.isTokenHere=false;}
        //anything else?
        bind();
    }
    
    /**
     * Binding the remote object (stub) in the local registry
     */
    private void bind() {
        try{
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(Integer.toString(id), this);
            System.err.println("Component " + id + " is created");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
    
    public void run() {        
        while(true) {
            int wait = (int) (Math.random()*3000);
            try {
                Thread.sleep(wait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // try {
            //     this.sendRequest();
            // } catch (MalformedURLException | RemoteException | NotBoundException e) {
            //     e.printStackTrace();
            // }
        }
    }

    public void sendRequest(){
        System.out.println("Request Sent");
    }

    public synchronized void receiveRequest (int id, int requestNumber){
        System.out.println("Request Recieved!");
    }
    
    public synchronized void receiveToken(int[] grants){
        System.out.println("Token Recieved!");
    }
    
    public void sendToken() {
        System.out.println("Token Sent");        
    }
    

   
}
