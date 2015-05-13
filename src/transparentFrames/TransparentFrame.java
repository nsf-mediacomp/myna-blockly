package transparentFrames;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

public class TransparentFrame extends JFrame{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private GridBagLayout layout;
	private GridBagConstraints gbConstraints;
	
	private JLabel label;
	//private JLabel label_for_no_2;
	
	public TransparentFrame(int labelNumber, int x, int y) {
		
		super("tt");
		
		setUndecorated(true);
		this.setAlwaysOnTop(true);
		
		//intitialize labels
		label = new JLabel(Integer.toString(labelNumber));
		label.setBorder(new EmptyBorder(1, 2, 1, 2));
		add(label);
		
		setLocation(x, y);
		pack();
		
	}
	
}
