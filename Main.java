import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;


public class Main{

    //delete this when you solve try/catch for main(String[] args) 
    public static int numProcesses=2;
    public static void main(String[] args) {

        System.out.println("Hello World");

        /**
         * GET the number of components from user AKA numProcesses
         * it should only be one arg, int, >0
         * 
         */
        for(String s : args){
            
            System.out.println(s);
                        
            }
    
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
        }
}


