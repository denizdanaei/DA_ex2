import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;


public class Main{

    //delete this when you solve try/catch for main(String[] args) 
    public static int numProcesses=2;
    public static void main(String[] args) {

        /**
         * GET the number of components from user AKA numProcesses
         * it should only be one arg, int, >0
         * 
         */
        if (args.length == 0) {
            throw new IllegalArgumentException("Enter the Number of Components.");
          }else if(args.length > 1){            
            throw new IllegalArgumentException("Enter one Number Only!");
          }
            numProcesses=Integer.parseInt(args[0]);
            System.out.println(numProcesses);
                            
        // Create Registry        
        try{
            Registry registry = LocateRegistry.createRegistry(1099);
        } catch (Exception e) {
            System.err.println("Could not create registry exception: " + e.toString()); 
            e.printStackTrace(); 
        } 

        //create threads
        Thread[] myThreads = new Thread[numProcesses];

        //create components and run threads
        for (int i = 0; i < numProcesses; i++){
            try {
                    Component component = new Component(i, numProcesses);
                    myThreads[i] = new Thread(component);
            } catch (RemoteException e) {
                    e.printStackTrace();
                }
        }
        
        
        //create components and run threads
        for (int i = 0; i < numProcesses; i++){

            myThreads[i].start();
        }
    }
}


