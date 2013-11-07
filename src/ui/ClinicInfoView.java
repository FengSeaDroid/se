package ui;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

import control.MainControl;
import net.miginfocom.swing.MigLayout;

public class ClinicInfoView extends JPanel {
	private Map<String,String> clinicInfo = new HashMap<String,String>();
	
	public ClinicInfoView(boolean edible){
		super(new MigLayout("","[150]20[150]20[150]","20[]20"));
		clinicInfo = MainControl.getClinicInfo();
		JLabel clinicNameLabel = new JLabel(clinicInfo.get("clinicName"));
		clinicNameLabel.setFont (clinicNameLabel.getFont ().deriveFont (32.0f));
		JLabel clinicTel = new JLabel(clinicInfo.get("clinicTel"));
		clinicTel.setFont (clinicTel.getFont ().deriveFont (32.0f));

		JLabel physicianName = new JLabel(clinicInfo.get("physicianName"));
		physicianName.setFont (physicianName.getFont ().deriveFont (32.0f));

		this.add(clinicNameLabel,"alignx right");
		this.add(clinicTel,"alignx right");
		this.add(physicianName,"alignx right");
	}
	
}
