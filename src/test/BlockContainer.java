package test;

public class BlockContainer extends MovableComponent{

	private int y;
	public BlockContainer(int ipX, int ipY) {
		super(ipX);
		this.y = ipY;
	}
	
	public int getX() {
		return this.y;
	}
	
	public int getY() {
		return this.y;
	}
	
	public void setY(int ipY) {
		this.y = ipY;
	}
	
}
