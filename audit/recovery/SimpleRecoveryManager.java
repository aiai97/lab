package audit.recovery;


import java.util.ArrayList;

interface ShutdownLister{
    void onShutdown(String reason);
}

interface RecoveryListener{
    void onRecoveryStarted();
    void onRecoverySucceeded();
    void onRecoveryFailed(Exception e);
}

interface RecoveryManager{
    void startRecovery();
    void stopRecovery();
    void addRecoveryListener(RecoveryListener recoveryListener);
    void removeRecoveryListener(RecoveryListener recoveryListener);

}
public class SimpleRecoveryManager implements RecoveryListener, RecoveryManager{
    private ArrayList<RecoveryListener> listeners = new ArrayList<RecoveryListener>();
    private boolean recovering = false;

    @Override
    public void onRecoveryStarted() {
        System.out.println("[SimpleRecoveryManager] Recovery process started.");
    }

    @Override
    public void onRecoverySucceeded() {
        System.out.println("[SimpleRecoveryManager] Recovery process succeeded.");
    }

    @Override
    public void onRecoveryFailed(Exception e) {
        System.err.println("[SimpleRecoveryManager] Recovery process failed: " + e.getMessage());
        e.printStackTrace();
    }

    @Override
    public void startRecovery() {
        onRecoveryStarted();
        if(!recovering){
            try {
                Thread.sleep(2000);
                for (RecoveryListener listener : listeners) {
                    listener.onRecoverySucceeded();
                }
            } catch (Exception e) {
                System.err.println("[SimpleRecoveryManager] Error during recovery failure handling: " + e.getMessage());
                for (RecoveryListener listener : listeners) {
                    listener.onRecoveryFailed(e);
                }
            }finally {
                recovering = false;
            }
        }
    }

    @Override
    public void stopRecovery() {

    }

    @Override
    public void addRecoveryListener(RecoveryListener recoveryListener) {
        listeners.add(recoveryListener);
    }

    @Override
    public void removeRecoveryListener(RecoveryListener recoveryListener) {
        listeners.remove(recoveryListener);
    }
}
