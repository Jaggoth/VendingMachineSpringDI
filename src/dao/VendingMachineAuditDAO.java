package dao;

import java.io.IOException;

public interface VendingMachineAuditDAO {
	void writeAuditEntry(String entry) throws IOException;
}
