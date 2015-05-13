package components;

import java.util.List;
import java.util.Vector;

import org.json.JSONException;
import org.json.JSONObject;

public class BlockContainer extends AbstMovableComponent {
    private final int initialInsertHeight;
    private int insertHeight;
    
    public final static int WIDTH_BEFORE_INSERT_AREA = 14;
    public final static int INSERT_AREA_HEIGHT = 11;
    
    
    // Begin JSON Keys
    private static final String KEY_INITIAL_INSERT_HEIGHT = "initial_insert_height";
    private static final String KEY_INSERT_HEIGHT = "insert_height";
    
    private Vector<AbstMovableComponent> blocksCollection = new Vector<AbstMovableComponent>();
    
	public BlockContainer(String name, String nickName, int initialX, int initialY, 
			String parentName, Boolean isInFocus, int length,int height,int initialInsertHeight, int numParams, int parameterLength1, int parameterLength2, int parameterLength3 
			) {
		super(name, nickName, initialX, initialY, parentName, isInFocus, length, height, numParams, parameterLength1, parameterLength2, parameterLength3);
		this.initialInsertHeight = initialInsertHeight;
		this.setInsertHeight(initialInsertHeight);
	}
	
	public static BlockContainer load(JSONObject storedState) throws JSONException {
		String name = storedState.getString(KEY_NAME);
		String nickName = storedState.getString(KEY_NICKNAME);
		int initialX = storedState.getInt(KEY_INITIAL_X);
		int initialY = storedState.getInt(KEY_INITIAL_Y);
		String parentName = storedState.getString(KEY_PARENT_ID);
		boolean isInFocus = storedState.getBoolean(KEY_IN_FOCUS);
		int length = storedState.getInt(KEY_LENGTH);
		int height = storedState.getInt(KEY_HEIGHT);
		int initialInsertHeight = storedState.getInt(KEY_INITIAL_INSERT_HEIGHT);
		int insertHeight = storedState.getInt(KEY_INSERT_HEIGHT);
		int numParams = storedState.getInt(KEY_NUMBER_PARAMETERS);
		int parameterLength1 = storedState.getInt(KEY_PARAMETER_LENGTH_1);
		int parameterLength2 = storedState.getInt(KEY_PARAMETER_LENGTH_2);
		int parameterLength3 = storedState.getInt(KEY_PARAMETER_LENGTH_3);
		
		int x = storedState.getInt(KEY_X);
		int y = storedState.getInt(KEY_Y);
		int id = storedState.getInt(KEY_ID);
		int parentId = storedState.getInt(KEY_PARENT_ID);
		BlockContainer ret =  new BlockContainer(name, nickName, initialX, initialY, parentName, isInFocus, length, height, initialInsertHeight, numParams, parameterLength1, parameterLength2, parameterLength3);
		ret.x = x;
		ret.y = y;
		ret.id = id;
		ret.parentID = parentId;
		ret.insertHeight = insertHeight; 
		return ret;
	}

	public JSONObject save() throws JSONException {
		JSONObject js = new JSONObject();
		
		js.put(KEY_INITIAL_INSERT_HEIGHT, initialInsertHeight);
		js.put(KEY_INSERT_HEIGHT, insertHeight);
		js.put(KEY_INITIAL_X, initialX);
		js.put(KEY_INITIAL_Y, initialY);
		js.put(KEY_NAME, name);
		js.put(KEY_NICKNAME, nickName);
		js.put(KEY_ID, id);
		js.put(KEY_LENGTH, length);
		js.put(KEY_HEIGHT, height);
		js.put(KEY_IN_FOCUS, isInFocus);
		js.put(KEY_PARENT_ID, parentID);
		js.put(KEY_NUMBER_PARAMETERS, numParams);
		js.put(KEY_PARAMETER_LENGTH_1, parameterLength1);
		js.put(KEY_PARAMETER_LENGTH_2, parameterLength2);
		js.put(KEY_PARAMETER_LENGTH_3, parameterLength3);
		
		return js;
	}


	public int getInitialInsertHeight() {
		return this.initialInsertHeight;
	}
	
	public void setInsertHeight(int currentInsertHeight) {
		this.insertHeight = currentInsertHeight;
	}



	/**
	 * @return the currentInsertHeight
	 */
	public int getInsertHeight() {
		return insertHeight;
	}



	public void addComponent(AbstMovableComponent comp) {
		blocksCollection.add(comp);
	}
	public void removeComponent(AbstMovableComponent comp) {
		blocksCollection.remove(comp);
	}
	
	/* (non-Javadoc)
	 * @see components.AbstMovableComponent#getHeight()
	 */
	
	@Override
	public int getHeight() {
		int totalHeightOfContainer = super.getHeight();
		for(AbstMovableComponent temp : this.blocksCollection ) {
			//For the first element reduce the increment in height
			if(temp == blocksCollection.elementAt(0)) {
				totalHeightOfContainer = totalHeightOfContainer + temp.getHeight() - BlockContainer.INSERT_AREA_HEIGHT ;
			}
			
			else {
				totalHeightOfContainer = totalHeightOfContainer + temp.getHeight();
			}
			
		}
		
		return totalHeightOfContainer;
	}
	

	public int getChildrenHeight() {
		int totalHeightOfChildren = 0;
		for(AbstMovableComponent temp : this.blocksCollection ) {
			//For the first element reduce the increment in height
				//totalHeightOfChildren = totalHeightOfChildren + temp.getHeight() - BlockContainer.INSERT_AREA_HEIGHT ;
				totalHeightOfChildren = totalHeightOfChildren + temp.getHeight();
			
			
		}
		
		return totalHeightOfChildren;
	}
	

	/**
	 * @param moveBy moves the components down by moveBy -- It also recursively calls all the children of the given component 
	 */
	@Override
	public void moveDown(int moveBy) {
		super.moveDown(moveBy);
		for(AbstMovableComponent temp : this.blocksCollection ) {
			temp.moveDown(moveBy);
		}
	}

    public int getIndexOfComponentInBlocksCollection(AbstScratchComponent component) {
    	return this.getBlocksCollection().indexOf(component);
    }
    
    public List<AbstMovableComponent> getSubListOfBlocksCollection(int startIndex, int endIndex) {
    	return this.blocksCollection.subList(startIndex, endIndex);
    }
    
    public int getSizeOfBlocksCollection() {
    	return this.getBlocksCollection().size();
    }
    
    public void addComponentAt(AbstMovableComponent component, int position) {
    	this.getBlocksCollection().insertElementAt(component, position);
    }
    

	/**
	 * @return the blocksCollection
	 */
	private Vector<AbstMovableComponent> getBlocksCollection() {
		return blocksCollection;
	}
	
	

}
