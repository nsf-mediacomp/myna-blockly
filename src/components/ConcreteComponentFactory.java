package components;

import java.io.File;





import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Vector;


import dynamicGrammar.DynamicGrammarEngine;
import scratchState.AppState;
import scratchState.ScriptsState;
import util.Stringifier;

public class ConcreteComponentFactory {
	
	private static ConcreteComponentFactory instance = null;
	
	private enum FolderName {
		STATIC("staticComponents"),
		BLOCK("movableBlocks"),
		CONTAINER("movableBlockContainers");
		
		private final String folderName;
		private FolderName(String folderName) {
			this.folderName = folderName;
		}
		
		public String toString() {
			return folderName;
		}
	};
	
	private static enum PropertyName {
		NAME("name"),
		NICK_NAME("nickName"),
		PARENT_NAME("parentName"),
		INITIALLOCATION_X("initialLocationX"),
		INITIAL_LOCATION_Y("initialLocationY"),
		IS_IN_FOCUS("isInFocus"),
		LENGTH("length"),
		HEIGHT("height"),
		INITIAL_INSERT_HEIGHT("initialInsertHeight"),
		NUMBER_PARAMETERS("numParams"),
		PARAMETER_LENGTH_1("parameterLength1"),
		PARAMETER_LENGTH_2("parameterLength2"),
		PARAMETER_LENGTH_3("parameterLength3") ;
		
		private final String propertyName;
		private PropertyName(String propertyName) {
			this.propertyName = propertyName;
		}
		
		public String toString() {
			return propertyName;
		}
	}
	
	private static Vector<AbstScratchComponent> scratchComponentCollection = new Vector<AbstScratchComponent>();
		
	private ConcreteComponentFactory() {
		
	}
	
	public static ConcreteComponentFactory getInstance() {
		if(instance == null) {
			instance = new ConcreteComponentFactory();
		}
		return instance;
	}
	
	public AbstScratchComponent deliverObjects(String componentName) {
		if(componentName.equals("pause") || componentName.equals("resume") || componentName.equals("okay") || componentName.equals("help") || componentName.equals("done")) {
			return null;
		}
		String folderFound = this.getFolderName(componentName);
		int id = AppState.getCurrentScriptsState().getComponents().size() + 1;
		
		if(folderFound.equals(FolderName.STATIC.toString())) {
		//The component is static
			AbstScratchComponent scratchComponent = this.getScratchComponent(folderFound, componentName);
			scratchComponentCollection.add(scratchComponent);
			return scratchComponent;
		}
		
		else if(folderFound.equals(FolderName.BLOCK.toString())) {
			AbstScratchComponent scratchComponent = this.createBlock(folderFound, componentName);
			
			//for dynamic grammar set ID
			scratchComponent.setId(id);
            this.addToGrammar(id);
			this.addToHashTable(id, scratchComponent);
			return scratchComponent;
		}
		else if(folderFound.equals(FolderName.CONTAINER.toString())) {
			AbstScratchComponent scratchComponent = this.createComponentContainer(folderFound, componentName);
			//for dynamic grammar set ID
			scratchComponent.setId(id);
            this.addToGrammar(id);
			this.addToHashTable(id, scratchComponent);
			return scratchComponent;
		}
		else {
			return null;
		}
		
	}
	
	private String getFolderName(String componentName) {
		int foundIndex = -1;
		for (FolderName folderName : FolderName.values()) {
			boolean exists = getClass().getResource("/components/"+folderName.toString()+"/"+componentName+".properties") != null;
			if (exists) return folderName.toString();
		}
		
		return null;
	}
	

	private void addToGrammar(int id) {
		DynamicGrammarEngine grammar = DynamicGrammarEngine.getInstance();

		grammar.generateDynamicCommand(id);

	}
	
	private void addToHashTable(int key, AbstScratchComponent component) {
		AppState.getCurrentScriptsState().getComponents().put(key, component);
	}
	
	public AbstScratchComponent deliverExistingObjectsByNumber(String componentNumber) {
		int componentID = Stringifier.numberWithName(componentNumber);
		//int componentLocationInVector = componentID - 1;
		//return SpriteState.getComponentAtLocationInState(componentLocationInVector);
		return AppState.getCurrentScriptsState().getComponents().get(componentID);
	}
	
	
	public AbstScratchComponent getScratchComponent(String folderFound, String componentName) {
		AbstScratchComponent existingObject = this.getExistingObject(componentName);
		//Check if the object is already existing
		if( existingObject == null) {
			AbstScratchComponent newObject =  this.createScratchComponent(folderFound,componentName);
			//add new object to the vector
			return newObject;
		}
		else {
			return existingObject;
		}
		
	}
	
	private AbstScratchComponent getExistingObject(String componentName) {
		AbstScratchComponent result = null;
	
		for(AbstScratchComponent temp: scratchComponentCollection) {
			if(temp.getName().equals(componentName)) {
				result = temp;
				break;
			}
		}
		
		return result;
		
	}
	
	private AbstScratchComponent createScratchComponent(String folderFound, String componentName) {
		
		Properties prop = this.getPropertiesByComponentName(folderFound, componentName);
		
		String name = prop.getProperty(PropertyName.NAME.toString());
		String nickName = prop.getProperty(PropertyName.NICK_NAME.toString());
		String parentName = prop.getProperty(PropertyName.PARENT_NAME.toString());
		String intialLocationXAsString = prop.getProperty(PropertyName.INITIALLOCATION_X.toString());
		String initialLocationYAsString = prop.getProperty(PropertyName.INITIAL_LOCATION_Y.toString());
				
		int initialLocationX = this.stringToInt(intialLocationXAsString);
		int initialLocationY = this.stringToInt(initialLocationYAsString);
		
		AbstScratchComponent iSCInstance = new ScratchComponent(name, nickName, initialLocationX, initialLocationY, parentName);
		
		return iSCInstance;
	}
	
	private AbstScratchComponent createBlock(String folderFound, String componentName) {
		
		Properties prop = this.getPropertiesByComponentName(folderFound, componentName);
		String name = prop.getProperty(PropertyName.NAME.toString());
		String nickName = prop.getProperty(PropertyName.NICK_NAME.toString());
		String parentName = prop.getProperty(PropertyName.PARENT_NAME.toString());
		String intialLocationXAsString = prop.getProperty(PropertyName.INITIALLOCATION_X.toString());
		String initialLocationYAsString = prop.getProperty(PropertyName.INITIAL_LOCATION_Y.toString());		
		String focusAsString = prop.getProperty(PropertyName.IS_IN_FOCUS.toString());
		String lengthAsString = prop.getProperty(PropertyName.LENGTH.toString());
		String heightAsString = prop.getProperty(PropertyName.HEIGHT.toString());
		String numParamsAsString = prop.getProperty(PropertyName.NUMBER_PARAMETERS.toString());
		String parameterLength1AsString = prop.getProperty(PropertyName.PARAMETER_LENGTH_1.toString());
		String parameterLength2AsString = prop.getProperty(PropertyName.PARAMETER_LENGTH_2.toString());
		String parameterLength3AsString = prop.getProperty(PropertyName.PARAMETER_LENGTH_3.toString());

				
		int initialLocationX = this.stringToInt(intialLocationXAsString);
		int initialLocationY = this.stringToInt(initialLocationYAsString);
		
		Boolean isInFocus = Boolean.parseBoolean(focusAsString);
		
		int length = this.stringToInt(lengthAsString);
		int height = this.stringToInt(heightAsString);
		int numParams = this.stringToInt(numParamsAsString);
		int parameterLength1 = this.manageEmptyStrings(parameterLength1AsString);
		int parameterLength2 = this.manageEmptyStrings(parameterLength2AsString);
		int parameterLength3 = this.manageEmptyStrings(parameterLength3AsString);
	
		AbstScratchComponent iSCInstance = new Block(name, nickName, initialLocationX,  initialLocationY, parentName,isInFocus,length, height, numParams, parameterLength1, parameterLength2, parameterLength3);
		
		return iSCInstance;
		
	}
	
	private AbstScratchComponent createComponentContainer(String folderFound, String componentName) {
		Properties prop = this.getPropertiesByComponentName(folderFound, componentName);
		
		String name = prop.getProperty(PropertyName.NAME.toString());
		String nickName = prop.getProperty(PropertyName.NICK_NAME.toString());
		String parentName = prop.getProperty(PropertyName.PARENT_NAME.toString());
		String intialLocationXAsString = prop.getProperty(PropertyName.INITIALLOCATION_X.toString());
		String initialLocationYAsString = prop.getProperty(PropertyName.INITIAL_LOCATION_Y.toString());		
		String focusAsString = prop.getProperty(PropertyName.IS_IN_FOCUS.toString());
		String lengthAsString = prop.getProperty(PropertyName.LENGTH.toString());
		String heightAsString = prop.getProperty(PropertyName.HEIGHT.toString());
		String numParamsAsString = prop.getProperty(PropertyName.NUMBER_PARAMETERS.toString());
		String parameterLength1AsString = prop.getProperty(PropertyName.PARAMETER_LENGTH_1.toString());
		String parameterLength2AsString = prop.getProperty(PropertyName.PARAMETER_LENGTH_2.toString());
		String parameterLength3AsString = prop.getProperty(PropertyName.PARAMETER_LENGTH_3.toString());
		String initialInsertHeightAsString = prop.getProperty(PropertyName.INITIAL_INSERT_HEIGHT.toString());
		
		Boolean isInFocus = Boolean.parseBoolean(focusAsString);
		
		int initialLocationX = this.stringToInt(intialLocationXAsString);
		int initialLocationY = this.stringToInt(initialLocationYAsString);
		int length = this.stringToInt(lengthAsString);
		int height = this.stringToInt(heightAsString);
		int initialInsertHeight  = this.stringToInt(initialInsertHeightAsString);
		int numParams = this.stringToInt(numParamsAsString);
		int parameterLength1 = this.manageEmptyStrings(parameterLength1AsString);
		int parameterLength2 = this.manageEmptyStrings(parameterLength2AsString);
		int parameterLength3 = this.manageEmptyStrings(parameterLength3AsString);
				
		AbstScratchComponent iSCInstance = new BlockContainer(name, nickName, initialLocationX, initialLocationY, parentName,isInFocus, length, height, initialInsertHeight, numParams, parameterLength1, parameterLength2, parameterLength3 );
		
		return iSCInstance;
	}
	
   private int manageEmptyStrings(String ipString) {
	   int opInt;
	   if(ipString != null) {
		   opInt = this.stringToInt(ipString);
		}
		else {
			opInt = 0;
		}
	   return opInt;
   }
	
	private int stringToInt(String ipString) {
		int result = Integer.parseInt(ipString);
		return result;
	}
	
	private Properties getPropertiesByComponentName(String folderFound, String componentName) {
		Properties prop = new Properties();
		try {
			 prop.load(getClass().getResourceAsStream("/components/"+folderFound +"/"+componentName+".properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

}
