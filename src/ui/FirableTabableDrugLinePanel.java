package ui;

import javax.swing.JComponent;

@SuppressWarnings({"serial"})
public class FirableTabableDrugLinePanel extends SuggestionPanel implements Tabable {
	
	//initially isFired = false;
	//keyboard check, if there's space, fire, isFired = true
	//keyboard check, if there's still space, do nothing
	// if the space goes away, isFired = false
	
	//extend jcombobox with a flag!!!
	
	public FirableTabableDrugLinePanel(int width) {
		super(width);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setTabTarget(JComponent jc) {
		// TODO Auto-generated method stub
	}

}
