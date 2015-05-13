package test;

public abstract class MovableComponent implements IScratchComponent{
	
	private int x; 
	public MovableComponent(int ipX) {
		this.x = ipX;
	}
	public int getX() {
		return this.x;
	}
	public void setX(int ipX)
	{
		this.x = ipX;
	}
}
