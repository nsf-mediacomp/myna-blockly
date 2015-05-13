package scratchState;

import org.json.JSONException;
import org.json.JSONObject;

public class SpriteState {
	
	private ScriptsState scriptsState;
	
	// Begin JSON keys
	private static final String KEY_SCRIPTS_STATE = "scripts_state";
	
	public SpriteState() {
		scriptsState = new ScriptsState();
	}
	
	private SpriteState(ScriptsState scriptsState) {
		this.scriptsState = scriptsState;
	}
	
	public static SpriteState load(JSONObject storedState) throws JSONException {
		ScriptsState scriptsState = ScriptsState.load(storedState.getJSONObject(KEY_SCRIPTS_STATE));
		return new SpriteState(scriptsState);
	}
	
	public JSONObject save() throws JSONException {
		JSONObject js = new JSONObject();
		js.put(KEY_SCRIPTS_STATE, scriptsState.save());
		return js;
	}
	
	public ScriptsState getScriptsState() {
		return scriptsState;
	}

}
