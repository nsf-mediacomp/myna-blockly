package test;


public class TestDriver {
 
	public static void main(String args[]) {
		
		
		
		IScratchComponent bc = new BlockContainer(1,1);
		
		TestOverloading tovl = new TestOverloading();
		//tovl.methodForIScratchComp(ibc);
		//tovl.methodForBlockContainer(bc);
	
	}	
		/*IScratchComponent iSC = new BlockContainer(1, 1);
		
		System.out.println( iSC.getX() );
		System.out.println(iSC.getY());
		
		iSC.setX(2);
		iSC.setY(2);
	
		System.out.println( iSC.getX() );
		System.out.println(iSC.getY());*/
		
		/*TEST COMMAND PARSER AND OBJECT REPOSITORY
		 * 
		CommandParser cp = new CommandParser();
		String[] parsedCommand = cp.parseCommand("drop in 400");
		
		for(int i=0;i<parsedCommand.length;i++) {
			System.out.println("element "+i +": "+parsedCommand[i]);
		}
		
		ObjectRepository objrepInstance = ObjectRepository.getInstance();
		
        if(parsedCommand.length == 1) {	
			System.out.println(parsedCommand[0]);
		}
		else if(parsedCommand.length ==2) {
			System.out.println(parsedCommand[0] + ":" + parsedCommand[1]);

		}
        
        */
		
		
		
		/*ObjectRepository instance = ObjectRepository.getInstance();
		
		AbstScratchComponent test = instance.deliverObjects("control");
	    
		AbstScratchComponent test2 = instance.deliverObjects("control");
		
		System.out.println(test.getName()+":"+test2.getName());*/
		
		//cp.findIfActionCommandExists();
		
		/*String s = "click file";
		int i = s.indexOf("click");
		String cmd = s.substring(i, i+"click".length());
		System.out.println(i + cmd);*/
		
		/*TEST VECTOR TO STRING[]
		 * 
		 * Vector<String> tempCollection = new Vector<String>();
		tempCollection.add("one");
		tempCollection.add("two");
		
		String[] ss = (String[])tempCollection.toArray(new String[tempCollection.size()]);*/
		
		/*AbstScratchComponent test = new Block("test", "test", new Point(1,1), "test", true, 10, 10);
		
		System.out.println(test.getInitialLocation());
		System.out.println(test.getCurrentLocation());
	    test.setCurrentLocation(new Point(2,2));
	    System.out.println(test.getCurrentLocation());*/
    
	
	
	//Test Method of object Repository
		
}
