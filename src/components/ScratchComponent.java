package components;

import org.json.JSONException;
import org.json.JSONObject;


public class ScratchComponent extends AbstScratchComponent {
	
	public ScratchComponent(String name, String nickName, int initialLocationX, int initialLocationY, String parentName) {
		super(name, nickName, initialLocationX, initialLocationY, parentName);
	}
	
	public static ScratchComponent load(JSONObject storedState) throws JSONException {
		String name = storedState.getString(KEY_NAME);
		String nickName = storedState.getString(KEY_NICKNAME);
		int initialX = storedState.getInt(KEY_INITIAL_X);
		int initialY = storedState.getInt(KEY_INITIAL_Y);
		String parentName = storedState.getString(KEY_PARENT_NAME);
		ScratchComponent ret =  new ScratchComponent(name, nickName, initialX, initialY, parentName);
		return ret;
	}
	
	public JSONObject save() throws JSONException {
		JSONObject js = new JSONObject();
		
		js.put(KEY_INITIAL_X, initialX);
		js.put(KEY_INITIAL_Y, initialY);
		js.put(parentName, parentName);
		js.put(KEY_NAME, name);
		js.put(KEY_NICKNAME, nickName);
		
		return js;
	}
	

}
