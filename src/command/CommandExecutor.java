package command;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.awt.Dialog;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import robot.SingletonRobot;
import scratchState.AppState;
import scratchState.ScriptsState;

import components.AbstMovableComponent;
import components.AbstScratchComponent;
import components.BlockContainer;

import util.StringToKeyCode;


public class CommandExecutor {
	
	private static CommandExecutor instance = null;
	
	private static Robot robot = null;
	
	private static JFrame dialogRoot = new JFrame();
	
	//Needed for adjusting horizontal expansion of parameters
	private String temp = "";
	private AbstMovableComponent editedComponent = null;
	private int parameterNumber = 0;
	
	private CommandExecutor() {
	}
	
	
	public static CommandExecutor getInstance() {
		if(instance == null) {
			instance = new CommandExecutor();
			robot = SingletonRobot.getInstance();
		}
		return instance;
	}
	
	public void click(int clickX, int clickY) {
		//To be continued
		
		robot.mouseMove(clickX, clickY);   
		robotMousePress();
		robotMouseRelease();
	}
	
	public void drag(AbstScratchComponent comp) {

		int currentX = comp.getX();
		int currentY = comp.getY();
		
		
		robot.mouseMove(currentX, currentY);   
		robotMousePress();
		//This is to prevent "help" coming up on scratch when clicked continuously on an object. 
		//We just click and move the object 5 pixels towards its right direction.
		try {
			//Add this delay or else move after click will happen too soon and help will be displayed instead of moving the component.
			Thread.sleep(30);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		currentX = currentX + 5;
				
		comp.setX(currentX);
		
		robot.mouseMove(currentX, currentY);   

	}
	
	public void dragAndDrop(AbstScratchComponent comp) {
				
		int currentX =  comp.getX();
		int currentY =  comp.getY();
		
		ScriptsState scriptsState = AppState.getCurrentScriptsState();
		
		int tempCurrentDropPointX = scriptsState.getDropX();
		int tempCurrentDropPointY = scriptsState.getDropY();
				
		robot.mouseMove(currentX, currentY);   
		robotMousePress();
		
    	robot.mouseMove(tempCurrentDropPointX, tempCurrentDropPointY);
		
		//change components current location								
		comp.setX(tempCurrentDropPointX);
		comp.setY(tempCurrentDropPointY);
		
		//add the component at the end of the Sprite State Vector
		AppState.getCurrentScriptsState().getMovableComponents().add(comp);
	
		int compHeight = comp.getHeight();
		
		this.moveCurrentDropPoint(compHeight);
        
		robotMouseRelease();
	}
	
	public void dropAfter(AbstScratchComponent toBeDropped, AbstScratchComponent target) {

		int targetLocationX = target.getX();
		int targetLocationY = target.getY();
		
		int targetCompHeight = target.getHeight();
		
		int currentDropX =  targetLocationX;
		int currentDropY = targetLocationY + targetCompHeight ;
			
		
		robot.mouseMove(currentDropX, currentDropY);
		robotMouseRelease();
		
		//move current drop point
		this.moveCurrentDropPoint(toBeDropped.getHeight());
		
		//Move the component's location
		toBeDropped.setX(currentDropX);
		toBeDropped.setY(currentDropY);
		
		if(!isInnerComponent(target)) {
			ScriptsState scriptsState = AppState.getCurrentScriptsState();
			//find if target is the last element if so insert toBeDroppped at the end of the vector Else insert in between
				if(!scriptsState.getMovableComponents().lastElement().equals(target)) {
					//add in between
					this.moveDownComponentsBelow(scriptsState.getMovableComponents().indexOf(target) + 1, scriptsState.getMovableComponents().size(), toBeDropped.getHeight());
				}
				//add the component to the vector
				this.addComponentToCollectionAt(toBeDropped, scriptsState.getMovableComponents().indexOf(target) + 1);
		
		}
		//If target is not a child insert tobedropped into the movablecomponentvector else insert into vector of the parent.
		else {
			ScriptsState scriptsState = AppState.getCurrentScriptsState();
			//get the position of target in its parent.
 			//in any case call the moving down logic
 			this.moveComponentsBelowInnerComponent((AbstMovableComponent)target,toBeDropped.getHeight(),false);
 			
 			//get the super parent of target
 			AbstScratchComponent superParent = this.getSuperParent(target);
 			//move the components below the super parent below
 			this.moveDownComponentsBelow(scriptsState.getMovableComponents().indexOf(superParent) + 1 , scriptsState.getMovableComponents().size(), toBeDropped.getHeight());
 			
			AbstScratchComponent parentOfTarget = scriptsState.getComponents().get(target.getParentID());
			toBeDropped.setParentID(parentOfTarget.getId());
			
			
			//add the toBeDropped to the target's parent's internal vector according to the position.
		    parentOfTarget.addComponentAt((AbstMovableComponent)toBeDropped, parentOfTarget.getIndexOfComponentInBlocksCollection(target) + 1);


		}

	}
	
	public void dropBefore(AbstScratchComponent toBeDropped, AbstScratchComponent target) {
	
		int currentDropX = target.getX();
		int currentDropY = target.getY();
				
		robot.mouseMove(currentDropX , currentDropY);
		robotMouseRelease();
        
		//If the target is not a inner component do the following
		if(!isInnerComponent(target)) {
			ScriptsState scriptsState = AppState.getCurrentScriptsState();
			//TODO: Error handling IF toBeDropped is a BlockContainer do not allow it to be dropped in between.
			// find if target is the first element if so insert toBeDroppped at the beginning of the vector Else insert in between
			if(!scriptsState.getMovableComponents().firstElement().equals(target)) {
				//Move components below downward
				this.moveDownComponentsBelow(scriptsState.getMovableComponents().indexOf(target), scriptsState.getMovableComponents().size(), toBeDropped.getHeight());
				toBeDropped.setX(currentDropX);
				toBeDropped.setY(currentDropY);
				
				//move the current drop point. Since the component is being dropped in between components will move below
			    this.moveCurrentDropPoint(toBeDropped.getHeight());

			
			}
		
			else { // It is being dropped at the beginning hence it will move up.
				toBeDropped.setX(currentDropX);
				toBeDropped.setY(currentDropY - toBeDropped.getHeight());
			}
		
			// add the component at the position of target
			this.addComponentToCollectionAt(toBeDropped, scriptsState.getMovableComponents().indexOf(target));
			
        
		}
		
		//If target is not a child insert toBeDropped into the movableComponentVector else insert into vector of the parent.
		//Execute below if target is a inner component
 		else {
 			ScriptsState scriptsState = AppState.getCurrentScriptsState();
 			//get the position of target in its parent.
 			//in any case call the moving down logic
 			this.moveComponentsBelowInnerComponent((AbstMovableComponent)target,toBeDropped.getHeight(),true);
 			
 			//get the super parent of target
 			AbstScratchComponent superParent = this.getSuperParent(target);
 			//move the components below the super parent below
 			this.moveDownComponentsBelow(scriptsState.getMovableComponents().indexOf(superParent) + 1, scriptsState.getMovableComponents().size(), toBeDropped.getHeight());
 			
 			toBeDropped.setX(currentDropX);
			toBeDropped.setY(currentDropY);
			
			AbstScratchComponent parentOfTarget = scriptsState.getComponents().get(target.getParentID());
			toBeDropped.setParentID(parentOfTarget.getId());
			
			
			//add the toBeDropped to the target's parent's internal vector according to the position.
		    parentOfTarget.addComponentAt((AbstMovableComponent)toBeDropped, parentOfTarget.getIndexOfComponentInBlocksCollection(target));

		    // move the current drop point by components height always.
		    this.moveCurrentDropPoint(toBeDropped.getHeight());
		}
        

	}
	
	public void dropIn(AbstScratchComponent toBeDropped, AbstScratchComponent target) {
		
		AbstScratchComponent outerComponent = null;
		
		int childrenHeightOftarget = target.getChildrenHeight();
		int moveDownBy;
		
		int targetLocationX = target.getX();
		int targetLocationY = target.getY();
		
		int currentDropX =  targetLocationX + BlockContainer.WIDTH_BEFORE_INSERT_AREA;
		int currentDropY =  targetLocationY + target.getInitialInsertHeight() + childrenHeightOftarget ;
		
		robot.mouseMove(currentDropX , currentDropY);
		robotMouseRelease();
		
		toBeDropped.setX(currentDropX);
		toBeDropped.setY(currentDropY);
		
		
		
		//set the current drop point 	
		if(childrenHeightOftarget == 0) {
			moveDownBy = toBeDropped.getHeight() - BlockContainer.INSERT_AREA_HEIGHT;
			//this.moveCurrentDropPoint(toBeDropped.getHeight() - BlockContainer.INSERT_AREA_HEIGHT);		
		}
		else {
			moveDownBy = toBeDropped.getHeight();
			//this.moveCurrentDropPoint(toBeDropped.getHeight());
		}
		this.moveCurrentDropPoint(moveDownBy);
		
		//add to the vector
		target.addComponent((AbstMovableComponent) toBeDropped);
		
		//set parentID of the toBeDropped to target's ID.
		toBeDropped.setParentID(target.getId());
		
		// find if target is the last element in vector. Else move the components as needed.
		
		//Find if target is an inner Element
		if(isInnerComponent(target)) {
			//move the innerComponents down
			this.moveComponentsBelowInnerComponent((AbstMovableComponent)target, moveDownBy, false);
			
			//find the super parents object
			outerComponent = this.getSuperParent(target);
		}
		
		else {
			
			outerComponent = target;
		}
		
		ScriptsState scriptsState = AppState.getCurrentScriptsState();
		if(!scriptsState.getMovableComponents().lastElement().equals(outerComponent)) {
			//move the components below target downward
			this.moveDownComponentsBelow(scriptsState.getMovableComponents().indexOf(outerComponent) + 1, scriptsState.getMovableComponents().size(), moveDownBy);
		}	
	}
	
	public void setParameterForComponents(AbstMovableComponent ipTargetcomp, int parameterNumber) {
		int moveDownHeight = 8;
		editedComponent = ipTargetcomp;
		parameterNumber = parameterNumber;
		if(parameterNumber == 1) {
			robot.mouseMove(ipTargetcomp.getX() + ipTargetcomp.getParameterLength1(), ipTargetcomp.getY() + moveDownHeight);
		}
		else if(parameterNumber== 2) {
			robot.mouseMove(ipTargetcomp.getX() + ipTargetcomp.getParameterLength2(), ipTargetcomp.getY() + moveDownHeight);
		}
		else if(parameterNumber == 3) {		
			robot.mouseMove(ipTargetcomp.getX() + ipTargetcomp.getParameterLength3(), ipTargetcomp.getY() + moveDownHeight);
		}
		robotMousePress();
		robotMouseRelease();	
	}
	
	//Allows user to enter numbers or letters, one at a time
	public void editParameter(int value, String val) {
		robot.keyPress(KeyEvent.VK_DELETE);
		System.out.println("after delete");
		robot.keyPress(value);
		robot.delay(50);
		robot.keyRelease(value);
		temp = temp + val;
		int numParams = editedComponent.getNumParams();
		if(temp.length() > 1) {
			if(numParams > 2) {
				//need a method "done" for entering parameter values otherwise, adjust one character at a time
				if(parameterNumber == 1) {
					editedComponent.addParameterLength2(8);
					editedComponent.addParameterLength3(8);
				}
				if(parameterNumber == 2)
					editedComponent.addParameterLength3(8);
			} else if(numParams > 1) {
				//need a method "done" for entering parameter values otherwise, adjust one character at a time
				if(parameterNumber == 1) 
					editedComponent.addParameterLength2(8);
			}
		}
	}
	
	public void pause(boolean pause) {
		//Display a message dialog to the user indicating that the voice recognition has been paused, click or say Unpause to continue		
		//Create button text
		final Object [] options = {"Resume"};
		Thread pauseThread = new Thread() {
		@Override
		public void run() {
		int n = JOptionPane.showOptionDialog(dialogRoot, "Voice recognition has been paused. Click or say 'Resume' to continue.", "Pause",
			    JOptionPane.OK_OPTION,	//button option
			    JOptionPane.INFORMATION_MESSAGE,  //icon
			    null,     //do not use a custom Icon
			    options,  //the titles of buttons
				options[0]); //default button title
			}
		};
		
		//Determine if paused or not
		if(pause)
		{
			//Display jframe and start thread
			pauseThread.start();
			dialogRoot.setModalExclusionType(Dialog.ModalExclusionType.NO_EXCLUDE);
		}
		else
		{
			//Interrupt thread
			pauseThread.interrupt();

			//Close jframe
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.delay(50);
			robot.keyRelease(KeyEvent.VK_ENTER);
			dialogRoot.dispose();
		}
	}	
	
	


	public void scroll(AbstScratchComponent component) {
		//Check if scrollbar is already at the desired edge
		
		//Click appropriate scrollbar
		robot.mouseMove(component.getX(), component.getY());   
		robotMousePress();
		robotMouseRelease();
		
		//TODO: adjust the rest of that window....I'll need to get the command (location and direction) to update
		//the items in the window
		//also can't execute the scroll if it's already at the edge
	}
	
	public void delete(AbstScratchComponent toBeDeleted)
    {
		int pos;
		int currentX = toBeDeleted.getX();
		int currentY = toBeDeleted.getY();
					
		
		//if(!isInnerComponent(toBeDeleted)) {

			ScriptsState scriptsState = AppState.getCurrentScriptsState();
			//Determine if toBeDeleted is the last element, if so remove toBeDeleted from the end of the vector, else remove from between
				if(!scriptsState.getMovableComponents().lastElement().equals(toBeDeleted)) {
					//Delete in between
					
					//Separate blocks, Get location of block to move back
					pos = scriptsState.getMovableComponents().indexOf(toBeDeleted) + 1;
					int newX = scriptsState.getMovableComponents().elementAt(pos).getX();
					int newY = scriptsState.getMovableComponents().elementAt(pos).getY();
					robot.mouseMove(newX, newY);
					robotMousePress();
					robot.mouseMove(newX + 10, newY + 50);
					robotMouseRelease();
					
					//Move mouse to block to be deleted
					robot.mouseMove(currentX, currentY);
					robotMouseRelease();
					
					//Drag to side
					robotMousePress();
					robot.mouseMove(25, 500);
					robotMouseRelease();
					
					//Get location of block to move back
					newX = scriptsState.getMovableComponents().elementAt(pos).getX();
					newY = scriptsState.getMovableComponents().elementAt(pos).getY();
					
					//Reconnect blocks
					robot.mouseMove(newX + 10, newY + 50);
					robotMousePress();
					robot.mouseMove(currentX, currentY);
					robotMouseRelease();
					
		            //Remove from vector and update number labels
		            this.removeComponentAt(toBeDeleted, pos - 1);
		            
		            //Pull up labels
		            this.moveDownComponentsBelow(pos-1, scriptsState.getMovableComponents().size(), toBeDeleted.getHeight()*-1);
					
					//Change drop point height
		            int blockHeight = toBeDeleted.getHeight() * -1;
		            this.moveCurrentDropPoint(blockHeight);
				}
				else {
					//Delete at the end
					
					pos = scriptsState.getMovableComponents().indexOf(toBeDeleted);
					
					//Move mouse to block to be deleted
					robot.mouseMove(currentX, currentY);
					robotMouseRelease();
					
					//Drag to side
		            robotMousePress();
		            robot.mouseMove(25, 500);
		            robotMouseRelease();
		            
		            //Remove from vector and update numbers
		            this.removeComponentAt(toBeDeleted, pos);
		            
		            //Change drop point height
		            int blockHeight = toBeDeleted.getHeight() * -1;
		            this.moveCurrentDropPoint(blockHeight);
				}

        //TODO: innerComponent
        //TODO: fix labels for middle block
    }
	
	private void moveCurrentDropPoint(int componentHeight) {
		ScriptsState scriptsState = AppState.getCurrentScriptsState();
		int currentDropX = scriptsState.getDropX() ;
		int currentDropY = scriptsState.getDropY() + componentHeight;
				
		scriptsState.setDropX(currentDropX);
		scriptsState.setDropY(currentDropY);
	}
	
	private void moveDownComponentsBelow(int startIndex, int endIndex, int ipMoveBy) {
		List<AbstScratchComponent> subList = AppState.getCurrentScriptsState().getMovableComponents().subList(startIndex, endIndex);
		for(AbstScratchComponent temp : subList ) {
			temp.moveDown(ipMoveBy);
		}
	}
	
	/*
	 * @param: comp takes an abstract ScratchComponent and finds if it is an inner component
	 * */
	private boolean isInnerComponent(AbstScratchComponent comp) {
		if(comp.getParentID() != 0) {
			return true;
		}
		else { 
			return false;
		}
	}
	
	/*
	 * @param: ipChildID takes components  as input and finds the super parent for it.
	 * */
	private AbstScratchComponent getSuperParent(AbstScratchComponent ipChild) {
		AbstScratchComponent tempComponent = ipChild;
		while(tempComponent.getParentID() != 0) {
			tempComponent = AppState.getCurrentScriptsState().getComponents().get(tempComponent.getParentID());
		}
		return tempComponent;
	}
	
	private void moveComponentsBelowInnerComponent(AbstMovableComponent targetComponent, int ipHeightOfTobeDropped, boolean moveTargetComponentFirstTime) {
		AbstMovableComponent temp = targetComponent;
		
		while(temp.getParentID() != 0) {
		//get components after the ipComp in ipComp's parent
			
			ScriptsState scriptsState = AppState.getCurrentScriptsState();
			
			AbstMovableComponent targetParent = (AbstMovableComponent) scriptsState.getComponents().get(temp.getParentID());
			
			List<AbstMovableComponent> componentsAfter;
			
			if(moveTargetComponentFirstTime) {
			 componentsAfter = targetParent.getSubListOfBlocksCollection(targetParent.getIndexOfComponentInBlocksCollection(temp), targetParent.getSizeOfBlocksCollection());
			 moveTargetComponentFirstTime = false;
			}
			else {
				 componentsAfter = targetParent.getSubListOfBlocksCollection(targetParent.getIndexOfComponentInBlocksCollection(temp)+ 1, targetParent.getSizeOfBlocksCollection());

			}
			
         	for(AbstMovableComponent tempComponent : componentsAfter) {
         		tempComponent.moveDown(ipHeightOfTobeDropped);
         	}
         	
         	temp = (AbstMovableComponent) scriptsState.getComponents().get(temp.getParentID());			
		}
		//After this call method moveDownComponentsBelow to move superComponents sibling below.
		
	}
	
    private void addComponentToCollectionAt(AbstScratchComponent ipComp, int ipPosition) {
    	AppState.getCurrentScriptsState().getMovableComponents().insertElementAt(ipComp, ipPosition);
    }
		
    private void removeComponentAt(AbstScratchComponent ipComp, int ipPosition) {
    	AppState.getCurrentScriptsState().getMovableComponents().removeElementAt(ipPosition);
    	AppState.getCurrentScriptsState().getComponents().remove((Integer)ipPosition + 1);
    }
    
	private void robotMousePress() {
		robot.mousePress(InputEvent.BUTTON1_MASK);
	}
	
	private void robotMouseRelease() {
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
	public void displayError(String phrase) {
		//Display a message dialog to the user indicating that there is an error	
		//Create button text
		final Object [] options = {"OK"};
		final String statement = phrase + "Myna must be restarted. Press OK to continue.";

		int n = JOptionPane.showOptionDialog(dialogRoot, statement, "Error",
			    JOptionPane.OK_OPTION,	//button option
			    JOptionPane.ERROR_MESSAGE,  //icon
			    null,     //do not use a custom Icon
			    options,  //the titles of buttons
				options[0]); //default button title
	}	
	
	public void display(String phrase, boolean okay) {
		//Display a message dialog to the user indicating that the voice recognition has begun		
		//Create button text
		final Object [] options = {"OK"};
		final String statement = phrase + " Say 'Help' at any time for a list of vocal commands. Click or say 'OK' to continue.";
		Thread displayThread = new Thread() {
		@Override
		public void run() {
		int n = JOptionPane.showOptionDialog(dialogRoot, statement, "Information",
			    JOptionPane.OK_OPTION,	//button option
			    JOptionPane.INFORMATION_MESSAGE,  //icon
			    null,     //do not use a custom Icon
			    options,  //the titles of buttons
				options[0]); //default button title
			}
		};
		
		//Determine if paused or not
		if(okay)
		{
			//Display jframe and start thread
			displayThread.start();
			dialogRoot.setModalExclusionType(Dialog.ModalExclusionType.NO_EXCLUDE);
		}
		else
		{
			//Interrupt thread
			displayThread.interrupt();

			//Close jframe
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.delay(50);
			robot.keyRelease(KeyEvent.VK_ENTER);
			dialogRoot.dispose();
		}
	}
	
	public void displayHelp(boolean okay) {
		//Display a message dialog to the user providing a list and description of vocal commands		
		//Create button text
		final Object [] options = {"Done"};
		final String statement = "Navigational Commands:" + "\n" + 
		"1. First, go to the menu of your choice such as 'Motion' or 'Control'. All programs should begin with a control element." + "\n" +
		"2. 'Drag and Drop ____' - Drags the element into the editor region and drops it. This statement " +
				"should be followed by one of the components such as 'move steps'." + "\n" +  
		"3. 'Drag ____' - Drags the element. This statement should be followed by one of the components such as 'go to'." + "\n" + 
		"4. 'Drop after ___' - Drops the dragged element (#3) after a given number of an element on the screen. For example, " +
		"'Drag move steps, drop after two'." + "\n" + 
		"5. 'Drop before ___' - Works the same as #4 but drops it before the given element." + "\n" + 
		"6. 'Drop in ____' - Works the same as #4 and #5 but drops it inside a given element such as inside an if or a for loop." + "\n" + 
		"7. 'Play' - Executes the program if the program begins with 'When clicked'." + "\n" + 
		"8. 'Set property ___ at ___' - Sets the property value (1, 2, or 3) of the specified element. For example, if move steps is " +
		"the second block, to change the parameter, say, 'Set property one at two'." + "\n" + 
		"9. 'Number ___' - Types the stated number, for example, 'Number two' will type the number 2. For multiple numbers, " +
		"such as 23, say, 'Number two, Number three'." + "\n" +
		"10. 'Pause' - Will pause the voice recognition system." + "\n" + 
		"11. 'Resume' - Will resume the voice recognition system." + "\n" + "\n" + 
		"Click or say 'Done' to continue.";
		Thread displayThread = new Thread() {
		@Override
		public void run() {
		int n = JOptionPane.showOptionDialog(dialogRoot, statement, "Help",
			    JOptionPane.OK_OPTION,	//button option
			    JOptionPane.INFORMATION_MESSAGE,  //icon
			    null,     //do not use a custom Icon
			    options,  //the titles of buttons
				options[0]); //default button title
			}
		};
		
		//Determine if paused or not
		if(okay)
		{
			//Display jframe and start thread
			displayThread.start();
			dialogRoot.setModalExclusionType(Dialog.ModalExclusionType.NO_EXCLUDE);
		}
		else
		{
			//Interrupt thread
			displayThread.interrupt();

			//Close jframe
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.delay(50);
			robot.keyRelease(KeyEvent.VK_ENTER);
			dialogRoot.dispose();
		}
	}
}
