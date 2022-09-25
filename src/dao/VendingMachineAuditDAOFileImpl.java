package dao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

@Component
public class VendingMachineAuditDAOFileImpl implements VendingMachineAuditDAO {

    private final String AUDIT_FILE;
    //Default constructor
    public VendingMachineAuditDAOFileImpl() {
        this.AUDIT_FILE = "audit.txt";
    }
    //Contractor for testing
    public VendingMachineAuditDAOFileImpl(String auditTestFile) {
        this.AUDIT_FILE = auditTestFile;
    }


    @Override
    public void writeAuditEntry(String entry) throws IOException {
        PrintWriter out = new PrintWriter(new FileWriter(AUDIT_FILE, true));
        LocalDateTime timestamp = LocalDateTime.now();
        out.println(timestamp.toString() + " : " +entry);
        out.flush();
        out.close();
    }
}
