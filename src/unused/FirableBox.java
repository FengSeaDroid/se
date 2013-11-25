package unused;

import javax.swing.JComboBox;
import javax.swing.JTextField;

public class FirableBox extends JComboBox<JTextField>{
	
	private static final long serialVersionUID = -5970625848197390449L;

	private boolean fired = false;
	
	public boolean isFired(){
		return this.fired;
	}
	
	public void setFired(boolean fire){
		this.fired=fire;
	}
}
