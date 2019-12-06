public interface ComponentIface {

    public int getId();
    public void broadcastRequest();
    public void onRequest(int pid, int seq);

    // Simulation helpers
    public void initToken();
    public void initNetwork(ComponentIface[] components);
    public void printStatus();
}