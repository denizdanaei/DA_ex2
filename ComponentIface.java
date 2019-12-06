import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ComponentIface extends Remote {

    public int getId() throws RemoteException;
    public void broadcastRequest() throws RemoteException;
    public void onRequest(int pid, int seq) throws RemoteException;
    public void onTokenReceive(Token t) throws RemoteException;

    // Simulation helpers
    public void initToken() throws RemoteException;
    public void initNetwork(ComponentIface[] components) throws RemoteException;
    public void setCSDelay(int delay) throws RemoteException;
    public void printStatus() throws RemoteException;
}