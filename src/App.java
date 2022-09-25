import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import controller.VendingMachineController;
import dao.VendingMachineAuditDAO;
import dao.VendingMachineAuditDAOFileImpl;
import dao.VendingMachineDAO;
import dao.VendingMachineDAOFileImpl;
import service.VendingMachineServiceLayer;
import service.VendingMachineServiceLayerImpl;
import view.UserIO;
import view.UserIOConsoleImpl;
import view.VendingMachineView;

public class App {

	public static void main(String[] args) {

//        UserIO io = new UserIOConsoleImpl();
//        VendingMachineView view = new VendingMachineView(io);
//        VendingMachineAuditDAO auditDao = new VendingMachineAuditDAOFileImpl();
//        VendingMachineDAO dao = new VendingMachineDAOFileImpl();
//        VendingMachineServiceLayer service = new VendingMachineServiceLayerImpl(auditDao, dao);
//
//        VendingMachineController controller = new VendingMachineController(view, service);
//
//        controller.run();
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		VendingMachineController controller = ctx.getBean("controller", VendingMachineController.class);
		controller.run();
	}

}
