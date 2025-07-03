package audit;

public class ContractDraft implements Auditable<ContractDraft>{
    private String id;
    private String status;
    public ContractDraft(String id) { this.id = id; }

    public String getId() { return id; }

    public void markApproved() { status = "approved"; }
    public void markRejected(String reason) { status = "rejected: " + reason; }
}
