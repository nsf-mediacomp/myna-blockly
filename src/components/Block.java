package components;

import org.json.JSONException;
import org.json.JSONObject;

public class Block extends AbstMovableComponent {

	
	public Block(String name, String nickName, int initialX, int initialY, String parentName, Boolean isInFocus, int length, int height, int numParams, int parameterLength1, int parameterLength2, int parameterLength3) {
		super(name, nickName, initialX, initialY, parentName, isInFocus ,length, height, numParams, parameterLength1, parameterLength2, parameterLength3);
		
	}
	
	public static Block load(JSONObject storedState) throws JSONException {
		String name = storedState.getString(KEY_NAME);
		String nickName = storedState.getString(KEY_NICKNAME);
		int initialX = storedState.getInt(KEY_INITIAL_X);
		int initialY = storedState.getInt(KEY_INITIAL_Y);
		String parentName = storedState.getString(KEY_PARENT_NAME);
		boolean isInFocus = storedState.getBoolean(KEY_IN_FOCUS);
		int parentId = storedState.getInt(KEY_PARENT_ID);
		int length = storedState.getInt(KEY_LENGTH);
		int height = storedState.getInt(KEY_HEIGHT);
		int numParams = storedState.getInt(KEY_NUMBER_PARAMETERS);
		int parameterLength1 = storedState.getInt(KEY_PARAMETER_LENGTH_1);
		int parameterLength2 = storedState.getInt(KEY_PARAMETER_LENGTH_2);
		int parameterLength3 = storedState.getInt(KEY_PARAMETER_LENGTH_3);
		
		int x = storedState.getInt(KEY_X);
		int y = storedState.getInt(KEY_Y);
		int id = storedState.getInt(KEY_ID);
		
		Block ret =  new Block(name, nickName, initialX, initialY, parentName, isInFocus, length, height, numParams, parameterLength1, parameterLength2, parameterLength3);
		ret.x = x;
		ret.y = y;
		ret.id = id;
		ret.parentID = parentId;
		ret.id = id;
		return ret;
	}
	
	public JSONObject save() throws JSONException {
		JSONObject js = new JSONObject();
		
		js.put(KEY_INITIAL_X, initialX);
		js.put(KEY_INITIAL_Y, initialY);
		js.put(KEY_X, x);
		js.put(KEY_Y, y);
		js.put(KEY_NAME, name);
		js.put(KEY_NICKNAME, nickName);
		js.put(KEY_ID, id);
		js.put(KEY_PARENT_ID, parentID);
		js.put(KEY_PARENT_NAME, parentName);
		js.put(KEY_LENGTH, length);
		js.put(KEY_HEIGHT, height);
		js.put(KEY_IN_FOCUS, isInFocus);
		js.put(KEY_NUMBER_PARAMETERS, numParams);
		js.put(KEY_PARAMETER_LENGTH_1, parameterLength1);
		js.put(KEY_PARAMETER_LENGTH_2, parameterLength2);
		js.put(KEY_PARAMETER_LENGTH_3, parameterLength3);
		
		return js;
	}
	
}
