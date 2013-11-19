package ui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import control.MainControl;
import net.miginfocom.swing.MigLayout;

public class ClinicInfoView extends JPanel {
	
	/**
	 *  Height of the view
	 */
	//private final int viewHeight = 200;
	//private final float fontSize = 20;
	
	//SOME DIFFERENT SETTING HERE
	private final int viewHeight = 100;
	
	
	//private Map<String,String> clinicInfo = new HashMap<String,String>();
	
	public ClinicInfoView(boolean edible){
		//super(new MigLayout("","[grow]","20[]20[]20[]20"));
		//this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		//try some different setting here.
		super(new MigLayout("","[grow]","[][][]"));
		this.setBorder(BorderFactory.createEmptyBorder());
		//
		
		
		this.setPreferredSize(new Dimension(getMaximumSize().width,this.viewHeight));
		//clinicInfo = MainControl.getMainControl().getClinicInfo();
		JLabel clinicNameLabel = new JLabel(MainControl.getMainControl().getClinicName());
		//clinicNameLabel.setFont (clinicNameLabel.getFont ().deriveFont (fontSize));
		JLabel clinicTel = new JLabel(MainControl.getMainControl().getClinicTel());
		//clinicTel.setFont (clinicTel.getFont ().deriveFont (fontSize));

		JLabel physicianName = new JLabel(MainControl.getMainControl().getPhysicianName());
		JLabel clinicAddress = new JLabel(MainControl.getMainControl().getClinicAddress());
		
		//physicianName.setFont (physicianName.getFont ().deriveFont (fontSize));

		this.add(clinicNameLabel,"align left,cell 2 1");
		this.add(clinicTel,"align left, cell 2 3");
		this.add(physicianName,"align left, cell 2 0");
		this.add(clinicAddress,"align left,cell 2 2");
	}
	
}
