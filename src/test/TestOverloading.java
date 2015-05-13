package test;

public class TestOverloading {

	public TestOverloading() {
		
	}
	public void methodForIScratchComp(IScratchComponent isc) {
		System.out.println("Hello from methodForIScratchComp");
	}
	
	public void methodForIScratchComp(BlockContainer isc) {
		System.out.println("Hello from methodForBlockContainer");

	}
	
	/*public void methodForBlockContainer(BlockContainer bc) {
		System.out.println("Hello from methodForBlockContainer");

	}*/
}
