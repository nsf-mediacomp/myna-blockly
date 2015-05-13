package dynamicGrammar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import scratchState.AppState;
import scratchState.ScriptsState;
import util.Stringifier;
import config.Config;
import edu.cmu.sphinx.jsgf.JSGFGrammar;
import edu.cmu.sphinx.jsgf.JSGFGrammarException;
import edu.cmu.sphinx.jsgf.JSGFGrammarParseException;
import edu.cmu.sphinx.jsgf.JSGFRuleGrammar;
import edu.cmu.sphinx.jsgf.rule.JSGFRule;
import edu.cmu.sphinx.jsgf.rule.JSGFRuleAlternatives;
import edu.cmu.sphinx.jsgf.rule.JSGFRuleToken;
import edu.cmu.sphinx.util.props.ConfigurationManager;

public class DynamicGrammarEngine {

	private static DynamicGrammarEngine instance = null;
	private DynamicGrammarEngine() {
		
	}
	
	public static DynamicGrammarEngine getInstance() {
		if(instance == null) {
			instance = new DynamicGrammarEngine();
		}
		return instance;
	}
		
	public void generateDynamicCommand(int ipId) {
		
		ConfigurationManager cm = Config.getManager();
		
		JSGFGrammar grammar = (JSGFGrammar) cm.lookup("mynaGrammar");
		JSGFRuleGrammar ruleGrammar = grammar.getRuleGrammar();
		
		String idAsString = Stringifier.nameOfNumber(ipId);
		JSGFRule componentRule = ruleGrammar.getRule("components");
		    
		JSGFRule newRule = new JSGFRuleToken(idAsString);
		
		JSGFRuleAlternatives ra = new JSGFRuleAlternatives(new LinkedList<JSGFRule>());
		
		if(componentRule != null) {
			ra.append(componentRule);
			ra.append(newRule);
	        ruleGrammar.setRule("components", ra, true);
		} else {
	        ruleGrammar.setRule("components", newRule, true);
		}
        
        try {
			grammar.commitChanges();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSGFGrammarParseException e) {
			e.printStackTrace();
		} catch (JSGFGrammarException e) {
			e.printStackTrace();
		}
			  
		  
	}
	
	public void cleanUpGrammar() {
		
		ConfigurationManager cm = Config.getManager();
		
		JSGFGrammar grammar = (JSGFGrammar) cm.lookup("mynaGrammar");
		JSGFRuleGrammar ruleGrammar = grammar.getRuleGrammar();
		
		JSGFRuleToken dynamicRule = new JSGFRuleToken("default");
		ruleGrammar.setRule("components", dynamicRule, true);
		
		try {
			grammar.commitChanges();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSGFGrammarParseException e) {
			e.printStackTrace();
		} catch (JSGFGrammarException e) {
			e.printStackTrace();
		}
		  
		System.out.println(ruleGrammar.getRule("components"));
	}
	
	public void reloadGrammar() {
		
		ConfigurationManager cm = Config.getManager();
		
		JSGFGrammar grammar = (JSGFGrammar) cm.lookup("mynaGrammar");
		JSGFRuleGrammar ruleGrammar = grammar.getRuleGrammar();
		
		    
		  if(AppState.getCurrentScriptsState().getComponents().size() > 0) {
            
			  Set<Integer> keys = AppState.getCurrentScriptsState().getComponents().keySet();
			  
              String[] componentIdArray = intSetToStringArray(keys);
              
              List<JSGFRule> l = new LinkedList<JSGFRule>();
              for (String s : componentIdArray) {
            	  l.add(new JSGFRuleToken(s));
              }
 			  
			  JSGFRule dynamicRule = new JSGFRuleAlternatives(l);
			  
			  ruleGrammar.setRule("components", dynamicRule, true);

			  // apply the changes
			  
			  try {
				grammar.commitChanges();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSGFGrammarParseException e) {
				e.printStackTrace();
			} catch (JSGFGrammarException e) {
				e.printStackTrace();
			}
			  
			  System.out.println(ruleGrammar.getRule("components"));
			  
		}
	}
	
	
	public Boolean isCommandInCompIDArray(String ipCommand) {
		
		Set<Integer> keys = AppState.getCurrentScriptsState().getComponents().keySet();
        ArrayList<Integer> integerList = enumToArrayList(keys);
        ArrayList<String> stringList = intArrayListToString(integerList);
		
		for(String temp:stringList) {
			if(temp.equals(ipCommand)) {
				return true;
			}	
		}
		return false;
		
	}
	
	private String[] intSetToStringArray(Set<Integer> ints) {
		ArrayList<Integer> integerCollection = this.enumToArrayList(ints);
		ArrayList<String> stringCollection = this.intArrayListToString(integerCollection);
		String[] opArray = this.arrayListToStringArray(stringCollection);
		return opArray;
	}
	
	private ArrayList<Integer> enumToArrayList(Set<Integer> ints) {
		ArrayList<Integer> opIDCollection = new ArrayList<Integer>(ints);
        return opIDCollection;
	}
	private ArrayList<String> intArrayListToString(ArrayList<Integer> ipIntArrayList) {
		ArrayList<String> opStringArrayList = new ArrayList<String>();
		for(int temp: ipIntArrayList) {
			opStringArrayList.add(Stringifier.nameOfNumber(temp));
		}
		return opStringArrayList;
	}
	
	private String[] arrayListToStringArray(ArrayList<String> ipList) {
		  String[] opArray = (String[])ipList.toArray(new String[ipList.size()]);
		  return opArray;
	}
	
}
