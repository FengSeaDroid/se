package ui.listener;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import control.MainControl;

public class PrintButtonSubmit implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		//get the time stamp
		Date issuedate=new Date(e.getWhen());
		System.out.println(issuedate);


		//
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

		//get the effectivedate
		JPanel jp= (JPanel) clist[1];
		JPanel jp2=(JPanel) jp.getComponent(1);
		JTextField effectivedateField=null;
		for (int i = 0; i < jp2.getComponentCount(); i++) {
			if(jp2.getComponent(i).getName()!=null){
				if(jp2.getComponent(i).getName().equals("effectiveDateView")){
					effectivedateField=(JTextField) 
							((JPanel)jp.getComponent(i)).getComponent(1);
				}
			}
		}

		//System.out.println(effectivedateField.getText());

		String effectivedate=effectivedateField.getText();

		Component[] clist3=((JPanel)clist2[0]).getComponents();
		for (int i=0;i<clist3.length;i++){
			JComboBox<String> jc=(JComboBox<String>) ((JPanel)clist3[i]).getComponent(0);
			if(jc.getSelectedItem()!=null){
				send.add(jc.getSelectedItem().toString());
				System.out.println(jc.getSelectedItem().toString());
			}
		}

		try {
			MainControl.getMainControl().print(send,effectivedate);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

}
