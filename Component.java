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
    // private int Sn; //Sequence Number RN[id]=Sn
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
        // Sn = RN[id];
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
            System.err.println("Component " + id + " is created\n\n");
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
            try {
                this.sendRequest();
            }
             catch (MalformedURLException | RemoteException | NotBoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendRequest() throws MalformedURLException, RemoteException, NotBoundException {
        RN[id]++;
        // System.out.println("Sn="+Sn);
        System.out.println("Component "+id+": RN= "+ Arrays.toString(RN));
        System.out.println("Request Sent from "+ id);

        for(int i=0;i<n;i++){
            if(i!=id){
                Component_RMI reciever = (Component_RMI) Naming.lookup(Integer.toString(i));
                System.out.println("to "+ i);
                // System.out.println("\n\n");
                reciever.receiveRequest(id, RN[id]);
            }
       }
    }

    public synchronized void receiveRequest (int src, int Sn){
            System.out.println("Request Recieved at "+ id);
            if(RN[src]<Sn){
                RN[src]++;
                System.out.println("Request Accepted");
                System.out.println("Component "+id+": RN= "+ Arrays.toString(RN));
            }else{
                System.out.println("Request is Outdated");}
            System.out.println("\n\n");

    
    }
    
    public synchronized void receiveToken(int[] grants){
        System.out.println("Token Recieved!");
    }
    
    public void sendToken() {
        System.out.println("Token Sent");        
    }
    

   
}
