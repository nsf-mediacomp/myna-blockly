package command;


public interface CommandParser {
	/*Declaring action command constants
	 *Improvement: Get these action commands from the grammar
	 */
	public static final String[] ACTION_COMMANDS = {"click", "right click", "reach", "move", "drag", "drag and drop", "drop after",
		"drop before", "drop in", "edit one at", "edit two at", "edit three at", "pause", "resume", "number", "letter", 
		"scroll", "delete", "okay", "help", "done"};
	
	public String[] parseCommand(String command);
}
