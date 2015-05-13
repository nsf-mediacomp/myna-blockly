package robot;

import java.awt.AWTException;
import java.awt.Robot;

public class SingletonRobot extends Robot {
	private static Robot instance = null;
	private SingletonRobot() throws AWTException{
		
	}
	public static Robot getInstance() {
	      if(instance == null) {
	         try {
				instance = new Robot();
			} catch (AWTException e) {
				e.printStackTrace();
			}
	      }
	      return instance;
	}
}
