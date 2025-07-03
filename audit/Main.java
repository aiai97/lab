package audit;

public class Main {
    public static void main(String[] args) {
        AuditService<ContractDraft> auditService = new AuditService<>();

        ContractDraft draft1 = new ContractDraft("draft1");
        ContractDraft draft2 = new ContractDraft("draft2");

        auditService.approve(draft1);
        auditService.reject(draft2, "Missing signature");

        System.out.println("Audit completed.");
    }
}
