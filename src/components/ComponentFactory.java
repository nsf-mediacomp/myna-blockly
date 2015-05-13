/**
 * 
 */
package components;



/**
 * 
 *
 */
public interface ComponentFactory {
	
 public AbstScratchComponent deliverObjects(String componentName);
 
 public abstract ComponentFactory getInstance();
 
}
