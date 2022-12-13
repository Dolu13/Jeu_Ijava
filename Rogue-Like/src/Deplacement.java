
public class Deplacement implements Comparable<Deplacement> {
	private int x;
	private int y;
	private boolean canGo;
	private boolean canEat;
	
	public Deplacement(int x, int y, boolean canGo, boolean canEat) {
		this.x = x;
		this.y = y;
		this.canGo = canGo;
		this.canEat = canEat;
	}
	
	public int getX() { return this.x; }
	public int getY() { return this.y; }
	public boolean canGo() { return this.canGo; }
	public boolean canEat() { return this.canEat; }
	public void setX(int x) { this.x = x; }
	public void setY(int y) { this.y = y; }
	
	public String toString() { return this.x+" "+this.y; }

	public int compareTo(Deplacement p) {
		int result;
		if (this.getY() != p.getY()) {
			result = this.getY()-p.getY();
		} else {
			result = this.getX()-p.getX();
		}
		return result;
	}
}
