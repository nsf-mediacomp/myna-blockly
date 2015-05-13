package util;

import java.awt.event.KeyEvent;

public class StringToKeyCode {

	public static int getKeyCode(int value) {
		int keyValue = 0;
		
		if(value == 1)
			keyValue = KeyEvent.VK_1;
		else if(value == 2)
			keyValue = KeyEvent.VK_2;
		else if(value == 3)
			keyValue = KeyEvent.VK_3;
		else if(value == 4)
			keyValue = KeyEvent.VK_4;
		else if(value == 5)
			keyValue = KeyEvent.VK_5;
		else if(value == 6)
			keyValue = KeyEvent.VK_6;
		else if(value == 7)
			keyValue = KeyEvent.VK_7;
		else if(value == 8)
			keyValue = KeyEvent.VK_8;
		else if(value == 9)
			keyValue = KeyEvent.VK_9;
		else if(value == 0)
			keyValue = KeyEvent.VK_0;
		
		return keyValue;
	}
	
	public static int getKeyCode(char value) {
		int keyValue = 0;
		
		if(value == 'a')
			keyValue = KeyEvent.VK_A;
		else if(value == 'b')
			keyValue = KeyEvent.VK_B;
		else if(value == 'c')
			keyValue = KeyEvent.VK_C;
		else if(value == 'd')
			keyValue = KeyEvent.VK_D;
		else if(value == 'e')
			keyValue = KeyEvent.VK_E;
		else if(value =='f')
			keyValue = KeyEvent.VK_F;
		else if(value == 'g')
			keyValue = KeyEvent.VK_G;
		else if(value == 'h')
			keyValue = KeyEvent.VK_H;
		else if(value == 'i')
			keyValue = KeyEvent.VK_I;
		else if(value == 'j')
			keyValue = KeyEvent.VK_J;
		else if(value == 'k')
			keyValue = KeyEvent.VK_K;
		else if(value == 'j')
			keyValue = KeyEvent.VK_J;
		else if(value == 'l')
			keyValue = KeyEvent.VK_L;
		else if(value == 'm')
			keyValue = KeyEvent.VK_M;
		else if(value == 'n')
			keyValue = KeyEvent.VK_N;
		else if(value == 'o')
			keyValue = KeyEvent.VK_O;
		else if(value == 'p')
			keyValue = KeyEvent.VK_P;
		else if(value == 'q')
			keyValue = KeyEvent.VK_Q;
		else if(value == 'r')
			keyValue = KeyEvent.VK_R;
		else if(value == 's')
			keyValue = KeyEvent.VK_S;
		else if(value == 't')
			keyValue = KeyEvent.VK_T;
		else if(value == 'u')
			keyValue = KeyEvent.VK_U;
		else if(value == 'v')
			keyValue = KeyEvent.VK_V;
		else if(value == 'w')
			keyValue = KeyEvent.VK_W;
		else if(value == 'x')
			keyValue = KeyEvent.VK_X;
		else if(value == 'y')
			keyValue = KeyEvent.VK_Y;
		else if(value == 'z')
			keyValue = KeyEvent.VK_Z;
		
		return keyValue;
	}	
	
}
