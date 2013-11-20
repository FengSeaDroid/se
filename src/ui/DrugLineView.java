package ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.*;

import control.MainControl;
import net.miginfocom.swing.MigLayout;
import domain.Drug;

public class DrugLineView extends JPanel implements FocusListener {

	private JList<Drug> drugList = new JList<Drug>();
	private JScrollPane drugLineScroll;
	private JPanel drugLinePanel;

	public DrugLineView() {
		super(new MigLayout());
		drugLinePanel = new JPanel(new MigLayout());
		drugLineScroll = new JScrollPane(drugLinePanel);
		drugLineScroll.setPreferredSize(new Dimension(this.getMaximumSize().width,400));
		//give it a name to find easily
		drugLinePanel.setName("drugInfoAll");
		//
		
		this.add(drugLineScroll,"wrap");
		drugLinePanel.add(this.drugFiller(),"wrap");

		this.add(refillAndSig(),"wrap,align right");

		this.add(this.buttonView(),"center");
	}

	private JComponent drugFiller(){
		JPanel drugFill = new JPanel(new MigLayout());
		//JTextField drugLine = new JTextField(55);
		JComboBox drugLine = new JComboBox();

		drugLine.setEditable(true);
		drugLine.setPreferredSize(new Dimension(500,10));
		//create the model
		SearchBoxModel sbm = new SearchBoxModel(drugLine,MainControl.getMainControl().getFormulary().getAllDrugSet());
		//set the model on the combobox
		drugLine.setModel(sbm);
		//set the model as the item listener also
		drugLine.addItemListener(sbm);


		drugLine.getEditor().getEditorComponent().addFocusListener(this);

		drugFill.add(drugLine);

		//add function of delete button
		JButton deletebutton=new JButton("delete");
		drugFill.add(deletebutton);

		deletebutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JButton thebutton=(JButton) e.getSource();

				JPanel fatherpanel=(JPanel) thebutton.getParent().getParent();
				JPanel sonpanel=(JPanel) thebutton.getParent();
				fatherpanel.remove(sonpanel);
				fatherpanel.revalidate();
				fatherpanel.repaint();
			}
		});
		//function ends here

		return drugFill;
	}


	//print button here.add function now
	private JComponent buttonView(){
		JPanel printView = new JPanel(new MigLayout());

		JButton printbutton=new JButton("Print");
		printbutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JButton thebuton=(JButton) e.getSource();
				JPanel container=(JPanel) thebuton.getParent().getParent();
				//System.out.println(container);
				
				Component[] clist=container.getComponents();

				for (int i=0;i<clist.length;i++){
					//System.out.println(clist[i]);
				}
				
				System.out.println("--------------------------");
				
				JScrollPane jsp=(JScrollPane) clist[0];
				Component[] clist2=jsp.getViewport().getComponents();

				for (int i=0;i<clist2.length;i++){
					//System.out.println(clist2[i]);
				}
				
				
				Set<String> send = new HashSet();
				
				Component[] clist3=((JPanel)clist2[0]).getComponents();
				for (int i=0;i<clist3.length;i++){
					JComboBox<String> jc=(JComboBox<String>) ((JPanel)clist3[i]).getComponent(0);
					if(jc.getSelectedItem()!=null){
					send.add(jc.getSelectedItem().toString());
					System.out.println(jc.getSelectedItem().toString());
					}
				}

			
			}
		});


		printView.add(printbutton);
		return printView;

	}

	private JComponent refillAndSig(){
		JPanel ras= new JPanel(new MigLayout());

		JPanel jp1 = new JPanel();
		jp1.add(new JLabel("Refill:"));
		JTextField jt5=new JTextField(5);
		jp1.add(jt5);
		ras.add(jp1,"align left");

		JPanel jp2 = new JPanel();
		jp2.add(new JLabel("Signature"));
		
		Image image;
		image=this.getToolkit().createImage(MainControl.getMainControl().getPatientManager().getSignature(MainControl.getMainControl().getPhysicianID()));
		ImageIcon icon=new ImageIcon(image);
		icon.setImage(image.getScaledInstance(100, 30,Image.SCALE_DEFAULT));
		JLabel imageLable=new JLabel();
		imageLable.setIcon(icon);
		
		//JTextField jt6=new JTextField(15);
		//jp2.add(jt6);
		jp2.add(imageLable);
		ras.add(jp2,"span 3,align right");

		return ras;
	}

	@Override
	public void focusGained(FocusEvent e) {
//		this.drugLineScroll.add(this.drugFiller(),"wrap");
		this.drugLinePanel.add(this.drugFiller(),"wrap");
		drugLinePanel.revalidate();
//		drugLineScroll.revalidate();
		
		System.out.println("listened!!!");
	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub

	}
}
