package audit;

import audit.Auditable;

public class AuditService<T extends Auditable<?>> {

    public void approve(T auditable) {
        if (auditable == null) {
            throw new IllegalArgumentException("Auditable object cannot be null");
        }
        auditable.markApproved();
        System.out.println("approved " + auditable.getId());
    }

    public void reject(T auditable, String reason) {
        if (auditable == null) {
            throw new IllegalArgumentException("Auditable object cannot be null");
        }
        if (reason == null || reason.isEmpty()) {
            throw new IllegalArgumentException("Reason for rejection cannot be null or empty");
        }
        auditable.markRejected(reason);
        System.out.println("rejected " + auditable.getId() + " for reason: " + reason);
    }
}
