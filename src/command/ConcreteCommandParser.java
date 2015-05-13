/**
 * 
 */
package command;

import java.util.Vector;


public class ConcreteCommandParser implements CommandParser {

	public ConcreteCommandParser() { }
	
	public String[] parseCommand(String command) {
		
		Vector<Integer> matchedIndexList = this.findMatches(command);
		
		//case 1 no matches found
		if (matchedIndexList.size() == 0) {
			String[] processedCommand = { command };
			return processedCommand;
		}
		else if (matchedIndexList.size() == 1) {
			int matchedIndex =  matchedIndexList.elementAt(0);
			String[] processedCommand = this.classifyCommands(ACTION_COMMANDS[matchedIndex], command);
			return processedCommand;
		}
		
		else { // (matchedIndexList.size() > 1)
			String bestMatch = this.filterMatches(matchedIndexList);
			String[] processedCommand = this.classifyCommands(bestMatch, command);
			return processedCommand;
		}
	}
	
	private Vector<Integer> findMatches(String command) {
		
		Vector<Integer> matchedCandidateList = new Vector<Integer>();
		
		for (int i = 0; i<ACTION_COMMANDS.length; i++)
			if (command.startsWith(ACTION_COMMANDS[i]))
				matchedCandidateList.add(i);
		return matchedCandidateList;
	}
	
	private String filterMatches(Vector<Integer> matchedCandidateList) {
        
		int  firstInList = matchedCandidateList.elementAt(0);
		int biggestMatch = ACTION_COMMANDS[firstInList].length();
	    
	    for (int i = 1; i < matchedCandidateList.size(); i++) {
	    	int currentIndex = matchedCandidateList.elementAt(i);
	    	if (ACTION_COMMANDS[currentIndex].length() > ACTION_COMMANDS[biggestMatch].length())
	    		biggestMatch = currentIndex;
	    }
	    	return ACTION_COMMANDS[biggestMatch];
	}
	
	private String[] classifyCommands(String filteredCommand , String command) {
		//ERROR WHEN USER SAYS JUST "DRAG AND DROP"   
		System.out.println("COMMAND PARSER: filtered command: "+ filteredCommand +": command: "+command);

		//Test if filteredCommand = command indicating a single word command
		if (filteredCommand.equals(command)) {
			String classifiedCommands[] = {filteredCommand};
			return classifiedCommands;
		}
		else {
			String actionCommand = command.substring(0,filteredCommand.length());
			String componentName = command.substring(filteredCommand.length() + 1, command.length());
		
			String classifiedCommands[] = {actionCommand, componentName};
			return classifiedCommands;
		}
	}
	
}
