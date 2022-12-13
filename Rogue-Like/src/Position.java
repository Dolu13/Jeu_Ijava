
public class Position implements Comparable<Position> {
	private int x;
	private int y;
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() { return this.x; }
	public int getY() { return this.y; }
	public void setX(int x) { this.x = x; }
	public void setY(int y) { this.y = y; }

	public int compareTo(Position p) {
		int result;
		if (this.getY() != p.getY()) {
			result = this.getY()-p.getY();
		} else {
			result = this.getX()-p.getX();
		}
		return result;
	}
	
	public String toString() { return "("+this.getX()+":"+this.getY()+")"; }
	
	public boolean equals(Position p) {
		if (p == null) { return false; }
		if (this == p) { return true; }
		if (this.getX() != p.getX() || this.getY() != p.getY()) { return false; }
		return true;
	}
}
