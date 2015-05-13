package scratchState;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import components.AbstScratchComponent;

public class ScriptsState {
	
	// Begin JSON keys
	private static final String KEY_DROP_X = "drop_x";
	private static final String KEY_DROP_Y = "drop_y";
	private static final String KEY_MOVABLE_COMPONENTS = "movable_components";
	private static final String KEY_COMPONENT_DATA = "component_data";
	private static final String KEY_COMPONENT_CLASS = "component_class";
	
	public ScriptsState() {
		
		movableComponents = new Vector<AbstScratchComponent>();
		components = new LinkedHashMap<Integer, AbstScratchComponent>();
		
		dropX = 300;
		dropY = 200;
		
	}
	
	private ScriptsState(Vector<AbstScratchComponent> movableComponents, Map<Integer, AbstScratchComponent> components, int dropX, int dropY) {
		this.movableComponents = movableComponents;
		this.components = components;
		this.dropX = dropX;
		this.dropY = dropY;

	}
	
	private Vector<AbstScratchComponent> movableComponents = new Vector<AbstScratchComponent>();
	private Map<Integer, AbstScratchComponent> components = new LinkedHashMap<Integer, AbstScratchComponent>();

	private int dropX = 300;
	private int dropY = 200;
	
	public static ScriptsState load(JSONObject storedState) throws JSONException {
		int dropX = storedState.getInt(KEY_DROP_X);
		int dropY = storedState.getInt(KEY_DROP_Y);
		JSONArray rawMovableComponents = storedState.getJSONArray(KEY_MOVABLE_COMPONENTS);
		Vector<AbstScratchComponent> movableComponents = new Vector<AbstScratchComponent>(rawMovableComponents.length());
		Map<Integer, AbstScratchComponent> components = new LinkedHashMap<Integer, AbstScratchComponent>();
		movableComponents.setSize(rawMovableComponents.length());
		for (int i = rawMovableComponents.length() - 1; i >= 0; --i) {
			JSONObject componentInfo = rawMovableComponents.getJSONObject(i);
			String componentClass = componentInfo.getString(KEY_COMPONENT_CLASS);
			JSONObject componentData = componentInfo.getJSONObject(KEY_COMPONENT_DATA);
			Method loader;
			try {
				loader = Class.forName(componentClass).getMethod("load", JSONObject.class);
				AbstScratchComponent component;
				component = (AbstScratchComponent) loader.invoke(null, componentData);
				movableComponents.set(i, component);
				components.put(component.getId(), component);
			} catch (IllegalArgumentException e) {
				throw new JSONException(e);
			} catch (IllegalAccessException e) {
				throw new JSONException(e);
			} catch (InvocationTargetException e) {
				throw new JSONException(e);
			} catch (SecurityException e) {
				throw new JSONException(e);
			} catch (NoSuchMethodException e) {
				throw new JSONException(e);
			} catch (ClassNotFoundException e) {
				throw new JSONException(e);
			}
		}
		return new ScriptsState(movableComponents, components, dropX, dropY);
	}
	
	public JSONObject save() throws JSONException {
		JSONObject js = new JSONObject();
		
		JSONArray storedMovableComponents = new JSONArray();
		
		for(AbstScratchComponent component : movableComponents) {
			JSONObject movableComponent = new JSONObject();
			movableComponent.put(KEY_COMPONENT_CLASS, component.getClass().getName());
			movableComponent.put(KEY_COMPONENT_DATA, component.save());
			storedMovableComponents.put(movableComponent);
		}
		
		js.put(KEY_DROP_X, dropX);
		js.put(KEY_DROP_Y, dropY);
		js.put(KEY_MOVABLE_COMPONENTS, storedMovableComponents);
		return js;
		
	}
	
	public Vector<AbstScratchComponent> getMovableComponents() {
		return movableComponents;
	}
	
	public Map<Integer, AbstScratchComponent> getComponents() {
		return components;
	}
	
	public int getDropX() {
		return dropX;
	}
	
	public int getDropY() {
		return dropY;
	}
	
	public void setDropX(int dropX) {
		this.dropX = dropX;
	}
	
	public void setDropY(int dropY) {
		this.dropY = dropY;
	}

}
