package audit;

public interface Auditable <T>{
    String getId();
    void markApproved();
    void markRejected(String reason);
}
