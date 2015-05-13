package scratchState;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

public class AppState {
	
	private static final int FORMAT_VERSION = 1;
	
	private static Map<String, SpriteState> spriteStates;
	private static StageState stageState;
	private static SpriteState selectedSprite;
	private static String selectedKey;
	private static int nextSpriteNumber;
	
	// Begin JSON keys
	private static final String KEY_NEXT_SPRITE_NUMBER = "next_sprite_number";
	private static final String KEY_STAGE_STATE = "stage_state";
	private static final String KEY_SPRITE_STATES = "sprite_states";
	private static final String KEY_SELECTED = "selected";
	
	public static void initialize() {
		spriteStates = new HashMap<String, SpriteState>();
		stageState = new StageState();
		selectedSprite = null;
		selectedKey = null;
		nextSpriteNumber = 1;
	}
	
	public static void setFile(File file) {

	}
	
	public static void initalizeFrom(JSONObject storedState) throws JSONException {
		int nextSpriteNumber = storedState.getInt(KEY_NEXT_SPRITE_NUMBER);
		StageState stageState = StageState.load(storedState.getJSONObject(KEY_STAGE_STATE));
		String selectedKey = storedState.getString(KEY_SELECTED);
		Map<String, SpriteState> spriteStates = new HashMap<String, SpriteState>();
		JSONObject rawSpriteStates = storedState.getJSONObject(KEY_SPRITE_STATES);
		{
			Iterator<String> i = rawSpriteStates.keys();
			while(i.hasNext()) {
				String key = i.next();
				SpriteState spriteState = SpriteState.load(rawSpriteStates.getJSONObject(key));
				spriteStates.put(key, spriteState);
			}
		}
		AppState.spriteStates = spriteStates;
		AppState.selectedKey = selectedKey;
		AppState.selectedSprite = spriteStates.get(selectedKey);
		AppState.nextSpriteNumber = nextSpriteNumber;
		AppState.stageState = stageState;
	}
	
	public static JSONObject save() throws JSONException {
		JSONObject js = new JSONObject();
		JSONObject stageStorage = stageState.save();
		JSONObject spritesStorage = new JSONObject();
		
		for (Entry<String, SpriteState> entry : spriteStates.entrySet()) {
			spritesStorage.put(entry.getKey(), entry.getValue().save());
		}
		
		js.put(KEY_NEXT_SPRITE_NUMBER, nextSpriteNumber);
		js.put(KEY_SELECTED, selectedKey);
		js.put(KEY_STAGE_STATE, stageStorage);
		js.put(KEY_SPRITE_STATES, spritesStorage);
		
		return js;
	}
	
	public static SpriteState getCurrentSpriteState() {
		return selectedSprite;
	}
	
	public static void selectSprite(String spriteName) throws IllegalArgumentException {
		selectedSprite = spriteStates.get(spriteName);
		if (selectedSprite == null) {
			selectedKey = null;
			throw new IllegalArgumentException("No Such Sprite State");
		} else selectedKey = spriteName;
	}
	
	public static void selectStage() {
		selectedSprite = null;
		selectedKey = null;
	}
	
	public static int spriteCount() {
		return spriteStates.size();
	}
	
	public static StageState stageState() {
		return stageState;
	}
	
	public static void addSprite() {
		spriteStates.put("sprite"+ nextSpriteNumber++, new SpriteState());
	}
	
	public static void removeSprite(String name) {
		spriteStates.remove(name);
	}
	
	public static void renameSprite(String oldName, String newName) {
		spriteStates.put(newName, spriteStates.get(oldName));
		spriteStates.remove(oldName);
	}
	
	//** Convenience method to get the ScriptsState of the stage or the currently-selected SpriteState
	public static ScriptsState getCurrentScriptsState() {
		if (selectedSprite == null) return stageState.getScriptsState();
		else return selectedSprite.getScriptsState();
	}
	
	public static Set<String> spriteNames() {
		return spriteStates.keySet();
	}
	
}
