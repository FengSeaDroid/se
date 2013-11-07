package ui;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import control.MainControl;
import net.miginfocom.swing.MigLayout;

public class ClinicInfoView extends JPanel {
	private Map<String,String> clinicInfo = new HashMap<String,String>();
	
	public ClinicInfoView(boolean edible){
		super(new MigLayout());
		clinicInfo = MainControl.getClinicInfo();
	}
	
}
