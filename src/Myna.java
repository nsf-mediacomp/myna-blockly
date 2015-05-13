

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import persistance.Persistance;
import scratchState.AppState;
import transparentFrames.TransparentFrame;
import util.StringToKeyCode;
import util.Stringifier;
import command.CommandExecutor;
import command.CommandParser;
import command.ConcreteCommandParser;
import components.AbstScratchComponent;
import components.AbstMovableComponent;
import components.ConcreteComponentFactory;
import config.Config;
import dynamicGrammar.DynamicGrammarEngine;
import edu.cmu.sphinx.decoder.ResultListener;
import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;
import edu.cmu.sphinx.util.props.PropertyException;
import edu.cmu.sphinx.util.props.PropertySheet;
import edu.cmu.sphinx.linguist.Linguist;

public class Myna implements ResultListener {

	/**
	 * This is the driver class for this application
	 */

	static AbstScratchComponent draggedComponent = null;

	static int invokeCounter = 0;

	static int tempCounter = 2;

	static ArrayList<TransparentFrame> transparentFrames = new ArrayList<TransparentFrame>();
	
	static ConfigurationManager cm = Config.getManager();
	
	public static boolean paused = false;
	
	public static void main(String[] args) {
		try {
			// RecognizerModeDesc desc = new RecognizerModeDesc(Locale.ENGLISH,
			// Boolean.TRUE);
			// EngineList list = Central.availableRecognizers(desc);
			
			Recognizer mynaRecognizer = (Recognizer) cm.lookup("recognizer");
			CommandExecutor executor = CommandExecutor.getInstance();
			
			// We have to redirect stderr to a dummy while Sphinx initializes because it sets the log level too late
			PrintStream stderr = System.err;
			System.setErr(new PrintStream(new OutputStream() {
				@Override
				public void write(int b) throws IOException { }
			}));
			mynaRecognizer.allocate();
			System.setErr(stderr);

			Microphone microphone = (Microphone) cm.lookup("microphone");
			
			if (args.length == 0) {
				AppState.initialize();
				AppState.addSprite();
				AppState.selectSprite("sprite1");
			} else {
				File saveFile = new File(args[0]);
				Persistance.setFile(saveFile);
				if (saveFile.exists()) {
					Persistance.load();
					DynamicGrammarEngine.getInstance().reloadGrammar();
					refreshFrames();
				}
				else {
					AppState.initialize();
					AppState.addSprite();
					AppState.selectSprite("sprite1");
				}
			}

			mynaRecognizer.addResultListener(new Myna());
			
			if (!microphone.startRecording()) {
				//TODO: Need to display in pop-up
				executor.displayError("Cannot start microphone.");
				System.out.println("Cannot start microphone.");
				mynaRecognizer.deallocate();
				System.exit(1);
			}
			//TODO: Need to display dialog window that voice is activated
			executor.display("Myna has started.", true);
			System.out.println("ready");
			while(true) {
				try {
					//TODO: Need to display in pop-up
					System.out.println(mynaRecognizer.recognize().getBestFinalResultNoFiller());
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		
	}


	private void acceptCommand(String command) {
		
		CommandParser parser = new ConcreteCommandParser();
		String[] parsedCommand = parser.parseCommand(command);
		ConcreteComponentFactory singObjrepInstance = ConcreteComponentFactory.getInstance();
		DynamicGrammarEngine singDynamicGramInstance = DynamicGrammarEngine
				.getInstance();

		boolean isComponentNumber = false;

		boolean isSingleWord = false;
		boolean isDoubleWord = false;
		boolean isNumber = false;

		// Declare the object
		AbstScratchComponent component = null;
		
		if (parsedCommand.length == 1) {
			// TODO: Error handling -- if command is not in static component
			// properties say command not found
			isSingleWord = true;
			component = singObjrepInstance.deliverObjects(parsedCommand[0]);
		} else if (parsedCommand.length == 2) {
			isDoubleWord = true;
			if(parsedCommand[0].equals("number") || parsedCommand[0].equals("letter")) {
				performEdit(parsedCommand[1], parsedCommand[0]);
				isNumber = true;
			}
			if (singDynamicGramInstance
					.isCommandInCompIDArray(parsedCommand[1]) && !isNumber) {
				isComponentNumber = true;
				component = singObjrepInstance
						.deliverExistingObjectsByNumber(parsedCommand[1]);
			}

			if (!isComponentNumber && !isNumber) {
				component = singObjrepInstance.deliverObjects(parsedCommand[1]);
			}

		}

		if(!isNumber)
			performAction(component, parsedCommand[0], isSingleWord, isDoubleWord);

	}

	private void performAction(AbstScratchComponent component, String actionCommand, boolean isSingleWord, boolean isDoubleWord) {

		CommandExecutor executor = CommandExecutor.getInstance();
		if(actionCommand.equals("resume"))
			paused = false;
		if(!paused)
		{
			if (isSingleWord) {
				if (actionCommand.equals("random")) {
					// TODO: Extract this to a method -- also call initialize
					// grammar in case of switching between the Sprites.
					tempCounter++;
					AppState.addSprite();
					DynamicGrammarEngine.getInstance().cleanUpGrammar();
					// DynamicGrammarEngine.getInstance().reloadGrammar();

				} else if (actionCommand.equals("save")) {
					if (Persistance.getFile() != null)  {
						try {
							Persistance.save();
						} catch (IOException ioe) {
							ioe.printStackTrace();
						}
					} else {
						System.err.println("No save file specified");
					}
				} else if (actionCommand.equals("pause")) {
					paused = true;
					executor.pause(paused);
				} else if (actionCommand.equals("resume")) {
					paused = false;
					executor.pause(paused);
				} else if (actionCommand.equals("okay")) {
					executor.display("Myna has started.", false);
				} else if (actionCommand.equals("help")) {
					executor.displayHelp(true);
					
				} else if (actionCommand.equals("done")) {
					executor.displayHelp(false);
				} 
			if (component != null)
				executor.click(component.getInitialX(),
				component.getInitialY());
			} else if (isDoubleWord) {
				// TODO: Commands like click Motion will not work -- fix this
				if (actionCommand.equals("click")) {
					executor.click(component.getX(),
					component.getY());
				} else if (actionCommand.equals("scroll")) {
					System.out.println("scroll: " + component.toString());
					executor.scroll(component);
				} else if (actionCommand.equals("delete")) {
					executor.delete(component);
					//refreshFrames();
				}else if (actionCommand.equals("drag")) {
					draggedComponent = component;
					executor.drag(component);
				} else if (actionCommand.equals("drag and drop")) {
					executor.dragAndDrop(component);
				} else if (actionCommand.equals("drop after")
					|| actionCommand.equals("drop before")
					|| actionCommand.equals("drop in")) {

					if (actionCommand.equals("drop after")) {
						if (draggedComponent != null) {
							executor.dropAfter(draggedComponent, component);
						}
					} else if (actionCommand.equals("drop before")) {
						if (draggedComponent != null) {
							executor.dropBefore(draggedComponent, component);
						}
					} else if (actionCommand.equals("drop in")) {
						if (draggedComponent != null) {
							executor.dropIn(draggedComponent, component);
						}
					}
				} else if (actionCommand.contains("edit")) {
					if (actionCommand.contains("edit one at")) {
						executor.setParameterForComponents((AbstMovableComponent) component, 1);
					}
					if (actionCommand.contains("edit two at")) {
						executor.setParameterForComponents((AbstMovableComponent) component, 2);
					}
					if (actionCommand.contains("edit three at")) {
						executor.setParameterForComponents((AbstMovableComponent) component, 3);
					}	
					return;
				}
			
			}
			refreshFrames();
		}
	}

	public void performEdit(String value, String actionCommand) {
		CommandExecutor executor = CommandExecutor.getInstance();
		int val = 0;
		int keyValue = 0;
		if(actionCommand.equals("number")) {
			//pass string value to Stringifier class in util folder to convert to int value
			val = Stringifier.stringToInt(value);
			//pass int value to StringToKeyCode class in util folder to convert int to keyboard value
			keyValue = StringToKeyCode.getKeyCode(val);
		}
		else if (actionCommand.equals("letter")) {
			char letter = value.charAt(0);
			//pass char value to StringToKeyCode class in util folder to convert char to keyboard value
			keyValue = StringToKeyCode.getKeyCode(letter);
		}					
		executor.editParameter(keyValue, value);
	}
	
	@Override
	public void newProperties(PropertySheet arg0) throws PropertyException { }

	@Override
	public void newResult(Result r) {
		String userPhrase = r.getBestFinalResultNoFiller();
		if (userPhrase != null) {
			acceptCommand(userPhrase);
			invokeCounter++;
		}
	}
	
	public static void refreshFrames() {
		// TODO recycle frames rather than disposing/recreating
		Collection<AbstScratchComponent> components = AppState.getCurrentScriptsState().getComponents().values();

		for (int i = 0; i < transparentFrames.size(); i++) {
			transparentFrames.get(i).dispose();
		}
		transparentFrames.clear();
		// TransparentFrame countResetter = TransparentFrame.getInstance();

		for (AbstScratchComponent temp : components) {
			int displayX = temp.getX();
			int displayY = temp.getY();
			int length = temp.getLength();

			// Call Transparent Frames here
			TransparentFrame tfr = new TransparentFrame(temp.getId(), displayX + length + 30, displayY);
			tfr.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			tfr.setVisible(true);
			transparentFrames.add(tfr);
		}
	}
	

}
