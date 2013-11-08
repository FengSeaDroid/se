package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import control.MainControl;
import net.miginfocom.swing.MigLayout;

public class ClinicInfoView extends JPanel {
	
	/**
	 *  Height of the view
	 */
	private final int viewHeight = 200;
	private final float fontSize = 20;
	
	private Map<String,String> clinicInfo = new HashMap<String,String>();
	
	public ClinicInfoView(boolean edible){
		super(new MigLayout("","[grow]","20[]20[]20[]20"));
		this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		this.setPreferredSize(new Dimension(getMaximumSize().width,this.viewHeight));
		clinicInfo = MainControl.getClinicInfo();
		JLabel clinicNameLabel = new JLabel(clinicInfo.get("clinicName"));
		clinicNameLabel.setFont (clinicNameLabel.getFont ().deriveFont (fontSize));
		JLabel clinicTel = new JLabel(clinicInfo.get("clinicTel"));
		clinicTel.setFont (clinicTel.getFont ().deriveFont (fontSize));

		JLabel physicianName = new JLabel(clinicInfo.get("physicianName"));
		physicianName.setFont (physicianName.getFont ().deriveFont (fontSize));

		this.add(clinicNameLabel,"align right");
		this.add(clinicTel,"align right");
		this.add(physicianName,"align right");
	}
	
}
