package scratchState;

import org.json.JSONException;
import org.json.JSONObject;

public class StageState {
	
	private ScriptsState scriptsState;
	
	// Begin JSON keys
	private static String KEY_SCRIPTS_STATE = "scripts_state";
	
	public StageState() {
		this.scriptsState = new ScriptsState();
	}
	
	private StageState(ScriptsState scriptsState) {
		this.scriptsState = scriptsState;
	}
	
	public static StageState load(JSONObject storedState) throws JSONException {
		ScriptsState scriptsState = ScriptsState.load(storedState.getJSONObject(KEY_SCRIPTS_STATE));
		return new StageState(scriptsState);
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
