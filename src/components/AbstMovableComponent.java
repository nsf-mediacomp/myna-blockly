package components;

import java.util.List;

public abstract class AbstMovableComponent extends AbstScratchComponent {

	protected int x;
	protected int y;
	protected Boolean isInFocus;
	protected int length;
	protected int height;
	protected int id = 0;
    protected int parentID = 0;	
    protected int numParams;
    protected int parameterLength1;
    protected int parameterLength2;
    protected int parameterLength3;
    
    // Begin JSON keys
    
    protected static final String KEY_X = "x";
    protected static final String KEY_Y = "y";
    protected static final String KEY_IN_FOCUS = "in_focus";
    protected static final String KEY_LENGTH = "length";
    protected static final String KEY_HEIGHT = "height";
    protected static final String KEY_ID = "id";
    protected static final String KEY_PARENT_ID = "parent_id";
    protected static final String KEY_NUMBER_PARAMETERS = "number_parameters";
    protected static final String KEY_PARAMETER_LENGTH_1 = "parameter_length_1";
    protected static final String KEY_PARAMETER_LENGTH_2 = "parameter_length_2";
    protected static final String KEY_PARAMETER_LENGTH_3 = "parameter_length_3";
			
	public AbstMovableComponent(String name, String nickName, int initialLocationX, int initialLocationY, String parentName, 
			 Boolean isInFocus, int length, int height, int numParams, int parameterLength1, int parameterLength2, int parameterLength3) {
		super(name, nickName, initialLocationX, initialLocationY, parentName);
		
		this.x = initialLocationX;
		this.y = initialLocationY;
		this.isInFocus = isInFocus;
		this.length = length;
		this.height = height;
		this.numParams = numParams;
		this.parameterLength1 = parameterLength1;
		this.parameterLength2 = parameterLength2;
		this.parameterLength3 = parameterLength3;
	}
	
	

	/**
	 * @return the x-coordinate of the current location
	 */
	public int getX() {
		return x;
	}

    

	public void setX(int currentLocationX) {
		this.x = currentLocationX;
	}



	/**
	 * @return the y-coordinate of the current location
	 */
	public int getY() {
		return y;
	}



	public void setY(int currentLocationY) {
		this.y = currentLocationY;
	}



	public Boolean getIsInFocus() {
		return isInFocus;
	}

	public void setIsInFocus(Boolean isInFocus) {
		this.isInFocus = isInFocus;
	}

	/**
	 * @return the length
	 */
	public int getLength() {
		return length;
	}

	/**
	 * @param length the length to set
	 */
	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * @return the width
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param width the width to set
	 */
	public void setHeight(int width) {
		this.height = width;
	}
	

	/**
	 * @param moveBy moves the components down by moveBy 
	 */
	public void moveDown(int moveBy) {
		this.setY(this.getY()+ moveBy);
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * @return the parentID
	 */
	public int getParentID() {
		return parentID;
	}



	/**
	 * @param parentID the parentID to set
	 */
	public void setParentID(int parentID) {
		this.parentID = parentID;
	}



	public int getInitialInsertHeight() {
		throw new UnsupportedOperationException();
	}

	public void addComponent(AbstMovableComponent comp) {
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
	
	 public int getIndexOfComponentInBlocksCollection(AbstScratchComponent component) {
			throw new UnsupportedOperationException();
	    }
	    
	 public List<AbstMovableComponent> getSubListOfBlocksCollection(int startIndex, int endIndex) {
			throw new UnsupportedOperationException();
	 }
	 public int getSizeOfBlocksCollection() {
			throw new UnsupportedOperationException();
	 }
	    
	 public void addComponentAt(AbstMovableComponent component, int position) {
			throw new UnsupportedOperationException();
	    }



	/**
	 * @return the parameterLength1
	 */
	public int getParameterLength1() {
		return parameterLength1;
	}



	/**
	 * @param parameterLength1 the parameterLength1 to set
	 */
	public void setParameterLength1(int parameterLength1) {
		this.parameterLength1 = parameterLength1;
	}

    public void addParameterLength1(int value) {
    	this.parameterLength1 += value;
    }

	/**
	 * @return the parameterLength2
	 */
	public int getParameterLength2() {
		return parameterLength2;
	}



	/**
	 * @param parameterLength2 the parameterLength2 to set
	 */
	public void setParameterLength2(int parameterLength2) {
		this.parameterLength2 = parameterLength2;
	}

    public void addParameterLength2(int value) {
    	this.parameterLength2 = this.parameterLength2 + value;
    }

	/**
	 * @return the parameterLength3
	 */
	public int getParameterLength3() {
		return parameterLength3;
	}



	/**
	 * @param parameterLength3 the parameterLength3 to set
	 */
	public void setParameterLength3(int parameterLength3) {
		this.parameterLength3 = parameterLength3;
	}
	
    public void addParameterLength3(int value) {
    	this.parameterLength3 = this.parameterLength3 + value;
    }
    
	//These are methods to perform actions on Scratch components
	public int getNumParams(){
		return numParams;
	}

}
