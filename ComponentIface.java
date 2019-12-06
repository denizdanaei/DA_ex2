public interface ComponentIface {

    public int getId();
    public void broadcastRequest();
    public void onRequest(int pid, int seq);
    public void onTokenReceive(Token t);

    // Simulation helpers
    public void initToken();
    public void initNetwork(ComponentIface[] components);
    public void printStatus();
}