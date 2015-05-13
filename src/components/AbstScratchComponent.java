package components;
import org.json.JSONException;
import org.json.JSONObject;

import scratchState.AppState;

public abstract class AbstScratchComponent {
	
	protected final String name;
	protected final String nickName;
	protected final int initialX;
	protected final int initialY;

	protected final String parentName;
	
	// Begin JSON keys
	
	protected static final String KEY_NAME = "name";
	protected static final String KEY_NICKNAME = "nickname";
	protected static final String KEY_INITIAL_X = "initial_x";
	protected static final String KEY_INITIAL_Y = "initial_y";
	protected static final String KEY_PARENT_NAME = "parent_name";
	
	public AbstScratchComponent(String name, String nickName, int initialLocationX, int initialLocationY, String parentName) {
		this.name = name;
		this.nickName = nickName;
		this.initialX = initialLocationX;
		this.initialY = initialLocationY;
		this.parentName = parentName;
	}
	
	public String getName() {	
		return this.name;
	}

	public String getNickName() {
		return this.nickName;
	}
	
	
	
	public int getInitialX() {
		return initialX;
	}

	public int getInitialY() {
		return initialY;
	}

	public String getParentName() {
		return this.parentName;
	}
	
	
	
	// methods of AbstMovableComponent
	/**
	 * @return the currentLocationX
	 */
	public int getX() {
		return AppState.getCurrentScriptsState().getDropX();
	}



	/**
	 * @param currentLocationX the currentLocationX to set
	 */
	public void setX(int currentLocationX) {
		AppState.getCurrentScriptsState().setDropX(currentLocationX);
	}



	/**
	 * @return the currentLocationY
	 */
	public int getY() {
		return AppState.getCurrentScriptsState().getDropY();
	}



	/**
	 * @param currentLocationY the currentLocationY to set
	 */
	public void setY(int currentLocationY) {
		AppState.getCurrentScriptsState().setDropY(currentLocationY);
	}

	public Boolean getIsInFocus() {
		throw new UnsupportedOperationException();

	}
	public void setIsInFocus(Boolean isInFocus) {
		throw new UnsupportedOperationException();

	}
	
	/**
	 * @return the length
	 */
	public int getLength() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @param length the length to set
	 */
	public void setLength(int length) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @return the width
	 */
	public int getHeight() {
		throw new UnsupportedOperationException();
	}
	

	/**
	 * @param moveBy moves the components down by ipMoveBy 
	 */
	public void moveDown(int moveBy) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @param width the width to set
	 */
	public void setHeight(int width) {
		throw new UnsupportedOperationException();
	}
	
	public void setId(int id) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @return the id
	 */
	public int getId() {
		throw new UnsupportedOperationException();
	}
	
	public int getParentID() {
		throw new UnsupportedOperationException();
	}
	
	public void setParentID(int parentID) {
		throw new UnsupportedOperationException();
	}
	
	//methods of Block container
	public int getInitialInsertHeight() {
		throw new UnsupportedOperationException();
	}
	
	public void addComponent(AbstMovableComponent comp) {
		throw new UnsupportedOperationException();
	}
	
	public void addComponentAt(AbstMovableComponent component, int position) {
		throw new UnsupportedOperationException();
    }
	
	 public int getIndexOfComponentInBlocksCollection(AbstScratchComponent component) {
			throw new UnsupportedOperationException();
	    }
	 
	public void removeComponent(AbstMovableComponent comp) {
		throw new UnsupportedOperationException();
	}
	public void setInsertHeight(int currentInsertHeight) {
		throw new UnsupportedOperationException();
	}

	public int getCurrentInsertHeight() {
		throw new UnsupportedOperationException();
	}
	
	public int getChildrenHeight() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * @return the parameterLength1
	 */
	public int getParameterLength1() {
		throw new UnsupportedOperationException();
	}



	/**
	 * @param parameterLength1 the parameterLength1 to set
	 */
	public void setParameterLength1(int parameterLength1) {
		throw new UnsupportedOperationException();
	}



	/**
	 * @return the parameterLength2
	 */
	public int getParameterLength2() {
		throw new UnsupportedOperationException();
	}



	/**
	 * @param parameterLength2 the parameterLength2 to set
	 */
	public void setParameterLength2(int parameterLength2) {
		throw new UnsupportedOperationException();
	}



	/**
	 * @return the parameterLength3
	 */
	public int getParameterLength3() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @param parameterLength3 the parameterLength3 to set
	 */
	public void setParameterLength3(int parameterLength3) {
		throw new UnsupportedOperationException();
	}
	
	public abstract JSONObject save() throws JSONException;
}
